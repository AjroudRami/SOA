package fr.polytech.unice.esb.services.flights.models.requests;

public class ListRequest {

    public Filter[] filterBy;
    public String orderBy;
    public String destination;
    public String departure;
    public int departureTimeStamp;
    public int arrivalTimeStamp;

    public int getDepartureTimeStamp() {
        return departureTimeStamp;
    }


    public void setDepartureTimeStamp(int departureTimeStamp) {
        this.departureTimeStamp = departureTimeStamp;
    }

    public int getArrivalTimeStamp() {
        return arrivalTimeStamp;
    }

    public void setArrivalTimeStamp(int arrivalTimeStamp) {
        this.arrivalTimeStamp = arrivalTimeStamp;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }


    public Filter[] getFilterBy() {
        return filterBy;
    }

    public ListRequest(Filter[] filterBy, String orderBy, String destination, String departure) {
        this.filterBy = filterBy;
        this.orderBy = orderBy;
        this.destination = destination;
        this.departure = departure;
    }

    public void setFilterBy(Filter[] filterBy) {
        this.filterBy = filterBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public ListRequest(){};
}
