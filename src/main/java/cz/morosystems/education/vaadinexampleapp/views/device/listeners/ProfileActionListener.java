package cz.morosystems.education.vaadinexampleapp.views.device.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * 
 * Listener that navigate to profile form.
 * 
 * @author radoslav.kuzma
 * 
 */
public class ProfileActionListener implements Button.ClickListener {

    Navigator navigator;

    public ProfileActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
        navigator.navigateTo(MainNavigator.USER_VIEW + "/profile");

    }
}
