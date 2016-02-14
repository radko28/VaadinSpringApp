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

import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.managers.CustomerAreaManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AddCustomerAreaActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.EditCustomerAreaActionListener;


public class CustomerAreaList {

    public CustomerAreaList(CustomerAreaManager customerAreaManager, Navigator navigator, VerticalLayout panelContent) {
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());

        Table table = new Table("");

        table.setImmediate(true);
        table.setSelectable(true);


        table.addContainerProperty(rb.getString("customerAreaName"), String.class, null, rb.getString("customerAreaName"), null, null);
        table.addContainerProperty(rb.getString("customerAreaType"), String.class, null, rb.getString("customerAreaType"), null, null);
        table.addContainerProperty(rb.getString("customerAreaDesc"), String.class, null, rb.getString("customerAreaDesc"), null, null);
        table.addContainerProperty(rb.getString("customerAreaCreated"), String.class, null, rb.getString("customerAreaCreated"), null, null);
        table.addContainerProperty(rb.getString("customerAreaActions"), HorizontalLayout.class, null, rb.getString("customerAreaActions"),
                null, null);


        List<CustomerArea> customerAreaList = customerAreaManager.findCustomerAreas();

        HorizontalLayout hbox = null;
        String customerAreaDeleteConfirm = rb.getString("customerAreaDeleteConfirm");
        if (customerAreaList != null) {
            for (CustomerArea customerArea : customerAreaList) {
                hbox = new HorizontalLayout();
                Button edit = new Button(rb.getString("customerAreaEdit"), new EditCustomerAreaActionListener(navigator,
                        customerArea.getId()));
                edit.setStyleName("link");
                hbox.addComponent(edit);
                Label span = new Label(" | ");
                span.setStyleName("link-label");
                hbox.addComponent(span);
                String customerAreaName = " " + customerArea.getName();
                Link delete = new Link(rb.getString("customerAreaDelete"), new ExternalResource(
                        "javascript:cz.morosystems.education.vaadinexampleapp.views.forms.customerarea(confirm("
                                + customerAreaDeleteConfirm + customerAreaName + "?'), '" + customerArea.getId() + "')"));
                delete.setStyleName("link");
                hbox.addComponent(delete);

                table.addItem(
                        new Object[] { customerArea.getName().toString(), customerArea.getCustomerAreaType().toString(),
                                customerArea.getDescription(), AppHelper.getCreatedString(customerArea.getCreated()), hbox },
                        customerArea.getId());


            }
        }
        panelContent.addComponent(table);
        panelContent.setComponentAlignment(table, Alignment.TOP_LEFT);

        Button addb = new Button(rb.getString("customerAreaAddnewCustomerArea"), new AddCustomerAreaActionListener(navigator));
        addb.setStyleName("link");
        panelContent.addComponent(addb);

    }

}
