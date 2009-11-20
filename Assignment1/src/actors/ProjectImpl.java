/**
 * 
 */
package actors;

import java.util.ArrayList;
import java.util.List;

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
	private List<String> programmers;

	/**
	 * 
	 */
	public ProjectImpl() {
	}
	
	public ProjectImpl(String _id, String _name, String _type, int _size, 
			List<String> _prequesiteProjects, List<String> _resources,
			List<String> _programmers) {

		this.id = _id;
		this.name = _name;
		this.type = _type;
		this.size = _size;
		this.prequesiteProjects = _prequesiteProjects;
		this.resources = _resources;
		this.programmers = _programmers;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param prequesiteProjects the prequesiteProjects to set
	 */
	public void setPrequesiteProjects(List<String> prequesiteProjects) {
		this.prequesiteProjects = (ArrayList<String>)prequesiteProjects;
	}

	/**
	 * @return the prequesiteProjects
	 */
	public List<String> getPrequesiteProjects() {
		return prequesiteProjects;
	}

	/**
	 * @return the resources
	 */
	public List<String> getResources() {
		return resources;
	}

	/**
	 * @param programmers the programmers to set
	 */
	public void setProgrammers(List<String> programmers) {
		this.programmers = programmers;
	}

	/**
	 * @return the programmers
	 */
	public List<String> getProgrammers() {
		return programmers;
	}
	
}
