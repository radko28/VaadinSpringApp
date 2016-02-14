package cz.morosystems.education.vaadinexampleapp.managers;

import cz.morosystems.education.vaadinexampleapp.entities.User;

/**
 * Interface for sending email
 * 
 * @author andrej.klima
 * 
 */
public interface EmailManager {

    /**
     * Send email to user's email address with selected subject
     */
    public void sendEMail(User user, String subject, String password);
}
