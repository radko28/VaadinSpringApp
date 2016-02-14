package cz.morosystems.education.vaadinexampleapp.managers.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cz.morosystems.education.vaadinexampleapp.dao.CustomerAreaDao;
import cz.morosystems.education.vaadinexampleapp.dao.ProjectDao;
import cz.morosystems.education.vaadinexampleapp.dao.ProjectRoleDao;
import cz.morosystems.education.vaadinexampleapp.dao.UserDao;
import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.entities.Project;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectManager;

/**
 * 
 * @author radoslav.kuzma
 * 
 */
@Component
@Scope("prototype")
public class ProjectManagerImpl implements ProjectManager, Serializable {

    @Autowired
    ProjectRoleDao projectRoleDao;
    @Autowired
    CustomerAreaDao customerAreaDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ProjectDao projectDao;

    @Override
    public Project findProjectById(String id) {
        Project result = null;
        result = projectDao.findProjectRoleById(id);
        Project project = projectDao.findCustomerAreaById(id);
        List<CustomerArea> customerArea = new ArrayList<CustomerArea>();
        customerArea.add(project.getCustomerArea().get(0));
        result.setCustomerArea(customerArea);
        return result;
    }

    @Override
    public List<Project> findProjectByUserName(String username) {
        User user = userDao.findUserByUserName(username);
        List<Project> result = projectDao.findProjectsByUser(user);
        List<Project> resultRoles = projectDao.findProjectsRoleByUser(user);
        int index = 0;
        for (Project item : result) {
            item.setProjectRoles(resultRoles.get(index).getProjectRoles());
            index++;
        }
        return result;
    }
    @Override
    public List<CustomerArea> getAllCustomerAreas() {
        return customerAreaDao.findAll();
    }

    @Override
    public List<ProjectRole> getAllProjectRoles() {
        return projectRoleDao.findAll();
    }

    @Override
    public void save(Project project, String username) {
        User user = userDao.findUserByUserName(username);
        ProjectRole projectRole = projectRoleDao.findProjectRoleByType(project.getProjectRoleType());
        CustomerArea customerArea = customerAreaDao.findCustomerAreaByType(project.getCustomerAreaType());

        List<ProjectRole> projectRoles = new ArrayList<ProjectRole>();
        List<CustomerArea> customerAreas = new ArrayList<CustomerArea>();

        projectRoles.add(projectRole);
        customerAreas.add(customerArea);

        project.setUsers(user);
        project.setCustomerArea(customerAreas);
        project.setProjectRoles(projectRoles);
        project.setCreated(new org.joda.time.DateTime(new Date()));
        projectDao.save(project);

    }

    @Override
    public void deleteProject(String id) {
        Project project = projectDao.findProjectById(id);
        projectDao.remove(project);

    }

    @Override
    public void update(Project project) {
        ProjectRole projectRole = projectRoleDao.findProjectRoleByType(project.getProjectRoleType());
        CustomerArea customerArea = customerAreaDao.findCustomerAreaByType(project.getCustomerAreaType());

        List<ProjectRole> projectRoles = new ArrayList<ProjectRole>();
        List<CustomerArea> customerAreas = new ArrayList<CustomerArea>();

        projectRoles.add(projectRole);
        customerAreas.add(customerArea);

        project.setCustomerArea(customerAreas);
        project.setProjectRoles(projectRoles);


        projectDao.update(project);

    }

}
