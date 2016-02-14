package cz.morosystems.education.vaadinexampleapp.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.vaadin.server.StreamResource.StreamSource;

import cz.morosystems.education.vaadinexampleapp.views.listeners.ImageUploader;


public class ActualImage implements StreamSource {


    private ImageUploader imageUploader;
    private ByteArrayInputStream bais = null;


    public ActualImage(ImageUploader imageUploader) {
        this.imageUploader = imageUploader;
    }


    @Override
    public InputStream getStream() {
        try {
            bais = new ByteArrayInputStream(imageUploader.getPictureDatabase());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bais;
    }
}