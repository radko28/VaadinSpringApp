package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.ViewChangeListener;

import cz.morosystems.education.vaadinexampleapp.util.UserUtil;
import cz.morosystems.education.vaadinexampleapp.views.LoginView;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.device.DevicesView;
import cz.morosystems.education.vaadinexampleapp.views.user.UserView;

/**
 * 
 * Security listener for changing navigation between views.
 * 
 * @author radoslav.kuzma
 * 
 */
public class ViewChangeSecurityChecker implements ViewChangeListener {

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {


        boolean result = false;

        if (event.getNewView() instanceof LoginView && event.getOldView() == null) {
            result = true;
        }

        if (event.getViewName().equals(MainNavigator.ADMIN_VIEW) && event.getOldView() instanceof DevicesView) {
            result = false;
        }
        if (event.getViewName().equals(MainNavigator.USER_VIEW) && event.getOldView() instanceof UserView) {
            result = false;
        }
        if (event.getViewName().equals(MainNavigator.ADMIN_VIEW) || event.getViewName().equals(MainNavigator.USER_VIEW)) {
            boolean logged = UserUtil.getInstance().isLogged();
            result = logged;
        }

        if (event.getViewName().equals(MainNavigator.LOGIN_VIEW) && event.getOldView() instanceof UserView) {
            UserUtil.getInstance().setLogged(false);
            result = true;
        }

        if (event.getViewName().equals(MainNavigator.LOGIN_VIEW) && event.getOldView() instanceof DevicesView) {
            UserUtil.getInstance().setLogged(false);
            result = true;
        }

        return result;
    }
    @Override
    public void afterViewChange(ViewChangeEvent event) {


    }
}