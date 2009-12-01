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

	/**
	 * @param fileName Log file that given as an argument.
	 */
	public LogHelper(String fileName) {
		LogHelper.LOG_FILE_NAME = fileName;
		this.logger = Logger.getLogger("MainLogger");
		//this.logger.setUseParentHandlers(false);

	    try {
	    	this.fh = new FileHandler(fileName, true);
	    	//this.fh.
	    	this.logger.addHandler(this.fh);
	    	this.logger.setLevel(Level.INFO);
	    	//A formatter that made to log a simple text
	        CustomFormatter formatter = new CustomFormatter();
	        this.fh.setFormatter(formatter);
	        // This block configure the logger with handler and formatter
	    	//this.logger.

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
		Date date = new Date();
		this.logger.info(message+" at "+DateFormat.getTimeInstance(
				DateFormat.MEDIUM).format(date));
	}

	/**
	 *
	 * @param message String to be logged
	 * @param withDate Indicates whether to put "at+TIME" to the message.
	 */
	public synchronized void log(String message, boolean withDate) {
		Date date = new Date();
		if (!withDate)
			this.logger.info(message);
		else
			this.logger.info(message+" at "+DateFormat.getTimeInstance(
					DateFormat.MEDIUM).format(date));
	}
}
