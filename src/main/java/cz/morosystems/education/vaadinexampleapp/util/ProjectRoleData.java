package cz.morosystems.education.vaadinexampleapp.util;

import cz.morosystems.education.vaadinexampleapp.entities.ProjectRoleType;


public class ProjectRoleData {

    private String name;
    private ProjectRoleType projectRoleType;

    public ProjectRoleData(String name, ProjectRoleType projectRoleType) {
        this.name = name;
        this.projectRoleType = projectRoleType;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public ProjectRoleType getProjectRoleType() {
        return projectRoleType;
    }


    public void setProjectRoleType(ProjectRoleType projectRoleType) {
        this.projectRoleType = projectRoleType;
    }

}
