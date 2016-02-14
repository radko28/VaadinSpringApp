package cz.morosystems.education.vaadinexampleapp.views.device;


import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.entities.CellPhone;
import cz.morosystems.education.vaadinexampleapp.entities.Computer;
import cz.morosystems.education.vaadinexampleapp.entities.Device;
import cz.morosystems.education.vaadinexampleapp.entities.ForeignLanguage;
import cz.morosystems.education.vaadinexampleapp.entities.Project;
import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.DeviceManager;
import cz.morosystems.education.vaadinexampleapp.managers.EmailManager;
import cz.morosystems.education.vaadinexampleapp.managers.ForLangsManager;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectManager;
import cz.morosystems.education.vaadinexampleapp.managers.UserManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.DeviceType;
import cz.morosystems.education.vaadinexampleapp.util.DeviceView;
import cz.morosystems.education.vaadinexampleapp.util.UserUtil;
import cz.morosystems.education.vaadinexampleapp.views.HeaderPanel;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.MenuPanel;
import cz.morosystems.education.vaadinexampleapp.views.device.forms.CellPhoneForm;
import cz.morosystems.education.vaadinexampleapp.views.device.forms.ComputerForm;
import cz.morosystems.education.vaadinexampleapp.views.device.forms.ProfileForm;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.AddActionListener;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.EditActionListener;
import cz.morosystems.education.vaadinexampleapp.views.forms.ForeignLangForm;
import cz.morosystems.education.vaadinexampleapp.views.forms.ForeignLangList;
import cz.morosystems.education.vaadinexampleapp.views.forms.ProjectForm;
import cz.morosystems.education.vaadinexampleapp.views.forms.ProjectList;

/**
 * 
 * View panel for ROLE_USER. Devices and user profile.
 * 
 * @author radoslav.kuzma
 * 
 */
@Component
@Scope("prototype")
@VaadinView(DevicesView.NAME)
public class DevicesView extends VerticalLayout implements View {

    public static final String NAME = "DevicesView";
    public static final String COMPUTER_VIEW = "ComputerView";
    public static final String CELL_PHONE_VIEW = "CellPhoneView";
    private MenuPanel menu = null;
    private VerticalLayout panelContent = null;
    private String username = "";
    private Device device;

    private DeviceManager deviceManager;
    private UserManager userManager;
    private ForLangsManager forlangsManager;
    private ProjectManager projectManager;
    private Navigator navigator;
    private Panel panel;
    private EmailManager emailManager;
    private ProfileForm profileForm;
    private User user;
    private ForeignLanguage foreingLanguage;
    private Project project;

