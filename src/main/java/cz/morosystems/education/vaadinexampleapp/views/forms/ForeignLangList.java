package cz.morosystems.education.vaadinexampleapp.views.forms;

import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.entities.ForeignLanguage;
import cz.morosystems.education.vaadinexampleapp.managers.ForLangsManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AddForeignLangActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.EditForeignLangActionListener;

/**
 * 
 * View list of the languages.
 * 
 * @author radoslav.kuzma
 * 
 */
public class ForeignLangList {

    public ForeignLangList(ForLangsManager forlangsManager, Navigator navigator, String username, VerticalLayout panelContent) {


        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());

        Table table = new Table("");

        table.setImmediate(true);
        table.setSelectable(true);

        /*
         * Define the names and data types of columns. The "default value" parameter is meaningless here.
         */
        table.addContainerProperty(rb.getString("forlangsName"), String.class, null, rb.getString("forlangsName"), null, null);
        table.addContainerProperty(rb.getString("forlangsLevel"), String.class, null, rb.getString("forlangsLevel"), null, null);
        table.addContainerProperty(rb.getString("forlangsAdded"), String.class, null, rb.getString("forlangsAdded"), null, null);
        table.addContainerProperty(rb.getString("forlangsDesc"), String.class, null, rb.getString("forlangsDesc"), null, null);
        table.addContainerProperty(rb.getString("forlangsActions"), HorizontalLayout.class, null, rb.getString("forlangsActions"), null,
                null);

        // view devices list
        List<ForeignLanguage> forlangsList = forlangsManager.findForeignLanguagesByUserName(username);

        HorizontalLayout hbox = null;
        String forlangsDeleteConfirm = rb.getString("forlangsDeleteConfirm");
        if (forlangsList != null) {
            for (ForeignLanguage foreignLanguage : forlangsList) {
                hbox = new HorizontalLayout();
                Button edit = new Button(rb.getString("forlangsEdit"),
                        new EditForeignLangActionListener(navigator, foreignLanguage.getId()));
                edit.setStyleName("link");
                hbox.addComponent(edit);
                Label span = new Label(" | ");
                span.setStyleName("link-label");
                hbox.addComponent(span);
                String langName = " " + foreignLanguage.getLanguage().toString();
                Link delete = new Link(rb.getString("forlangsDelete"), new ExternalResource(
                        "javascript:cz.morosystems.education.vaadinexampleapp.views.forms(confirm(" + forlangsDeleteConfirm + langName
                                + "?'), '" + foreignLanguage.getId() + "')"));
                delete.setStyleName("link");
                hbox.addComponent(delete);
                table.addItem(
                        new Object[] { foreignLanguage.getLanguage().toString(), foreignLanguage.getLevel().toString(),
                                AppHelper.getCreatedString(foreignLanguage.getCreated()), foreignLanguage.getDescription(), hbox },
                        foreignLanguage.getId());


            }
        }
        panelContent.addComponent(table);
        panelContent.setComponentAlignment(table, Alignment.TOP_LEFT);

        Button addDeviceb = new Button(rb.getString("forlangsAddnewLang"), new AddForeignLangActionListener(navigator));
        addDeviceb.setStyleName("link");
        panelContent.addComponent(addDeviceb);

    }

}
