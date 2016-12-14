package cs213.photoalbum;

import java.util.HashMap;

/**
 * Created by deeptisailam on 12/10/16.
 */
class Image {
    private HashMap<String,String> image_tags;
    private String image_name;
    private String image_uri;
    private int imageIndex;
    private int albumIndex;

    Image() {
    }

     HashMap<String, String> getImage_tags() {
        return image_tags;
    }

    void setImage_tags(HashMap<String, String> image_tags) {
        this.image_tags = image_tags;
    }

    String getImage_name() {
        return image_name;
    }

    void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    String getImage_uri() {
        return image_uri;
    }

    void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public int getAlbumIndex() {
        return albumIndex;
    }

    public void setAlbumIndex(int albumIndex) {
        this.albumIndex = albumIndex;
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
