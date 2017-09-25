package fr.polytech.unice.esb.services.travel.models.documents;

/**
 * A hotel night
 */
public class HotelNight {

    private int date;
    private String room;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
