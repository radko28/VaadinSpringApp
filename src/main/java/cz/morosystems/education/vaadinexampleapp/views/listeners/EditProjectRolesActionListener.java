package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;


public class EditProjectRolesActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -138817746568101724L;
    private Navigator navigator;
    private String id;

    public EditProjectRolesActionListener(Navigator navigator, String id) {
        this.navigator = navigator;
        this.id = id;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/edit?projectRoleId=" + id);

    }

}
