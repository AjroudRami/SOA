package fr.polytech.unice.esb.services.flights.models.requests;

public class ListRequest {

    public String[] getFilterBy() {
        return filterBy;
    }

    public ListRequest(String[] filterBy, String orderBy) {
        this.filterBy = filterBy;
        this.orderBy = orderBy;
    }

    public void setFilterBy(String[] filterBy) {
        this.filterBy = filterBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String[] filterBy;
    public String orderBy;

    public ListRequest(){};
}
