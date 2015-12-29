package org.bahmni.module.eventlogservice.controller;

import IT.BaseIntegrationTest;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EventLogControllerIT extends BaseIntegrationTest {
    @Autowired
    private EventLogController eventLogController;

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/eventLogController/insertEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldGetAllEventLogsByFilter() throws Exception {
        List<EventLog> events = eventLogController.getEvents(null, "202020");

        assertNotNull(events);
        assertEquals(5, events.size());
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/eventLogController/insertEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldGetAllEventsAfterUuidAndByFilter() throws Exception {
        List<EventLog> events = eventLogController.getEvents("uuid1", "202020");

        assertNotNull(events);
        assertEquals(4, events.size());
    }
}