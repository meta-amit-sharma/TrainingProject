package com.metacube.springapi.vehicle.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.rowset.serial.SerialException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.metacube.springapi.Exceptions.VehicleNotFoundException;
import com.metacube.springapi.Exceptions.XmlParseFileException;
import com.metacube.springapi.vehicle.Vehicle;
import com.metacube.springapi.vehicle.enums.UseCaseName;
import com.metacube.springapi.vehicle.repository.VehicleRepository;
import com.metacube.springapi.vehicle.service.VehicleService;
import com.metacube.springapi.vehicle.utils.XmlAttributes;
import com.metacube.springapi.vehicle.utils.XmlParser;
/**
 * Service class providing the service to access the database
 * @author Amit Sharma
 *
 */
@Service
public class VehicleServiceImpl  implements VehicleService{

	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	XmlParser xmlParser;
	
	@Autowired
	XmlAttributes xmlAttributes;
	
	@Autowired
	JdbcTemplate jdbcTemplate ;
	
	/**
	 * Function to add a new entry in database
	 * @param xml
	 * @return 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws SerialException
	 * @throws SQLException
	 * @throws XmlParseFileException 
	 */
	public HttpStatus addNewVehicleEntry( String xml) throws ParserConfigurationException, SAXException, IOException, SerialException, SQLException, XmlParseFileException {
		return vehicleRepository.save(xmlParser.getXmlObject(xml));
	}
	
	/**
	 * Function to search for required entry in database
	 * @param vin
	 * @param useCaseName
	 * @param sourceId
	 * @return Object with required vehicle service details 
	 * @throws IOException 
	 * @throws VehicleNotFoundException 
	 */
	public List<Map<String, Integer>> getVehicle(String vin, String useCaseName, String sourceId) throws IOException, VehicleNotFoundException {
		Properties properties = xmlAttributes.getProperty();
		List<Vehicle> listOfVehicles = vehicleRepository.findAllByVinAndUseCaseNameAndSourceId(vin, useCaseName, sourceId);
		if(listOfVehicles.isEmpty()) {
			throw new VehicleNotFoundException("No details found with these entries");
		}
		List<Map<String, Integer>> requiredDetails = new ArrayList<>();
		Map<String, Integer> map;
		for(Vehicle v: listOfVehicles) {
			map = new HashMap<>();
			map.put(properties.getProperty("maintenanceGapToService"), v.getMaintenanceGapToService());
			map.put(properties.getProperty("maintenanceServiceDue"), v.getMaintenanceServiceDue());
			map.put(properties.getProperty("maintenanceGapType"), v.getMaintenanceGapType());
			requiredDetails.add(map);
		}
		return requiredDetails;
	}
	
	/**
	 * Function to get XML file of particular record
	 * @param vin
	 * @param useCaseName
	 * @param sourceId
	 * @return XML String
	 * @throws SQLException
	 */
	public String getXmlContent(String vin, String useCaseName, String sourceId) throws SQLException {
		List<Vehicle> listOfVehicles = vehicleRepository.findAllByVinAndUseCaseNameAndSourceId(vin, useCaseName, sourceId);
		String s="";
		/*For converting Clob data type to String type*/
		for(Vehicle v: listOfVehicles) {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner (v.getXml().getAsciiStream());
			while(scanner.hasNext()) {
				s+=scanner.nextLine();
			}
			s+="\r\n--------------------------------------------------\r\n";
		}
		return s;
	}
	
	/*To get appropriate UseCaseName enum*/
	private UseCaseName getUseCaseName(String useCaseName) {
		switch(useCaseName) {
			case "STR" :
				return UseCaseName.STR;
			case "PTR" :
				return UseCaseName.STR;
			case "KTR" :
				return UseCaseName.KTR;
			default:
				return null;
		}
	}
	
}
