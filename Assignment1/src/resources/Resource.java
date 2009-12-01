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
	 * @throws InterruptedException in case there's an exception and pass it up
	 */
	public void acquire() throws InterruptedException;
	
	/**
	 * Releases resource.
	 */
	public void realese();
	
	/**
	 * 
	 * @return type of the resource.
	 */
	public String getType();
	
	/**
	 * 
	 * @return String with values
	 */
	public String toString();
}
