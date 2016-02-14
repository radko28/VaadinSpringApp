package cz.morosystems.education.vaadinexampleapp.views;

import java.util.ResourceBundle;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.UserUtil;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.DevicesActionListener;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.ForLangsActionListener;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.KnowledgeListener;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.ProfileActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AdminCertificateTypesActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AdminCustomerAreasActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AdminKnowledgeTypesActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AdminProjectRolesActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.AdminUsersActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.ProjectActionListener;

/**
 * 
 * Menu navigation panel for choosing content pages.
 * 
 * @author radoslav.kuzma
 * 
 */
public class MenuPanel extends Panel {

    private Button myProfileb;
    private Button myDevicesb;
    private Button myForLangsb;
    private Button myKnowledge;
    private Button myProject;
    private Button adminProjectRoles;
    private Button adminUsers;
    private Button adminCustomerAreas;
    private Button adminCertificateTypes;
    private Button adminKnowledgeTypes;
    private VerticalLayout menuContent;
    private Navigator navigator;
    private KnowledgeListener knowledeListener;

    public MenuPanel(Navigator navigator) {
        this.navigator = navigator;
        knowledeListener = new KnowledgeListener(navigator);
        setHeight("100%");
        setWidth("200");
        menuContent = new VerticalLayout();
        menuContent.setMargin(true);
        setStyleName(com.vaadin.ui.themes.Reindeer.PANEL_LIGHT);
        setContent(menuContent);
    }
    public void setComponents() {
        menuContent.removeAllComponents();

        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());

        int param = UserUtil.getInstance().getMenuParam();

