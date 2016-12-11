package cs213.photoalbum;

import java.util.HashMap;

/**
 * Created by deeptisailam on 12/10/16.
 */
public class Image {
    private HashMap<String,String> image_tags;
    private String album_name;
    private String image_name;

    public Image(HashMap<String, String> image_tags, String album_name, String image_name) {
        this.image_tags = image_tags;
        this.album_name = album_name;
        this.image_name = image_name;
    }

    public Image() {
    }

    public HashMap<String, String> getImage_tags() {
        return image_tags;
    }

    public void setImage_tags(HashMap<String, String> image_tags) {
        this.image_tags = image_tags;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
