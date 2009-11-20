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
	public void setProgrammers(List<String> programmers);

	/**
	 * @return the programmers
	 */
	public List<String> getProgrammers();

}
