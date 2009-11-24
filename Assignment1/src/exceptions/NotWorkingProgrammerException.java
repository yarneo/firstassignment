/**
 *
 */
package exceptions;

/**
 * @author alonseg
 *
 */
public class NotWorkingProgrammerException extends RuntimeException {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Error reason
	 */
	private String mistake;

	/**
	 *
	 */
	public NotWorkingProgrammerException() {
	}

	/**
	 * @param arg0 Exception reason
	 */
	public NotWorkingProgrammerException(String arg0) {
		super(arg0);
		this.mistake = arg0;
	}

	/**
	 * @param arg0 Exception reason
	 */
	public NotWorkingProgrammerException(Throwable arg0) {
		super(arg0);
		this.mistake = arg0.getMessage();
	}

	/**
	 * @param arg0 Exception reason
	 * @param arg1 Throwable object
	 */
	public NotWorkingProgrammerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.mistake = arg0;
	}

	/**
	 *
	 * @return Error reason
	 */
	public String getError() {
		return this.mistake;
	}

}
