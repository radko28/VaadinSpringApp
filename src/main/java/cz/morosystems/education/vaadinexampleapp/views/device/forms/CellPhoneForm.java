package cz.morosystems.education.vaadinexampleapp.views.device.forms;

import java.util.ResourceBundle;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import cz.morosystems.education.vaadinexampleapp.entities.CellPhone;
import cz.morosystems.education.vaadinexampleapp.managers.DeviceManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.DevicesActionListener;

/**
 * 
 * Form for edit and add Cell Phone device.
 * 
 * 
 * @author radoslav.kuzma
 * 
 */

public class CellPhoneForm extends FormLayout {

    private TextField name;
    private TextField phoneNumber;
    String empty = "";

    public CellPhoneForm(final DeviceManager deviceManager, final Navigator navigator, final String username, final CellPhone device) {
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        empty = rb.getString("field.required");
        name = new TextField(rb.getString("cellphoneName"));
        addComponent(name);
        phoneNumber = new TextField(rb.getString("cellphonePhoneNumber"));
        addComponent(phoneNumber);
        if (device != null) {
            name.setValue(device.getName());
            phoneNumber.setValue(device.getPhoneNumber());
        }
        Button saveb = null;
        if (device == null) {
            saveb = new Button(rb.getString("cellphoneAdd"), new Button.ClickListener() {

                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    if (validate()) {
                        CellPhone phone = new CellPhone();
                        phone.setName(name.getValue());
                        phone.setPhoneNumber(phoneNumber.getValue());
                        deviceManager.save(phone, username);
                        navigator.navigateTo(MainNavigator.USER_VIEW + "/");
                    }
                }
            });
        } else {
            saveb = new Button(rb.getString("cellphoneSave"), new Button.ClickListener() {

                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    if (validate()) {
                        device.setName(name.getValue());
                        device.setPhoneNumber(phoneNumber.getValue());
                        deviceManager.update(device, username);
                        navigator.navigateTo(MainNavigator.USER_VIEW + "/");
                    }
                }
            });
        }

        Button cancelb = new Button(rb.getString("cellphoneStorno"), new DevicesActionListener(navigator));
        GridLayout buttonLayout = new GridLayout(2, 1);
        addComponent(buttonLayout);
        buttonLayout.addComponent(saveb);
        buttonLayout.addComponent(cancelb);


        setSizeUndefined();
        setMargin(true);
    }

    boolean validate() {
        boolean valid = true;
        if (name.getValue().length() < 1) {
            name.setComponentError(new UserError(empty));
            valid = false;
        } else {
            name.setComponentError(null);
        }

        if (phoneNumber.getValue().length() < 1) {
            phoneNumber.setComponentError(new UserError(empty));
            valid = false;
        } else {
            phoneNumber.setComponentError(null);
        }

        return valid;
    }

    public String getName() {
        return name.getValue();
    }
}
