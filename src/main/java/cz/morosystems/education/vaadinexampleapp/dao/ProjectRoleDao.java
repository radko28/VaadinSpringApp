package cz.morosystems.education.vaadinexampleapp.dao;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRoleType;


public interface ProjectRoleDao {

    public List<ProjectRole> findAll();

    public ProjectRole findProjectRoleByType(ProjectRoleType projectRoleType);

    public ProjectRole findProjectRoleById(String id);

    public void remove(ProjectRole projectRole);

    public void save(ProjectRole projectRole);

    public void update(ProjectRole projectRole);

}
