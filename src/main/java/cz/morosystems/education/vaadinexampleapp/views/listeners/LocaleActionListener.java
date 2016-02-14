package cz.morosystems.education.vaadinexampleapp.views.listeners;


import java.util.Locale;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;

import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.UserUtil;

/**
 * 
 * Listener for changing localization.
 * 
 * @author radoslav.kuzma
 * 
 */
public class LocaleActionListener implements ClickListener {

    String languageCode = "";

    // private GridLayout headerContent;

    public LocaleActionListener() {


    }

    @Override
    public void buttonClick(ClickEvent event) {
        Button button = (Button)event.getSource();
        Resource resource = (Resource)button.getIcon();
        String image = resource.toString();
        if (image.equals("img/czech_republic.gif")) {
            Locale.setDefault(new Locale("cs"));
            UI.getCurrent().setLocale(new Locale("cs"));
            UserUtil.getInstance().setLanguageCode("cs");
            UI.getCurrent().setImmediate(true);
            UserUtil.getInstance().getMenuPanel().setComponents();
            localeContent();
        } else if (image.equals("img/united_kingdom.gif")) {
            Locale.setDefault(new Locale("en"));
            UI.getCurrent().setLocale(new Locale("en"));
            UI.getCurrent().setImmediate(true);
            UserUtil.getInstance().setLanguageCode("en");
            UserUtil.getInstance().getMenuPanel().setComponents();
            localeContent();
        }

    }

    private void localeContent() {
        switch (UserUtil.getInstance().getContent()) {
        case AppHelper.CONTENT_DEVICES:
            UserUtil.getInstance().getDevicesView().setDeviceComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_LOGIN:
            UserUtil.getInstance().getLoginView().setComponents();
            break;
        case AppHelper.CONTENT_ADD_CELLPHONE:
            UserUtil.getInstance().getDevicesView().setAddCellPhoneComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_EDIT_CELLPHONE:
            UserUtil.getInstance().getDevicesView().setEditCellPhoneComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_ADD_COMPUTER:
            UserUtil.getInstance().getDevicesView().setAddComputerComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_EDIT_COMPUTER:
            UserUtil.getInstance().getDevicesView().setEditComputerComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;

        case AppHelper.CONTENT_PROFILE:
            UserUtil.getInstance().getDevicesView().setProfileComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_USER:
            UserUtil.getInstance().getUserView().setUserComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;
        case AppHelper.CONTENT_EDIT_USER:
            UserUtil.getInstance().getUserView().setEditComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;
        case AppHelper.CONTENT_ADD_USER:
            UserUtil.getInstance().getUserView().setAddComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;
        case AppHelper.CONTENT_FOR_LANG:
            UserUtil.getInstance().getDevicesView().setForLangsComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_ADD_FOR_LANG:
            UserUtil.getInstance().getDevicesView().setAddForLangsComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_EDIT_FOR_LANG:
            UserUtil.getInstance().getDevicesView().setEditForLangsComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_PROJECT:
            UserUtil.getInstance().getDevicesView().setProjectComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_ADD_PROJECT:
            UserUtil.getInstance().getDevicesView().setAddProjectComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_EDIT_PROJECT:
            UserUtil.getInstance().getDevicesView().setEditProjectComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_KNOWLEDGE:
            UserUtil.getInstance().getDevicesView().setKnowledgeComponents();
            UserUtil.getInstance().getHeaderPanelDevices().setComponents();
            break;
        case AppHelper.CONTENT_ADMIN_PROJECT_ROLES:
            UserUtil.getInstance().getUserView().setProjectRolesComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;
        case AppHelper.CONTENT_ADD_PROJECT_ROLES:
            UserUtil.getInstance().getUserView().setAddProjectRoleComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;
        case AppHelper.CONTENT_EDIT_PROJECT_ROLES:
            UserUtil.getInstance().getUserView().setEditProjectRoleComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;
        case AppHelper.CONTENT_ADMIN_CUSTOMER_AREAS:
            UserUtil.getInstance().getUserView().setCustomerAreasComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;
        case AppHelper.CONTENT_ADD_CUSTOMER_AREAS:
            UserUtil.getInstance().getUserView().setAddCutomerAreaComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;
        case AppHelper.CONTENT_EDIT_CUSTOMER_AREAS:
            UserUtil.getInstance().getUserView().setEditCustomerAreaComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;

        case AppHelper.CONTENT_ADMIN_CERTIFICATE_TYPES:
            UserUtil.getInstance().getUserView().setCertificateTypesComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;
        case AppHelper.CONTENT_ADMIN_KNOWLEDGE_TYPES:
            UserUtil.getInstance().getUserView().setKnowledgeTypesComponents();
            UserUtil.getInstance().getHeaderPanelUser().setComponents();
            break;


        }


    }
}
