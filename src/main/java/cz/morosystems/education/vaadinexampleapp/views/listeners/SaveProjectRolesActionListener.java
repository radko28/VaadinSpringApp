package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectRoleManager;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.forms.ProjectRoleForm;


public class SaveProjectRolesActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -417292860741365511L;

    private Navigator navigator;
    private ProjectRoleForm projectRoleForm;
    private ProjectRoleManager projectRoleManager;


    public SaveProjectRolesActionListener(Navigator navigator, ProjectRoleForm projectRoleForm, ProjectRoleManager projectRoleManager) {
        this.navigator = navigator;
        this.projectRoleForm = projectRoleForm;
        this.projectRoleManager = projectRoleManager;

    }

    @Override
    public void buttonClick(ClickEvent event) {
        ProjectRole projectRole = new ProjectRole();

        projectRole.setDescription(projectRoleForm.getDescription());
        projectRole.setName(projectRoleForm.getName());
        projectRole.setProjectRoleType(projectRoleForm.getProjectRoleType());
        projectRoleManager.save(projectRole);
        navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/projectRoles");

    }

}
