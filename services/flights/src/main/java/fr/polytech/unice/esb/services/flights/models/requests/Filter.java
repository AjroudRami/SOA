package fr.polytech.unice.esb.services.flights.models.requests;

public class Filter {
    private String name;
    private String[] args;

    public Filter(){}

    public Filter(String name, String[] args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}