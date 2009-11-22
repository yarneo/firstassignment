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
	
	/**
	 * Trying to stop the thread
	 */
	public void stop();
	
	/**
	 * @param type Type of the project
	 * @return is the programmer capable with this type of work
	 */
	public boolean isCapable(String type);
	
	/**
	 * Putting a new project in the programmer mailbox
	 * @param p Project applied
	 */
	public void sendNewProject(Project p);
}
