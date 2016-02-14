package cz.morosystems.education.vaadinexampleapp.views.user;

import java.util.List;
import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.entities.RoleType;
import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.CustomerAreaManager;
import cz.morosystems.education.vaadinexampleapp.managers.EmailManager;
import cz.morosystems.education.vaadinexampleapp.managers.KnowledgeManager;
import cz.morosystems.education.vaadinexampleapp.managers.ProjectRoleManager;
import cz.morosystems.education.vaadinexampleapp.managers.UserManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.util.ProfileImage;
import cz.morosystems.education.vaadinexampleapp.util.UserUtil;
import cz.morosystems.education.vaadinexampleapp.views.HeaderPanel;
import cz.morosystems.education.vaadinexampleapp.views.MainNavigator;
import cz.morosystems.education.vaadinexampleapp.views.MenuPanel;
import cz.morosystems.education.vaadinexampleapp.views.forms.CustomerAreaForm;
import cz.morosystems.education.vaadinexampleapp.views.forms.CustomerAreaList;
import cz.morosystems.education.vaadinexampleapp.views.forms.ProjectRoleForm;
import cz.morosystems.education.vaadinexampleapp.views.forms.ProjectRoleList;

/**
 * 
 * View for users list/edit/add form, ROLE_ADMIN.
 * 
 * @author andrej.klima
 * 
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(UserView.NAME)
public class UserView extends VerticalLayout implements View {

    private VerticalLayout panelContent = null;
    private Label legend = null;

    private static final long serialVersionUID = 1L;
    private ResourceBundle rb = null;

    public static final String NAME = "UserView";
    static final String[] visibleColumns = { "username", "firstname", "lastname", "birthdateString" };
    static final DateTimeFormatter dtf = DateTimeFormat.forPattern(AppHelper.DATE_FORMAT);
    Navigator navigator;
    Panel panel;
    private MenuPanel menu = null;
    private String userId = null;

    private User user;
    private ProjectRole projectRole;
    private CustomerArea customerArea;
    private UserManager userManager;
    private List<User> ul;
    private UserListener userListener = new UserListener();
    BeanFieldGroup<User> binder;
    private UserForm userForm;

    private EmailManager emailManager;
    private KnowledgeManager knowledgeManager;
    private ProjectRoleManager projectRoleManager;
    private CustomerAreaManager customerAreaManager;

    public UserView(final Navigator navigator, final UserManager userManager, final EmailManager emailManager,
            final KnowledgeManager knowledgeManager, final ProjectRoleManager projectRoleManager,
            final CustomerAreaManager customerAreaManager) {
        this.navigator = navigator;
        this.userManager = userManager;
        this.emailManager = emailManager;
        this.knowledgeManager = knowledgeManager;
        this.projectRoleManager = projectRoleManager;
        this.customerAreaManager = customerAreaManager;
        setSizeFull();

        HeaderPanel headerPanel = new HeaderPanel(1, navigator);
        UserUtil.getInstance().setHeaderPanelUser(headerPanel);
        addComponent(headerPanel);


        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSizeFull();


        // Have a menu on the left side of the screen
        menu = new MenuPanel(navigator);
        UserUtil.getInstance().setMenuPanel(menu);
        hLayout.addComponent(menu);


        // A panel that contains a content area on right
        panel = new Panel();
        panel.setSizeFull();
        panel.setStyleName(com.vaadin.ui.themes.Reindeer.PANEL_LIGHT);
        hLayout.addComponent(panel);
        hLayout.setExpandRatio(panel, 1.0f);
        addComponent(hLayout);
        setExpandRatio(hLayout, 1.0f);


        JavaScript.getCurrent().addFunction("cz.morosystems.education.vaadinexampleapp.views.user", new JavaScriptFunction() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(org.json.JSONArray arguments) throws org.json.JSONException {
                boolean delete = arguments.getBoolean(0);
                String userId = arguments.getString(1);

                if (delete) {
                    userManager.remove(userId);
                    ul = userManager.findAllUsers();
                }
                navigator.navigateTo(MainNavigator.ADMIN_VIEW);
            }
        });


        JavaScript.getCurrent().addFunction("cz.morosystems.education.vaadinexampleapp.views.forms.projectrole", new JavaScriptFunction() {


            /**
             * 
             */
            private static final long serialVersionUID = -4805630308062198976L;

            @Override
            public void call(org.json.JSONArray arguments) throws org.json.JSONException {
                boolean delete = arguments.getBoolean(0);
                String id = arguments.getString(1);

                if (delete) {
                    projectRoleManager.deleteProjectRole(id);
                    setProjectRolesComponents();

                }
                navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/projectRoles");
            }
        });

        JavaScript.getCurrent().addFunction("cz.morosystems.education.vaadinexampleapp.views.forms.customerarea", new JavaScriptFunction() {


            /**
                     * 
                     */
            private static final long serialVersionUID = 2460160663881528106L;


            @Override
            public void call(org.json.JSONArray arguments) throws org.json.JSONException {
                boolean delete = arguments.getBoolean(0);
                String id = arguments.getString(1);

                if (delete) {
                    customerAreaManager.deleteCustomerArea(id);
                    setCustomerAreasComponents();

                }
                navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/customerAreas");
            }
        });

    }
    @SuppressWarnings("serial")
    public void setUserComponents() {

        rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        UserUtil.getInstance().setContent(AppHelper.CONTENT_USER);
        panelContent.removeAllComponents();

        Label legend = new Label(rb.getString("usersUsers"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        ul = userManager.findAllUsers();
        BeanItemContainer<User> bc = new BeanItemContainer<User>(User.class, ul);

        Table ut = new Table("", bc);
        ut.addStyleName(com.vaadin.ui.themes.Reindeer.TABLE_BORDERLESS);
        ut.setVisibleColumns(visibleColumns);
        ut.addGeneratedColumn("photo", new Table.ColumnGenerator() {

            public Component generateCell(Table source, Object itemId, Object columnId) {
                final User u = (User)itemId;
                StreamSource imagesource = new ProfileImage(u);
                StreamResource resource = new StreamResource(imagesource, "filename");
                Image image = new Image(null, resource);
                return image;
            }
        });

        ut.addGeneratedColumn("authorities", new Table.ColumnGenerator() {

            public Component generateCell(Table source, Object itemId, Object columnId) {
                final User u = (User)itemId;
                CheckBox cb = new CheckBox();
                cb.setValue(u.getAuthorities().getAuthority().equals(RoleType.ROLE_ADMIN));
                cb.setImmediate(true);
                return cb;
            }
        });
        ut.addGeneratedColumn("userId", new Table.ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                User u = (User)itemId;

                final Button edit = new Button(rb.getString("usersEdit"));
                edit.setData(u.getUserId());

                edit.setDescription("edit");
                edit.addClickListener(userListener);
                edit.addStyleName("link");
                String deleteUser = rb.getString("delete.user");
                Link delete = new Link(rb.getString("usersDelete"), new ExternalResource(
                        "javascript:cz.morosystems.education.vaadinexampleapp.views.user(confirm(" + deleteUser + u.getUsername() + "'), '"
                                + u.getUserId() + "')"));
                delete.setDescription("delete user");
                GridLayout layout = new GridLayout(3, 1);
                layout.addComponent(edit);
                Label span = new Label(" | ");
                span.setStyleName("link-label");
                layout.addComponent(span);
                layout.addComponent(delete);
                return layout;
            }
        });

        String[] header = { rb.getString("usersUsername"), rb.getString("usersFirstName"), rb.getString("usersLastName"),
                rb.getString("usersDateofBirth"), rb.getString("labelPhoto"), rb.getString("usersAdmin"), rb.getString("usersActions") };
        ut.setColumnHeaders(header);
        ut.setImmediate(true);
        panelContent.addComponent(ut);
        Button addUser = new Button(rb.getString("usersAddnewUser"));
        addUser.setDescription("add");
        addUser.addClickListener(userListener);
        addUser.addStyleName("link");
        panelContent.addComponent(addUser);

    }
    @Override
    public void enter(ViewChangeEvent event) {
        panelContent = new VerticalLayout();

        if (null == event.getParameters() || event.getParameters().length() == 0) {

            UserUtil.getInstance().setMenuParam(10);
            menu.setComponents();
            setUserComponents();
        } else {
            String[] urlParams = event.getParameters().split("=");
            if (urlParams[0].equals("edit")) {
                user = userManager.findUserById(urlParams[1]);
                this.setEditComponents();
                this.setAddEditComponents();

            } else if (urlParams[0].equals("add")) {
                this.setAddComponents();
                this.setAddEditComponents();

            } else if (urlParams[0].equals("projectRoles")) {
                panelContent = new VerticalLayout();


                setProjectRolesComponents();

                UserUtil.getInstance().setMenuPanel(menu);
                UserUtil.getInstance().setMenuParam(11);
                menu.setComponents();
            } else if (urlParams[0].equals("add?projectRole")) {
                panelContent = new VerticalLayout();
                this.setAddProjectRoleComponents();
            } else if (urlParams[0].equals("edit?projectRoleId")) {
                panelContent = new VerticalLayout();
                this.projectRole = projectRoleManager.findProjectRoleById(urlParams[1]);
                this.setEditProjectRoleComponents();
            } else if (urlParams[0].equals("customerAreas")) {
                panelContent = new VerticalLayout();


                setCustomerAreasComponents();

                UserUtil.getInstance().setMenuPanel(menu);
                UserUtil.getInstance().setMenuParam(12);
                menu.setComponents();

            } else if (urlParams[0].equals("add?customerArea")) {
                panelContent = new VerticalLayout();
                this.setAddCutomerAreaComponents();
            } else if (urlParams[0].equals("edit?customerAreaId")) {
                panelContent = new VerticalLayout();
                this.customerArea = customerAreaManager.findCustomerAreaById(urlParams[1]);
                this.setEditCustomerAreaComponents();

            } else if (urlParams[0].equals("certificateTypes")) {
                panelContent = new VerticalLayout();


                setCertificateTypesComponents();

                UserUtil.getInstance().setMenuPanel(menu);
                UserUtil.getInstance().setMenuParam(13);
                menu.setComponents();
            } else if (urlParams[0].equals("knowledgeTypes")) {
                panelContent = new KnowledgeTypeView(knowledgeManager);

                setKnowledgeTypesComponents();

                UserUtil.getInstance().setMenuPanel(menu);
                UserUtil.getInstance().setMenuParam(14);
                menu.setComponents();
            }

        }


        panelContent.setMargin(true);
        panel.setContent(panelContent);
    }


    public void setEditCustomerAreaComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_EDIT_CUSTOMER_AREAS);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("editCustomerArea"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        CustomerAreaForm content = new CustomerAreaForm(customerAreaManager, navigator, customerArea);
        panelContent.addComponent(content);


    }
    public void setAddCutomerAreaComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADD_CUSTOMER_AREAS);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("addCustomerArea"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        CustomerAreaForm content = new CustomerAreaForm(customerAreaManager, navigator, null);
        panelContent.addComponent(content);

    }
    public void setEditProjectRoleComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_EDIT_PROJECT_ROLES);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("editProjectRole"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        ProjectRoleForm content = new ProjectRoleForm(projectRoleManager, navigator, projectRole);
        panelContent.addComponent(content);


    }
    public void setAddProjectRoleComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADD_PROJECT_ROLES);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("addProjectRole"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        ProjectRoleForm content = new ProjectRoleForm(projectRoleManager, navigator, null);
        panelContent.addComponent(content);


    }
    public void setKnowledgeTypesComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADMIN_KNOWLEDGE_TYPES);

        setSizeFull();
        ((KnowledgeTypeView)panelContent).addKnowledgeTypeViewComponents();
    }

    public void setCertificateTypesComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADMIN_CERTIFICATE_TYPES);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("legendCertificateTypes"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);


    }

    public void setCustomerAreasComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADMIN_CUSTOMER_AREAS);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("legendCustomerAreas"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        new CustomerAreaList(customerAreaManager, navigator, panelContent);


    }

    public void setProjectRolesComponents() {
        panelContent.removeAllComponents();
        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADMIN_PROJECT_ROLES);


        setSizeFull();
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        Label legend = new Label(rb.getString("legendProjectRoles"));
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);

        new ProjectRoleList(projectRoleManager, navigator, panelContent);


    }


    public class UserListener implements Button.ClickListener {

        private static final long serialVersionUID = 1L;

        @Override
        public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
            String desc = event.getButton().getDescription();

            if ("edit".equals(desc) || "delete".equals(desc)) {
                userId = (String)event.getButton().getData();
                navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/" + desc + "?userId=" + userId);
            } else if ("save".equals(desc) || "update".equals(desc)) {
                boolean valid = true;

                long time = -1;
                userForm.getBirthDate().setComponentError(null);

                try {
                    userForm.getBirthDate().validate();
                    time = userForm.getBirthDate().getValue().getTime();
                } catch (NullPointerException npe) {
                    String empty = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage()).getString("field.required");
                    userForm.getBirthDate().setComponentError(new UserError(empty));
                    valid = false;
                } catch (com.vaadin.data.Validator.InvalidValueException e) {
                    valid = false;
                }

                boolean userNameNotExist = true;

                if ("save".equals(desc)) {
                    if (null != userManager.findUserByUserName(userForm.getUserNameField().getValue())) {
                        userNameNotExist = false;
                        String exist = rb.getString("field.username");
                        userForm.getUserNameField().setComponentError(new UserError(exist));
                        valid = false;
                    } else {
                        userForm.getUserNameField().setComponentError(null);
                    }
                }


                try {
                    binder.commit();
                    byte photo[] = userForm.getUploader().getPictureDatabase();
                    user.setPhoto(photo);
                } catch (CommitException e) {
                    valid = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (valid) {
                    user.setBirthdate(new DateTime(time));
                    if ("update".equals(desc)) {
                        userManager.update(user, String.valueOf(user.getRole()));
                    } else if (userNameNotExist) {
                        String password = user.getPassword();
                        userManager.save(user, String.valueOf(user.getRole()));
                        emailManager.sendEMail(user, "email.register", password);
                    }

                    ul = userManager.findAllUsers();
                    navigator.navigateTo(MainNavigator.ADMIN_VIEW);
                }
            } else if ("add".equals(desc)) {
                navigator.navigateTo(MainNavigator.ADMIN_VIEW + "/add");
            } else if ("cancel".equals(desc)) {
                navigator.navigateTo(MainNavigator.ADMIN_VIEW);
            }
            userForm.getUploader().deleteImage();
        }
    }

    public void setAddComponents() {
        rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        UserUtil.getInstance().setContent(AppHelper.CONTENT_ADD_USER);
        user = new User();
        legend = new Label(rb.getString("addAddnewUser"));
        setAddEditComponents();

    }
    public void setEditComponents() {
        rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        UserUtil.getInstance().setContent(AppHelper.CONTENT_EDIT_USER);
        legend = new Label(rb.getString("editAddnewUser") + " " + user.getUsername());
        setAddEditComponents();
    }
    private void setAddEditComponents() {
        panelContent.removeAllComponents();
        binder = new BeanFieldGroup<User>(User.class);
        binder.setItemDataSource(user);
        legend.setStyleName("header-label-2");
        panelContent.addComponent(legend);
        panelContent.setComponentAlignment(legend, Alignment.TOP_LEFT);
        panelContent.addComponent(userForm = new UserForm(user, binder, userListener));
        panelContent.setComponentAlignment(userForm, Alignment.TOP_LEFT);
    }

}