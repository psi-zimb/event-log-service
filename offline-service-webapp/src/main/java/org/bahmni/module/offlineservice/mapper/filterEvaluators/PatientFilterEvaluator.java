package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.EventsLog;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;

public class PatientFilterEvaluator implements FilterEvaluator {
    @Override
    public void evaluateFilter(String objectUuid, EventsLog eventsLog) {
        if (objectUuid == null) {
            return;
        }
        Patient patientByUuid = Context.getPatientService().getPatientByUuid(objectUuid);
        eventsLog.setFilter(patientByUuid.getPersonAddress().getAddress1());
    }
}
