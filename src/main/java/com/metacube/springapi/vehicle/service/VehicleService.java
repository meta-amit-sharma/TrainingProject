package com.metacube.springapi.vehicle.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpStatus;
import org.xml.sax.SAXException;

import com.metacube.springapi.Exceptions.VehicleNotFoundException;
import com.metacube.springapi.Exceptions.XmlParseFileException;

public interface VehicleService {

	
	public HttpStatus addNewVehicleEntry( String xml) throws ParserConfigurationException, SAXException, IOException, SerialException, SQLException, XmlParseFileException;
	
	public List<Map<String, Integer>> getVehicle(String vin, String useCaseName, String sourceId) throws IOException, VehicleNotFoundException;
	
	public String getXmlContent(String vin, String useCaseName, String sourceId) throws SQLException;
	
}
