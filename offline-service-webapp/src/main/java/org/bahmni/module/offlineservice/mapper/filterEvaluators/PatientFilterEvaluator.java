package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.EventsLog;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientFilterEvaluator implements FilterEvaluator {
    private PatientService patientService;


    @Autowired
    public PatientFilterEvaluator(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public void evaluateFilter(String objectUuid, EventsLog eventsLog) {
        if (objectUuid == null) {
            return;
        }
        Patient patientByUuid = patientService.getPatientByUuid(objectUuid);
        eventsLog.setFilter(patientByUuid.getPersonAddress().getAddress1());
    }
}
