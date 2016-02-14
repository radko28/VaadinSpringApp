package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;


public class EditProjectActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -3795027004439603133L;
    private Navigator navigator;
    private String id;

    public EditProjectActionListener(Navigator navigator, String id) {
        this.navigator = navigator;
        this.id = id;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.USER_VIEW + "/edit?projectId=" + id);

    }

}
