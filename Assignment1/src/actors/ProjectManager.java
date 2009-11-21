package actors;

import java.util.List;

public interface ProjectManager {
	public List<Project> publishProjects();
	public void updateProjs(String finishedProject);
	public void Publish();
}
