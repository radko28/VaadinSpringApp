package cz.morosystems.education.vaadinexampleapp.views;

import java.util.ResourceBundle;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.UserUtil;
import cz.morosystems.education.vaadinexampleapp.views.listeners.LocaleActionListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.LogoutActionListener;

/**
 * 
 * Header panel with information about project. Logo, logged user and localization.
 * 
 * @author radoslav.kuzma
 * 
 */
public class HeaderPanel extends Panel {

    /**
     * 
     */
    private static final long serialVersionUID = 7494037239723845578L;
    private GridLayout grid = null;
    private int param;

    public HeaderPanel(int param, Navigator navigator) {
        super();
        this.param = param;
        grid = new GridLayout(3, 1);
        setContent(grid);
        grid.setSizeFull();
        setStyleName(com.vaadin.ui.themes.Reindeer.PANEL_LIGHT);
        setComponents();
    }

    public void setComponents() {
        grid.removeAllComponents();
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        GridLayout hl = new GridLayout(3, 1);
        grid.addComponent(hl);
        // logo app
        ThemeResource resource = new ThemeResource("img/global.jpg");
        Embedded image = new Embedded("", resource);
        image.setMimeType("image/jpg");
        hl.addComponent(image);
        Label spanImage = new Label("        ");
        spanImage.setStyleName("header-span");
        hl.addComponent(spanImage);
        // main legend
        String headerLegendString = "";
        if (AppHelper.hasAdminRole() && UserUtil.getInstance().isLogged()) {
            headerLegendString = rb.getString("adminlegend");
        } else {
            headerLegendString = rb.getString("userlegend");
        }
        Label headerLegend = new Label(headerLegendString);
        headerLegend.setStyleName("header-label");
        hl.addComponent(headerLegend);
        hl.setComponentAlignment(headerLegend, Alignment.MIDDLE_RIGHT);

        // userinfo
        if (param > 0) {
            GridLayout gridLog = new GridLayout(3, 1);


            String wholeName = "";
            if (UserUtil.getInstance().isLogged()) {
                wholeName = UserUtil.getInstance().getUserManager().getWholeNameByUserName(AppHelper.getUserName());

            }
            GridLayout nameBox = new GridLayout(3, 1);

            nameBox.addComponent(new Label(rb.getString("userInfoLoggedinas")));
            Label span2 = new Label(" ");
            span2.setStyleName("link-label");
            nameBox.addComponent(span2);
            nameBox.addComponent(new Label(wholeName));

            StringBuilder logoutLabel = new StringBuilder();
            logoutLabel.append(rb.getString("userInfoLogOut"));
            Button logoutb = new Button(logoutLabel.toString(), new LogoutActionListener(UserUtil.getInstance().getNavigator()));
            logoutb.setStyleName("link");

            gridLog.addComponent(nameBox);
            Label span = new Label(" ");
            span.setStyleName("link-label");
            gridLog.addComponent(span);
            gridLog.addComponent(logoutb);
            grid.addComponent(gridLog);
        } else {
            grid.addComponent(new Label(""));
        }


        // locale
        HorizontalLayout hbox = new HorizontalLayout();
        ThemeResource czresource = new ThemeResource("img/czech_republic.gif");
        Button czb = new Button("", new LocaleActionListener());
        czb.setIcon(czresource);
        czb.setStyleName("link");
        hbox.addComponent(czb);
        ThemeResource enresource = new ThemeResource("img/united_kingdom.gif");
        Button enb = new Button("", new LocaleActionListener());
        enb.setStyleName("link");
        enb.setIcon(enresource);
        hbox.addComponent(enb);

        grid.addComponent(hbox);
    }

    public void setParam(int i) {
        this.param = param;

    }
}
