package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.EventLog;

public interface FilterEvaluator {
    void evaluateFilter(String objectUuid, EventLog eventLog);
}
