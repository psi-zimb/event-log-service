package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.EventsLog;
import org.openmrs.Encounter;
import org.openmrs.api.EncounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncounterFilterEvaluator implements FilterEvaluator {
    private final EncounterService encounterService;

    @Autowired
    public EncounterFilterEvaluator(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    @Override
    public void evaluateFilter(String objectUuid, EventsLog eventsLog) {
        if (objectUuid == null) {
            return;
        }
        Encounter encounterByUuid = encounterService.getEncounterByUuid(objectUuid);
        eventsLog.setFilter(encounterByUuid.getPatient().getPersonAddress().getAddress1());

    }
}
