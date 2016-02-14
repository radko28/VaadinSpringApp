package cz.morosystems.education.vaadinexampleapp.views.device.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * 
 * Navigate to list of devices.
 * 
 * @author radoslav.kuzma
 * 
 */
public class DevicesActionListener implements Button.ClickListener {

    private static final long serialVersionUID = 2833879408119033467L;
    private Navigator navigator;

    public DevicesActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
        navigator.navigateTo(MainNavigator.USER_VIEW + "/");

    }
}
