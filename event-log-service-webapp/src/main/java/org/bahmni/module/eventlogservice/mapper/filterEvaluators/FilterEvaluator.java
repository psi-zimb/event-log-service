package org.bahmni.module.eventlogservice.mapper.filterEvaluators;

import org.bahmni.module.eventlogservice.model.EventLog;

public interface FilterEvaluator {
    void evaluateFilter(String objectUuid, EventLog eventLog);
}
