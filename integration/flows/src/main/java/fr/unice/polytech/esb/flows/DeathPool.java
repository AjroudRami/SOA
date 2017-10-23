package fr.unice.polytech.esb.flows;

import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.DEAD_PARTNER;
import static fr.unice.polytech.esb.flows.utils.Endpoints.DEATH_POOL;

public class DeathPool extends RouteBuilder {

    static int REDELIVERIES = 2;

    @Override
    public void configure() throws Exception {

        errorHandler(
                deadLetterChannel(DEATH_POOL)
                        .useOriginalMessage()
                        .maximumRedeliveries(REDELIVERIES)
                        .redeliveryDelay(500)
        );

        from(DEAD_PARTNER)
                .log("No answer from the partner: ")
                .log("${body}")
        ;
    }

}