package cz.morosystems.education.vaadinexampleapp.views;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.managers.CustomerAreaManager;
import cz.morosystems.education.vaadinexampleapp.managers.DeviceManager;
import cz.morosystems.education.vaadinexampleapp.managers.EmailManager;
import cz.morosystems.education.vaadinexampleapp.managers.ForLangsManager;
import cz.morosystems.education.vaadinexampleapp.managers.KnowledgeManager;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectManager;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectRoleManager;
import cz.morosystems.education.vaadinexampleapp.managers.UserManager;
import cz.morosystems.education.vaadinexampleapp.util.UserUtil;
import cz.morosystems.education.vaadinexampleapp.views.device.DevicesView;
import cz.morosystems.education.vaadinexampleapp.views.listeners.ViewChangeSecurityChecker;
import cz.morosystems.education.vaadinexampleapp.views.user.UserView;

/**
 * 
 * 
 * Main class of the application. Setting views to Navigation.
 * 
 * @author radoslav.kuzma
 * 
 */
@Component
@Scope("prototype")
@Theme("mytheme")
public class MainNavigator extends UI {

    @Autowired
    private DeviceManager deviceManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private ForLangsManager forlangsManager;

    @Autowired
    private ProjectManager projectManager;

    @Autowired
    private KnowledgeManager knowledgeManager;

    @Autowired
    private ProjectRoleManager projectRoleManager;

    @Autowired
    private CustomerAreaManager customerAreaManager;

    @Resource
    private AuthenticationManager authenticationManager;

    private Navigator navigator;


    public static final String ADMIN_VIEW = "admin";
    public static final String USER_VIEW = "user";
    public static final String LOGIN_VIEW = "login";

    private String view = "";

    @Autowired
    private EmailManager emailManager;

    @Override
    protected void init(VaadinRequest request) {


        getPage().setTitle("Navigation Example");


        // Set the content root layout for the UI
        Layout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);

        UI.getCurrent().setLocale(new Locale("es"));
        // this.setLocale();
        LocaleContextHolder.setLocale(new Locale("es"));


        // Use the UI as the view display
        ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(content);


        // Create a navigator to control the page
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addViewChangeListener(new ViewChangeSecurityChecker());
        UserUtil.getInstance().setNavigator(navigator);
        UserUtil.getInstance().setUserManager(userManager);
        UserUtil.getInstance().setAuthenticationManager(authenticationManager);

        LoginView loginView = new LoginView(navigator, userManager, authenticationManager);
        DevicesView devicesView = new DevicesView(navigator, deviceManager, userManager, forlangsManager, emailManager, projectManager);
        UserView userView = new UserView(navigator, userManager, emailManager, knowledgeManager, projectRoleManager, customerAreaManager);
        UserUtil.getInstance().setDeviceView(devicesView);
        UserUtil.getInstance().setLoginView(loginView);
        UserUtil.getInstance().setUserView(userView);


        // Create and register the views

        navigator.addView(LOGIN_VIEW, loginView);
        navigator.addView(USER_VIEW, devicesView);
        navigator.addView(ADMIN_VIEW, userView);
        navigator.navigateTo(MainNavigator.LOGIN_VIEW);


    }
}