package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.EventsLog;

public interface FilterEvaluator {
    void evaluateFilter(String objectUuid, EventsLog eventsLog);
}
