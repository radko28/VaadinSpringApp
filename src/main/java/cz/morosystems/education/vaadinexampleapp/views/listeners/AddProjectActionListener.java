package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;


public class AddProjectActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -5021626034402900620L;
    private Navigator navigator;

    public AddProjectActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.USER_VIEW + "/add?project");

    }

}
