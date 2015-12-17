package org.bahmni.module.offlineservice.scheduler.jobs;

import org.bahmni.module.offlineservice.mapper.EventRecordsToEventsLogMapper;
import org.bahmni.module.offlineservice.model.EventRecords;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.bahmni.module.offlineservice.repository.EventRecordsRepository;
import org.bahmni.module.offlineservice.repository.EventsLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class EventLogPublisherJobTest {
    @InjectMocks
    private EventLogPublisherJob eventLogPublisherJob = new EventLogPublisherJob();

    @Mock
    private EventRecordsRepository eventRecordsRepository;
    @Mock
    private EventsLogRepository eventsLogRepository;
    @Mock
    private EventRecordsToEventsLogMapper eventRecordsToEventsLogMapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldCopyEventRecordsToEventsLog() throws Exception {
        Date timestamp = new Date();
        EventsLog eventsLog = new EventsLog();
        eventsLog.setTimestamp(timestamp);
        when(eventsLogRepository.findFirstByOrderByTimestampDesc()).thenReturn(eventsLog);

        List<EventRecords> eventRecords = new ArrayList<EventRecords>();
        when(eventRecordsRepository.findAllEventsAfterTimestamp(timestamp)).thenReturn(eventRecords);

        ArrayList<EventsLog> eventsLogs = new ArrayList<EventsLog>();
        when(eventRecordsToEventsLogMapper.map(eventRecords)).thenReturn(eventsLogs);
        eventLogPublisherJob.process();

        verify(eventsLogRepository, times(1)).findFirstByOrderByTimestampDesc();
        verify(eventRecordsRepository, times(1)).findAllEventsAfterTimestamp(timestamp);
        verify(eventRecordsRepository, times(0)).findAll();
        verify(eventRecordsToEventsLogMapper, times(1)).map(eventRecords);
        verify(eventsLogRepository, times(1)).save(eventsLogs);
    }

    @Test
    public void shouldCopyAllEventRecordsForTheFirstTime() throws Exception {
        when(eventsLogRepository.findFirstByOrderByTimestampDesc()).thenReturn(null);

        List<EventRecords> eventRecords = new ArrayList<EventRecords>();
        when(eventRecordsRepository.findAll()).thenReturn(eventRecords);

        eventLogPublisherJob.process();

        verify(eventRecordsRepository, times(1)).findAll();
        verify(eventRecordsRepository, times(0)).findAllEventsAfterTimestamp(any(Date.class));
    }
}