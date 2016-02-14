package cz.morosystems.education.vaadinexampleapp.views.device.listeners;

import java.util.ResourceBundle;

import org.joda.time.DateTime;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.EmailManager;
import cz.morosystems.education.vaadinexampleapp.managers.UserManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;
import cz.morosystems.education.vaadinexampleapp.views.device.forms.ProfileForm;

/**
 * 
 * Listener for changing user profile.ROLE_USER.
 * 
 * @author radoslav.kuzma
 * 
 */
public class ProfileListener implements Button.ClickListener {

    private BeanFieldGroup<User> binder;
    private User user;
    private UserManager userManager;
    private ProfileForm profileForm;
    private String verifyError = "";

    private EmailManager emailManager;

    public ProfileListener(BeanFieldGroup<User> binder, User user, UserManager userManager, ProfileForm profileForm, EmailManager email) {
        this.binder = binder;
        this.user = user;
        this.userManager = userManager;
        this.profileForm = profileForm;
        this.emailManager = email;
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        verifyError = rb.getString("field.passwordVerify");
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
        String desc = event.getButton().getDescription();
        boolean valid = false;
        if ("profile".equals(desc)) {
            try {
                binder.commit();
                user.setBirthdate(new DateTime(profileForm.getBirthDateField().getValue().getTime()));
                byte photo[] = profileForm.getUploader().getPictureDatabase();
                user.setPhoto(photo);
                valid = true;
            } catch (CommitException ce) {
                ce.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (valid) {
                userManager.updateProfile(user);
            }
        } else if ("password".equals(desc)) {
            if (userManager.verifyPassword(user.getUsername(), profileForm.getOldPasswordField().getValue())) {
                profileForm.getOldPasswordField().setComponentError(null);
                user.setPassword(profileForm.getNewPasswordField().getValue());
                emailManager.sendEMail(user, "email.update", user.getPassword());
                userManager.updatePassword(user);
            } else {
                profileForm.getOldPasswordField().setComponentError(new UserError(verifyError));
            }
        }
    }
}