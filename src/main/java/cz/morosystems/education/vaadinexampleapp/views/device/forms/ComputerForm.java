package cz.morosystems.education.vaadinexampleapp.views.device.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import cz.morosystems.education.vaadinexampleapp.entities.Computer;
import cz.morosystems.education.vaadinexampleapp.entities.ComputerType;
import cz.morosystems.education.vaadinexampleapp.managers.DeviceManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.ComputerData;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.DevicesActionListener;

/**
 * 
 * Form for edit and add Computer device.
 * 
 * 
 * @author radoslav.kuzma
 * 
 */
public class ComputerForm extends FormLayout {

    /**
     * 
     */
    private static final long serialVersionUID = -968431417617627235L;

    private TextField name;
    private Computer device;
    private ComboBox computerTypeCombo;
    String empty = "";

    public ComputerForm(final DeviceManager deviceManager, final Navigator navigator, final String username, final Computer device) {
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        empty = rb.getString("field.required");
        List<ComputerData> computerTypes = new ArrayList<ComputerData>();
        computerTypes.add(new ComputerData(ComputerType.NOTEBOOK.toString(), ComputerType.NOTEBOOK));
        computerTypes.add(new ComputerData(ComputerType.PC.toString(), ComputerType.PC));
        BeanItemContainer<ComputerData> objects = new BeanItemContainer(ComputerData.class, computerTypes);


        computerTypeCombo = new ComboBox(rb.getString("computerComputerType"), objects);
        computerTypeCombo.setNullSelectionAllowed(false);
        computerTypeCombo.setItemCaptionPropertyId("description");
        computerTypeCombo.setValue(computerTypeCombo.getItemIds().iterator().next());
        addComponent(computerTypeCombo);

        name = new TextField(rb.getString("computerName"));
        addComponent(name);

        if (device != null) {
            name.setValue(device.getName());
            for (Object item : computerTypeCombo.getItemIds()) {
                ComputerData computerData = (ComputerData)item;
                if (computerData.getComputerType().equals(device.getComputerType())) {
                    computerTypeCombo.setValue(item);
                    break;
                }

            }
        }
        Button saveb = null;
        if (device == null) {
            saveb = new Button(rb.getString("computerAdd"), new Button.ClickListener() {

                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    if (validate()) {
                        Computer comp = new Computer();
                        comp.setName(name.getValue());
                        comp.setComputerType(((ComputerData)computerTypeCombo.getValue()).getComputerType());
                        deviceManager.save(comp, username);
                        navigator.navigateTo(MainNavigator.USER_VIEW + "/");
                    }
                }
            });
        } else {
            saveb = new Button(rb.getString("computerSave"), new Button.ClickListener() {

                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    if (validate()) {
                        device.setName(name.getValue());
                        device.setComputerType(((ComputerData)computerTypeCombo.getValue()).getComputerType());
                        deviceManager.update(device, username);
                        navigator.navigateTo(MainNavigator.USER_VIEW + "/");
                    }
                }
            });
        }


        Button cancelb = new Button(rb.getString("computerStorno"), new DevicesActionListener(navigator));

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
            valid = false;
            name.setComponentError(new UserError(empty));
        } else {
            name.setComponentError(null);
        }

        return valid;
    }
}
