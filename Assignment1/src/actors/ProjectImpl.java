/**
 * 
 */
package actors;

import java.util.ArrayList;
import java.util.List;

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
	private int size;
	private List<String> prequesiteProjects;
	private List<String> resources;
	private List<Programmer> programmers;
	
	private boolean _isAnotherHandNeeded;

	/**
	 * 
	 */
	public ProjectImpl() {
		this._isAnotherHandNeeded = true;
	}
	
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
		
		this.programmers = new ArrayList<Programmer>();
		
		this._isAnotherHandNeeded=true;
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
	 * @param _size the size to set
	 */
	public synchronized void setSize(int _size) {
		this.size = _size;
	}

	/**
	 * @return the size
	 */
	public synchronized int getSize() {
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
	public void setProgrammers(List<Programmer> _programmers) {
		this.programmers = _programmers;
	}

	/**
	 * @return the programmers
	 */
	public List<Programmer> getProgrammers() {
		return this.programmers;
	}

	@Override
	public synchronized boolean isAnotherHandNeeded(double workPhaseHour, double productivityRate) {
		if(this._isAnotherHandNeeded) {
			this.setSize(this.getSize()-(int)(workPhaseHour/productivityRate));
			if(this.getSize()<0) {
				this.setSize(0);
				this._isAnotherHandNeeded = false;
			}return true;
		} else return false;
		
	}
	
	/**
	 * Called by a programmer who's commiting for the project
	 * @param p Programmer who commits for the project
	 */
	public void commit(Programmer p) {
		this.programmers.add(p);
	}
	
	/**
	 * Called by a programmer who's work phase is done
	 * @param p Programmer who's done
	 * @throws NotWorkingProgrammerException throws exception in case programmer didn't commit.
	 */
	public synchronized void done(Programmer p) throws NotWorkingProgrammerException {
		if(!this.programmers.remove(p))
			throw new NotWorkingProgrammerException("Programmer has not commited!");
	}
}
