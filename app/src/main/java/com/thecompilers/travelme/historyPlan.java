package com.thecompilers.travelme;
//this is get and set the history of places
public class historyPlan {
    private  int id;
    private String name ;
    private String startdate;
    private String enddate ;
    private String location;
    private String note;
    private String places;

    public historyPlan() {
    }

    public historyPlan(int id, String name, String startdate, String enddate, String location, String note, String places) {
        this.id = id;
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
        this.location = location;
        this.note = note;
        this.places = places;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }
}
