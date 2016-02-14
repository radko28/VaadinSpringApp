package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

import cz.morosystems.education.vaadinexampleapp.util.UserUtil;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * 
 * Logout listener. Calls if you want to finish work wit application.
 * 
 * @author radoslav.kuzma
 * 
 */
public class LogoutActionListener implements Button.ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -4777294451317812657L;
    Navigator navigator;

    public LogoutActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
        UserUtil.getInstance().setLogged(false);
        navigator.navigateTo(MainNavigator.LOGIN_VIEW);

    }
}
