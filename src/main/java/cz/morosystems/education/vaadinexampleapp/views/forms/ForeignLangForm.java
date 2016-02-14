package cz.morosystems.education.vaadinexampleapp.views.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;

import cz.morosystems.education.vaadinexampleapp.entities.ForeignLanguage;
import cz.morosystems.education.vaadinexampleapp.entities.ForeingLanguageType;
import cz.morosystems.education.vaadinexampleapp.entities.LanguageLevelType;
import cz.morosystems.education.vaadinexampleapp.managers.ForLangsManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.ForeingLanguageTypeData;
import cz.morosystems.education.vaadinexampleapp.util.LanguageLevelTypeData;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.ForLangsActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.SaveForeignLangActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.UpdateForeignLangActionListener;

/**
 * 
 * Forms for edit and add foreign language.
 * 
 * @author radoslav.kuzma
 * 
 */
public class ForeignLangForm extends FormLayout {

    private TextArea description;
    private ComboBox languageLevelTypesCombo;
    private ComboBox foreingLanguageTypesCombo;
    private ForeignLanguage foreingLanguage;
    private ResourceBundle rb;


    public ForeignLangForm(ForLangsManager forlangsManager, Navigator navigator, String username, ForeignLanguage foreingLanguage) {

        this.foreingLanguage = foreingLanguage;


        rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        List<ForeingLanguageTypeData> foreignLanguageTypes = new ArrayList<ForeingLanguageTypeData>();
        foreignLanguageTypes.add(new ForeingLanguageTypeData(ForeingLanguageType.ANG.toString(), ForeingLanguageType.ANG));
        foreignLanguageTypes.add(new ForeingLanguageTypeData(ForeingLanguageType.GER.toString(), ForeingLanguageType.GER));
        foreignLanguageTypes.add(new ForeingLanguageTypeData(ForeingLanguageType.ESP.toString(), ForeingLanguageType.ESP));
        foreignLanguageTypes.add(new ForeingLanguageTypeData(ForeingLanguageType.ITA.toString(), ForeingLanguageType.ITA));
        foreignLanguageTypes.add(new ForeingLanguageTypeData(ForeingLanguageType.FRA.toString(), ForeingLanguageType.FRA));
        foreignLanguageTypes.add(new ForeingLanguageTypeData(ForeingLanguageType.RUS.toString(), ForeingLanguageType.RUS));
        BeanItemContainer<ForeingLanguageTypeData> foreignLanguageTypesObjects = new BeanItemContainer(ForeingLanguageTypeData.class,
                foreignLanguageTypes);

        List<LanguageLevelTypeData> languageLevelTypes = new ArrayList<LanguageLevelTypeData>();
        languageLevelTypes.add(new LanguageLevelTypeData(LanguageLevelType.A1.toString(), LanguageLevelType.A1));
        languageLevelTypes.add(new LanguageLevelTypeData(LanguageLevelType.A2.toString(), LanguageLevelType.A2));
        languageLevelTypes.add(new LanguageLevelTypeData(LanguageLevelType.B1.toString(), LanguageLevelType.B1));
        languageLevelTypes.add(new LanguageLevelTypeData(LanguageLevelType.B2.toString(), LanguageLevelType.B2));
        languageLevelTypes.add(new LanguageLevelTypeData(LanguageLevelType.C1.toString(), LanguageLevelType.C1));
        languageLevelTypes.add(new LanguageLevelTypeData(LanguageLevelType.C2.toString(), LanguageLevelType.C2));

        BeanItemContainer<LanguageLevelTypeData> languageLevelTypesObjects = new BeanItemContainer(LanguageLevelTypeData.class,
                languageLevelTypes);


        foreingLanguageTypesCombo = new ComboBox(rb.getString("forlangsName"), foreignLanguageTypesObjects);
        foreingLanguageTypesCombo.setNullSelectionAllowed(false);
        foreingLanguageTypesCombo.setItemCaptionPropertyId("description");
        foreingLanguageTypesCombo.setValue(foreingLanguageTypesCombo.getItemIds().iterator().next());
        addComponent(foreingLanguageTypesCombo);

        languageLevelTypesCombo = new ComboBox(rb.getString("forlangsLevel"), languageLevelTypesObjects);
        languageLevelTypesCombo.setNullSelectionAllowed(false);
        languageLevelTypesCombo.setItemCaptionPropertyId("description");
        languageLevelTypesCombo.setValue(languageLevelTypesCombo.getItemIds().iterator().next());
        addComponent(languageLevelTypesCombo);


        description = new TextArea(rb.getString("forlangsDesc"));
        description.setWordwrap(false);
        description.setStyleName("v-textarea-input-lang");
        addComponent(description);
        Button saveb = null;

        if (foreingLanguage == null) {

            saveb = new Button(rb.getString("forlangsAdd"), new SaveForeignLangActionListener(navigator, this, forlangsManager, username));
        } else {
            description.setValue(foreingLanguage.getDescription());
            for (Object item : foreingLanguageTypesCombo.getItemIds()) {
                ForeingLanguageTypeData foreingLanguageTypeData = (ForeingLanguageTypeData)item;
                if (foreingLanguageTypeData.getLanguage().equals(foreingLanguage.getLanguage())) {
                    foreingLanguageTypesCombo.setValue(item);
                    break;
                }

            }
            for (Object item : languageLevelTypesCombo.getItemIds()) {
                LanguageLevelTypeData languageLevelTypeData = (LanguageLevelTypeData)item;
                if (languageLevelTypeData.getLevel().equals(foreingLanguage.getLevel())) {
                    languageLevelTypesCombo.setValue(item);
                    break;
                }

            }


            saveb = new Button(rb.getString("forlangsSave"),
                    new UpdateForeignLangActionListener(navigator, this, forlangsManager, username));
        }

        Button cancelb = new Button(rb.getString("forlangsStorno"), new ForLangsActionListener(navigator));
        GridLayout buttonLayout = new GridLayout(2, 1);
        addComponent(buttonLayout);
        buttonLayout.addComponent(saveb);
        buttonLayout.addComponent(cancelb);


        setSizeUndefined();
        setMargin(true);

    }


    public ComboBox getLanguageLevelTypesCombo() {
        return languageLevelTypesCombo;
    }


    public void setLanguageLevelTypesCombo(ComboBox languageLevelTypesCombo) {
        this.languageLevelTypesCombo = languageLevelTypesCombo;
    }


    public ComboBox getForeingLanguageTypesCombo() {
        return foreingLanguageTypesCombo;
    }


    public void setForeingLanguageTypesCombo(ComboBox foreingLanguageTypesCombo) {
        this.foreingLanguageTypesCombo = foreingLanguageTypesCombo;
    }


    public String getDescription() {
        return description.getValue();
    }


    public void setDescription(TextArea description) {
        this.description = description;
    }


    public ForeignLanguage getForeingLanguage() {
        return foreingLanguage;
    }


    public void setForeingLanguage(ForeignLanguage foreingLanguage) {
        this.foreingLanguage = foreingLanguage;
    }

    public boolean validate() {
        boolean valid = true;
        if (description.getValue().length() < 1) {
            valid = false;
            description.setComponentError(new UserError(rb.getString("field.required")));
        } else if (description.getValue().length() > 255) {
            valid = false;
            description.setComponentError(new UserError(rb.getString("field.description.255")));
        } else {
            description.setComponentError(null);
        }

        return valid;
    }
}
