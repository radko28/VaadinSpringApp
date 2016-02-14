package cz.morosystems.education.vaadinexampleapp.managers.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cz.morosystems.education.vaadinexampleapp.dao.ProjectRoleDao;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectRoleManager;

@Component
@Scope("prototype")
public class ProjectRoleManagerImpl implements ProjectRoleManager, Serializable {

    @Autowired
    ProjectRoleDao projectRoleDao;

    @Override
    public List<ProjectRole> findProjectRoles() {
        return projectRoleDao.findAll();
    }

    @Override
    public void deleteProjectRole(String id) {
        ProjectRole projectRole = findProjectRoleById(id);
        projectRoleDao.remove(projectRole);
    }

    @Override
    public ProjectRole findProjectRoleById(String id) {
        return projectRoleDao.findProjectRoleById(id);
    }


    @Override
    public void save(ProjectRole projectRole) {
        projectRole.setCreated(new org.joda.time.DateTime(new Date()));
        projectRoleDao.save(projectRole);


    }

    @Override
    public void update(ProjectRole projectRole) {
        projectRoleDao.update(projectRole);
    }

}
