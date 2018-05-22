package com.metacube.springapi.vehicle.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.springframework.stereotype.Service;

/**
 * Class to read the properties from the file
 * @author Amit Sharma
 *
 */
@Service
public class XmlAttributes {

	/**
	 * It loads the properties and returns it
	 * @return
	 * @throws IOException
	 */
	public Properties getProperty() throws IOException{ 
		FileReader reader=new FileReader(getUrl().getPath());  
	    Properties p=new Properties();  
	    p.load(reader);
	    reader.close();
	    return p; 
	} 

	/**
	 * Function to read the file
	 * @return
	 */
	public URL getUrl() {
		return getClass().getResource("xmlAttributes.properties");
	}
}
