package fr.unice.polytech.esb.flows.hotels.data;

import java.util.Date;

public class HotelRequest {

    private String place;
    private Date from;
    private Date to;

    public HotelRequest() {
    }

    public HotelRequest(String place, Date from, Date to) {
        this.place = place;
        this.from = from;
        this.to = to;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
