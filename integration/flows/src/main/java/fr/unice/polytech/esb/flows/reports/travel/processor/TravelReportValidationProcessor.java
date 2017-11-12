package fr.unice.polytech.esb.flows.reports.travel.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class TravelReportValidationProcessor implements Processor {


    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);

        ObjectReader reader = mapper.readerFor(
                new TypeReference<String>() {
                    // Nothing to implement.
                });

        String status = reader.readValue(node.get("status"));

        if(status.equals("ACCEPTED")){
            exchange.getIn().setHeader("status","ACCEPTED");
        }
        else {
            exchange.getIn().setHeader("status", "REJECTED");
        }
        exchange.getIn().setBody(node.toString());
    }
}
