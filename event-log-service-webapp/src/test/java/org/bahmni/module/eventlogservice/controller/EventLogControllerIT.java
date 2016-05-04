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
    public void shouldGetAllEventLogsByFilterStartingWithAndCategoryNotIn() throws Exception {
        List<EventLog> events = eventLogController.getEvents(null, "2020");

        assertNotNull(events);
        assertEquals(3, events.size());
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/eventLogController/insertEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldGetAllEventsAfterUuidAndByFilterStartingWithAndCategoryNotIn() throws Exception {
        List<EventLog> events = eventLogController.getEvents("uuid8", "2020");

        assertNotNull(events);
        assertEquals(2, events.size());
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/eventLogController/insertEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldGetAllEventLogsByCategoryAndFilterIsNull() throws Exception {
        List<EventLog> events = eventLogController.getAddressHierarchyEvents(null, null);

        assertNotNull(events);
        assertEquals(2, events.size());
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/eventLogController/insertEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldGetAllEventLogsAfterUuidByCategoryAndFilterIsNull() throws Exception {
        List<EventLog> events = eventLogController.getAddressHierarchyEvents("uuid6", null);

        assertNotNull(events);
        assertEquals(1, events.size());
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/eventLogController/insertEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldGetAllEventLogsByCategoryAndFilterStartingWith() throws Exception {
        List<EventLog> events = eventLogController.getAddressHierarchyEvents(null, "2020");

        assertNotNull(events);
        assertEquals(5, events.size());
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/eventLogController/insertConceptEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldGetAllConceptEventLogsByCategory() throws Exception {
        List<EventLog> events = eventLogController.getConcepts(null);

        assertNotNull(events);
        assertEquals(5, events.size());
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/eventLogController/insertEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldGetAllEventLogsAfterUuidByCategoryAndFilterStartingWith() throws Exception {
        List<EventLog> events = eventLogController.getAddressHierarchyEvents("uuid3", "2020");

        assertNotNull(events);
        assertEquals(4, events.size());
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/eventLogController/insertConceptEventLogs.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
    })
    @Test
    public void shouldGetAllConceptEventsAfterUuidAndByCategoryStartingWith() throws Exception {
        List<EventLog> events = eventLogController.getConcepts("conceptUuid1");

        assertNotNull(events);
        assertEquals(2, events.size());
    }



}