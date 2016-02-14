package cz.morosystems.education.vaadinexampleapp.views.listeners;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.util.ActualImage;
import cz.morosystems.education.vaadinexampleapp.util.ProfileImage;


public class ImageUploader implements SucceededListener, Receiver {

    private File file;
    private ByteArrayOutputStream baos = null; // Stream to write to
    private FileOutputStream fos = null;
    private byte pictureDatabase[];
    private Image image = null;
    private VerticalLayout vL = null;
    private ResourceBundle rb = null;


    public ImageUploader(VerticalLayout vL, ResourceBundle rb) {
        this.vL = vL;
        this.rb = rb;
    }
    public OutputStream receiveUpload(String filename, String mimeType) {

        if (mimeType.startsWith("image/")) {
            try {

                file = new File(filename);
                fos = new FileOutputStream(file);
            } catch (final java.io.FileNotFoundException e) {
                Notification.show("Could not open file<br/>", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
                fos = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Notification.show(rb.getString("photoFormat"), Notification.TYPE_ERROR_MESSAGE);
        }
        return fos; // Return the output stream to write to
    }
    public void uploadSucceeded(SucceededEvent event) {

        if (fos != null) {
            try {
                pictureDatabase = toByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            StreamSource imagesource = new ActualImage(this);
            BufferedImage bimg = null;
            try {
                bimg = ImageIO.read(imagesource.getStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bimg == null || bimg.getHeight() > 400 || bimg.getWidth() > 400) {
                pictureDatabase = null;
                Notification.show(rb.getString("photoSize"), Notification.TYPE_ERROR_MESSAGE);
            } else {
                StreamResource resource = new StreamResource(imagesource, "filename");
                if (image != null) {
                    vL.removeComponent(image);
                }
                image = new Image(null, resource);
                image.setVisible(true);
                image.setSource(resource);
                vL.addComponent(image);
            }

        }
    }
    private byte[] toByteArray(File file) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = new FileInputStream(file);
        try {
            byte[] buf = new byte[1024];
            while (true) {
                int r = in.read(buf);
                if (r == -1) {
                    break;
                }
                out.write(buf, 0, r);
            }

        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }

    public byte[] getPictureDatabase() throws IOException {
        deleteImage();
        return pictureDatabase;
    }

    public void deleteImage() {
        if (file != null && file.exists()) {
            file.delete();
        }
    }
    public Component getPhotoLayout(User user) {
        Upload photo = new Upload(rb.getString("addPhoto"), null);
        photo.setReceiver(this);
        photo.addSucceededListener(this);
        vL.addComponent(photo);
        StreamSource imagesource = null;
        if (user.getPhoto() != null) {
            imagesource = new ProfileImage(user);
            StreamResource resource = new StreamResource(imagesource, "filename");
            image = new Image(null, resource);
            vL.addComponent(image);
        }


        return vL;
    }


}
