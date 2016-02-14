package cz.morosystems.education.vaadinexampleapp.views.user;

import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.entities.KnowledgeType;
import cz.morosystems.education.vaadinexampleapp.managers.KnowledgeManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;

/**
 * 
 * @author andrej.klima
 * 
 */
public class KnowledgeTypeView extends VerticalLayout implements View, Button.ClickListener {

    private static final long serialVersionUID = 4784651462970513509L;

    private TextField knowledgeTypeName = new TextField();
    private KnowledgeManager knowledgeManager;
    private List<KnowledgeType> knowledgeTypeList;

    public KnowledgeTypeView(final KnowledgeManager knowledgeManager) {
        this.knowledgeManager = knowledgeManager;
        knowledgeTypeList = knowledgeManager.findAll();
    }

    public void addKnowledgeTypeViewComponents() {
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("legendKnowledgeTypes"));
        legend.setStyleName("header-label-2");
        addComponent(legend);
        Table ktt = new Table("", new BeanItemContainer<KnowledgeType>(KnowledgeType.class, knowledgeTypeList));
        ktt.addStyleName(com.vaadin.ui.themes.Reindeer.TABLE_BORDERLESS);
        ktt.setVisibleColumns(new String[] {"type"});
        ktt.addGeneratedColumn("knowtypeId", new Table.ColumnGenerator() {
            private static final long serialVersionUID = 1L;

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Button edit = new Button(rb.getString("usersEdit"));
                edit.setDescription("edit");
                edit.addStyleName("link");
                return new VerticalLayout(edit);
            }
        });
        ktt.setColumnHeaders(new String[] {rb.getString("knowledge.type"), rb.getString("usersActions")});
        addComponent(ktt);

        Button addNewType = new Button("Add new Knowledge type",this);
        addNewType.setDescription("addNewType");
        addComponent(addNewType);
        addComponent(knowledgeTypeName);
    }
    @Override
    public void buttonClick(ClickEvent event) {
        String desc = event.getButton().getDescription();
        if("addNewType".equals(desc)) {
            knowledgeTypeName.setValue(null);
            knowledgeTypeName.focus();
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {}

}
