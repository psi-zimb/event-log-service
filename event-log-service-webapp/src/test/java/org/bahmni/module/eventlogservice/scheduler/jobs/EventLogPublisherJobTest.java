package org.bahmni.module.eventlogservice.scheduler.jobs;

import org.bahmni.module.eventlogservice.mapper.EventRecordsToEventLogMapper;
import org.bahmni.module.eventlogservice.model.EventRecords;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.EventRecordsRepository;
import org.bahmni.module.eventlogservice.repository.EventLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class EventLogPublisherJobTest {
    private Job eventLogPublisherJob;

    @Mock
    private EventRecordsRepository eventRecordsRepository;
    @Mock
    private EventLogRepository eventLogRepository;
    @Mock
    private EventRecordsToEventLogMapper eventRecordsToEventLogMapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        eventLogPublisherJob = new EventLogPublisherJob(eventRecordsRepository, eventLogRepository, eventRecordsToEventLogMapper);
    }

    @Test
    public void shouldCopyEventRecordsToEventLog() throws Exception {
        Date timestamp = new Date();
        EventLog eventLog = new EventLog();
        eventLog.setUuid("uuid");
        eventLog.setTimestamp(timestamp);
        when(eventLogRepository.findFirstByOrderByIdDesc()).thenReturn(eventLog);


        EventRecords eventRecords = new EventRecords();
        eventRecords.setId(12);
        when(eventRecordsRepository.findByUuid("uuid")).thenReturn(eventRecords);
        ArrayList<EventRecords> recordsList = new ArrayList<EventRecords>();
        when(eventRecordsRepository.findTop10ByIdAfter(eventRecords.getId())).thenReturn(recordsList);

        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventRecordsToEventLogMapper.map(recordsList)).thenReturn(eventLogs);
        eventLogPublisherJob.process();

        verify(eventLogRepository, times(1)).findFirstByOrderByIdDesc();
        verify(eventRecordsRepository, times(1)).findByUuid("uuid");
        verify(eventRecordsRepository, times(1)).findTop10ByIdAfter(eventRecords.getId());
        verify(eventRecordsRepository, times(0)).findAll();
        verify(eventRecordsToEventLogMapper, times(1)).map(recordsList);
        verify(eventLogRepository, times(1)).save(eventLogs);
    }

    @Test
    public void shouldCopyAllEventRecordsForTheFirstTime() throws Exception {
        when(eventLogRepository.findFirstByOrderByTimestampDesc()).thenReturn(null);

        List<EventRecords> eventRecords = new ArrayList<EventRecords>();
        when(eventRecordsRepository.findAll()).thenReturn(eventRecords);

        eventLogPublisherJob.process();

        verify(eventRecordsRepository, times(1)).findAll();
        verify(eventRecordsRepository, times(0)).findAllEventsAfterTimestamp(any(Date.class));
    }
}