package cz.morosystems.education.vaadinexampleapp.views.device.listeners;

import java.util.HashMap;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.server.UserError;
import cz.morosystems.education.vaadinexampleapp.entities.Knowledge;
import cz.morosystems.education.vaadinexampleapp.managers.UserManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;

/**
 * Listener for KnowledgeView page navigation
 * 
 * @author andrej.klima
 * 
 */
public class KnowledgeListener implements Button.ClickListener {

    private static final long serialVersionUID = 1L;

    private Navigator navigator;
    private FormLayout form;
    private UserManager userManager;
    private String username;

    public KnowledgeListener(Navigator navigator) {
        this.navigator = navigator;
    }

    public KnowledgeListener(final Navigator navigator, final UserManager userManager, FormLayout form, String username) {
        this.navigator = navigator;
        this.form = form;
        this.userManager = userManager;
        this.username = username;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        String description = event.getButton().getDescription();

        if ("addKnowledge".equals(description)) {
            form.setData(new Knowledge());
            ((ComboBox)form.getComponent(0)).focus();
        } else if ("addSave".equals(description)) {
            ComboBox typeSelect = ((ComboBox)form.getComponent(0));
            Object value = typeSelect.getValue();

            if (null == value) {
                String error = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage()).getString("knowledge.type.error");
                typeSelect.setComponentError(new UserError(error));
                return;
            }

            Knowledge data = (Knowledge)form.getData();
            data.setType(value.toString());
            data.setDetail(((TextField)form.getComponent(1)).getValue());

            if (data.getKnowledgeId().length() < 1) {
                userManager.addUserNewKnowledge(username, data);
            } else if (data.getKnowledgeId().length() > 1) {
                userManager.editUserKnowledge(data);
            }
        }

        navigator.navigateTo(MainNavigator.USER_VIEW + "/knowledge");
    }
}
