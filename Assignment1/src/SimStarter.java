import utils.InitialThreadHandler;
import utils.LogHelper;
import utils.MainParser;

public class SimStarter {

	private static String confFile;
	private static String logFile;


	/**
	 * Run and initiate the whole system.
	 * @param args main arguments
	 */
	public static void main(String[] args) {
		//TODO VERY IMPORTANT!!! Count the total projects.
		//getting the arguments
		confFile = args[0];
		logFile = args[1];

		//setting up a static logger which tracks the whole system. Usage: Logger.log(message) from anywhere.
		new LogHelper(logFile);

		//creating an instance that parses the configuration file.
		MainParser mp = new MainParser(confFile);

		//TODO Main - create managers and programmers.
		InitialThreadHandler initialThreadHandler = new InitialThreadHandler(mp);
	}
}