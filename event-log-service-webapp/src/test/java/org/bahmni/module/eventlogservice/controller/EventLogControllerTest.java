package org.bahmni.module.eventlogservice.controller;

import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.EventLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class EventLogControllerTest {

    @Mock
    private EventLogRepository eventLogRepository;

    private EventLogController eventLogController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        eventLogController = new EventLogController(eventLogRepository);
    }

    @Test
    public void shouldGetEventLog() throws Exception {
        String uuid = "uuid1";
        String filterBy = "303020";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog lastReadEventLog = new EventLog();
        lastReadEventLog.setId(1000);
        when(eventLogRepository.findByUuid(uuid)).thenReturn(lastReadEventLog);
        when(eventLogRepository.findTop100ByFilterStartingWithAndIdAfter(filterBy, lastReadEventLog.getId())).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getEvents(uuid, filterBy);


        verify(eventLogRepository, times(1)).findByUuid(uuid);
        verify(eventLogRepository, times(1)).findTop100ByFilterStartingWithAndIdAfter(filterBy, lastReadEventLog.getId());
        verify(eventLogRepository, never()).findTop100ByFilterStartingWith(anyString());
        assertNotNull(events);
    }

    @Test
    public void shouldGetConceptEventLog() throws Exception {
        String uuid = "uuid1";
        String category = "offline-concepts";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog lastReadEventLog = new EventLog();
        lastReadEventLog.setId(1000);
        when(eventLogRepository.findByUuid(uuid)).thenReturn(lastReadEventLog);
        when(eventLogRepository.findTop100ByCategoryIsAndIdAfter(category, lastReadEventLog.getId())).thenReturn(eventLogs);

        List<EventLog> concepts = eventLogController.getConcepts(uuid);


        verify(eventLogRepository, times(1)).findByUuid(uuid);
        verify(eventLogRepository, times(1)).findTop100ByCategoryIsAndIdAfter(category, lastReadEventLog.getId());
        verify(eventLogRepository, never()).findTop100ByCategoryIs(anyString());
        assertNotNull(concepts);
    }

    @Test
    public void shouldGetAllEventLogForTheFirstTime() throws Exception {
        String filterBy = "303020";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogRepository.findTop100ByFilterStartingWith(filterBy)).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getEvents(null, filterBy);


        verify(eventLogRepository, times(1)).findTop100ByFilterStartingWith(filterBy);
        verify(eventLogRepository, never()).findByUuid(anyString());
        verify(eventLogRepository, never()).findTop100ByFilterStartingWithAndIdAfter(anyString(), anyInt());
        assertNotNull(events);
    }

    @Test
    public void shouldGetAllConceptEventLogsForTheFirstTime() throws Exception {
        String category = "offline-concepts";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogRepository.findTop100ByFilterStartingWith(category)).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getConcepts(null);


        verify(eventLogRepository, times(1)).findTop100ByCategoryIs(category);
        verify(eventLogRepository, never()).findByUuid(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryIsAndIdAfter(anyString(), anyInt());
        assertNotNull(events);
    }
}