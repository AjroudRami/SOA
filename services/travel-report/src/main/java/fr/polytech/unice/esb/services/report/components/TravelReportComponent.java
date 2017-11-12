package fr.polytech.unice.esb.services.report.components;

import fr.polytech.unice.esb.services.report.models.documents.TravelReport;

import java.util.List;
import java.util.Optional;

public interface TravelReportComponent {


    /**
     * Creates a travel report
     * @param travel
     * @return
     */
    TravelReport put(TravelReport travel);

    /**
     * List the different travel reports
     * @return
     */
    List<TravelReport> list();

    /**
     *
     * @param travel
     * @return the most recent travel report document
     */
    Optional<TravelReport> get(TravelReport travel);

}
