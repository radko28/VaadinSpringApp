package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.entities.Project;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectManager;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.forms.ProjectForm;


public class UpdateProjectActionListener implements ClickListener {

    private Navigator navigator;
    private ProjectForm projectForm;
    private ProjectManager projectManager;


    public UpdateProjectActionListener(Navigator navigator, ProjectForm projectForm, ProjectManager projectManager) {
        this.navigator = navigator;
        this.projectForm = projectForm;
        this.projectManager = projectManager;

    }

    @Override
    public void buttonClick(ClickEvent event) {
        Project project = projectForm.getProject();
        project.setCompany(projectForm.getCompany());
        project.setDescription(projectForm.getDescription());
        project.setName(projectForm.getName());
        project.setTechnology(projectForm.getTechnology());
        project.setProjectRoleType(projectForm.getProjectRoleCombo());
        project.setCustomerAreaType(projectForm.getCustomerAreaCombo());
        project.setFromDate(projectForm.getFromDate());
        project.setToDate(projectForm.getToDate());

        projectManager.update(project);
        navigator.navigateTo(MainNavigator.USER_VIEW + "/project");

    }

}
