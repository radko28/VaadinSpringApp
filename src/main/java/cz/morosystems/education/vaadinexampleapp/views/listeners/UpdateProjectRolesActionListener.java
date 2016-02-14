package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectRoleManager;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.forms.ProjectRoleForm;


public class UpdateProjectRolesActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1222083405835017659L;


    private Navigator navigator;
    private ProjectRoleForm projectRoleForm;
    private ProjectRoleManager projectRoleManager;

    public UpdateProjectRolesActionListener(Navigator navigator, ProjectRoleForm projectRoleForm, ProjectRoleManager projectRoleManager) {
        this.navigator = navigator;
        this.projectRoleForm = projectRoleForm;
        this.projectRoleManager = projectRoleManager;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        ProjectRole projectRole = projectRoleForm.getProjectRole();

        projectRole.setDescription(projectRoleForm.getDescription());
        projectRole.setName(projectRoleForm.getName());
        projectRole.setProjectRoleType(projectRoleForm.getProjectRoleType());
        projectRoleManager.update(projectRole);
        navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/projectRoles");

    }
}
