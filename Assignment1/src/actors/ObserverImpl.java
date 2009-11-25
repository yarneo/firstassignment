/**
 * 
 */
package actors;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import resources.ProgrammerResourceHandler;

import utils.MainParser;
import utils.ObserverInfoGatherer;

/**
 * @author Alon Segal
 *
 */
public class ObserverImpl implements Observer, Runnable {
	
	private Scanner scanner;
	private String userInput;
	private ObserverInfoGatherer myInfo;

	/**
	 * 
	 */
	public ObserverImpl(MainParser _mp) {
		this.myInfo = new ObserverInfoGatherer(_mp);
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
	for(Iterator<Project> i = this.myInfo.getCurrentProjects().iterator(); i.hasNext(); ) {
		Project tempProject = i.next();
		//type
		System.out.println(tempProject.getType());
		//id number
		System.out.println(tempProject.getId());
		//a project needs to tell me how much of it has been completed
		//TODO a project needs to tell me how much of it has been completed
		System.out.println("");
		//which programmers work on it now
		for(Iterator<Programmer> j = tempProject.getProgrammers().iterator(); i.hasNext();) {
		//TODO add getName method to the Programmer	, cant add getter to thread, need to 
		//change the programmers working on project to list of strings of their names.
			System.out.println(j.next().getName());
		}
	}
	}
	
	private void pendingProjects() {
	for(Iterator<Project> i = this.myInfo.getPendingProjects().iterator(); i.hasNext();) {
		Project tempProject = i.next();
		//type
		System.out.println(tempProject.getType());
		//id number
		System.out.println(tempProject.getId());
		//dependancies
		for(Iterator<String> j = tempProject.getPrequesiteProjects().iterator(); i.hasNext();) {
			System.out.println(j.next());
		}
	}
	}
	
	private void completedProjects() {
	for(Iterator<Project> i = this.myInfo.getCompletedProjects().iterator(); i.hasNext();) {
		Project tempProject = i.next();
		//type
		System.out.println(tempProject.getType());
		//id number
		System.out.println(tempProject.getId());
		//time taken
		//TODO add a field to the object Project that has time taken to finish project,
		//obviously if the project hasent been worked on yet or hasent finished the time there
		//will be 0, or null. name of field will be time, and getter will be getTime
		System.out.println(tempProject.getTime());
	}
	}
	
	private void programmer() {
	for(Iterator<Programmer> i = this.myInfo.getProgrammers().iterator(); i.hasNext();) {
		Programmer tempProgrammer = i.next();
		//name of programmers
		//TODO add a getter for the field name in the Programmer Object
		//cant add getter to thread then it needs to take information from the object
		//ProgrammerInfo
		System.out.println(tempProgrammer.getName());
	}
	}
	private void programmerInfo(String name) {
		for(Iterator<Programmer> i = this.myInfo.getProgrammers().iterator(); i.hasNext();) {
			//still need the getter for the field name, as written in the todo above
			//and as written above, need to make an object ProgrammerInfo which has all the 
			//info about the programmers as the info written below:
			if(i.next().getName() == name) {
				System.out.println(getName());
				System.out.println(getProductivityRate());
				System.out.println(getWorkPhaseHours());
				System.out.println(getBudget);
				System.out.println(getSpecializations);
				//need to add a field on the programmer which is the programmers' current 
				//status which means: is he assigned to a project and if so, what
				//project id, and what resources does he hold
				System.out.println(getCurrentStatus);
			}
		}
	}
	
	private void addProgrammer(String name, List<String> types, double rate, double budget, double workHours) {
		//TODO what do i do with the board and the programresourcehandler
		//also, how do i activate the thread using the initialthreadhandler?
		Programmer progy = new Programmer(name,types,rate,budget,workHours,
				programresourcemanager?,board? );
		
	}
	
	private void addBudget(String number) {
		//TODO addBudget, how do i add it using the ProgrammerInfo you have added?
	}
	
	private void stop() {
		//TODO stop? who do i stop and how
	}

}
