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
		SimStarter.confFile = args[0];
		SimStarter.logFile = args[1];
		ConfCreator conf = new ConfCreator();
		//conf.createConf();

		//setting up a static logger which tracks the whole system. Usage: Logger.log(message) from anywhere.
		LogHelper.LOG_FILE_NAME  = SimStarter.logFile;

		//creating an instance that parses the configuration file.
		MainParser mp = new MainParser(SimStarter.confFile);

		InitialThreadHandler initialThreadHandler = new InitialThreadHandler(mp);
	}
}