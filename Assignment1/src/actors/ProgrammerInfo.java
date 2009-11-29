/**
 * 
 */
package actors;

import java.util.List;

import resources.Resource;

/**
 * @author Alon Segal
 *
 */
public class ProgrammerInfo {
	
	private boolean _isThereNewBudget;
	private boolean isWorking;
	private Project currentProject;
	private List<Resource> _currentResources;
	
	private String name;
	private double productivityRate;
	private double workPhaseHours;
	private List<String> specializations;
	
	private double newBudget;
	
	/**
	 * 
	 * @param _name
	 * @param _specializations
	 * @param _productivityRate
	 * @param _workPhaseHours
	 * @param _budget
	 */
	public ProgrammerInfo(String _name, List<String> _specializations, double _productivityRate,
			double _workPhaseHours, double _budget) {
		
		this.name = _name;
		this.specializations = _specializations;
		this.productivityRate = _productivityRate;
		this.workPhaseHours = _workPhaseHours;
		this.newBudget = _budget;
		
		this._isThereNewBudget = false;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return True if there is a new budget, false otherwise
	 */
	public boolean isThereNewBudget() {
		return this._isThereNewBudget;
	}
	
	/**
	 * Add more budget for the programmer
	 * @param _budget Budget to add
	 */
	public void addBudget(double _budget) {
		this.newBudget += _budget;
		this._isThereNewBudget = true;
	}
	
	/**
	 * @return the newBudget
	 */
	public double getNewBudget() {
		this._isThereNewBudget = false;
		return this.newBudget;
	}
	
	/**
	 * @return the budget
	 */
	public double getBudget() {
		return this.newBudget;
	}
	
	/**
	 * @return the productivityRate
	 */
	public double getProductivityRate() {
		return this.productivityRate;
	}

	/**
	 * @return the workPhaseHours
	 */
	public double getWorkPhaseHours() {
		return this.workPhaseHours;
	}

	/**
	 * @return the specializations
	 */
	public List<String> getSpecializations() {
		return this.specializations;
	}
	
	/**
	 * @param _name the name to set
	 */
	public void setName(String _name) {
		this.name = _name;
	}
	
	/**
	 * 
	 * @return true if currently working on a project, false otherwise
	 */
	public boolean isCurrentlyWorking() {
		return this.isWorking;
	}
	
	/**
	 * 
	 * @return The project that being worked on.
	 */
	public Project getCurrentProject() {
		return this.currentProject;
	}
	
	/**
	 * 
	 * @return The current resources that being worked on
	 */
	public List<Resource> getCurrentResources() {
		return this._currentResources;
	}

	/**
	 * @param thereNewBudget the _isThereNewBudget to set
	 */
	public void setIsThereNewBudget(boolean thereNewBudget) {
		this._isThereNewBudget = thereNewBudget;
	}

	/**
	 * @param project the _currentProject to set
	 */
	public void setCurrentProject(Project project) {
		this.currentProject = project;
	}

	/**
	 * @param resources the _currentResources to set
	 */
	public void setCurrentResources(List<Resource> resources) {
		this._currentResources = resources;
	}

	/**
	 * @param working the _isWorking to set
	 */
	public void setIsWorking(boolean working) {
		this.isWorking = working;
	}
}
