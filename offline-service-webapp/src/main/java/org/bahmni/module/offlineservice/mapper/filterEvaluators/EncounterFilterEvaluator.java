package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.EventsLog;
import org.bahmni.module.offlineservice.model.PersonAttribute;
import org.bahmni.module.offlineservice.repository.PersonAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncounterFilterEvaluator implements FilterEvaluator {
    public static final String ATTRIBUTE_TYPE_NAME = "Catchment number";
    @Autowired
    private PersonAttributeRepository personAttributeRepository;

    @Override
    public void evaluateFilter(String objectUuid, EventsLog eventsLog) {
        if (objectUuid == null) {
            return;
        }
        PersonAttribute personAttribute = personAttributeRepository.findByEncounterUuidAndPersonAttributeType(objectUuid, ATTRIBUTE_TYPE_NAME);
        if (personAttribute == null) {
            return;
        }
        eventsLog.setFilter(personAttribute.getValue());
    }
}
