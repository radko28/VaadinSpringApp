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

import cz.morosystems.education.vaadinexampleapp.entities.Project;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AddProjectActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.EditProjectActionListener;


public class ProjectList {

    public ProjectList(ProjectManager projectManager, Navigator navigator, String username, VerticalLayout panelContent) {


        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());

        Table table = new Table("");

        table.setImmediate(true);
        table.setSelectable(true);


        table.addContainerProperty(rb.getString("projectName"), String.class, null, rb.getString("projectName"), null, null);
        table.addContainerProperty(rb.getString("projectArea"), String.class, null, rb.getString("projectArea"), null, null);
        table.addContainerProperty(rb.getString("projectRole"), String.class, null, rb.getString("projectRole"), null, null);
        table.addContainerProperty(rb.getString("projectTechnology"), String.class, null, rb.getString("projectTechnology"), null, null);
        table.addContainerProperty(rb.getString("projectCompany"), String.class, null, rb.getString("projectCompany"), null, null);
        table.addContainerProperty(rb.getString("projectFromDate"), String.class, null, rb.getString("projectFromDate"), null, null);
        table.addContainerProperty(rb.getString("projectToDate"), String.class, null, rb.getString("projectToDate"), null, null);
        table.addContainerProperty(rb.getString("projectAdded"), String.class, null, rb.getString("projectAdded"), null, null);
        table.addContainerProperty(rb.getString("projectDesc"), String.class, null, rb.getString("projectDesc"), null, null);
        table.addContainerProperty(rb.getString("projectActions"), HorizontalLayout.class, null, rb.getString("projectActions"), null, null);


        List<Project> projectList = projectManager.findProjectByUserName(username);

        HorizontalLayout hbox = null;
        String projectDeleteConfirm = rb.getString("projectDeleteConfirm");
        if (projectList != null) {
            for (Project project : projectList) {
                hbox = new HorizontalLayout();
                Button edit = new Button(rb.getString("projectEdit"), new EditProjectActionListener(navigator, project.getId()));
                edit.setStyleName("link");
                hbox.addComponent(edit);
                Label span = new Label(" | ");
                span.setStyleName("link-label");
                hbox.addComponent(span);
                String projectName = " " + project.getName();
                Link delete = new Link(rb.getString("projectDelete"), new ExternalResource(
                        "javascript:cz.morosystems.education.vaadinexampleapp.views.forms.project(confirm(" + projectDeleteConfirm
                                + projectName + "?'), '" + project.getId() + "')"));
                delete.setStyleName("link");
                hbox.addComponent(delete);

                table.addItem(
                        new Object[] { project.getName().toString(), project.getCustomerAreaTypeView().toString(),
                                project.getProjectRoleTypeView().toString(), project.getTechnology(), project.getCompany(),
                                AppHelper.getCreatedString(project.getFromDate()), AppHelper.getCreatedString(project.getToDate()),
                                AppHelper.getCreatedString(project.getCreated()), project.getDescription(), hbox }, project.getId());


            }
        }
        panelContent.addComponent(table);
        panelContent.setComponentAlignment(table, Alignment.TOP_LEFT);

        Button addDeviceb = new Button(rb.getString("projectAddnewProject"), new AddProjectActionListener(navigator));
        addDeviceb.setStyleName("link");
        panelContent.addComponent(addDeviceb);

    }

}
