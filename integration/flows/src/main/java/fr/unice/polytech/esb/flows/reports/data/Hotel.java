package fr.unice.polytech.esb.flows.reports.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Hotel {

    @JsonProperty
    private String hotelId;
    @JsonProperty private List<Night> nights;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public List<Night> getNights() {
        return nights;
    }

    public void setNights(List<Night> nights) {
        this.nights = nights;
    }

    private class Night{
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
}
