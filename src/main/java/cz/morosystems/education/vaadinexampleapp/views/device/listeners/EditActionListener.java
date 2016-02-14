package cz.morosystems.education.vaadinexampleapp.views.device.listeners;


import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * 
 * Listener that navigate to form for editing device.
 * 
 * @author radoslav.kuzma
 * 
 */

public class EditActionListener implements Button.ClickListener {


    /**
     * 
     */
    private static final long serialVersionUID = -6161590665146162092L;
    String deviceId;
    Navigator navigator;

    public EditActionListener(Navigator navigator, String deviceId) {
        this.deviceId = deviceId;
        this.navigator = navigator;

    }

    @Override
    public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {

        navigator.navigateTo(MainNavigator.USER_VIEW + "/edit?deviceId=" + deviceId);

    }
}
