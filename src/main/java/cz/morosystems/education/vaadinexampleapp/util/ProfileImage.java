package cz.morosystems.education.vaadinexampleapp.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.StreamResource.StreamSource;

import cz.morosystems.education.vaadinexampleapp.entities.User;


public class ProfileImage implements StreamSource {


    private User user;
    private ByteArrayInputStream bais = null;


    public ProfileImage(User user) {
        this.user = user;
    }


    @Override
    public InputStream getStream() {
        if (user != null && user.getPhoto() != null) {
            bais = new ByteArrayInputStream(user.getPhoto());
        }
        return bais;
    }
}
