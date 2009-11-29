/**
 * 
 */
package utils;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * @author Alon Segal
 *
 */
public class CustomFormatter extends SimpleFormatter {

	/**
	 * 
	 */
	public CustomFormatter() {
	}
	
	//Overides the format method to create a custom format desired in the project.
	/**
	 * Overrides the format method
	 * @param record LogRecord instance
	 * @return message to log
	 */
	@Override
	public String format(LogRecord record) {
		return record.getMessage()+"\r\n";
	}

}
