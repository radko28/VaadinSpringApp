package cz.morosystems.education.vaadinexampleapp.views.listeners;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import cz.morosystems.education.vaadinexampleapp.entities.ForeignLanguage;
import cz.morosystems.education.vaadinexampleapp.managers.ForLangsManager;
import cz.morosystems.education.vaadinexampleapp.util.ForeingLanguageTypeData;
import cz.morosystems.education.vaadinexampleapp.util.LanguageLevelTypeData;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.forms.ForeignLangForm;

/**
 * Navigation listener for save new language to database.
 * 
 * 
 * @author radoslav.kuzma
 * 
 */
public class SaveForeignLangActionListener implements ClickListener {

    private Navigator navigator;
    private ForeignLangForm foreignLangForm;
    private ForLangsManager forlangsManager;
    private String username;

    public SaveForeignLangActionListener(Navigator navigator, ForeignLangForm foreignLangForm, ForLangsManager forlangsManager,
            String username) {
        this.navigator = navigator;
        this.foreignLangForm = foreignLangForm;
        this.forlangsManager = forlangsManager;
        this.username = username;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (foreignLangForm.validate()) {
            ForeignLanguage foreingLanguage = new ForeignLanguage();
            foreingLanguage.setLanguage(((ForeingLanguageTypeData)foreignLangForm.getForeingLanguageTypesCombo().getValue()).getLanguage());
            foreingLanguage.setLevel(((LanguageLevelTypeData)foreignLangForm.getLanguageLevelTypesCombo().getValue()).getLevel());
            foreingLanguage.setDescription(foreignLangForm.getDescription());
            forlangsManager.save(foreingLanguage, username);
            navigator.navigateTo(MainNavigator.USER_VIEW + "/forlangs");
        }

    }

}
