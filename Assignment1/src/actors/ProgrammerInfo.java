/**
 * 
 */
package actors;

import java.util.List;

/**
 * @author Alon Segal
 *
 */
public class ProgrammerInfo {
	
	private boolean _isThereNewBudget;
	
	private String name;
	private double newBudget;
	
	/**
	 * 
	 * @param _name name of the programmer
	 */
	public ProgrammerInfo(String _name) {
		
		this.name = _name;
		
		this._isThereNewBudget = false;
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
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

}
