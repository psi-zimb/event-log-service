package org.bahmni.module.offlineservice.scheduler.jobs;

import IT.BaseIntegrationTest;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.bahmni.module.offlineservice.repository.EventsLogRepository;
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
    private EventsLogRepository eventsLogRepository;

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/insertEventsLog.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/insertEventRecords.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldPublishDataFromEventRecordsToEventsLog() throws Exception {
        eventLogPublisherJob.process();

        List<EventsLog> eventsLogs = eventsLogRepository.findAll();
        assertEquals(2, eventsLogs.size());
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/insertEventRecords.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldPublishDataFromEventRecordsToEventsLogForTheFirstTime() throws Exception {
        eventLogPublisherJob.process();

        List<EventsLog> eventsLogs = eventsLogRepository.findAll();
        assertEquals(1, eventsLogs.size());
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/insertEventRecords.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldNotPublishTheSameEventsMultipleTimes() throws Exception {
        eventLogPublisherJob.process();

        List<EventsLog> eventsLogs = eventsLogRepository.findAll();
        assertEquals(1, eventsLogs.size());

        eventLogPublisherJob.process();

        eventsLogs = eventsLogRepository.findAll();
        assertEquals(1, eventsLogs.size());
    }
}