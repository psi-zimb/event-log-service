package org.bahmni.module.eventlogservice.scheduler.jobs;

import org.bahmni.module.eventlogservice.fetcher.EventLogFetcher;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.EventLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class EventLogPublisherJobTest {

    private Job eventLogPublisherJob;
    @Mock
    private EventLogRepository eventLogRepository;
    @Mock
    private EventLogFetcher eventLogFetcher;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        eventLogPublisherJob = new EventLogPublisherJob(eventLogRepository, eventLogFetcher);
    }

    @Test
    public void shouldCopyEventRecordsToEventLog() throws Exception {
        Date timestamp = new Date();
        EventLog eventLog = new EventLog();
        eventLog.setUuid("uuid");
        eventLog.setTimestamp(timestamp);
        when(eventLogRepository.findFirstByOrderByIdDesc()).thenReturn(eventLog);


        EventLog eventLogNew = new EventLog();
        eventLogNew.setId(12);
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        eventLogs.add(eventLogNew);
        when(eventLogFetcher.fetchEventLogsAfter(eventLog.getUuid())).thenReturn(eventLogs);
        eventLogPublisherJob.process();

        verify(eventLogRepository, times(1)).findFirstByOrderByIdDesc();
        verify(eventLogFetcher, times(1)).fetchEventLogsAfter(eventLog.getUuid());
        verify(eventLogRepository, times(1)).save(eventLogs);
    }

    @Test
    public void shouldCopyAllEventRecordsForTheFirstTime() throws Exception {
        when(eventLogRepository.findFirstByOrderByTimestampDesc()).thenReturn(null);

        EventLog eventLogNew = new EventLog();
        eventLogNew.setId(12);
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        eventLogs.add(eventLogNew);
        when(eventLogFetcher.fetchEventLogsAfter("")).thenReturn(eventLogs);

        eventLogPublisherJob.process();
        verify(eventLogRepository, times(1)).findFirstByOrderByIdDesc();
        verify(eventLogFetcher, times(1)).fetchEventLogsAfter("");
        verify(eventLogRepository, times(1)).save(eventLogs);

    }

    @Test
    public void shouldThrowErrorWhenNoEventIsPresentInEventRecords() throws Exception {
        Date timestamp = new Date();
        EventLog eventLog = new EventLog();
        eventLog.setUuid("uuid");
        eventLog.setTimestamp(timestamp);
        when(eventLogRepository.findFirstByOrderByIdDesc()).thenReturn(eventLog);
        List<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogFetcher.fetchEventLogsAfter("uuid")).thenReturn(eventLogs);

        eventLogPublisherJob.process();

        verify(eventLogRepository, times(1)).findFirstByOrderByIdDesc();
        verify(eventLogFetcher, times(1)).fetchEventLogsAfter("uuid");
        verify(eventLogRepository, times(1)).save(eventLogs);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowErrorWhenMapperThrowIOException() throws Exception {
        Date timestamp = new Date();
        EventLog eventLog = new EventLog();
        eventLog.setUuid("uuid");
        eventLog.setTimestamp(timestamp);
        when(eventLogRepository.findFirstByOrderByIdDesc()).thenReturn(eventLog);
        List<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogFetcher.fetchEventLogsAfter("uuid")).thenThrow(new IOException());

        eventLogPublisherJob.process();
    }

}