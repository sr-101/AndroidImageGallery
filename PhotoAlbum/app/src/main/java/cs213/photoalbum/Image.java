package cs213.photoalbum;

/**
 * Created by deeptisailam on 12/10/16.
 */
class Image {
    private String person_tag;
    private String location_tag;
    private String image_name;
    private String image_uri;

    Image() {
    }

    public String getPerson_tag() {
        return person_tag;
    }

    public void setPerson_tag(String person_tag) {
        this.person_tag = person_tag;
    }

    public String getLocation_tag() {
        return location_tag;
    }

    public void setLocation_tag(String location_tag) {
        this.location_tag = location_tag;
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

    @Override
    public String toString() {
        return "Image{" +
                "person_tag='" + person_tag + '\'' +
                ", location_tag='" + location_tag + '\'' +
                ", image_name='" + image_name + '\'' +
                ", image_uri='" + image_uri + '\'' +
                '}';
    }
}
