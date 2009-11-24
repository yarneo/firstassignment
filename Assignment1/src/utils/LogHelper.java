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
	
	public static String LOG_FILE_NAME;
	
	private Logger logger;
	private FileHandler fh;
	private Date date;

	/**
	 * @param fileName Log file that given as an argument.
	 */
	public LogHelper(String fileName) {
		LogHelper.LOG_FILE_NAME = fileName;
		this.logger = Logger.getLogger("MainLogger");
		this.date = new Date();
	    
	    try {
	        // This block configure the logger with handler and formatter
	    	this.fh = new FileHandler(fileName, true);
	    	this.logger.addHandler(this.fh);
	    	this.logger.setLevel(Level.INFO);
	        //A formatter that made to log a simple text
	        CustomFormatter formatter = new CustomFormatter();
	        this.fh.setFormatter(formatter);
	        
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
	public  synchronized void log(String message) {
		this.logger.log(Level.INFO, message+" at "+DateFormat.getTimeInstance(
				DateFormat.MEDIUM).format(this.date));
	}
	
	/**
	 * 
	 * @param message String to be logged
	 * @param withDate Indicates whether to put "at+TIME" to the message.
	 */
	public synchronized void log(String message, boolean withDate) {
		if (!withDate)
			this.logger.log(Level.INFO, message);
		else
			this.logger.log(Level.INFO, message+" at "+DateFormat.getTimeInstance(
					DateFormat.MEDIUM).format(this.date));
	}
}
