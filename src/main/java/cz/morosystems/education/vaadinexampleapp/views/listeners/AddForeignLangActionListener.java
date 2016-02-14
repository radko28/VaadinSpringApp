package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * Navigation listener for add page of language.
 * 
 * 
 * @author radoslav.kuzma
 * 
 */
public class AddForeignLangActionListener implements ClickListener {

    private Navigator navigator;

    public AddForeignLangActionListener(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        navigator.navigateTo(MainNavigator.USER_VIEW + "/add?forlangs");

    }

}
