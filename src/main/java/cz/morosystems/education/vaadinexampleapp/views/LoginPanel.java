package cz.morosystems.education.vaadinexampleapp.views;

import java.util.ResourceBundle;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.LoginActionListener;

/**
 * Login panel for users login and authentication.
 * 
 * @author radoslav.kuzma
 * 
 */
public class LoginPanel extends Panel {

    private static final long serialVersionUID = -8729795734753575682L;
    private TextField login;
    private PasswordField password;
    private Label badCredential = new Label();

    public LoginPanel(final Navigator navigator, final AuthenticationManager authenticationManager) {
        super();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());

        Label loginLegend = new Label(rb.getString("loginLegend"));
        loginLegend.setStyleName("header-label-2");
        loginLegend.setHeight("50");

        setStyleName(com.vaadin.ui.themes.Reindeer.PANEL_LIGHT);
        login = new TextField();
        login.setWidth(null);
        login.focus();
        Label loginLabel = new Label(rb.getString("loginLogin"));
        loginLabel.setStyleName("input-label");
        loginLabel.setWidth(null);
        password = new PasswordField();
        password.setWidth(null);
        Label passwordLabel = new Label(rb.getString("loginPassword"));
        passwordLabel.setStyleName("input-label");
        passwordLabel.setWidth(null);
        loginLabel.setHeight("50");
        passwordLabel.setHeight("50");

        badCredential.setStyleName("error-label");

        VerticalLayout hl = new VerticalLayout();
        GridLayout grid = new GridLayout(2, 6);
        hl.addComponent(loginLegend);
        badCredential.setHeight("50");
        hl.addComponent(badCredential);
        hl.addComponent(grid);
        setContent(hl);
        grid.setWidth("270px");
        // ////grid.addComponent(loginLegend, 0, 0);
        // //////grid.insertRow(1);
        // grid.addComponent(badCredential, 0, 2, 1, 2);
        grid.setRowExpandRatio(3, 0.1f);
        grid.addComponent(loginLabel, 0, 3);
        grid.setComponentAlignment(loginLabel, Alignment.TOP_LEFT);
        grid.addComponent(login, 1, 3);
        grid.setComponentAlignment(login, Alignment.TOP_LEFT);
        grid.setRowExpandRatio(4, 0.1f);
        grid.addComponent(passwordLabel, 0, 4);
        grid.setComponentAlignment(passwordLabel, Alignment.TOP_LEFT);
        grid.addComponent(password, 1, 4);
        grid.setComponentAlignment(password, Alignment.TOP_LEFT);

        Button loginb = new Button(rb.getString("loginSend"));
        grid.addComponent(loginb, 1, 5);
        grid.setComponentAlignment(loginb, Alignment.TOP_LEFT);
        loginb.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                boolean valid = true;
                if (null == login.getValue() || login.getValue().length() < 1 || null == password.getValue()
                        || password.getValue().length() < 1) {
                    String empty = rb.getString("field.required");
                    login.setComponentError(new UserError(empty));
                    password.setComponentError(new UserError(empty));
                    valid = false;
                } else {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(login, password);
                    Authentication returned = null;

                    try {
                        returned = authenticationManager.authenticate(auth);
                    } catch (org.springframework.security.core.AuthenticationException e) {}

                    if (null == returned) {
                        badCredential.setValue(rb.getString("badCredential"));
                        login.setComponentError(null);
                        password.setComponentError(null);
                        valid = false;
                    } else {
                        SecurityContextHolder.getContext().setAuthentication(returned);
                    }
                }

                if (valid) {
                    new LoginActionListener(navigator).buttonClick(event);
                }
            }
        });
    }

    public String getLogin() {
        return login.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

}
