package fr.polytech.unice.esb.services.travel.models.documents;

/**
 * A hotel night
 */
public class HotelNight {

    private long date;
    private String room;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
