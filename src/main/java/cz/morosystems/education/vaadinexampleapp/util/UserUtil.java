package cz.morosystems.education.vaadinexampleapp.util;

import org.springframework.security.authentication.AuthenticationManager;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.navigator.Navigator;

import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.UserManager;
import cz.morosystems.education.vaadinexampleapp.views.HeaderPanel;
import cz.morosystems.education.vaadinexampleapp.views.LoginView;
import cz.morosystems.education.vaadinexampleapp.views.MenuPanel;
import cz.morosystems.education.vaadinexampleapp.views.device.DevicesView;
import cz.morosystems.education.vaadinexampleapp.views.user.UserView;

/**
 * 
 * Aplication global object for storing data
 * 
 * @author radoslav.kuzma
 * 
 */
public class UserUtil {

    private static UserUtil userUtil = new UserUtil();
    Navigator navigator;
    private UserManager userManager;
    private AuthenticationManager authenticationManager;
    private boolean logged;
    private String languageCode = "cs";
    private MenuPanel menuPanel;
    private int menuParam;
    private DevicesView devicesView;
    private int content;
    private LoginView loginView;
    private UserView userView;
    private HeaderPanel headerPanelUser;
    private HeaderPanel headerPanelDevices;
    private BeanFieldGroup<User> binder;

    public static UserUtil getInstance() {
        return userUtil;
    }


    public Navigator getNavigator() {
        return navigator;
    }


    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }


    public static UserUtil getUserUtil() {
        return userUtil;
    }


    public static void setUserUtil(UserUtil userUtil) {
        UserUtil.userUtil = userUtil;
    }


    public UserManager getUserManager() {
        return userManager;
    }


    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }


    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }


    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    public boolean isLogged() {
        return logged;
    }


    public void setLogged(boolean logged) {
        this.logged = logged;
    }


    public String getLanguageCode() {
        return languageCode;
    }


    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }


    public void setMenuPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }
    public MenuPanel getMenuPanel() {
        return this.menuPanel;
    }


    public int getMenuParam() {
        return this.menuParam;
    }

    public void setMenuParam(int menuParam) {
        this.menuParam = menuParam;
    }


    public DevicesView getDevicesView() {
        return devicesView;
    }


    public void setDeviceView(DevicesView devicesView) {
        this.devicesView = devicesView;

    }


    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;

    }


    public void setContent(int content) {
        this.content = content;

    }

    public int getContent() {
        return content;

    }


    public LoginView getLoginView() {
        return loginView;
    }


    public void setHeaderPanelDevices(HeaderPanel headerPanelDevices) {
        this.headerPanelDevices = headerPanelDevices;

    }

    public HeaderPanel getHeaderPanelDevices() {
        return headerPanelDevices;

    }


    public void setUserView(UserView userView) {
        this.userView = userView;

    }

    public UserView getUserView() {
        return userView;

    }


    public void setHeaderPanelUser(HeaderPanel headerPanelUser) {
        this.headerPanelUser = headerPanelUser;

    }

    public HeaderPanel getHeaderPanelUser() {
        return headerPanelUser;

    }


    public void setUserBinder(BeanFieldGroup<User> binder) {
        this.binder = binder;

    }

    public BeanFieldGroup<User> getUserBinder() {
        return this.binder;

    }


}
