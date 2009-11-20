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
import actors.Programmer;
import actors.ProgrammerImpl;

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
	private List<String> projMangers;
	private double simulationHour;

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
	public void parse() {
		this.parseProjectTypes();
		this.parseResources();
		this.parseProjectMangers();
		this.parseProgrammers();
		this.parseSimulationHour();
	}
	
	// private functions
	
	private void parseProjectTypes() {
		this.projectTypes = Arrays.asList(prop.getProperty("projectTypes").split(", "));
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
		this.projMangers = new ArrayList<String>();
		int amount = Integer.parseInt(prop.getProperty("numOfProjectManagers"));
		for(int i=0; i<amount; i++) {
			this.projMangers.add(prop.getProperty("projectManager"+(i+1)+"Name"));
		}
	}
	
	private void parseProgrammers() {
		ProgrammerResourceHandler prh = new ProgrammerResourceHandler(this.getResources());
		
		this.programmers = new ArrayList<Programmer>();
		int amount = Integer.parseInt(prop.getProperty("numOfProgrammers"));
		for(int i=0; i<amount; i++) {
			String name = prop.getProperty("programmer"+(i+1)+"Name");
			double pRate = Double.parseDouble(prop.getProperty("programmer"+(i+1)+"ProductivityRate"));
			double wPhaseHours = Double.parseDouble(prop.getProperty("programmer"+(i+1)+"WorkPhaseHours"));
			double budget = Double.parseDouble(prop.getProperty("programmer"+(i+1)+"Budget"));
			List<String> special = Arrays.asList(prop.getProperty("programmer"+(i+1)+"Specialization").split(","));
			Programmer p = new ProgrammerImpl(name, special, pRate, wPhaseHours, budget, prh);
			this.programmers.add(p);
		}
	}
	
	private void parseSimulationHour() {
		this.simulationHour = Double.parseDouble(prop.getProperty("simulationHour"));
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
		return this.simulationHour;
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
	public List<String> getProjectManagers() {
		return this.projMangers;
	}
}
