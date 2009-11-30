/**
 * 
 */
package actors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import utils.ObserverInfoGatherer;

import exceptions.NotWorkingProgrammerException;

/**
 * @author Alon
 *
 */
public class ProjectImpl implements Project {
	
	//members
	private String id;
	private String name;
	private String type;
	private double size;
	private List<String> prequesiteProjects;
	private List<String> resources;
	private List<ProgrammerInfo> programmers;
	
	private boolean _isAnotherHandNeeded;
	private double hoursCompleted;
	
	private long startTime;
	private long finalTime;
	
	/**
	 * 
	 * @param _id ID of the project
	 * @param _name Name of the prject
	 * @param _type Type of the project
	 * @param _size Size of the project (in hours)
	 * @param _prequesiteProjects The prerequisite projects
	 * @param _resources The resources needed for the project
	 */
	public ProjectImpl(String _id, String _name, String _type, int _size, 
			List<String> _prequesiteProjects, List<String> _resources) {

		this.id = _id;
		this.name = _name;
		this.type = _type;
		this.size = _size;
		this.prequesiteProjects = _prequesiteProjects;
		this.resources = _resources;
		
		this.programmers = new ArrayList<ProgrammerInfo>();
		
		this._isAnotherHandNeeded = true;
		this.hoursCompleted = 0;
		
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id; 
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
	 * @param _type the type to set
	 */
	public void setType(String _type) {
		this.type = _type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @return the size
	 */
	public synchronized double getSize() {
		return this.size;
	}

	/**
	 * @param _prequesiteProjects the prequesiteProjects to set
	 */
	public void setPrequesiteProjects(List<String> _prequesiteProjects) {
		this.prequesiteProjects = (ArrayList<String>)_prequesiteProjects;
	}

	/**
	 * @return the prequesiteProjects
	 */
	public List<String> getPrequesiteProjects() {
		return this.prequesiteProjects;
	}

	/**
	 * @return the resources
	 */
	public List<String> getResources() {
		return this.resources;
	}

	/**
	 * @param _programmers the programmers to set
	 */
	public void setProgrammers(List<ProgrammerInfo> _programmers) {
		this.programmers = _programmers;
	}

	/**
	 * @return the programmers
	 */
	public String getProgrammers() {
		return this.generateStringProgrammers();
	}

	@Override
	public synchronized boolean isAnotherHandNeeded(double workPhaseHour, double productivityRate) {
		if(this._isAnotherHandNeeded) {
			this.setSize(workPhaseHour/productivityRate);
			if (this.size<=0) 
				this._isAnotherHandNeeded = false;
			return true;
		} else return false;
		
	}
	
	/**
	 * publish the project on the board
	 */
	public void publish() {
		this.startTime =  System.currentTimeMillis();
	}
	
	/**
	 * Called by a programmer who's commiting for the project
	 * @param pInfo ProgrammerInfo who commits for the project
	 */
	public synchronized void commit(ProgrammerInfo pInfo) {
		this.programmers.add(pInfo);
	}
	
	/**
	 * Called by a programmer who's work phase is done
	 * @param p Programmer who's done
	 * @throws NotWorkingProgrammerException throws exception in case programmer didn't commit.
	 */
	public synchronized void done(ProgrammerInfo p) throws NotWorkingProgrammerException {
		if(!this.programmers.remove(p))
			throw new NotWorkingProgrammerException("Programmer has not commited!");
	}
	
	/**
	 * Checks if the project is completed.
	 * @return True if the project is completed, false otherwise.
	 */
	public synchronized boolean isCompleted() {
		return (this.getSize()<=0);
	}
	
	/**
	 * 
	 * @return The Number of hours that has been completed
	 */
	public double getHoursCompleted() {
		return this.hoursCompleted;
	}
	
	/**
	 * @param _size the size to set
	 */
	private synchronized void setSize(double _size) {
		this.hoursCompleted += _size;
		this.size -= _size;
		if(this.getSize()<0) {
			this.size = 0;
			this.finalTime = System.currentTimeMillis();
		}
	}
	
	/**
	 * 
	 * @return The time that passed since the craetion of the object. returns zero if the project hasn't completed yet.
	 */
	public long getTimeElapsed() {
		if(this.isCompleted())
			return this.finalTime - this.startTime;
		return 0;
	}
	
	//Private functions
	
	private String generateStringProgrammers() {
		String ans = "";
		for(Iterator<ProgrammerInfo> i = this.programmers.iterator(); i.hasNext();) {
			ProgrammerInfo p = i.next();
			ans+=p.getName()+",";
		}
		return ans.substring(0, ans.length()-1);
	}
}
