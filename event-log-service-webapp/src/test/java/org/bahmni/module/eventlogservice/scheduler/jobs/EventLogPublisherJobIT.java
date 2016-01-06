package org.bahmni.module.eventlogservice.scheduler.jobs;

import IT.BaseIntegrationTest;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.EventLogRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.junit.Assert.*;

public class EventLogPublisherJobIT extends BaseIntegrationTest {
    @Autowired
    private EventLogPublisherJob eventLogPublisherJob;
    @Autowired
    private EventLogRepository eventLogRepository;

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/insertEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/insertEventRecords.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldPublishDataFromEventRecordsToEventLog() throws Exception {
        eventLogPublisherJob.process();

        List<EventLog> eventLogs = eventLogRepository.findAll();
        assertEquals(2, eventLogs.size());
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/insertEventRecords.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldPublishDataFromEventRecordsToEventLogForTheFirstTime() throws Exception {
        eventLogPublisherJob.process();

        List<EventLog> eventLogs = eventLogRepository.findAll();
        assertEquals(2, eventLogs.size());
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/insertEventRecords.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldNotPublishTheSameEventsMultipleTimes() throws Exception {
        eventLogPublisherJob.process();

        List<EventLog> eventLogs = eventLogRepository.findAll();
        assertEquals(2, eventLogs.size());

        eventLogPublisherJob.process();

        eventLogs = eventLogRepository.findAll();
        assertEquals(2, eventLogs.size());
    }
}