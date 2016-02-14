package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.entities.Project;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectManager;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.forms.ProjectForm;


public class SaveProjectActionListener implements ClickListener {

    private Navigator navigator;
    private ProjectForm projectForm;
    private ProjectManager projectManager;
    private String username;

    public SaveProjectActionListener(Navigator navigator, ProjectForm projectForm, ProjectManager projectManager, String username) {
        this.navigator = navigator;
        this.projectForm = projectForm;
        this.projectManager = projectManager;
        this.username = username;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        // if (foreignLangForm.validate()) {
        Project project = new Project();
        project.setCompany(projectForm.getCompany());
        project.setDescription(projectForm.getDescription());
        project.setName(projectForm.getName());
        project.setTechnology(projectForm.getTechnology());
        project.setProjectRoleType(projectForm.getProjectRoleCombo());
        project.setCustomerAreaType(projectForm.getCustomerAreaCombo());
        project.setFromDate(projectForm.getFromDate());
        project.setToDate(projectForm.getToDate());
        projectManager.save(project, username);
        navigator.navigateTo(MainNavigator.USER_VIEW + "/project");
        // }

    }
}
