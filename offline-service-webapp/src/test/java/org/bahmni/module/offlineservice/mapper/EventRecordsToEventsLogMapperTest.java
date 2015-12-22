package org.bahmni.module.offlineservice.mapper;

import org.bahmni.module.offlineservice.mapper.filterEvaluators.EncounterFilterEvaluator;
import org.bahmni.module.offlineservice.mapper.filterEvaluators.PatientFilterEvaluator;
import org.bahmni.module.offlineservice.model.EventRecords;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;


@PrepareForTest({EventRecordsToEventsLogMapper.class})
@RunWith(PowerMockRunner.class)
@Ignore
public class EventRecordsToEventsLogMapperTest {
    public static final Date TIMESTAMP = new Date();

    private EventRecordsToEventsLogMapper eventRecordsToEventsLogMapper;

    @Mock
    private PatientFilterEvaluator patientFilterEvaluator;

    @Mock
    private EncounterFilterEvaluator encounterFilterEvaluator;

    @Before
    public void setUp() throws Exception {
        PowerMockito.whenNew(PatientFilterEvaluator.class).withAnyArguments().thenReturn(patientFilterEvaluator);
        PowerMockito.whenNew(EncounterFilterEvaluator.class).withAnyArguments().thenReturn(encounterFilterEvaluator);

//        eventRecordsToEventsLogMapper = new EventRecordsToEventsLogMapper();
    }

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

    @Test
    public void shouldEvaluateFilterForPatientEvents() throws Exception {
        ArrayList<EventRecords> eventRecords = new ArrayList<EventRecords>();
        EventRecords eventRecord = new EventRecords("uuid", "title", TIMESTAMP, "uri", "/openmrs/ws/rest/v1/patient/d95bf6c9-d1c6-41dc-aecf-1c06bd71386c?v=full", "patient");
        eventRecords.add(eventRecord);
        EventsLog eventsLog = new EventsLog("uuid", TIMESTAMP, "/openmrs/ws/rest/v1/patient/d95bf6c9-d1c6-41dc-aecf-1c06bd71386c?v=full", "patient", null);
        PowerMockito.whenNew(EventsLog.class).withAnyArguments().thenReturn(eventsLog);

        List<EventsLog> eventLogs = eventRecordsToEventsLogMapper.map(eventRecords);

        verify(patientFilterEvaluator, times(1)).evaluateFilter("d95bf6c9-d1c6-41dc-aecf-1c06bd71386c", eventsLog);

        assertNotNull(eventLogs);
        assertEquals(eventRecords.size(), eventLogs.size());
        assertEquals(eventRecord.getUuid(), eventLogs.get(0).getUuid());
        assertEquals(eventRecord.getTimestamp(), eventLogs.get(0).getTimestamp());
        assertEquals(eventRecord.getObject(), eventLogs.get(0).getObject());
        assertEquals(eventRecord.getCategory(), eventLogs.get(0).getCategory());
    }

    @Test
    public void shouldEvaluateFilterForEncounterEvents() throws Exception {
        ArrayList<EventRecords> eventRecords = new ArrayList<EventRecords>();
        EventRecords eventRecord = new EventRecords("uuid", "title", TIMESTAMP, "uri", "/openmrs/ws/rest/v1/encounter/d95bf6c9-d1c6-41dc-aecf-1c06bd71358c?v=full", "encounter");
        eventRecords.add(eventRecord);
        EventsLog eventsLog = new EventsLog("uuid", TIMESTAMP, "/openmrs/ws/rest/v1/encounter/d95bf6c9-d1c6-41dc-aecf-1c06bd71358c?v=full", "encounter", null);
        PowerMockito.whenNew(EventsLog.class).withAnyArguments().thenReturn(eventsLog);

        List<EventsLog> eventLogs = eventRecordsToEventsLogMapper.map(eventRecords);

        verify(encounterFilterEvaluator, times(1)).evaluateFilter("d95bf6c9-d1c6-41dc-aecf-1c06bd71358c", eventsLog);

        assertNotNull(eventLogs);
        assertEquals(eventRecords.size(), eventLogs.size());
        assertEquals(eventRecord.getUuid(), eventLogs.get(0).getUuid());
        assertEquals(eventRecord.getTimestamp(), eventLogs.get(0).getTimestamp());
        assertEquals(eventRecord.getObject(), eventLogs.get(0).getObject());
        assertEquals(eventRecord.getCategory(), eventLogs.get(0).getCategory());
    }
}