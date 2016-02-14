package cz.morosystems.education.vaadinexampleapp.views.device.listeners;


import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

import cz.morosystems.education.vaadinexampleapp.util.DeviceType;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * 
 * Listener that navigate to form for adding device.
 * 
 * @author radoslav.kuzma
 * 
 */
public class AddActionListener implements Button.ClickListener {


    private static final long serialVersionUID = 3804401577122433346L;
    DeviceType type;
    Navigator navigator;

    public AddActionListener(Navigator navigator, DeviceType type) {
        this.type = type;
        this.navigator = navigator;

    }

    @Override
    public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {

        navigator.navigateTo(MainNavigator.USER_VIEW + "/add?device=" + type);

    }
}
