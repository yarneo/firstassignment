/**
 * 
 */
package actorobjects;

import java.util.List;

/**
 * @author Alon Segal
 *
 */
public class ProgrammerObject {
	
	private String name;
	private double productivityRate;
	private double workPhaseHours;
	private double budget;
	private List<String> specializations;

	/**
	 * 
	 */
	public ProgrammerObject() {
	}

	/**
	 * @param _name the name to set
	 */
	public void setName(String _name) {
		this.name = _name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param _productivityRate the productivityRate to set
	 */
	public void setProductivityRate(double _productivityRate) {
		this.productivityRate = _productivityRate;
	}

	/**
	 * @return the productivityRate
	 */
	public double getProductivityRate() {
		return this.productivityRate;
	}

	/**
	 * @param _workPhaseHours the workPhaseHours to set
	 */
	public void setWorkPhaseHours(double _workPhaseHours) {
		this.workPhaseHours = _workPhaseHours;
	}

	/**
	 * @return the workPhaseHours
	 */
	public double getWorkPhaseHours() {
		return this.workPhaseHours;
	}

	/**
	 * @param _budget the budget to set
	 */
	public void setBudget(double _budget) {
		this.budget = _budget;
	}

	/**
	 * @return the budget
	 */
	public double getBudget() {
		return this.budget;
	}

	/**
	 * @param _specializations the specializations to set
	 */
	public void setSpecializations(List<String> _specializations) {
		this.specializations = _specializations;
	}

	/**
	 * @return the specializations
	 */
	public List<String> getSpecializations() {
		return this.specializations;
	}

}
