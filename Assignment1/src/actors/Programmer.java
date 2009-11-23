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
	 * @return the name
	 * 
	 * @pre: none.
	 * @inv: none.
	 * @post: none.
	 */
	public String getName();

	/**
	 * @return the productivityRate
	 * 
	 * @pre: none.
	 * @inv: none.
	 * @post: none.
	 */
	public double getProductivityRate();

	/**
	 * @return the workPhaseHours
	 * 
	 * @pre: none.
	 * @inv: none.
	 * @post: none.
	 */
	public double getWorkPhaseHours();

	/**
	 * @return the budget
	 * 
	 * @pre: none.
	 * @inv: none.
	 * @post: none.
	 */
	public double getBudget();

	/**
	 * @return the specializations
	 * 
	 * @pre: none.
	 * @inv: none.
	 * @post: none.
	 */
	public List<String> getSpecializations();
	
	/**
	 * Trying to stop the thread
	 * 
	 * @pre: this.shouldStop==false
	 * @inv: none.
	 * @post: this.shouldStop==true
	 */
	public void stop();
	
	/**
	 * @param type Type of the project
	 * @return is the programmer capable with this type of work
	 * 
	 * @pre: none.
	 * @inv: none.
	 * @post: none.
	 */
	public boolean isCapable(String type);
	
	/**
	 * Putting a new project in the programmer mailbox
	 * @param p Project applied
	 * 
	 * @pre: none.
	 * @inv: none.
	 * @post: this.mailbox.isEmpty()==false
	 */
	public void sendNewProject(Project p);
}
