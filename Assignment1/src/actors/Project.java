/**
 * 
 */
package actors;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Alon Segal
 *
 */
public interface Project {
	
	public String getId();

	/**
	 * @param name the name to set
	 */
	public void setName(String name);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param type the type to set
	 */
	public void setType(String type);

	/**
	 * @return the type
	 */
	public String getType();

	/**
	 * @param size the size to set
	 */
	public void setSize(int size);

	/**
	 * @return the size
	 */
	public int getSize();

	/**
	 * @param prequesiteProjects the prequesiteProjects to set
	 */
	public void setPrequesiteProjects(List<String> prequesiteProjects);

	/**
	 * @return the prequesiteProjects
	 */
	public List<String> getPrequesiteProjects();

	/**
	 * @return the resources
	 */
	public List<String> getResources();

	/**
	 * @param programmers the programmers to set
	 */
	public void setProgrammers(List<Programmer> programmers);

	/**
	 * @return the programmers
	 */
	public List<Programmer> getProgrammers();
	
	/**
	 * indicates if another working hand is needed in the project. if needed, updates the project size
	 * @param p the programmer who called this method
	 * @return true if another programmer can commit, false otherwise.
	 */
	public boolean isAnotherHandNeeded(double workPhaseHour, double productivityRate);
	
	/**
	 * Called by a programmer who's commiting for the project
	 * @param p Programmer who commits for the project
	 */
	public void commit(Programmer p);
	
	/**
	 * Called by a programmer who's work phase is done
	 * @param p Programmer who's done
	 */
	public void done(Programmer p);

}
