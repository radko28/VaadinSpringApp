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

import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.entities.CustomerAreaType;
import cz.morosystems.education.vaadinexampleapp.managers.CustomerAreaManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.CustomerAreaData;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AdminProjectRolesActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.SaveCustomAreaActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.UpdateCustomAreaActionListener;


public class CustomerAreaForm extends FormLayout {

    /**
     * 
     */
    private static final long serialVersionUID = -6162952442477408882L;
    private CustomerArea customerArea;
    private ResourceBundle rb;
    private ComboBox customerAreaCombo;
    private TextField name;
    private TextArea description;

    public CustomerAreaForm(CustomerAreaManager customerAreaManager, Navigator navigator, CustomerArea customerArea) {
        this.customerArea = customerArea;
        rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());

        List<CustomerAreaData> customerAreas = new ArrayList<CustomerAreaData>();
        CustomerAreaType[] customerAreaTypes = CustomerAreaType.values();
        for (CustomerAreaType customerAreaType : customerAreaTypes) {
            customerAreas.add(new CustomerAreaData(customerAreaType.toString(), customerAreaType));

        }
        BeanItemContainer<CustomerAreaData> customerAreaDataObjects = new BeanItemContainer(CustomerAreaData.class, customerAreas);

        // project role
        customerAreaCombo = new ComboBox(rb.getString("customerArea"), customerAreaDataObjects);
        customerAreaCombo.setNullSelectionAllowed(false);
        customerAreaCombo.setItemCaptionPropertyId("name");
        customerAreaCombo.setValue(customerAreaCombo.getItemIds().iterator().next());
        addComponent(customerAreaCombo);
        // name of the project
        name = new TextField(rb.getString("customerAreaName"));
        addComponent(name);
        // description of the project
        description = new TextArea(rb.getString("customerAreaDesc"));
        description.setWordwrap(false);
        description.setStyleName("v-textarea-input-lang");
        addComponent(description);

        Button saveb = null;

        if (customerArea == null) {

            saveb = new Button(rb.getString("customerAreaAdd"), new SaveCustomAreaActionListener(navigator, this, customerAreaManager));
        } else {

            // project role
            for (Object item : customerAreaCombo.getItemIds()) {
                CustomerAreaData customerAreaData = (CustomerAreaData)item;
                if (customerAreaData.getCustomerAreaType().equals(customerArea.getCustomerAreaType())) {
                    customerAreaCombo.setValue(item);
                    break;
                }
            }

            // name
            name.setValue(customerArea.getName());
            description.setValue(customerArea.getDescription());
            saveb = new Button(rb.getString("customerAreaSave"), new UpdateCustomAreaActionListener(navigator, this, customerAreaManager));
        }

        Button cancelb = new Button(rb.getString("customerAreaStorno"), new AdminProjectRolesActionListener(navigator));
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

    public CustomerAreaType getCustomerRoleType() {
        return ((CustomerAreaData)customerAreaCombo.getValue()).getCustomerAreaType();
    }


    public CustomerArea getCustomerArea() {
        return customerArea;
    }

}
