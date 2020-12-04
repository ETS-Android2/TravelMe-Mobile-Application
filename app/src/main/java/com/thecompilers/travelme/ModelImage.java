package com.thecompilers.travelme;
//load image from images in databse
public class ModelImage {
    String id,location,imageurl;

    public ModelImage() {
    }

    public ModelImage(String id, String location, String imageurl) {
        this.id = id;
        this.location = location;
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