    public DevicesView(final Navigator navigator, final DeviceManager deviceManager, final UserManager userManager,
            final ForLangsManager forlangsManager, final EmailManager email, final ProjectManager projectManager) {


        this.navigator = navigator;
        this.deviceManager = deviceManager;
        this.userManager = userManager;
        this.forlangsManager = forlangsManager;
        this.emailManager = email;
        this.projectManager = projectManager;

        setSizeFull();

        HeaderPanel headerPanel = new HeaderPanel(1, navigator);
        UserUtil.getInstance().setHeaderPanelDevices(headerPanel);
        addComponent(headerPanel);


        // Layout with menu on left and view area on right
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSizeFull();

        // Have a menu on the left side of the screen
        menu = new MenuPanel(navigator);
        UserUtil.getInstance().setMenuPanel(menu);
        hLayout.addComponent(menu);

        // A panel that contains a content area on right
        panel = new Panel();
        panel.setStyleName(com.vaadin.ui.themes.Reindeer.PANEL_LIGHT);
        panel.setSizeFull();
        hLayout.addComponent(panel);
        hLayout.setExpandRatio(panel, 1.0f);

        addComponent(hLayout);
        setExpandRatio(hLayout, 1.0f);


        JavaScript.getCurrent().addFunction("cz.morosystems.education.vaadinexampleapp.views.devices", new JavaScriptFunction() {


            /**
             * 
             */
            private static final long serialVersionUID = -2604688587716832706L;

            @Override
            public void call(org.json.JSONArray arguments) throws org.json.JSONException {
                boolean delete = arguments.getBoolean(0);
                String deviceId = arguments.getString(1);

                if (delete) {
                    deviceManager.deleteDevice(deviceId);
                    setDeviceComponents();
                }
                navigator.navigateTo(MainNavigator.USER_VIEW + "/");
            }
        });

        JavaScript.getCurrent().addFunction("cz.morosystems.education.vaadinexampleapp.views.forms", new JavaScriptFunction() {


            /**
             * 
             */
            private static final long serialVersionUID = 8980739997558077941L;

            @Override
            public void call(org.json.JSONArray arguments) throws org.json.JSONException {
                boolean delete = arguments.getBoolean(0);
                String id = arguments.getString(1);

                if (delete) {
                    forlangsManager.deleteForeignLanguage(id);
                    setForLangsComponents();
                }
                navigator.navigateTo(MainNavigator.USER_VIEW + "/forlangs");
            }
        });

        JavaScript.getCurrent().addFunction("cz.morosystems.education.vaadinexampleapp.views.forms.project", new JavaScriptFunction() {


            /**
             * 
             */
            private static final long serialVersionUID = -3271763206430631380L;

            @Override
            public void call(org.json.JSONArray arguments) throws org.json.JSONException {
                boolean delete = arguments.getBoolean(0);
                String id = arguments.getString(1);

                if (delete) {
                    projectManager.deleteProject(id);
                    setProjectComponents();

                }
                navigator.navigateTo(MainNavigator.USER_VIEW + "/project");
            }
        });


    }


    @Override
    public void enter(ViewChangeEvent event) {

        this.username = AppHelper.getUserName();
        // this.username = "user";

        if (event.getParameters() == null || event.getParameters().isEmpty()) {

            panelContent = new VerticalLayout();
            setDeviceComponents();
            UserUtil.getInstance().setMenuParam(1);
            UserUtil.getInstance().setContent(AppHelper.CONTENT_DEVICES);
            menu.setComponents();
        } else {

            String[] params = event.getParameters().split("=");
            if (params[0].equals("add?device") && params[1].equals(DeviceType.PC.toString())) {
                panelContent = new VerticalLayout();
                setAddComputerComponents();


            } else if (params[0].equals("add?device") && params[1].equals(DeviceType.CELL_PHONE.toString())) {
                panelContent = new VerticalLayout();
                setAddCellPhoneComponents();

            } else if (params[0].equals("edit?deviceId")) {
                device = deviceManager.findDeviceById(params[1]);
                if (device instanceof Computer) {
                    panelContent = new VerticalLayout();
                    setEditComputerComponents();

                } else if (device instanceof CellPhone) {
                    panelContent = new VerticalLayout();
                    setEditCellPhoneComponents();
                }


            } else if (params[0].equals("profile")) {
                panelContent = new VerticalLayout();
                user = userManager.findUserByUserName(username);


                setProfileComponents();

                UserUtil.getInstance().setMenuPanel(menu);
                UserUtil.getInstance().setMenuParam(2);
                menu.setComponents();
            } else if (params[0].equals("knowledge")) {
                UserUtil.getInstance().setMenuPanel(menu);
                UserUtil.getInstance().setMenuParam(4);
                menu.setComponents();
                panelContent = new KnowledgeView(username, userManager, navigator);
                setKnowledgeComponents();
            } else if (params[0].equals("forlangs")) {
                panelContent = new VerticalLayout();


                setForLangsComponents();

                UserUtil.getInstance().setMenuPanel(menu);
                UserUtil.getInstance().setMenuParam(3);
                menu.setComponents();
            } else if (params[0].equals("add?forlangs")) {
                panelContent = new VerticalLayout();
                this.setAddForLangsComponents();
            } else if (params[0].equals("edit?forlangsId")) {
                panelContent = new VerticalLayout();
                this.foreingLanguage = forlangsManager.findForeignLanguageById(params[1]);
                this.setEditForLangsComponents();
            } else if (params[0].equals("project")) {
                panelContent = new VerticalLayout();


                setProjectComponents();

                UserUtil.getInstance().setMenuPanel(menu);
                UserUtil.getInstance().setMenuParam(5);
                menu.setComponents();
            } else if (params[0].equals("add?project")) {
                panelContent = new VerticalLayout();
                this.setAddProjectComponents();
            } else if (params[0].equals("edit?projectId")) {
                panelContent = new VerticalLayout();
                this.project = projectManager.findProjectById(params[1]);
                this.setEditProjectComponents();

            }

        }
        panelContent.setMargin(true);
        panel.setContent(panelContent); // Also clears
    }


