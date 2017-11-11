package fr.unice.polytech.esb.flows.reports.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.util.Date;

import static fr.unice.polytech.esb.flows.utils.Endpoints.TRAVEL_REPORT_FOLDER;
import static fr.unice.polytech.esb.flows.utils.Endpoints.TRAVEL_REPORT_SAVE;

public class TravelReportFileWriting extends RouteBuilder{
    private static ObjectMapper mapper = new ObjectMapper();
    private static int saveId = 0;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/travel-report/").post("/saveFile").type(Object.class).to(TRAVEL_REPORT_SAVE);

        // Process to add explanation travel report.
        from(TRAVEL_REPORT_SAVE)
                .routeId("travel-report-save-file")
                .routeDescription("Save the travel report in a file")

                .log("Saving the travel report to a file")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .process(exchange -> {
                    ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);
                    node.put("processingDate", new Date().toString());
                    exchange.getIn().setBody(node.toString());
                })
                // Send the request to the internal service.
                .to(TRAVEL_REPORT_FOLDER + "fileName=Report_" + (saveId ++) + ".json&fileExist=Append");
    }
}
