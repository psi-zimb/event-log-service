package org.bahmni.module.eventlogservice.mapper;

import org.bahmni.module.eventlogservice.mapper.filterEvaluators.AddressHierarchyFilterEvaluator;
import org.bahmni.module.eventlogservice.mapper.filterEvaluators.EncounterFilterEvaluator;
import org.bahmni.module.eventlogservice.mapper.filterEvaluators.FilterEvaluator;
import org.bahmni.module.eventlogservice.mapper.filterEvaluators.PatientFilterEvaluator;
import org.bahmni.module.eventlogservice.model.EventRecords;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EventRecordsToEventLogMapper {

    private static final String UUID_PATTERN = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";
    private final HashMap<String, FilterEvaluator> filterEvaluators;
    private Pattern pattern;

    @Autowired
    public EventRecordsToEventLogMapper(PatientFilterEvaluator patientFilterEvaluator, EncounterFilterEvaluator encounterFilterEvaluator, AddressHierarchyFilterEvaluator addressHierarchyFilterEvaluator) {
        filterEvaluators = new HashMap<String, FilterEvaluator>();
        filterEvaluators.put("patient", patientFilterEvaluator);
        filterEvaluators.put("Encounter", encounterFilterEvaluator);
        filterEvaluators.put("addressHierarchy", addressHierarchyFilterEvaluator);
        pattern = Pattern.compile(UUID_PATTERN);
    }

    public List<EventLog> map(List<EventRecords> eventRecords) {
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        for (EventRecords eventRecord : eventRecords) {
            EventLog eventLog = new EventLog(eventRecord.getUuid(), eventRecord.getTimestamp(), eventRecord.getObject(), eventRecord.getCategory(), null);
            evaluateFilter(eventRecord, eventLog);
            eventLogs.add(eventLog);
        }
        return eventLogs;
    }

    private void evaluateFilter(EventRecords eventRecord, EventLog eventLog) {
        String object = eventRecord.getObject();
        Matcher matcher = pattern.matcher(object);
        if (matcher.find() && filterEvaluators.get(eventRecord.getCategory()) != null) {
            filterEvaluators.get(eventRecord.getCategory()).evaluateFilter(matcher.group(0), eventLog);
        }
    }
}
