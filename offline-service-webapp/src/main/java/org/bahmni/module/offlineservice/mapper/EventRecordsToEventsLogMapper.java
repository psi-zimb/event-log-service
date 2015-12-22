package org.bahmni.module.offlineservice.mapper;

import org.bahmni.module.offlineservice.mapper.filterEvaluators.EncounterFilterEvaluator;
import org.bahmni.module.offlineservice.mapper.filterEvaluators.FilterEvaluator;
import org.bahmni.module.offlineservice.mapper.filterEvaluators.PatientFilterEvaluator;
import org.bahmni.module.offlineservice.model.EventRecords;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EventRecordsToEventsLogMapper {

    private static final String UUID_PATTERN = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";
    private final HashMap<String, FilterEvaluator> filterEvaluators;

    public EventRecordsToEventsLogMapper() {
        filterEvaluators = new HashMap<String, FilterEvaluator>();
        filterEvaluators.put("patient", new PatientFilterEvaluator());
        filterEvaluators.put("encounter", new EncounterFilterEvaluator());
    }

    public List<EventsLog> map(List<EventRecords> eventRecords) {
        ArrayList<EventsLog> eventsLogs = new ArrayList<EventsLog>();
        for (EventRecords eventRecord : eventRecords) {
            EventsLog eventsLog = new EventsLog(eventRecord.getUuid(), eventRecord.getTimestamp(), eventRecord.getObject(), eventRecord.getCategory(), null);
            evaluateFilter(eventRecord, eventsLog);
            eventsLogs.add(eventsLog);
        }
        return eventsLogs;
    }

    private void evaluateFilter(EventRecords eventRecord, EventsLog eventsLog) {
        String object = eventRecord.getObject();
        Pattern pattern = Pattern.compile(UUID_PATTERN);
        Matcher matcher = pattern.matcher(object);
        if (matcher.find()){
            filterEvaluators.get(eventRecord.getCategory()).evaluateFilter(matcher.group(0), eventsLog);
        }
    }
}
