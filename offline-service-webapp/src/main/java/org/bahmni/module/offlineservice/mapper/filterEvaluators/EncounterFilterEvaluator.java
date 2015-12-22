package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.EventsLog;
import org.openmrs.Encounter;
import org.openmrs.api.EncounterService;
import org.springframework.beans.factory.annotation.Autowired;

public class EncounterFilterEvaluator implements FilterEvaluator {
    @Autowired
    private EncounterService encounterService;

    @Override
    public void evaluateFilter(String objectUuid, EventsLog eventsLog) {
        if (objectUuid == null) {
            return;
        }
        Encounter encounterByUuid = encounterService.getEncounterByUuid(objectUuid);
        eventsLog.setFilter(encounterByUuid.getPatient().getPersonAddress().getAddress1());

    }
}
