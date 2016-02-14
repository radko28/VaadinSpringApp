package cz.morosystems.education.vaadinexampleapp.views;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.managers.UserManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.DeviceType;
import cz.morosystems.education.vaadinexampleapp.util.UserUtil;

/**
 * Prime application page creation
 *
 * @author radoslav.kuzma
 *
 */
@Component
@Scope("prototype")
public class LoginView extends VerticalLayout implements View {

    public static final String NAME = "DevicesView";
    public static final String COMPUTER_VIEW = "ComputerView";
    public static final String CELL_PHONE_VIEW = "CellPhoneView";
    private VerticalLayout panelContent;
    private HeaderPanel headerPanel;


    private UserManager userManager;

    Navigator navigator;
    Panel panel;
    private AuthenticationManager authenticationManager;

    public LoginView(final Navigator navigator, final UserManager userManager, AuthenticationManager authenticationManager) {


        this.navigator = navigator;
        this.userManager = userManager;
        this.authenticationManager = authenticationManager;


        setSizeFull();

        headerPanel = new HeaderPanel(0, navigator);
        addComponent(headerPanel);


        // Layout with menu on left and view area on right
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSizeFull();

        // Have a menu on the left side of the screen
        MenuPanel menu = new MenuPanel(navigator);
        hLayout.addComponent(menu);

        // A panel that contains a content area on right
        panel = new Panel();
        panel.setStyleName(com.vaadin.ui.themes.Reindeer.PANEL_LIGHT);
        panel.setSizeFull();
        hLayout.addComponent(panel);
        hLayout.setExpandRatio(panel, 1.0f);

        addComponent(hLayout);
        setExpandRatio(hLayout, 1.0f);


    }


    @Override
    public void enter(ViewChangeEvent event) {

        Label watching = null;
        panelContent = new VerticalLayout();
        panelContent.setMargin(true);
        panel.setContent(panelContent); // Also clears


        if (event.getParameters() == null || event.getParameters().isEmpty()) {
            UserUtil.getInstance().setContent(AppHelper.CONTENT_LOGIN);
            setComponents();
        } else {

            String[] params = event.getParameters().split("=");
            if (params[0].equals("add?device") && params[1].equals(DeviceType.PC.toString())) {


            } else if (params[0].equals("edit?deviceId")) {


            } else if (params[0].equals("add?device") && params[1].equals(DeviceType.CELL_PHONE.toString())) {}


        }
    }

    public void setComponents() {
        panelContent.removeAllComponents();
        LoginPanel loginContent = new LoginPanel(navigator, authenticationManager);
        panelContent.addComponent(loginContent);
        panelContent.setComponentAlignment(loginContent, Alignment.TOP_LEFT);
        headerPanel.setComponents();
    }
}
