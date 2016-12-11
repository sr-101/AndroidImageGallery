package cs213.photoalbum;

import java.util.ArrayList;

/**
 * Created by deeptisailam on 12/10/16.
 */

public class Album {
    private String album_name;

    private String album_cover;

    ArrayList<Image>images;

    public Album() {
    }

    public Album(String album_name, String album_cover, ArrayList<Image> images) {
        this.album_name = album_name;
        this.images = images;
        this.album_cover=album_cover;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_cover() {
        if(album_cover==null){
            album_cover="srk.jpg";
        }
        return album_cover;
    }

    public void setAlbum_cover(String album_cover) {
        this.album_cover = album_cover;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
