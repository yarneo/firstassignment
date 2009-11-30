/**
 *
 */
package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import resources.ProgrammerResourceHandler;
import resources.Resource;
import resources.ResourceImpl;
import actorobjects.ProgrammerObject;
import actors.Board;
import actors.BoardImpl;
import actors.Programmer;
import actors.ProgrammerInfo;
import actors.ProjectManager;

/**
 * Parse the main configuration file into a proper lists and vars.
 * @author Alon Segal
 *
 */
public class MainParser extends PropertyParser {
	
	private List<String> projectTypes;
	private List<String> resourcesTypes;
	private List<Programmer> programmers;
	private List<Resource> resources;
	private List<ProjectManager> projMangers;
	private List<ProgrammerInfo> programmersInfo;
	private ObserverInfoGatherer observerGatherer;
	
	private Board board;
	
	public static double SIMULATION_HOUR;

	/**
	 * @param name Name od file to be parsed.
	 */
	public MainParser(String name) {
		super(name);
		this.parse();
	}

	/**
	 * Parse the file into workable vars.
	 */
	private void parse() {
		this.parseProjectTypes();
		this.parseResources();
		this.createBoard();
		this.parseProgrammers();
		this.parseProjectMangers();
		this.parseSimulationHour();
	}

	// private functions

	private void parseProjectTypes() {
		this.projectTypes = Arrays.asList(prop.getProperty("projectTypes").split(","));
	}

	private void parseResources() {
		this.resources = new ArrayList<Resource>();
		this.resourcesTypes = Arrays.asList(prop.getProperty("resourceTypes").split(","));
		for(Iterator<String> i = this.resourcesTypes.iterator(); i.hasNext(); ) {
			String type = i.next();
			int amount = Integer.parseInt(prop.getProperty("amount_"+type));
			Resource r = new ResourceImpl(type, amount);
			this.resources.add(r);
		}
	}

	private void parseProjectMangers() {
		this.projMangers = new ArrayList<ProjectManager>();
		int amount = Integer.parseInt(prop.getProperty("numOfProjectManagers"));
		for(int i=0; i<amount; i++) {
			ProjectManager pm = new ProjectManager(prop.getProperty("projectManager"+(i+1)+"Name"), this.board);
			this.projMangers.add(pm);
		}
	}
	
	private void createBoard() {
		this.observerGatherer = new ObserverInfoGatherer();
		this.board = new BoardImpl(this.observerGatherer);
	}

	private void parseProgrammers() {
		ProgrammerResourceHandler prh = new ProgrammerResourceHandler(this.resources);
		this.programmersInfo = new ArrayList<ProgrammerInfo>();

		this.programmers = new ArrayList<Programmer>();
		int amount = Integer.parseInt(prop.getProperty("numOfProgrammers"));
		for(int i=0; i<amount; i++) {
			String name = prop.getProperty("programmer"+(i+1)+"Name");
			double pRate = Double.parseDouble(prop.getProperty("programmer"+(i+1)+"ProductivityRate"));
			double wPhaseHours = Double.parseDouble(prop.getProperty("programmer"+(i+1)+"WorkPhaseHours"));
			double budget = Double.parseDouble(prop.getProperty("programmer"+(i+1)+"Budget"));
			List<String> special = Arrays.asList(prop.getProperty("programmer"+(i+1)+"Specialization").split(","));
			
			ProgrammerObject po = new ProgrammerObject();
			
			po.setName(name);
			po.setSpecializations(special);
			po.setProductivityRate(pRate);
			po.setWorkPhaseHours(wPhaseHours);
			po.setBudget(budget);
			
			ProgrammerInfo pi = new ProgrammerInfo(po);
			this.programmersInfo.add(pi);
			Programmer p = new Programmer(po, pi, prh, this.board);
			
			this.programmers.add(p);
		}
	}

	/**
	 * @return the programmersInfo
	 */
	public List<ProgrammerInfo> getProgrammersInfo() {
		return this.programmersInfo;
	}

	private void parseSimulationHour() {
		MainParser.SIMULATION_HOUR = Double.parseDouble(prop.getProperty("simulationHour"));
	}

	//Setters getters


	/**
	 * @return the projectTypes
	 */
	public List<String> getProjectTypes() {
		return this.projectTypes;
	}

	/**
	 * @return the simulationHour
	 */
	public double getSimulationHour() {
		return MainParser.SIMULATION_HOUR;
	}

	/**
	 * @return the resourcesTypes
	 */
	public List<String> getResourcesTypes() {
		return this.resourcesTypes;
	}

	/**
	 * @return the programmers
	 */
	public List<Programmer> getProgrammers() {
		return this.programmers;
	}

	/**
	 * @return the resources
	 */
	public List<Resource> getResources() {
		return this.resources;
	}

	/**
	 * @return the projectManagers
	 */
	public List<ProjectManager> getProjectManagers() {
		return this.projMangers;
	}

	/**
	 * @return the observerGatherer
	 */
	public ObserverInfoGatherer getObserverGatherer() {
		return this.observerGatherer;
	}
}
