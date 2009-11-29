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
		Project p1 = new ProjectImpl("1", "Project1", "gui", 3, new ArrayList<String>(),
				new ArrayList<String>());
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
		ProgrammerInfo p1 = new ProgrammerInfo("Alon", new ArrayList<String>(), 0.21, 1.4, 2.1);
		ProgrammerInfo p2 = new ProgrammerInfo("Yarden", new ArrayList<String>(), 0.21, 1.4, 2.1);
		this.project.commit(p1);
		this.project.commit(p2);
		assertEquals("Expected Alon,Yarden and got "+this.project.getProgrammers(), 
				this.project.getProgrammers(), "Alon,Yarden");
	}

}
