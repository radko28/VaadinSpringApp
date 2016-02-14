package cz.morosystems.education.vaadinexampleapp.managers;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;


public interface ProjectRoleManager {

    List<ProjectRole> findProjectRoles();

    void deleteProjectRole(String id);

    ProjectRole findProjectRoleById(String string);

    void save(ProjectRole projectRole);

    void update(ProjectRole projectRole);

}
