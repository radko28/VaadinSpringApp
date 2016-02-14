package cz.morosystems.education.vaadinexampleapp.views.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.joda.time.DateTime;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.entities.CustomerAreaType;
import cz.morosystems.education.vaadinexampleapp.entities.Project;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRoleType;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.CustomerAreaData;
import cz.morosystems.education.vaadinexampleapp.util.ProjectRoleData;
import cz.morosystems.education.vaadinexampleapp.views.listeners.ProjectActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.SaveProjectActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.UpdateProjectActionListener;


public class ProjectForm extends FormLayout {

    private Project project;
    private ResourceBundle rb;
    private ComboBox customerAreaCombo;
    private ComboBox projectRoleCombo;
    private TextField company;
    private TextField name;
    private TextField technology;
    private TextArea description;
    private DateField fromDate;
    private DateField toDate;

    /**
     * 
     */
    private static final long serialVersionUID = -5585865104768462521L;

    public ProjectForm(ProjectManager projectManager, Navigator navigator, String username, Project project) {
        this.project = project;


        rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        List<ProjectRoleData> projectRoles = new ArrayList<ProjectRoleData>();
        List<ProjectRole> projectRoleList = projectManager.getAllProjectRoles();
        for (ProjectRole projectRole : projectRoleList) {
            projectRoles.add(new ProjectRoleData(projectRole.getName(), projectRole.getProjectRoleType()));
        }
        BeanItemContainer<ProjectRoleData> projectRolesDataObjects = new BeanItemContainer(ProjectRoleData.class, projectRoles);

        List<CustomerAreaData> customerAreas = new ArrayList<CustomerAreaData>();
        List<CustomerArea> customerAreaList = projectManager.getAllCustomerAreas();
        for (CustomerArea customerArea : customerAreaList) {
            customerAreas.add(new CustomerAreaData(customerArea.getName(), customerArea.getCustomerAreaType()));
        }

        BeanItemContainer<CustomerAreaData> customerAreaDataObjects = new BeanItemContainer(CustomerAreaData.class, customerAreas);

        // customer area
        customerAreaCombo = new ComboBox(rb.getString("projectArea"), customerAreaDataObjects);
        customerAreaCombo.setNullSelectionAllowed(false);
        customerAreaCombo.setItemCaptionPropertyId("name");
        customerAreaCombo.setValue(customerAreaCombo.getItemIds().iterator().next());
        addComponent(customerAreaCombo);
        // project role
        projectRoleCombo = new ComboBox(rb.getString("projectRole"), projectRolesDataObjects);
        projectRoleCombo.setNullSelectionAllowed(false);
        projectRoleCombo.setItemCaptionPropertyId("name");
        projectRoleCombo.setValue(projectRoleCombo.getItemIds().iterator().next());
        addComponent(projectRoleCombo);
        // company of the project
        company = new TextField(rb.getString("projectCompany"));
        addComponent(company);
        // duration of the project
        // from date
        fromDate = new DateField(rb.getString("projectFromDate"));
        fromDate.setDateFormat(AppHelper.DATE_FORMAT);
        addComponent(fromDate);
        // to date
        toDate = new DateField(rb.getString("projectToDate"));
        toDate.setDateFormat(AppHelper.DATE_FORMAT);
        addComponent(toDate);
        // name of the project
        name = new TextField(rb.getString("projectName"));
        addComponent(name);
        // technology
        technology = new TextField(rb.getString("projectTechnology"));
        addComponent(technology);
        // description of the project
        description = new TextArea(rb.getString("projectDesc"));
        description.setWordwrap(false);
        description.setStyleName("v-textarea-input-lang");
        addComponent(description);

        Button saveb = null;

        if (project == null) {

            saveb = new Button(rb.getString("projectAdd"), new SaveProjectActionListener(navigator, this, projectManager, username));
        } else {
            // customer area
            for (Object item : customerAreaCombo.getItemIds()) {
                CustomerAreaData customerAreaData = (CustomerAreaData)item;
                if (customerAreaData.getCustomerAreaType().equals(project.getCustomerAreaTypeView())) {
                    customerAreaCombo.setValue(item);
                    break;
                }
            }
            // project role
            for (Object item : projectRoleCombo.getItemIds()) {
                ProjectRoleData projectRoleData = (ProjectRoleData)item;
                if (projectRoleData.getProjectRoleType().equals(project.getProjectRoleTypeView())) {
                    projectRoleCombo.setValue(item);
                    break;
                }
            }
            // company
            company.setValue(project.getCompany());
            // name
            name.setValue(project.getName());
            technology.setValue(project.getTechnology());
            description.setValue(project.getDescription());
            fromDate.setValue(project.getFromDate().toDate());
            toDate.setValue(project.getToDate().toDate());
            saveb = new Button(rb.getString("projectSave"), new UpdateProjectActionListener(navigator, this, projectManager));
        }

        Button cancelb = new Button(rb.getString("projectStorno"), new ProjectActionListener(navigator));
        GridLayout buttonLayout = new GridLayout(2, 1);
        addComponent(buttonLayout);
        buttonLayout.addComponent(saveb);
        buttonLayout.addComponent(cancelb);


        setSizeUndefined();
        setMargin(true);

    }


    public CustomerAreaType getCustomerAreaCombo() {
        return ((CustomerAreaData)customerAreaCombo.getValue()).getCustomerAreaType();
    }


    public void setCustomerAreaCombo(ComboBox customerAreaCombo) {
        this.customerAreaCombo = customerAreaCombo;
    }


    public ProjectRoleType getProjectRoleCombo() {
        return ((ProjectRoleData)projectRoleCombo.getValue()).getProjectRoleType();
    }


    public void setProjectRoleCombo(ComboBox projectRoleCombo) {
        this.projectRoleCombo = projectRoleCombo;
    }


    public String getCompany() {
        return company.getValue();
    }


    public void setCompany(TextField company) {
        this.company = company;
    }


    public String getName() {
        return name.getValue();
    }


    public void setName(TextField name) {
        this.name = name;
    }


    public String getDescription() {
        return description.getValue();
    }


    public void setDescription(TextArea description) {
        this.description = description;
    }


    public DateTime getFromDate() {
        return new DateTime(fromDate.getValue());
    }


    public DateTime getToDate() {


        return new DateTime(toDate.getValue());
    }


    public String getTechnology() {
        return technology.getValue();
    }


    public void setTechnology(TextField technology) {
        this.technology = technology;
    }


    public Project getProject() {
        return project;
    }


}
