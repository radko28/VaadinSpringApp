package cz.morosystems.education.vaadinexampleapp.views.device;

import java.util.ResourceBundle;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.entities.Knowledge;
import cz.morosystems.education.vaadinexampleapp.entities.KnowledgeType;
import cz.morosystems.education.vaadinexampleapp.managers.UserManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.KnowledgeListener;

/**
 * View page with form for user to view knowledges, add, change or deletes knowledge
 * 
 * @author andrej.klima
 * 
 */
public class KnowledgeView extends VerticalLayout implements View, Button.ClickListener {

    private static final long serialVersionUID = 6235240081598526033L;

    FormLayout form;
    Set<Knowledge> knowledges;
    List<KnowledgeType> knowledgeTypes;

    public KnowledgeView(final String username, final UserManager userManager, final Navigator navigator) {
        knowledges = userManager.findUserByUserName(username).getKnowledge();
        knowledgeTypes = userManager.findAllKnowledgeTypes();
        JavaScript.getCurrent().addFunction("cz.morosystems.education.vaadinexampleapp.views.device.knowledgeview",
                new JavaScriptFunction() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void call(JSONArray arguments) throws JSONException {
                        boolean delete = arguments.getBoolean(0);
                        String knowledgeId = arguments.getString(1);

                        if (delete) {
                            userManager.removeUserKnowledge(knowledgeId);
                            knowledges = userManager.findUserByUserName(username).getKnowledge();
                        }
                        navigator.navigateTo(MainNavigator.USER_VIEW + "/knowledge");
                    }
                });
    }

    public void addViewComponents(final String username, final UserManager userManager, final Navigator navigator) {
        form = new FormLayout();

        KnowledgeListener listener = new KnowledgeListener(navigator, userManager, form, username);

        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        TextField details = new TextField(rb.getString("knowledge.detail"));
        ComboBox typeSelect = new ComboBox(rb.getString("knowledge.type.select"));
        Table knowtable = new Table("");

        Label knowLegend = new Label(rb.getString("labelKnowledge"));
        knowLegend.setStyleName("header-label-2");
        addComponent(knowLegend);
        knowtable.addContainerProperty(rb.getString("knowledge.type"), String.class, null);
        knowtable.addContainerProperty(rb.getString("knowledge.detail"), String.class, "detail");
        knowtable.addContainerProperty(rb.getString("usersActions"), HorizontalLayout.class, "Action");

        String deleteKnowledge = rb.getString("delete.knowledge");
        for (Knowledge k : knowledges) {
            Button edit = new Button(rb.getString("devicesEdit"), this);
            edit.setData(k.getKnowledgeId());
            edit.setStyleName("link");
            Label pipe = new Label(" | ");
            pipe.setStyleName("link-label");
            Link delete = new Link(rb.getString("usersDelete"), new ExternalResource(
                    "javascript:cz.morosystems.education.vaadinexampleapp.views.device.knowledgeview(confirm(" + deleteKnowledge
                            + k.getType() + " " + k.getDetail() + "'), '" + k.getKnowledgeId() + "')"));
            knowtable.addItem(new Object[] { k.getType(), k.getDetail(), new HorizontalLayout(edit, pipe, delete) }, k.getKnowledgeId());
        }

        knowtable.setPageLength(4);
        Button addKnowledge = new Button(rb.getString("addKnowledge"), listener);
        addKnowledge.setDescription("addKnowledge");
        GridLayout grid = new GridLayout(1, 2);
        grid.setSpacing(true);
        grid.addComponent(knowtable);
        grid.addComponent(addKnowledge, 0, 1);
        addComponent(grid);
        for (KnowledgeType type : knowledgeTypes) {
            typeSelect.addItem(type.toString());
        }
        form.setData(new Knowledge());
        form.addComponent(typeSelect);
        Button add = new Button(rb.getString("addSave"), listener);
        add.setDescription("addSave");
        form.addComponent(details);
        form.addComponent(add);
        addComponent(form);
    }
    @Override
    public void enter(ViewChangeEvent event) {}

    @Override
    public void buttonClick(ClickEvent event) {
        String knowledgeId = (String)event.getButton().getData();
        String editCaption = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage()).getString("usersEdit");
        for (Knowledge k : knowledges) {
            if (k.getKnowledgeId().equals(knowledgeId)) {
                form.setData(k);
                ((ComboBox)form.getComponent(0)).setValue(k.getType());
                ((TextField)form.getComponent(1)).setValue(k.getDetail());
                ((Button)form.getComponent(2)).setCaption(editCaption);
                break;
            }
        }
    }
}
