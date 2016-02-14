package cz.morosystems.education.vaadinexampleapp.managers;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.entities.Project;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;

/**
 * 
 * @author radoslav.kuzma
 * 
 */
public interface ProjectManager {

    Project findProjectById(String string);

    List<Project> findProjectByUserName(String username);

    List<CustomerArea> getAllCustomerAreas();

    List<ProjectRole> getAllProjectRoles();

    void save(Project project, String username);

    void deleteProject(String id);

    void update(Project project);

}
