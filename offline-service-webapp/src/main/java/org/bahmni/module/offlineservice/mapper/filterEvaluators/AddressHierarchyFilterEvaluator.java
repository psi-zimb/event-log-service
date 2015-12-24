package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.AddressHierarchyEntry;
import org.bahmni.module.offlineservice.model.EventLog;
import org.bahmni.module.offlineservice.repository.AddressHierarchyEntryRepository;
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
