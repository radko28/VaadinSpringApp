package cz.morosystems.education.vaadinexampleapp.dao;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.Project;
import cz.morosystems.education.vaadinexampleapp.entities.User;


/**
 * 
 * @author radoslav.kuzma
 * 
 */
public interface ProjectDao {

    void save(Project project);

    List<Project> findProjectsByUser(User user);

    List<Project> findProjectsRoleByUser(User user);

    Project findProjectById(String id);

    void remove(Project project);

    Project findCustomerAreaById(String id);

    Project findProjectRoleById(String id);

    void update(Project project);


}
