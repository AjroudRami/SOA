package scenarii;

import org.json.JSONObject;

import java.util.logging.Logger;

public class BusBusinessServiceStepDefinition {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    private String host = "localhost";
    private int port = 8181;
    private String group = "tars";
    private String service;
    private String requestName;
    private JSONObject request;

    private String answer;
}
