/**
 * 
 */
package actors;

import java.util.List;

/**
 * @author Alon Segal
 *
 */
public interface Programmer {
	
	/**
	 * 
	 * @param p Project to test if ready for assignment.
	 * @return true if project fits and false otherwise.
	 */
	public boolean isProjectFits(Project p);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @return the productivityRate
	 */
	public double getProductivityRate();

	/**
	 * @return the workPhaseHours
	 */
	public double getWorkPhaseHours();

	/**
	 * @return the budget
	 */
	public double getBudget();

	/**
	 * @return the specializations
	 */
	public List<String> getSpecializations();
}
