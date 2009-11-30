/**
 * 
 */
package resources;

import java.util.concurrent.Semaphore;

/**
 * @author Alon Segal
 *
 */
public class ResourceImpl implements Resource {
	
	//members
	private String type;
	private int amount;
	private Semaphore sem;

	/**
	 * 
	 * @param _type Type of the resource.
	 * @param _amount Amount of units available.
	 */
	public ResourceImpl(String _type, int _amount) {
		this.type = _type;
		this.amount = _amount;
		this.sem = new Semaphore(this.amount, true);
	}
	
	/**
	 * Acquire resource, wait if not available.
	 * @throws InterruptedException in case there's an exception and pass it up
	 */
	public void acquire() throws InterruptedException {
		try {
			this.sem.acquire();
		} catch(InterruptedException e) {
			throw new InterruptedException("waiting for resource");
		}
	}
	
	/**
	 * Releases resource.
	 */
	public void realese() {
		this.sem.release();
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}
}
