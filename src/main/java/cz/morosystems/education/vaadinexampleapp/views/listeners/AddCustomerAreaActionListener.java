package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;


public class AddCustomerAreaActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -7070750297713669968L;

    private Navigator navigator;

    public AddCustomerAreaActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/add?customerArea");

    }

}
