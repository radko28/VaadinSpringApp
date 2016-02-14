package cz.morosystems.education.vaadinexampleapp.views.forms;

import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectRoleManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AddProjectRolesActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.EditProjectRolesActionListener;


public class ProjectRoleList {

    public ProjectRoleList(ProjectRoleManager projectRoleManager, Navigator navigator, VerticalLayout panelContent) {
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());

        Table table = new Table("");

        table.setImmediate(true);
        table.setSelectable(true);


        table.addContainerProperty(rb.getString("projectRoleName"), String.class, null, rb.getString("projectRoleName"), null, null);
        table.addContainerProperty(rb.getString("projectRoleType"), String.class, null, rb.getString("projectRoleType"), null, null);
        table.addContainerProperty(rb.getString("projectRoleDesc"), String.class, null, rb.getString("projectRoleDesc"), null, null);
        table.addContainerProperty(rb.getString("projectRoleCreated"), String.class, null, rb.getString("projectRoleCreated"), null, null);
        table.addContainerProperty(rb.getString("projectRoleActions"), HorizontalLayout.class, null, rb.getString("projectRoleActions"),
                null, null);


        List<ProjectRole> projectRoleList = projectRoleManager.findProjectRoles();

        HorizontalLayout hbox = null;
        String projectRoleDeleteConfirm = rb.getString("projectRoleDeleteConfirm");
        if (projectRoleList != null) {
            for (ProjectRole projectRole : projectRoleList) {
                hbox = new HorizontalLayout();
                Button edit = new Button(rb.getString("projectRoleEdit"),
                        new EditProjectRolesActionListener(navigator, projectRole.getId()));
                edit.setStyleName("link");
                hbox.addComponent(edit);
                Label span = new Label(" | ");
                span.setStyleName("link-label");
                hbox.addComponent(span);
                String projectRoleName = " " + projectRole.getName();
                Link delete = new Link(rb.getString("projectRoleDelete"), new ExternalResource(
                        "javascript:cz.morosystems.education.vaadinexampleapp.views.forms.projectrole(confirm(" + projectRoleDeleteConfirm
                                + projectRoleName + "?'), '" + projectRole.getId() + "')"));
                delete.setStyleName("link");
                hbox.addComponent(delete);

                table.addItem(
                        new Object[] { projectRole.getName().toString(), projectRole.getProjectRoleType().toString(),
                                projectRole.getDescription(), AppHelper.getCreatedString(projectRole.getCreated()), hbox },
                        projectRole.getId());


            }
        }
        panelContent.addComponent(table);
        panelContent.setComponentAlignment(table, Alignment.TOP_LEFT);

        Button addDeviceb = new Button(rb.getString("projectRoleAddnewProject"), new AddProjectRolesActionListener(navigator));
        addDeviceb.setStyleName("link");
        panelContent.addComponent(addDeviceb);

    }

}
