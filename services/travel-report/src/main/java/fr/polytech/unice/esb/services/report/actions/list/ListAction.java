package fr.polytech.unice.esb.services.report.actions.list;

import fr.polytech.unice.esb.services.report.actions.DocumentAction;
import fr.polytech.unice.esb.services.report.components.TravelReportComponent;
import fr.polytech.unice.esb.services.report.models.documents.TravelReportStatus;
import fr.polytech.unice.esb.services.report.models.documents.TravelReports;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.stream.Collectors;

@Any
public class ListAction implements DocumentAction<Void, TravelReports>{

    @EJB
    private TravelReportComponent travels;

    @Override
    public TravelReports execute(Void document) throws Exception {
        return new TravelReports(travels.list().stream().filter(travel -> travel.getStatus() == TravelReportStatus.INPROGRESS).collect(Collectors.toList()));
    }

    @Override
    public Class<Void> getInputType() {
        return Void.class;
    }
}
