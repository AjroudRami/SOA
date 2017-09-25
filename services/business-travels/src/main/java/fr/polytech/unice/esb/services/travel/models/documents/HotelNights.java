package fr.polytech.unice.esb.services.travel.models.documents;

import java.util.List;

/**
 * Hotel nights
 */
public class HotelNights {

    private String hotelId;
    private List<HotelNight> nights;

    public List<HotelNight> getNights() {
        return nights;
    }

    public void setNights(List<HotelNight> nights) {
        this.nights = nights;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
}
