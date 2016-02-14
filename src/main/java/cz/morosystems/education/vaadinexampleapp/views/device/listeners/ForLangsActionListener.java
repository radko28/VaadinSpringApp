package cz.morosystems.education.vaadinexampleapp.views.device.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * Navigation listener for page list of languages for user.
 * 
 * 
 * @author radoslav.kuzma
 * 
 */
public class ForLangsActionListener implements Button.ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -3774087816893679948L;
    private Navigator navigator;

    public ForLangsActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.USER_VIEW + "/forlangs");

    }

}
