/**
 * 
 */
package actorobjects;

import java.util.List;

/**
 * @author Alon Segal
 *
 */
public class projectObject {
	
	//members
	private String id;
	private String name;
	private String type;
	private double size;
	private List<String> prequesiteProjects;
	private List<String> resources;

	/**
	 * 
	 */
	public projectObject() {
	}

	/**
	 * @param _id the id to set
	 */
	public void setId(String _id) {
		this.id = _id;
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
	public void setSize(double _size) {
		this.size = _size;
	}

	/**
	 * @return the size
	 */
	public double getSize() {
		return this.size;
	}

	/**
	 * @param _prequesiteProjects the prequesiteProjects to set
	 */
	public void setPrequesiteProjects(List<String> _prequesiteProjects) {
		this.prequesiteProjects = _prequesiteProjects;
	}

	/**
	 * @return the prequesiteProjects
	 */
	public List<String> getPrequesiteProjects() {
		return this.prequesiteProjects;
	}

	/**
	 * @param _resources the resources to set
	 */
	public void setResources(List<String> _resources) {
		this.resources = _resources;
	}

	/**
	 * @return the resources
	 */
	public List<String> getResources() {
		return this.resources;
	}

}
