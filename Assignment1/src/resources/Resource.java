/**
 * 
 */
package resources;

/**
 * @author Alon Segal
 *
 */
public interface Resource {
	
	/**
	 * Acquire resource, wait if not available.
	 */
	public void acquire();
	
	/**
	 * Releases resource.
	 */
	public void realese();
	
	/**
	 * 
	 * @return type of the resource.
	 */
	public String getType();
}
