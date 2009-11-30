/**
 * 
 */
package actors;

import java.util.Arrays;
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
public class Observer implements Runnable {
	
	private Scanner scanner;
	private String userInput;
	private ObserverInfoGatherer myInfo;
	private Board b;
	private ProgrammerResourceHandler prh;
	/**
	 * @param _mp my main parser
	 */
	public Observer(MainParser _mp) {
		this.myInfo = _mp.getObserverGatherer();
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
			if (this.userInput.equals("currentProjects"))
				this.currentProjects();
			if (this.userInput.equals("programmers"))
				this.programmer();
			if (this.userInput.startsWith("programmer ")) {
				String str;
				final int temp = 11;
				str = this.userInput.substring(temp);
				this.programmerInfo(str);
			}
			if (this.userInput.startsWith("addProgrammer ")) {
				String str;
				str = this.userInput.substring(14);
				String[] strArr;
				strArr = str.split(" ");
				if(strArr.length != 5) {
					System.out.println("are you trying to fool me? try again.");
				}
				else {
					String name;
					List<String> types;
					double rate;
					double budget;
					double workHours;
					name = strArr[0];
					types = Arrays.asList(strArr[1].split(","));
					try {
					rate = Double.parseDouble(strArr[2]);
					budget = Double.parseDouble(strArr[3]);
					workHours = Double.parseDouble(strArr[4]);
					this.addProgrammer(name, types, rate, budget, workHours);
					} 
					catch(NumberFormatException e) {  
						e.printStackTrace();
					};

				}
			}
			if (this.userInput.startsWith("addBudget ")) {
				String str;
				str = this.userInput.substring(10);
				String[] strArr;
				strArr = str.split(" ");
				if(strArr.length != 2) {
					System.out.println("are you trying to fool me? try again.");
				}
				else {
					String name;
					try {
					double budget;
					name = strArr[0];
					budget = Double.parseDouble(strArr[1]);
					this.addBudget(name, budget);
					} 
					catch(NumberFormatException e) {  
						e.printStackTrace();
					}
				}
			}
			if (this.userInput.equals("stop")) {
				this.stop();
			}
			//TODO continue the programmer methods
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
		System.out.println((double)(tempProject.getTimeElapsed()/1000.0)+" seconds");
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
			if(tempProgrammer.getName().equals(name)) {
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
