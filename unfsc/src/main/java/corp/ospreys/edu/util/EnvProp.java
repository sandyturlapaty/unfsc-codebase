/**
 * 
 */
package corp.ospreys.edu.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author sandyturlapaty
 *
 */
public class EnvProp {
	
	
	private static Properties prop; 
	private static String propFile = "environment.properties";
	
	static {
		if (null == prop) {
			 prop = new Properties();
			 try {
				 prop.load(EnvProp.class.getClassLoader().getResourceAsStream(propFile));
				 System.out.println("Loaded Properties: "+propFile);
			} catch (IOException e) {
				System.out.println("Error loading properties file: "+propFile+"\t Error: "+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * reads the property file and returns the key value
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return prop.getProperty(key);
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<String> getAsList(String key, String separator) {
		return Arrays.asList(prop.getProperty(key).split(separator));
	}

}
