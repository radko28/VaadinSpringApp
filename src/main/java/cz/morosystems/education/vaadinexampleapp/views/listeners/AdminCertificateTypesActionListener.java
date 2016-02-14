package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;


public class AdminCertificateTypesActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -9173883329079266413L;
    private Navigator navigator;

    public AdminCertificateTypesActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/certificateTypes");

    }

}
