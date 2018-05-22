package com.metacube.springapi.vehicle.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.metacube.springapi.Exceptions.AttributeValidationException;
import com.metacube.springapi.Exceptions.VehicleNotFoundException;
import com.metacube.springapi.Exceptions.XmlParseFileException;
import com.metacube.springapi.vehicle.Vehicle;
import com.metacube.springapi.vehicle.service.VehicleService;


/**
 * Controller class for the app
 * @author Amit Sharma
 *
 */
@RestController
public class VehicleController {
	
	@Autowired
	VehicleService vehicleService;
	
	/**
	 * Function for inserting a new record in database
	 * @param xml
	 * @return 
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws SerialException 
	 * @throws XmlParseFileException 
	 * @throws AttributeValidationException 
	 * @throws ParserConfigurationException 
	 * @throws Exception 
	 */
	@RequestMapping(method=RequestMethod.POST, value="/data")
	public HttpStatus inputXML(@RequestBody String xml) throws SerialException, IOException, SQLException, XmlParseFileException, AttributeValidationException, ParserConfigurationException{
		try{
			
			return vehicleService.addNewVehicleEntry(xml);
			
		}catch (SAXParseException se) {
			throw new XmlParseFileException("Invalid File");
		} catch (SAXException e) {
			throw new XmlParseFileException("Invalid File");
		} catch (TransactionSystemException cvE) {
			throw new AttributeValidationException(cvE.getMostSpecificCause().getLocalizedMessage());
		}
	}
	
	/**
	 * Function to get the XML file of the existing record 
	 * when searched by the combination of Vin, UseCaseName and SourceId.
	 * @param vin
	 * @param useCaseName
	 * @param sourceId
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/getXML")
	public String getXml(@RequestHeader String vin,@RequestHeader String useCaseName,@RequestHeader String sourceId) throws SQLException {
		return vehicleService.getXmlContent(vin, useCaseName, sourceId);
	}
	
	
	/**
	 * Function to get the value of "Maintenance Gap to Service",
	 * "Maintenance service due" and "Maintenance gap type" of the existing record 
	 * when searched by the combination of Vin, UseCaseName and SourceId
	 * @param vin
	 * @param useCaseName
	 * @param sourceId
	 * @return Object with required information
	 * @throws SQLException
	 * @throws IOException 
	 * @throws VehicleNotFoundException 
	 */
	@RequestMapping("/get")
	public List<Map<String, Integer>> getVechileDetails(@RequestHeader String vin,@RequestHeader String useCaseName,@RequestHeader String sourceId) throws SQLException, IOException, VehicleNotFoundException {
		return vehicleService.getVehicle(vin, useCaseName, sourceId);
	}

}
