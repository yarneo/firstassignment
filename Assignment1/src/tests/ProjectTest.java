/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import actorobjects.ProgrammerObject;
import actorobjects.projectObject;
import actors.ProgrammerInfo;
import actors.Project;
import actors.ProjectImpl;

/**
 * @author Alon Segal
 *
 */
public class ProjectTest {
	
	private Project project;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.project = createNewProject();
	}
	
	private Project createNewProject() {
		//Creating dummy data for the tested instance
    	List<Project> l = new ArrayList<Project>();
    	
    	projectObject po1 = new projectObject();
    	po1.setId("1");
    	po1.setName("project1");
    	po1.setType("gui");
    	po1.setPrequesiteProjects(new ArrayList<String>());
    	po1.setResources(new ArrayList<String>());
    	
		Project p1 = new ProjectImpl(po1);
		List<String> pp1 = new ArrayList<String>();

		pp1.add("11");
		pp1.add("33");
		pp1.add("44");

		p1.setPrequesiteProjects(pp1);

		l.add(p1);
		
		return p1;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link actors.ProjectImpl#getTimeElapsed()}.
	 */
	@Test
	public final void testGetTimeElapsed() {
		this.project.publish();
		try {
			Thread.sleep(100);
		} catch(InterruptedException e) {}
		assertNotSame("Time is zero", project.getTimeElapsed(), 0);
	}
	
	/**
	 * Test method for {@link actors.ProjectImpl#getProgrammers()}.
	 */
	@Test
	public final void testGetProgrammers() {
		ProgrammerObject po1 = new ProgrammerObject();
		po1.setName("Alon");
		po1.setProductivityRate(0.21);
		po1.setWorkPhaseHours(1.4);
		po1.setSpecializations(new ArrayList<String>());
		po1.setBudget(100);
		
		ProgrammerObject po2 = new ProgrammerObject();
		po2.setName("Yarden");
		po2.setProductivityRate(0.45);
		po2.setWorkPhaseHours(3.4);
		po2.setSpecializations(new ArrayList<String>());
		po2.setBudget(45);
		
		ProgrammerInfo p1 = new ProgrammerInfo(po1);
		ProgrammerInfo p2 = new ProgrammerInfo(po2);
		this.project.commit(p1);
		this.project.commit(p2);
		assertEquals("Expected Alon,Yarden and got "+this.project.getProgrammers(), 
				this.project.getProgrammers(), "Alon,Yarden");
	}

}
