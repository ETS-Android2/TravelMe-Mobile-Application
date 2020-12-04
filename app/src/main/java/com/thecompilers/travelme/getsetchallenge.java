package com.thecompilers.travelme;

public class getsetchallenge {
    int id,likes;
    String username,challenge,comments,date,location,photoUrl;
    String loguser;

    public getsetchallenge() {
    }

    public getsetchallenge(int id, int likes, String username, String challenge, String comments, String date, String location,String loguser,String photoUrl) {
        this.id = id;
        this.likes = likes;
        this.username = username;
        this.challenge = challenge;
        this.comments = comments;
        this.date = date;
        this.location = location;
        this.loguser=loguser;
        this.photoUrl=photoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLoguser() {
        return loguser;
    }

    public void setLoguser(String loguser) {
        this.loguser = loguser;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
