package cz.morosystems.education.vaadinexampleapp.managers.impl;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.vaadin.ui.UI;

import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.EmailManager;
import cz.morosystems.education.vaadinexampleapp.util.AppHelper;

/**
 * Class for sending email to registered user or user that changed his/her profile
 * 
 * @author andrej.klima
 * 
 */
@Component("emailManager")
@Scope("prototype")
public class EmailManagerImpl implements EmailManager {

    @Autowired
    private MailSender mailSender;

    @Value("${mail.sender}")
    private String emailSender;

    /**
     * Send email to user's email address with selected subject
     */
    @Override
    public void sendEMail(User user, String subject, String password) {
        ResourceBundle rb = AppHelper.getPropertiesFile(UI.getCurrent().getLocale().getLanguage());
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(emailSender);
        mail.setTo(user.getEmail());
        mail.setSubject(rb.getString(subject));
        StringBuffer text = new StringBuffer();
        text.append(rb.getString("profileFirstName") + " - " + user.getFirstname() + "\n");
        text.append(rb.getString("profileLastName") + " - " + user.getLastname() + "\n");
        text.append(rb.getString("profileBirthDate") + " - " + user.getBirthdateString() + "\n");
        text.append(rb.getString("profileUsername") + " - " + user.getUsername() + "\n");
        text.append(rb.getString("profilePassword") + " - " + password + "\n");

        mail.setText(text.toString());

        try {
            mailSender.send(mail);
        } catch (MailException me) {
            me.printStackTrace();
        }
    }
}
