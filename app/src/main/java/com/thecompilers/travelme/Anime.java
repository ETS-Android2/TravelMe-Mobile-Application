package com.thecompilers.travelme;
//this is used for getting and setting for places to visit
public class Anime {
    private  int id;
    private String placename ;
    private String description;
    private String rating ;
    private String location;
    private String image_url;


    public Anime() {
    }

    public Anime(int id, String placename, String description, String rating, String location, String image_url) {
        this.id = id;
        this.placename = placename;
        this.description = description;
        this.rating = rating;
        this.location = location;
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
