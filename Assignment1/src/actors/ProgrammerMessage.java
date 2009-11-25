/**
 * 
 */
package actors;

/**
 * @author Alon Segal
 *
 */
public class ProgrammerMessage {
	
	public static final String PROGRAMMER_PROJECT = "Programmer Project";
	public static final String PROGRAMMER_BUDGET = "Programmer Budget";
	
	private String type;
	private Project project;
	private double budget;
	

	/**
	 * Constructor
	 * @param _type the type of the message (use static finals)
	 * @param _obj can be only project or budget (as double)
	 */
	public ProgrammerMessage(String _type, Object _obj) {
		this.type = _type;
		if (this.type.equals(ProgrammerMessage.PROGRAMMER_PROJECT)) 
				this.project = (Project)_obj;
		
		if (this.type.equals(ProgrammerMessage.PROGRAMMER_BUDGET)) 
			this.budget = (Double)_obj;
	}


	/**
	 * @return the project
	 */
	public Project getProject() {
		return this.project;
	}

	/**
	 * @return the budget
	 */
	public double getBudget() {
		return this.budget;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

}
