package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.managers.CustomerAreaManager;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.forms.CustomerAreaForm;


public class SaveCustomAreaActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -7736002386792989881L;

    private Navigator navigator;
    private CustomerAreaForm customerAreaForm;
    private CustomerAreaManager customerAreaManager;

    public SaveCustomAreaActionListener(Navigator navigator, CustomerAreaForm customerAreaForm, CustomerAreaManager customerAreaManager) {
        this.navigator = navigator;
        this.customerAreaForm = customerAreaForm;
        this.customerAreaManager = customerAreaManager;

    }

    @Override
    public void buttonClick(ClickEvent event) {
        CustomerArea customerArea = new CustomerArea();

        customerArea.setDescription(customerAreaForm.getDescription());
        customerArea.setName(customerAreaForm.getName());
        customerArea.setCustomerAreaType(customerAreaForm.getCustomerRoleType());
        customerAreaManager.save(customerArea);
        navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/customerAreas");

    }

}
