/**
 * 
 */
package actors;

import java.util.Scanner;

/**
 * @author Alon Segal
 *
 */
public class ObserverImpl implements Observer, Runnable {
	
	private Scanner scanner;
	private String userInput;

	/**
	 * 
	 */
	public ObserverImpl() {
		scanner = new java.util.Scanner(System.in);
		userInput = "";
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (! userInput.equals("stop")) { 
			System.out.print("Observer> "); 
			userInput = scanner.nextLine(); 
			System.out.println("You entered: " + userInput); 
	     } 
		System.out.println("Bye");
	}
	
	//private functions
	
	private void currentProjects() {
		
	}
	
	private void pendingProjects() {
		
	}
	
	private void completedProjects() {
		
	}
	
	private void programmer(String name) {
		
	}
	
	private void addProgrammer(String name, String types, String rate, String load, String workHours) {
		
	}
	
	private void addBudget(String number) {
		
	}
	
	private void stop() {
		
	}

}
