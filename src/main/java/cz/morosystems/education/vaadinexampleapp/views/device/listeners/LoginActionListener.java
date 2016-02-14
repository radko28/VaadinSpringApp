package cz.morosystems.education.vaadinexampleapp.views.device.listeners;

import java.io.Serializable;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.UserUtil;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * Login action after user authorization
 * 
 * @author radoslav.kuzma
 * 
 */
public class LoginActionListener implements Button.ClickListener, Serializable {

    private static final long serialVersionUID = 5L;

    private Navigator navigator;

    public LoginActionListener(Navigator navigator) {

        this.navigator = navigator;
    }

    @Override
    public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
        UserUtil.getInstance().setLogged(true);
        UserUtil.getInstance().getHeaderPanelDevices().setComponents();
        UserUtil.getInstance().getHeaderPanelUser().setComponents();

        if (AppHelper.hasUserRole()) {
            navigator.navigateTo(MainNavigator.USER_VIEW);
        } else if (AppHelper.hasAdminRole()) {
            navigator.navigateTo(MainNavigator.ADMIN_VIEW);
        }
    }
}