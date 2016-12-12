package cs213.photoalbum;

import android.net.Uri;

import java.util.HashMap;

/**
 * Created by deeptisailam on 12/10/16.
 */
public class Image {
    private HashMap<String,String> image_tags;
    private String image_name;
    private String image_uri;

    public Image(HashMap<String, String> image_tags, String image_name) {
        this.image_tags = image_tags;
        this.image_name = image_name;
    }

    public Image(HashMap<String, String> image_tags, String image_name, String image_uri) {
        this.image_tags = image_tags;
        this.image_name = image_name;
        this.image_uri=image_uri;
    }

    public Image() {
    }

    public HashMap<String, String> getImage_tags() {
        return image_tags;
    }

    public void setImage_tags(HashMap<String, String> image_tags) {
        this.image_tags = image_tags;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    @Override
    public String toString() {
        return "Image{" +
                "image_tags=" + image_tags +
                ", image_name='" + image_name + '\'' +
                ", image_uri=" + image_uri +
                '}';
    }
}
