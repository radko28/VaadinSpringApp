package cz.morosystems.education.vaadinexampleapp.views.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.joda.time.DateTime;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.listeners.ImageUploader;

/**
 * UserForm to create/edit user entity
 * 
 * @author andrej.klima
 * 
 */
public class UserForm extends FormLayout {

    private static final long serialVersionUID = 6522313923772898036L;
    private static final SimpleDateFormat df = new SimpleDateFormat(AppHelper.DATE_FORMAT);

    TextField userName = new TextField();
    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField email = new TextField();
    PasswordField password = new PasswordField();
    PasswordField confirm = new PasswordField();
    DateField birthDate;
    CheckBox admin;
    private ImageUploader uploader;


    String require = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage()).getString("field.required");

    public UserForm(final User user, final BeanFieldGroup<User> binder, final UserView.UserListener listener) {
        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());

        String storno = null;
        Button save = new Button();
        if (null != user.getUserId() && user.getUserId().length() > 0) {
            storno = rb.getString("editStorno");
            save.setCaption(rb.getString("editSave"));
            save.setDescription("update");
            save.addClickListener(new Button.ClickListener() {

                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    boolean validPassword = true;

                    if (password.getValue().length() > 1 && password.getValue().trim().length() < 1) {
                        validPassword = false;
                        password.setComponentError(new UserError(rb.getString("field.password.incorrect")));
                    } else if (password.getValue().trim().length() > 0) {
                        validPassword = validatePassword();
                    }

                    if (validPassword) {
                        password.setComponentError(null);
                        confirm.setComponentError(null);

                        if (password.getValue().trim().length() > 0) {
                            user.setPassword(password.getValue());
                        } else {
                            user.setPassword(null);
                        }

                        listener.buttonClick(event);
                    }
                }
            });
        } else {
            storno = rb.getString("addStorno");
            save.setCaption(rb.getString("addSave"));
            save.setDescription("save");
            save.addClickListener(new Button.ClickListener() {

                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    boolean validPassword = true;

                    if (null == password.getValue() || password.getValue().length() < 1) {
                        validPassword = false;
                        password.setComponentError(new UserError(require));
                    } else if (password.getValue().trim().length() < 1) {
                        validPassword = false;
                        password.setComponentError(new UserError(rb.getString("field.password.incorrect")));
                    } else {
                        validPassword = validatePassword();
                    }

                    if (validPassword) {
                        user.setPassword(password.getValue());
                        password.setComponentError(null);
                        confirm.setComponentError(null);

                        listener.buttonClick(event);
                    }
                }
            });
            user.setBirthdate(DateTime.now());
        }

        Button cancel = new Button(storno, listener);
        cancel.setDescription("cancel");
        binder.bind(userName, "username");
        if (null != user.getUserId() && user.getUserId().length() > 0) {
            userName.setEnabled(false);
            userName.setStyleName("disabled");
        }
        binder.bind(firstName, "firstname");
        binder.bind(lastName, "lastname");
        binder.bind(email, "email");
        email.addValidator(new EmailValidator(rb.getString("field.invalidEmail")));
        password.setValue(" ");
        birthDate = new DateField() {

            private static final long serialVersionUID = 1L;

            @Override
            protected Date handleUnparsableDateString(String dateString) throws ConversionException {
                try {
                    return df.parse(dateString);
                } catch (java.text.ParseException pe) {
                    throw new ConversionException(rb.getString("value.incorrect"));
                }
            }
        };

        birthDate.setDateFormat(AppHelper.DATE_FORMAT);
        birthDate.setValue(user.getBirthdate().toDate());
        admin = binder.buildAndBind(rb.getString("addAdministrator"), "adminRole", CheckBox.class);
        String[] labels = { rb.getString("addUsername"), rb.getString("addFirstName"), rb.getString("addNewPassword"),
                rb.getString("addLastName"), rb.getString("addConfirmNewPassword"), rb.getString("addBirthDate"), rb.getString("addEmail") };

        AbstractComponent[] fields = { userName, firstName, password, lastName, confirm, birthDate, email };
        GridLayout grid = new GridLayout(4, 6);
        grid.setSpacing(true);
        grid.setWidth("800px");
        grid.setHeight("180px");

        for (int i = 0; i < labels.length; i++) {
            grid.addComponent(new Label(labels[i]));
            grid.addComponent(fields[i]);
        }

        grid.addComponent(admin, 2, 3, 3, 3);

        // upload profile picture
        VerticalLayout vL = new VerticalLayout();
        uploader = new ImageUploader(vL, rb);
        grid.addComponent(uploader.getPhotoLayout(user));
        //
        grid.addComponent(save, 1, 5);
        grid.addComponent(cancel, 2, 5);
        addComponent(grid);
        setComponentAlignment(grid, Alignment.TOP_LEFT);
    }
    boolean validatePassword() {
        if (null == confirm.getValue() || confirm.getValue().length() < 0) {
            confirm.setComponentError(new UserError(require));
            return false;
        }

        if (!password.getValue().equals(confirm.getValue())) {
            String confirmError = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage()).getString("field.confirm");
            password.setComponentError(new UserError(confirmError));
            confirm.setComponentError(new UserError(confirmError));
            return false;
        }

        return true;
    }
    public TextField getUserNameField() {
        return userName;
    }

    public DateField getBirthDate() {
        return birthDate;
    }

    public ImageUploader getUploader() {
        return uploader;
    }

    public void setUploader(ImageUploader uploader) {
        this.uploader = uploader;
    }

}