    public void setProfileComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_PROFILE);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("menuProfile"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        BeanFieldGroup<User> binder = new BeanFieldGroup<User>(User.class);
        binder.setItemDataSource(user);

        profileForm = new ProfileForm(user, binder, userManager, emailManager);
        panelContent.addComponent(profileForm);


    }
    public void setEditCellPhoneComponents() {
        panelContent.removeAllComponents();


        UserUtil.getInstance().setContent(AppHelper.CONTENT_EDIT_CELLPHONE);
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        String name = device.getName();
        Label watching = new Label(rb.getString("cellphoneEditMobilePhone") + "'" + name + "'");
        watching.setStyleName("header-label-2");
        watching.setSizeUndefined();
        panelContent.addComponent(watching);
        panelContent.setComponentAlignment(watching, Alignment.TOP_LEFT);
        CellPhoneForm content = new CellPhoneForm(deviceManager, navigator, username, (CellPhone)device);
        panelContent.addComponent(content);


    }

    public void setAddCellPhoneComponents() {
        panelContent.removeAllComponents();


        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADD_CELLPHONE);
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label watching = new Label(rb.getString("cellphoneAddnewMobilePhone"));
        watching.setStyleName("header-label-2");
        watching.setSizeUndefined();
        panelContent.addComponent(watching);
        panelContent.setComponentAlignment(watching, Alignment.TOP_LEFT);

        CellPhoneForm content = new CellPhoneForm(deviceManager, navigator, username, null);
        panelContent.addComponent(content);


    }

    public void setEditComputerComponents() {
        panelContent.removeAllComponents();


        UserUtil.getInstance().setContent(AppHelper.CONTENT_EDIT_COMPUTER);
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        String name = device.getName();
        Label watching = new Label(rb.getString("computerEditComputer") + "'" + name + "'");
        watching.setStyleName("header-label-2");
        watching.setSizeUndefined();
        panelContent.addComponent(watching);
        panelContent.setComponentAlignment(watching, Alignment.TOP_LEFT);
        ComputerForm content = new ComputerForm(deviceManager, navigator, username, (Computer)device);
        panelContent.addComponent(content);

    }

    public void setAddComputerComponents() {
        panelContent.removeAllComponents();


        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADD_COMPUTER);
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label watching = new Label(rb.getString("computerAddnewComputer"));
        watching.setStyleName("header-label-2");
        watching.setSizeUndefined();
        panelContent.addComponent(watching);
        panelContent.setComponentAlignment(watching, Alignment.TOP_LEFT);

        ComputerForm content = new ComputerForm(deviceManager, navigator, username, null);
        panelContent.addComponent(content);


    }

    public void setDeviceComponents() {
        panelContent.removeAllComponents();

        /* Create the table with a caption. */
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label watching = new Label(rb.getString("devicesDevices"));
        watching.setStyleName("header-label-2");
        panelContent.addComponent(watching);
        panelContent.setComponentAlignment(watching, Alignment.TOP_LEFT);
        Table table = new Table("");

        table.setImmediate(true);
        table.setSelectable(true);

        /*
         * Define the names and data types of columns. The "default value" parameter is meaningless here.
         */
        table.addContainerProperty(rb.getString("devicesType"), String.class, null, rb.getString("devicesType"), null, null);
        table.addContainerProperty(rb.getString("devicesAdded"), String.class, null, rb.getString("devicesAdded"), null, null);
        table.addContainerProperty(rb.getString("devicesName"), String.class, null, rb.getString("devicesName"), null, null);
        table.addContainerProperty(rb.getString("devicesNotes"), String.class, null, rb.getString("devicesNotes"), null, null);
        table.addContainerProperty(rb.getString("devicesActions"), HorizontalLayout.class, null, rb.getString("devicesActions"), null, null);

        // view devices list
        List<DeviceView> deviceList = deviceManager.findDevicesByUserName(username);

        HorizontalLayout hbox = null;
        String deleteDevice = rb.getString("delete.device");
        for (DeviceView device : deviceList) {
            hbox = new HorizontalLayout();
            Button edit = new Button(rb.getString("devicesEdit"), new EditActionListener(navigator, device.getDeviceId()));
            edit.setStyleName("link");
            hbox.addComponent(edit);
            Label span = new Label(" | ");
            span.setStyleName("link-label");
            hbox.addComponent(span);
            Link delete = new Link(rb.getString("devicesDelete"), new ExternalResource(
                    "javascript:cz.morosystems.education.vaadinexampleapp.views.devices(confirm(" + deleteDevice + device.getName()
                            + "?'), '" + device.getDeviceId() + "')"));
            delete.setStyleName("link");
            hbox.addComponent(delete);
            table.addItem(new Object[] { device.getDeviceType(), device.getCreatedString(), device.getName(), device.getNotes(), hbox },
                    device.getDeviceId());


        }

        panelContent.addComponent(table);
        panelContent.setComponentAlignment(table, Alignment.TOP_LEFT);

        Button addDeviceb = new Button(rb.getString("devicesAddnewComputer"), new AddActionListener(navigator, DeviceType.PC));
        addDeviceb.setStyleName("link");
        panelContent.addComponent(addDeviceb);

        Button addComputerDeviceb = new Button(rb.getString("devicesAddnewMobilePhone"), new AddActionListener(navigator,
                DeviceType.CELL_PHONE));
        addComputerDeviceb.setStyleName("link");
        panelContent.addComponent(addComputerDeviceb);

    }

    public void setForLangsComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_FOR_LANG);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("menuForLangs"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);
        new ForeignLangList(forlangsManager, navigator, username, panelContent);

    }

    public void setAddForLangsComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADD_FOR_LANG);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("addForLangs"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        ForeignLangForm content = new ForeignLangForm(forlangsManager, navigator, username, null);
        panelContent.addComponent(content);


    }

    public void setEditForLangsComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_EDIT_FOR_LANG);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("editForLangs"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        ForeignLangForm content = new ForeignLangForm(forlangsManager, navigator, username, this.foreingLanguage);
        panelContent.addComponent(content);
    }

    public void setKnowledgeComponents() {
        UserUtil.getInstance().setContent(AppHelper.CONTENT_KNOWLEDGE);
        panelContent.removeAllComponents();
        ((KnowledgeView)panelContent).addViewComponents(username, userManager, navigator);
    }

    public void setProjectComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_PROJECT);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("legendProject"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);
        new ProjectList(projectManager, navigator, username, panelContent);


    }


    public void setAddProjectComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADD_PROJECT);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("addProject"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        ProjectForm content = new ProjectForm(projectManager, navigator, username, null);
        panelContent.addComponent(content);

    }


    public void setEditProjectComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_EDIT_PROJECT);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("editProject"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        ProjectForm content = new ProjectForm(projectManager, navigator, username, project);
        panelContent.addComponent(content);

    }
}
