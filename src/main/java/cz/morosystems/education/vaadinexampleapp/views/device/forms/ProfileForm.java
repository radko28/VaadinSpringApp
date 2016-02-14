package cz.morosystems.education.vaadinexampleapp.views.device.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.EmailManager;
import cz.morosystems.education.vaadinexampleapp.managers.UserManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.device.listeners.ProfileListener;
import cz.morosystems.education.vaadinexampleapp.views.listeners.ImageUploader;

/**
 * 
 * Form for view and edit detail of the user.ROLE_USER.
 * 
 * @author andrej.klima
 * 
 */
public class ProfileForm extends FormLayout {

    private static final long serialVersionUID = -5271540141939041442L;
    private static final SimpleDateFormat df = new SimpleDateFormat(AppHelper.DATE_FORMAT);

    TextField userName = new TextField();
    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField email = new TextField();
    PasswordField oldPassword = new PasswordField();
    PasswordField password = new PasswordField();
    PasswordField confirm = new PasswordField();
    DateField birthDate;
    String required = "";
    private ProfileListener profileListener;

    private ImageUploader uploader = null;

    public ProfileForm(User user, final BeanFieldGroup<User> profile, UserManager userManager, EmailManager emailManager) {


        profile.bind(firstName, "firstname");
        profile.bind(lastName, "lastname");
        profile.bind(email, "email");

        setData(user);
        userName.setValue(user.getUsername());
        userName.setStyleName("disabled");
        userName.setEnabled(false);

        final ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        email.addValidator(new EmailValidator(rb.getString("field.invalidEmail")));

        birthDate = new DateField() {

            private static final long serialVersionUID = 1L;

            @Override
            protected Date handleUnparsableDateString(String dateString) throws ConversionException {
                try {
                    return df.parse(dateString);
                } catch (Exception e) {
                    throw new ConversionException(rb.getString("value.incorrect"));
                }
            }
        };
        birthDate.setValue(user.getBirthdateDate());
        birthDate.setRequired(true);
        required = rb.getString("field.required");
        birthDate.setRequiredError(required);
        birthDate.setDateFormat(AppHelper.DATE_FORMAT);
        profileListener = new ProfileListener(profile, user, userManager, this, emailManager);
        Button profileButton = new Button(rb.getString("profileChangeMyProfile"), profileListener);
        profileButton.setDescription("profile");

        Button changePassword = new Button(rb.getString("profileChangePassword"), new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                boolean valid = true;
                if (oldPassword.getValue().trim().length() < 1) {
                    valid = false;
                    oldPassword.setComponentError(new UserError(required));
                } else {
                    oldPassword.setComponentError(null);
                }

                if (password.getValue().trim().length() < 1) {
                    valid = false;
                    password.setComponentError(new UserError(required));
                } else {
                    password.setComponentError(null);
                }

                if (confirm.getValue().trim().length() < 1) {
                    valid = false;
                    confirm.setComponentError(new UserError(required));
                } else {
                    confirm.setComponentError(null);
                }

                if (valid) {
                    if (!password.getValue().equals(confirm.getValue())) {
                        password.setComponentError(new UserError(rb.getString("field.confirm")));
                        confirm.setComponentError(new UserError(rb.getString("field.confirm")));
                    } else {
                        password.setComponentError(null);
                        confirm.setComponentError(null);
                        profileListener.buttonClick(event);
                    }
                }
            }
        });
        changePassword.setDescription("password");

        String[] labels = { rb.getString("profileFirstName"), rb.getString("profileUsername"), rb.getString("profileLastName"),
                rb.getString("profilePassword"), rb.getString("profileBirthDate"), rb.getString("profileNewPassword"),
                rb.getString("addEmail") };
        AbstractComponent[] fields = { firstName, userName, lastName, oldPassword, birthDate, password, email };
        GridLayout grid = new GridLayout(4, 7);
        grid.setSpacing(true);
        grid.setWidth("800px");
        grid.setHeight("180px");

        for (int i = 0; i < labels.length; i++) {
            grid.addComponent(new Label(labels[i]));
            grid.addComponent(fields[i]);
        }
        grid.addComponent(new Label(rb.getString("profileConfirmNewPassword")));
        grid.addComponent(confirm);
        // upload profile picture
        VerticalLayout vL = new VerticalLayout();
        uploader = new ImageUploader(vL, rb);
        grid.addComponent(uploader.getPhotoLayout(user));
        //
        grid.addComponent(profileButton, 0, 5, 1, 6);
        grid.addComponent(changePassword, 3, 4, 3, 4);
        addComponent(grid);
    }

    public DateField getBirthDateField() {
        return birthDate;
    }

    public PasswordField getOldPasswordField() {
        return oldPassword;
    }

    public PasswordField getNewPasswordField() {
        return password;
    }

    public ImageUploader getUploader() {
        return uploader;
    }
}
