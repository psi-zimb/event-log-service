package org.bahmni.module.eventlogservice.mapper.filterEvaluators;

import org.bahmni.module.eventlogservice.model.AddressHierarchyEntry;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.AddressHierarchyEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressHierarchyFilterEvaluator implements FilterEvaluator {
    @Autowired
    private AddressHierarchyEntryRepository addressHierarchyEntryRepository;

    @Override
    public void evaluateFilter(String objectUuid, EventLog eventLog) {
        if (objectUuid == null) {
            return;
        }
        AddressHierarchyEntry addressHierarchyEntry = addressHierarchyEntryRepository.findByUuid(objectUuid);
        eventLog.setFilter(addressHierarchyEntry.getUserGeneratedId());
    }
}
