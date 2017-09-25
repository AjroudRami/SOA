package business;


import java.util.Optional;

public class CarRentalRequest {

    private String place;
    private int duration; // Duration in days

    public CarRentalRequest(String place, int duration) {
        this.place = place;
        this.duration = duration;
    }

    public Optional<String> getPlace(){
        return Optional.ofNullable(place);
    }

    public Optional<Integer> getDuration(){
        return Optional.of(duration);
    }
}