        if (param == 1) {
            menuContent.addComponent(new Label(rb.getString("menuDevices")));
            myProfileb = new Button(rb.getString("menuProfile"), new ProfileActionListener(navigator));
            myProfileb.setStyleName("link");
            menuContent.addComponent(myProfileb);
            myForLangsb = new Button(rb.getString("menuForLangs"), new ForLangsActionListener(navigator));
            myForLangsb.setStyleName("link");
            menuContent.addComponent(myForLangsb);
            myKnowledge = new Button(rb.getString("labelKnowledge"), knowledeListener);
            myKnowledge.setStyleName("link");
            menuContent.addComponent(myKnowledge);
            myProject = new Button(rb.getString("menuProject"), new ProjectActionListener(navigator));
            myProject.setStyleName("link");
            menuContent.addComponent(myProject);
        } else if (param == 2) {
            myDevicesb = new Button(rb.getString("menuDevices"), new DevicesActionListener(navigator));
            myDevicesb.setStyleName("link");
            menuContent.addComponent(myDevicesb);
            menuContent.addComponent(new Label(rb.getString("menuProfile")));
            myForLangsb = new Button(rb.getString("menuForLangs"), new ForLangsActionListener(navigator));
            myForLangsb.setStyleName("link");
            menuContent.addComponent(myForLangsb);
            myKnowledge = new Button(rb.getString("labelKnowledge"), knowledeListener);
            myKnowledge.setStyleName("link");
            menuContent.addComponent(myKnowledge);
            myProject = new Button(rb.getString("menuProject"), new ProjectActionListener(navigator));
            myProject.setStyleName("link");
            menuContent.addComponent(myProject);
        } else if (param == 3) {
            myDevicesb = new Button(rb.getString("menuDevices"), new DevicesActionListener(navigator));
            myDevicesb.setStyleName("link");
            menuContent.addComponent(myDevicesb);
            myProfileb = new Button(rb.getString("menuProfile"), new ProfileActionListener(navigator));
            myProfileb.setStyleName("link");
            menuContent.addComponent(myProfileb);
            menuContent.addComponent(new Label(rb.getString("menuForLangs")));
            myKnowledge = new Button(rb.getString("labelKnowledge"), knowledeListener);
            myKnowledge.setStyleName("link");
            menuContent.addComponent(myKnowledge);
            myProject = new Button(rb.getString("menuProject"), new ProjectActionListener(navigator));
            myProject.setStyleName("link");
            menuContent.addComponent(myProject);
        } else if (param == 4) {
            myDevicesb = new Button(rb.getString("menuDevices"), new DevicesActionListener(navigator));
            myDevicesb.setStyleName("link");
            menuContent.addComponent(myDevicesb);
            myProfileb = new Button(rb.getString("menuProfile"), new ProfileActionListener(navigator));
            myProfileb.setStyleName("link");
            menuContent.addComponent(myProfileb);
            myForLangsb = new Button(rb.getString("menuForLangs"), new ForLangsActionListener(navigator));
            myForLangsb.setStyleName("link");
            menuContent.addComponent(myForLangsb);
            menuContent.addComponent(new Label(rb.getString("labelKnowledge")));
            myProject = new Button(rb.getString("menuProject"), new ProjectActionListener(navigator));
            myProject.setStyleName("link");
            menuContent.addComponent(myProject);
        } else if (param == 5) {
            myDevicesb = new Button(rb.getString("menuDevices"), new DevicesActionListener(navigator));
            myDevicesb.setStyleName("link");
            menuContent.addComponent(myDevicesb);
            myProfileb = new Button(rb.getString("menuProfile"), new ProfileActionListener(navigator));
            myProfileb.setStyleName("link");
            menuContent.addComponent(myProfileb);
            myForLangsb = new Button(rb.getString("menuForLangs"), new ForLangsActionListener(navigator));
            myForLangsb.setStyleName("link");
            menuContent.addComponent(myForLangsb);
            myKnowledge = new Button(rb.getString("labelKnowledge"), knowledeListener);
            myKnowledge.setStyleName("link");
            menuContent.addComponent(myKnowledge);
            menuContent.addComponent(new Label(rb.getString("menuProject")));
        } else if (param == 10) {
            // users
            menuContent.addComponent(new Label(rb.getString("menuAdminUsers")));

            adminProjectRoles = new Button(rb.getString("menuAdminProjectRoles"), new AdminProjectRolesActionListener(navigator));
            adminProjectRoles.setStyleName("link");
            menuContent.addComponent(adminProjectRoles);

            adminCustomerAreas = new Button(rb.getString("menuAdminCustomerAreas"), new AdminCustomerAreasActionListener(navigator));
            adminCustomerAreas.setStyleName("link");
            menuContent.addComponent(adminCustomerAreas);

            adminCertificateTypes = new Button(rb.getString("menuAdminCertificateTypes"),
                    new AdminCertificateTypesActionListener(navigator));
            adminCertificateTypes.setStyleName("link");
            menuContent.addComponent(adminCertificateTypes);

            adminKnowledgeTypes = new Button(rb.getString("menuAdminKnowledgeTypes"), new AdminKnowledgeTypesActionListener(navigator));
            adminKnowledgeTypes.setStyleName("link");
            menuContent.addComponent(adminKnowledgeTypes);

        } else if (param == 11) {
            // project roles


            adminUsers = new Button(rb.getString("menuAdminUsers"), new AdminUsersActionListener(navigator));
            adminUsers.setStyleName("link");
            menuContent.addComponent(adminUsers);

            menuContent.addComponent(new Label(rb.getString("menuAdminProjectRoles")));

            adminCustomerAreas = new Button(rb.getString("menuAdminCustomerAreas"), new AdminCustomerAreasActionListener(navigator));
            adminCustomerAreas.setStyleName("link");
            menuContent.addComponent(adminCustomerAreas);

            adminCertificateTypes = new Button(rb.getString("menuAdminCertificateTypes"),
                    new AdminCertificateTypesActionListener(navigator));
            adminCertificateTypes.setStyleName("link");
            menuContent.addComponent(adminCertificateTypes);

            adminKnowledgeTypes = new Button(rb.getString("menuAdminKnowledgeTypes"), new AdminKnowledgeTypesActionListener(navigator));
            adminKnowledgeTypes.setStyleName("link");
            menuContent.addComponent(adminKnowledgeTypes);
        } else if (param == 12) {
            // customer areas
            adminUsers = new Button(rb.getString("menuAdminUsers"), new AdminUsersActionListener(navigator));
            adminUsers.setStyleName("link");
            menuContent.addComponent(adminUsers);


            adminProjectRoles = new Button(rb.getString("menuAdminProjectRoles"), new AdminProjectRolesActionListener(navigator));
            adminProjectRoles.setStyleName("link");
            menuContent.addComponent(adminProjectRoles);


            menuContent.addComponent(new Label(rb.getString("menuAdminCustomerAreas")));

            adminCertificateTypes = new Button(rb.getString("menuAdminCertificateTypes"),
                    new AdminCertificateTypesActionListener(navigator));
            adminCertificateTypes.setStyleName("link");
            menuContent.addComponent(adminCertificateTypes);

            adminKnowledgeTypes = new Button(rb.getString("menuAdminKnowledgeTypes"), new AdminKnowledgeTypesActionListener(navigator));
            adminKnowledgeTypes.setStyleName("link");
            menuContent.addComponent(adminKnowledgeTypes);
        } else if (param == 13) {
            // certificate types
            adminUsers = new Button(rb.getString("menuAdminUsers"), new AdminUsersActionListener(navigator));
            adminUsers.setStyleName("link");
            menuContent.addComponent(adminUsers);


            adminProjectRoles = new Button(rb.getString("menuAdminProjectRoles"), new AdminProjectRolesActionListener(navigator));
            adminProjectRoles.setStyleName("link");
            menuContent.addComponent(adminProjectRoles);

            adminCustomerAreas = new Button(rb.getString("menuAdminCustomerAreas"), new AdminCustomerAreasActionListener(navigator));
            adminCustomerAreas.setStyleName("link");
            menuContent.addComponent(adminCustomerAreas);


            menuContent.addComponent(new Label(rb.getString("menuAdminCertificateTypes")));

            adminKnowledgeTypes = new Button(rb.getString("menuAdminKnowledgeTypes"), new AdminKnowledgeTypesActionListener(navigator));
            adminKnowledgeTypes.setStyleName("link");
            menuContent.addComponent(adminKnowledgeTypes);
        } else if (param == 14) {
            // knowledge types
            adminUsers = new Button(rb.getString("menuAdminUsers"), new AdminUsersActionListener(navigator));
            adminUsers.setStyleName("link");
            menuContent.addComponent(adminUsers);

            adminProjectRoles = new Button(rb.getString("menuAdminProjectRoles"), new AdminProjectRolesActionListener(navigator));
            adminProjectRoles.setStyleName("link");
            menuContent.addComponent(adminProjectRoles);

            adminCustomerAreas = new Button(rb.getString("menuAdminCustomerAreas"), new AdminCustomerAreasActionListener(navigator));
            adminCustomerAreas.setStyleName("link");
            menuContent.addComponent(adminCustomerAreas);

            adminCertificateTypes = new Button(rb.getString("menuAdminCertificateTypes"),
                    new AdminCertificateTypesActionListener(navigator));
            adminCertificateTypes.setStyleName("link");
            menuContent.addComponent(adminCertificateTypes);

            menuContent.addComponent(new Label(rb.getString("menuAdminKnowledgeTypes")));


        }

    }
}
