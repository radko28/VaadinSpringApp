package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * Navigation listener for edit page of language.
 * 
 * 
 * @author radoslav.kuzma
 * 
 */

public class EditForeignLangActionListener implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = -4917156721689403108L;
    private Navigator navigator;
    private String id;

    public EditForeignLangActionListener(Navigator navigator, String id) {
        this.navigator = navigator;
        this.id = id;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.USER_VIEW + "/edit?forlangsId=" + id);

    }

}
