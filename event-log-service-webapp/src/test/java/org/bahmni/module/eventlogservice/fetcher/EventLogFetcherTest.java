package org.bahmni.module.eventlogservice.fetcher;

import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.web.helper.OpenMRSProperties;
import org.bahmni.module.eventlogservice.web.helper.OpenMRSWebClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringApplicationConfiguration

@PrepareForTest({EventLogFetcher.class})
@RunWith(PowerMockRunner.class)
public class EventLogFetcherTest {
    @InjectMocks
    private EventLogFetcher eventLogFetcher;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private OpenMRSProperties openMRSProperties;

    @Mock
    private OpenMRSWebClient openMRSWebClient;


    @Before
    public void setUp() throws Exception {
        initMocks(this);

    }

    @Test
    public void shouldMapEventRecordsToEventLog() throws Exception {
        eventLogFetcher.setBahmniEventLogURL("bahmniEventLogURL/");

        String lastReadEventUuid = "uuid";
        EventLog eventLog = new EventLog("uuid1",new Date(),"patientURL","Patient","202020");
        EventLog[] eventLogs = new EventLog[]{eventLog};
        when(openMRSWebClient.get(any(URI.class))).thenReturn(new ObjectMapper().writeValueAsString(eventLogs));
        List<EventLog> actualEventLogList = eventLogFetcher.fetchEventLogsAfter(lastReadEventUuid);
        Assert.assertEquals(eventLogs.length,actualEventLogList.size());

    }

    @Test
    public void shouldFetchEventLogsWhenLastReadUuidIsNull() throws Exception {
        eventLogFetcher.setBahmniEventLogURL("bahmniEventLogURL/");
        List<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog eventLog = new EventLog("uuid1",new Date(),"patientURL","Patient","202020");
        eventLogs.add(eventLog);
        when(openMRSWebClient.get(any(URI.class))).thenReturn(new ObjectMapper().writeValueAsString(eventLogs));
        List<EventLog> actualEventLogList = eventLogFetcher.fetchEventLogsAfter(null);
        Assert.assertEquals(eventLogs.size(),actualEventLogList.size());

    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowExceptionWhenUriIsInvalid() throws Exception {
        eventLogFetcher.setBahmniEventLogURL(null);
        List<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog eventLog = new EventLog("`invalid``",new Date(),"patientURL","Patient","202020");
        eventLogs.add(eventLog);
        when(openMRSWebClient.get(any(URI.class))).thenReturn(new ObjectMapper().writeValueAsString(eventLogs));
        eventLogFetcher.fetchEventLogsAfter(eventLog.getUuid());
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhenObjectMapperThrowsException() throws Exception {
        eventLogFetcher.setBahmniEventLogURL("bahmniEventLogURL/");
        List<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog eventLog = new EventLog("uuid",new Date(),"patientURL","Patient","202020");
        eventLogs.add(eventLog);
        when(openMRSWebClient.get(any(URI.class))).thenThrow(new IOException());
        eventLogFetcher.fetchEventLogsAfter(eventLog.getUuid());
    }

}