package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;


public class AdminProjectRolesActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = 2897771417044059342L;
    private Navigator navigator;

    public AdminProjectRolesActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/projectRoles");

    }

}
