package fr.polytech.unice.esb.services.report.actions.list;

import fr.polytech.unice.esb.services.report.actions.DocumentAction;
import fr.polytech.unice.esb.services.report.components.TravelReportComponent;
import fr.polytech.unice.esb.services.report.models.documents.TravelReport;
import fr.polytech.unice.esb.services.report.models.documents.TravelReportStatus;
import fr.polytech.unice.esb.services.report.models.exceptions.TravelReportCreationException;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.UUID;

@Any
public class CreateAction implements DocumentAction<TravelReport, TravelReport> {

    @EJB
    private TravelReportComponent travels;

    @Override
    public TravelReport execute(TravelReport document) throws Exception {

        document.setId(UUID.randomUUID().toString());
        document.setStatus(TravelReportStatus.INPROGRESS);
        if(document.getBusinessTravelId().isEmpty()){
            throw new TravelReportCreationException();
        }
        return travels.put(document);
    }

    @Override
    public Class<TravelReport> getInputType() {
        return TravelReport.class;
    }
}
