package cz.morosystems.education.vaadinexampleapp.views.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRoleType;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectRoleManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.ProjectRoleData;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AdminProjectRolesActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.SaveProjectRolesActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.UpdateProjectRolesActionListener;


public class ProjectRoleForm extends FormLayout {

    /**
     * 
     */
    private static final long serialVersionUID = -1109605461731913177L;

    private ProjectRole projectRole;
    private ResourceBundle rb;
    private ComboBox projectRoleCombo;
    private TextField name;
    private TextArea description;


    public ProjectRoleForm(ProjectRoleManager projectRoleManager, Navigator navigator, ProjectRole projectRole) {
        this.projectRole = projectRole;
        rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());

        List<ProjectRoleData> projectRoles = new ArrayList<ProjectRoleData>();
        ProjectRoleType[] projectRoleTypes = ProjectRoleType.values();
        for (ProjectRoleType projectRoleType : projectRoleTypes) {
            projectRoles.add(new ProjectRoleData(projectRoleType.toString(), projectRoleType));

        }
        BeanItemContainer<ProjectRoleData> projectRolesDataObjects = new BeanItemContainer(ProjectRoleData.class, projectRoles);

        // project role
        projectRoleCombo = new ComboBox(rb.getString("projectRole"), projectRolesDataObjects);
        projectRoleCombo.setNullSelectionAllowed(false);
        projectRoleCombo.setItemCaptionPropertyId("name");
        projectRoleCombo.setValue(projectRoleCombo.getItemIds().iterator().next());
        addComponent(projectRoleCombo);
        // name of the project
        name = new TextField(rb.getString("projectRoleName"));
        addComponent(name);
        // description of the project
        description = new TextArea(rb.getString("projectRoleDesc"));
        description.setWordwrap(false);
        description.setStyleName("v-textarea-input-lang");
        addComponent(description);

        Button saveb = null;

        if (projectRole == null) {

            saveb = new Button(rb.getString("projectRoleAdd"), new SaveProjectRolesActionListener(navigator, this, projectRoleManager));
        } else {

            // project role
            for (Object item : projectRoleCombo.getItemIds()) {
                ProjectRoleData projectRoleData = (ProjectRoleData)item;
                if (projectRoleData.getProjectRoleType().equals(projectRole.getProjectRoleType())) {
                    projectRoleCombo.setValue(item);
                    break;
                }
            }

            // name
            name.setValue(projectRole.getName());
            description.setValue(projectRole.getDescription());
            saveb = new Button(rb.getString("projectRoleSave"), new UpdateProjectRolesActionListener(navigator, this, projectRoleManager));
        }

        Button cancelb = new Button(rb.getString("projectRoleStorno"), new AdminProjectRolesActionListener(navigator));
        GridLayout buttonLayout = new GridLayout(2, 1);
        addComponent(buttonLayout);
        buttonLayout.addComponent(saveb);
        buttonLayout.addComponent(cancelb);


        setSizeUndefined();
        setMargin(true);


    }


    public String getName() {
        return name.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }

    public ProjectRoleType getProjectRoleType() {
        return ((ProjectRoleData)projectRoleCombo.getValue()).getProjectRoleType();
    }


    public ProjectRole getProjectRole() {
        return projectRole;
    }

}
