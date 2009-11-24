package actors;

import java.util.List;

public interface Board {
public void showBoardAnouncements();
public void addAnnouncement(List<Project> projects);
public void removeAnouncement();
public void editAnouncement();
}
