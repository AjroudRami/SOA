package request;

import java.util.Arrays;

public class ServiceRequest {

    private String event;
    private String destination;
    private String departure;
    private int departureTimeStamp;
    private Filter[] filterBy;
    private String orderBy;

    public ServiceRequest(){
    }

    public ServiceRequest(String event, String destination, String departure, int departureTimeStamp, Filter[] filterBy, String orderBy) {
        this.event = event;
        this.destination = destination;
        this.departure = departure;
        this.departureTimeStamp = departureTimeStamp;
        this.filterBy = filterBy;
        this.orderBy = orderBy;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
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

    public int getDepartureTimeStamp() {
        return departureTimeStamp;
    }

    public void setDepartureTimeStamp(int departureTimeStamp) {
        this.departureTimeStamp = departureTimeStamp;
    }

    public Filter[] getFilterBy() {
        return filterBy;
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

    public void AddFilter(Filter filter){
        if (this.filterBy == null) {
            filterBy = new Filter[0];
        }
        Filter[] newFilters = Arrays.copyOf(filterBy, filterBy.length + 1);
        newFilters[newFilters.length - 1] = filter;
        this.filterBy = newFilters;
    }
}