/**
 * 
 */
package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This is the abstract form of the parser. it is a expected to be extended.
 * @author Alon Segal
 *
 */
public abstract class PropertyParser {
	
	protected Properties prop;

	/**
	 * @param name Name of the config file.
	 */
	public PropertyParser(String name) {
		this.prop = new Properties();
		try{
			//loading file content into data structure prop
			this.prop.load(new FileInputStream(name));
		}catch(IOException e){}
	}
	
	/**
	 * Parse the config file given.
	 */
	public abstract void parse();

}
