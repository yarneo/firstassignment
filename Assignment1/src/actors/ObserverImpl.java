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
	private Board b;
	private ProgrammerResourceHandler prh;
	/**
	 * @param _mp my main parser
	 */
	public ObserverImpl(MainParser _mp) {
		this.myInfo = new ObserverInfoGatherer(_mp);
		this.scanner = new java.util.Scanner(System.in);
		this.userInput = "";
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (! this.userInput.equals("stop")) { 
			System.out.print("Observer> "); 
			this.userInput = this.scanner.nextLine(); 
			if (this.userInput.equals("completedProjects"))
				this.completedProjects();
			if (this.userInput.equals("pendingProjects"))
				this.pendingProjects();
			System.out.println("You entered: " + this.userInput); 
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
		System.out.println(tempProject.getHoursCompleted());
		//which programmers work on it now
		System.out.println(tempProject.getProgrammers());
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
		//obviously if the project hasent been worked on yet or hasent finished the time there
		//will be 0, or null. name of field will be time, and getter will be getTime
		System.out.println(tempProject.getTimeElapsed());
	}
	}
	
	private void programmer() {
	for(Iterator<ProgrammerInfo> i = this.myInfo.getProgrammers().iterator(); i.hasNext();) {
		ProgrammerInfo tempProgrammer = i.next();
		//name of programmers
		System.out.println(tempProgrammer.getName());
	}
	}
	private void programmerInfo(String name) {
		for(Iterator<ProgrammerInfo> i = this.myInfo.getProgrammers().iterator(); i.hasNext();) {
			//still need the getter for the field name, as written in the todo above
			//and as written above, need to make an object ProgrammerInfo which has all the 
			//info about the programmers as the info written below:
			ProgrammerInfo tempProgrammer = i.next();
			if(tempProgrammer.getName() == name) {
				System.out.println(tempProgrammer.getName());
				System.out.println(tempProgrammer.getProductivityRate());
				System.out.println(tempProgrammer.getWorkPhaseHours());
				System.out.println(tempProgrammer.getBudget());
				System.out.println(tempProgrammer.getSpecializations());
				//need to add a field on the programmer which is the programmers' current 
				//status which means: is he assigned to a project and if so, what
				//project id, and what resources does he hold
				if(tempProgrammer.isCurrentlyWorking()) {
					//current project he is working on
					System.out.println(tempProgrammer.getCurrentProject());
					//current resources he is holding
					System.out.println(tempProgrammer.getCurrentResources());
				}
				else {
					System.out.println("the programmer isnt currently assigned to any project");
				}
			}
		}
	}
	
	private void addProgrammer(String name, List<String> types, double rate, double budget, double workHours) {
		//TODO what do i do with the board and the programresourcehandler
		//also, how do i activate the thread using the initialthreadhandler?
		ProgrammerInfo newprog = new ProgrammerInfo(name,types,rate,workHours,budget);
		Programmer progy = new Programmer(name,types,rate,budget,workHours,newprog,
				this.prh,this.b );
		
	}
	
	private void addBudget(String programmerName ,Double budget) {
		for(Iterator<ProgrammerInfo> i = this.myInfo.getProgrammers().iterator(); i.hasNext();) {
			ProgrammerInfo tempy = i.next();
			if(tempy.getName()==programmerName) {
				tempy.addBudget(budget);
			}
		}
	}
	
	private void stop() {
		for(Iterator<Thread> i = this.myInfo.getMyThreads().iterator(); i.hasNext();) {
			i.next().interrupt();
		}
	}

}
