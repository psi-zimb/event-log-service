package org.bahmni.module.offlineservice.mapper;

import org.bahmni.module.offlineservice.model.EventRecords;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventRecordsToEventsLogMapper {

    public List<EventsLog> map(List<EventRecords> eventRecords) {
        ArrayList<EventsLog> eventsLogs = new ArrayList<EventsLog>();
        for (EventRecords eventRecord : eventRecords) {
            eventsLogs.add(new EventsLog(eventRecord.getUuid(), eventRecord.getTimestamp(), eventRecord.getObject(), eventRecord.getCategory(), null));
        }
        return eventsLogs;
    }
}
