package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;


public class AdminCustomerAreasActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -2268192333115028825L;
    private Navigator navigator;

    public AdminCustomerAreasActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/customerAreas");

    }

}
