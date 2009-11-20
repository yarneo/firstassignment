/**
 * 
 */
package utils;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alon Segal
 *
 */
public class LogHelper {
	
	private static Logger logger;
	private static FileHandler fh;
	private static Date date;

	/**
	 * @param fileName Log file that given as an argument.
	 */
	public LogHelper(String fileName) {
		LogHelper.logger = Logger.getLogger("MainLogger");
		LogHelper.date = new Date();
	    
	    try {
	        // This block configure the logger with handler and formatter
	    	LogHelper.fh = new FileHandler(fileName, true);
	    	LogHelper.logger.addHandler(LogHelper.fh);
	    	LogHelper.logger.setLevel(Level.INFO);
	        //A formatter that made to log a simple text
	        CustomFormatter formatter = new CustomFormatter();
	        LogHelper.fh.setFormatter(formatter);
	        
	    } catch (SecurityException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	//Overloading the log function
	
	/**
	 * @param message String to be logged with "at+TIME"
	 */
	public static synchronized void log(String message) {
		LogHelper.logger.log(Level.INFO, message+" at "+DateFormat.getTimeInstance(
				DateFormat.MEDIUM).format(LogHelper.date));
	}
	
	/**
	 * 
	 * @param message String to be logged
	 * @param withDate Indicates whether to put "at+TIME" to the message.
	 */
	public static synchronized void log(String message, boolean withDate) {
		if (!withDate)
			LogHelper.logger.log(Level.INFO, message);
		else
			LogHelper.logger.log(Level.INFO, message+" at "+DateFormat.getTimeInstance(
					DateFormat.MEDIUM).format(LogHelper.date));
	}
}
