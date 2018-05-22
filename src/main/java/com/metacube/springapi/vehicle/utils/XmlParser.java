package com.metacube.springapi.vehicle.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.metacube.springapi.Exceptions.XmlParseFileException;
import com.metacube.springapi.vehicle.Vehicle;
import com.metacube.springapi.vehicle.enums.UseCaseName;
/**
 * A class to provide the facility of XML string parsing
 * @author Amit Sharma
 *
 */
@Service
public class XmlParser {
	
	/*Data that is to be gathered from the XML file*/
	int maintenanceGapToService;
	int maintenanceServiceDue;
	int maintenanceGapType;
	String vin;
	String messageId;
	String action;
	String sourceId;
	String trackingId;
	String useCaseName;
	Clob xml;
	@Autowired
	XmlAttributes xmlAttributes;
	
	/**
	 * Function that converts the XML file to the Vehicle object with required values
	 * @param xmlInput
	 * @return Vehicle object
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws SerialException
	 * @throws SQLException
	 * @throws XmlParseFileException 
	 */
	public Vehicle getXmlObject( String xmlInput ) throws ParserConfigurationException, SAXException, IOException, SerialException, SQLException, XmlParseFileException {
		NodeList listOfNodes;
		Properties properties = xmlAttributes.getProperty();
		xml = new SerialClob(xmlInput.toCharArray());
		Document doc;
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = docBuilder.parse(new ByteArrayInputStream(xmlInput.getBytes(Charset.forName(properties.getProperty("characterCoding")))));
		
		if(doc.getElementsByTagName(properties.getProperty("payLoad")) != null) {
			listOfNodes = doc.getElementsByTagName(properties.getProperty("payLoad"));
		} else {
			throw new XmlParseFileException("Invalid File Encoding");
		}
		
		if(listOfNodes.item(0) != null) {
			messageId = ((Element)listOfNodes.item(0)).getAttribute(properties.getProperty("messageId"));
			if(messageId == "") {
				throw new XmlParseFileException(properties.getProperty("messageId")+" not found");
			}
			
			action = ((Element)listOfNodes.item(0)).getAttribute(properties.getProperty("action"));
			if(action == "") {
				throw new XmlParseFileException(properties.getProperty("action")+" not found");
			}
			
			listOfNodes =((Element) listOfNodes.item(0)).getElementsByTagName(properties.getProperty("ns1TelediagnosisResponse"));
		} else {
			throw new XmlParseFileException(properties.getProperty("payLoad")+" not found");
		}
		
		if(listOfNodes.item(0) != null) {
			vin = ((Element)listOfNodes.item(0)).getAttribute(properties.getProperty("vin"));
			if(vin == "") {
				throw new XmlParseFileException(properties.getProperty("vin")+" not found");
			}
			sourceId = ((Element)listOfNodes.item(0)).getAttribute(properties.getProperty("sourceId"));
			if(sourceId == "") {
				throw new XmlParseFileException(properties.getProperty("sourceId")+" not found");
			}
			trackingId = ((Element)listOfNodes.item(0)).getAttribute(properties.getProperty("trackingId"));
			if(trackingId == "") {
				throw new XmlParseFileException(properties.getProperty("trackingId")+" not found");
			}
			useCaseName = getUseCaseName(((Element)listOfNodes.item(0)).getAttribute(properties.getProperty("useCaseName")));
			if(useCaseName == null) {
				throw new XmlParseFileException(properties.getProperty("useCaseName")+" not found/valid");
			}
			listOfNodes = ((Element)listOfNodes.item(0)).getElementsByTagName(properties.getProperty("eesItem"));
		} else {
			throw new XmlParseFileException(properties.getProperty("ns1TelediagnosisResponse")+" not found");
		}
		
		if(listOfNodes.item(0) != null) {
			boolean mgts = false;
			boolean msd = false;
			boolean mgt = false;
			for(int index = 0; index < listOfNodes.getLength(); index++) {
				if(((Element) listOfNodes.item(index)).getAttribute(properties.getProperty("eesType")).equals(properties.getProperty("maintenanceGapToService"))) {
					mgts = true;
					if((((Element)((Element) listOfNodes.item(index)).getElementsByTagName(properties.getProperty("eesIntegerValue")).item(0)) != null) && isNumber(((Element)((Element) listOfNodes.item(index)).getElementsByTagName(properties.getProperty("eesIntegerValue")).item(0)).getTextContent())) {
						maintenanceGapToService = Integer.parseInt(((Element)((Element) listOfNodes.item(index)).getElementsByTagName(properties.getProperty("eesIntegerValue")).item(0)).getTextContent());
					} else {
						throw new XmlParseFileException(properties.getProperty("eesIntegerValue")+" for "+properties.getProperty("maintenanceGapToService")+" not found");
					}
				}else if(((Element) listOfNodes.item(index)).getAttribute(properties.getProperty("eesType")).equals(properties.getProperty("maintenanceServiceDue"))) {
					msd = true;
					if((((Element)((Element) listOfNodes.item(index)).getElementsByTagName(properties.getProperty("eesIntegerValue")).item(0)) != null) && isNumber(((Element)((Element) listOfNodes.item(index)).getElementsByTagName(properties.getProperty("eesIntegerValue")).item(0)).getTextContent())) {
						maintenanceServiceDue = Integer.parseInt( ((Element)((Element) listOfNodes.item(index)).getElementsByTagName(properties.getProperty("eesIntegerValue")).item(0)).getTextContent());
					} else {
						throw new XmlParseFileException(properties.getProperty("eesIntegerValue")+" for "+properties.getProperty("maintenanceServiceDue")+" not found");
					}
				}else if(((Element) listOfNodes.item(index)).getAttribute(properties.getProperty("eesType")).equals(properties.getProperty("maintenanceGapType"))) {
					mgt = true;
					if((((Element)((Element) listOfNodes.item(index)).getElementsByTagName(properties.getProperty("eesIntegerValue")).item(0)) != null) && isNumber(((Element)((Element) listOfNodes.item(index)).getElementsByTagName(properties.getProperty("eesIntegerValue")).item(0)).getTextContent())) {
						maintenanceGapType = Integer.parseInt(((Element)((Element) listOfNodes.item(index)).getElementsByTagName(properties.getProperty("eesIntegerValue")).item(0)).getTextContent());
					} else {
						throw new XmlParseFileException(properties.getProperty("eesIntegerValue")+" for "+properties.getProperty("maintenanceGapType")+" not found");
					}
				}
			}
			if(!(mgts && msd && mgt)) {
				String missingProperties="";
				if(!mgts) {
					missingProperties += " "+properties.getProperty("maintenanceGapToService");
				}
				if(!mgt) {
					missingProperties += " "+properties.getProperty("maintenanceGapType");
				}
				if(!msd) {
					missingProperties += " "+properties.getProperty("maintenanceServiceDue");
				}
				throw new XmlParseFileException("The mentioned properties are missing " + missingProperties);
			}
		} else {
			throw new XmlParseFileException(properties.getProperty("eesItem")+" not found");
		}
		
		
		return new Vehicle(vin,messageId,action,sourceId,trackingId,
						   useCaseName,maintenanceGapToService,
						   maintenanceServiceDue,maintenanceGapType,xml);
	}
	
	private boolean isNumber(String text) {
		if(text.length() == 0) {
			return false;
		}
		for(char c: text.toCharArray()) {
			if(!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	/*To get appropriate UseCaseName enum*/
	private String getUseCaseName(String useCaseName) {
		switch(useCaseName) {
			case "STR" :
			case "PTR" :
			case "KTR" :
				return useCaseName;
			default:
				return null;
		}
	}
	
}
