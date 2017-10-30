package fr.unice.polytech.esb.flows.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.esb.flows.reports.data.ApproveTravel;
import fr.unice.polytech.esb.flows.reports.data.BusinessTravel;
import fr.unice.polytech.esb.flows.reports.data.TravelExpense;
import fr.unice.polytech.esb.flows.reports.data.TravelReport;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.ArrayList;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

/**
 * Add expenses to the travel report
 */
public class TravelReportExpense extends RouteBuilder{


    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/travel-report/").post("/expenses").type(Object.class).to(TRAVEL_REPORT_EXPENSE);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEATH_POOL)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<BusinessTravel>()));

        // Process to approve business travel.
        from(TRAVEL_REPORT_EXPENSE)
                .routeId("travel-report-expense")
                .routeDescription("Add expenses to a travel report")

                .log("Generating a travel report expense")

                // Parse the body content from JSON string to ApproveTravel.
                .unmarshal().json(JsonLibrary.Jackson, ApproveTravel.class)

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> exchange.getIn()
                        .setBody(makeBody(exchange.getIn().getBody(TravelReport.class))))

                // Send the request to the internal service.
                .to(TRAVEL_REPORT_ENDPOINT);
    }

    private static String makeBody(TravelReport travelReport) {
        ObjectNode json = mapper.createObjectNode();
        json.put("event", "expenses");
        json.put("id", travelReport.getId());
        json.put("businessTravelId", travelReport.getBusinessTravelId());

        ArrayNode expenses = mapper.createArrayNode();
        travelReport.getExpenses().forEach((expense) -> expenses.add(convertExpense(expense)));
        json.put("expenses", expenses);
        return json.toString();
    }

    private static ObjectNode convertExpense(TravelExpense expense) {
        ObjectNode json = mapper.createObjectNode();
        json.put("date", expense.getDate());
        json.put("amount", expense.getAmount());
        json.put("description", expense.getDescription());
        return json;
    }
}
