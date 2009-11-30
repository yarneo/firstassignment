/**
 * 
 */
package actors;

import java.util.List;
/**
 * @author Alon Segal
 *
 */
public interface Project {
	/**
	 * gets the id of the project
	 * @return the id of the project
	 */
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
	 * @return the size
	 */
	public double getSize();

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
	public void setProgrammers(List<ProgrammerInfo> programmers);

	/**
	 * @return the programmers
	 */
	public String getProgrammers();
	
	/**
	 * indicates if another working hand is needed in the project. if needed, updates the project size
	 * @param workPhaseHour checks the workPhasehours
	 * @param productivityRate checks the productivity rate
	 * @return true if another programmer can commit, false otherwise.
	 */
	public boolean isAnotherHandNeeded(double workPhaseHour, double productivityRate);
	
	/**
	 * Called by a programmer who's commiting for the project
	 * @param pInfo Programmer who commits for the project
	 */
	public void commit(ProgrammerInfo pInfo);
	
	/**
	 * Called by a programmer who's work phase is done
	 * @param p Programmer who's done
	 */
	public void done(ProgrammerInfo p);
	
	/**
	 * Checks if the project is completed.
	 * @return True if the project is completed, false otherwise.
	 */
	public boolean isCompleted();
	
	/**
	 * 
	 * @return The Number of hours that has been completed
	 */
	public double getHoursCompleted();
	
	/**
	 * 
	 * @return The time that passed since the craetion of the object. returns zero if the project hasn't been completed yet.
	 */
	public long getTimeElapsed();
	
	/**
	 * publish the project on the board. call this when publishing!
	 */
	public void publish();

}
