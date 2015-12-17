package org.bahmni.module.offlineservice.mapper;

import org.bahmni.module.offlineservice.model.EventRecords;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class EventRecordsToEventsLogMapperTest {
    public static final Date TIMESTAMP = new Date();
    private EventRecordsToEventsLogMapper eventRecordsToEventsLogMapper = new EventRecordsToEventsLogMapper();

    @Test
    public void shouldMapEventRecordsToEventsLog() throws Exception {
        ArrayList<EventRecords> eventRecords = new ArrayList<EventRecords>();
        EventRecords eventRecord = new EventRecords("uuid", "title", TIMESTAMP, "uri", "object", "category");
        eventRecords.add(eventRecord);

        List<EventsLog> eventLogs = eventRecordsToEventsLogMapper.map(eventRecords);

        assertNotNull(eventLogs);
        assertEquals(eventRecords.size(), eventLogs.size());
        assertEquals(eventRecord.getUuid(), eventLogs.get(0).getUuid());
        assertEquals(eventRecord.getTimestamp(), eventLogs.get(0).getTimestamp());
        assertEquals(eventRecord.getObject(), eventLogs.get(0).getObject());
        assertEquals(eventRecord.getCategory(), eventLogs.get(0).getCategory());
        assertNull(eventLogs.get(0).getFilter());
    }
}