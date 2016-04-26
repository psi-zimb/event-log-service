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
    public void shouldGetEventLogExcludingCategories() throws Exception {
        String uuid = "uuid1";
        String filterBy = "303020";
        List<String> categoryList = new ArrayList<String>();
        categoryList.add("addressHierarchy");
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog lastReadEventLog = new EventLog();
        lastReadEventLog.setId(1000);
        when(eventLogRepository.findByUuid(uuid)).thenReturn(lastReadEventLog);
        when(eventLogRepository.findTop100ByFilterStartingWithAndIdAfterAndCategoryNotIn(filterBy, lastReadEventLog.getId(), categoryList)).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getEvents(uuid, filterBy);


        verify(eventLogRepository, times(1)).findByUuid(uuid);
        verify(eventLogRepository, times(1)).findTop100ByFilterStartingWithAndIdAfterAndCategoryNotIn(filterBy, lastReadEventLog.getId(), categoryList);
        verify(eventLogRepository, never()).findTop100ByFilterStartingWithAndCategoryNotIn(anyString(), anyList());
        assertNotNull(events);
    }

    @Test
    public void shouldGetAllEventLogExcludingCategoriesForTheFirstTime() throws Exception {
        String filterBy = "303020";
        List<String> categoryList = new ArrayList<String>();
        categoryList.add("addressHierarchy");
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogRepository.findTop100ByFilterStartingWithAndCategoryNotIn(filterBy, categoryList)).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getEvents(null, filterBy);


        verify(eventLogRepository, times(1)).findTop100ByFilterStartingWithAndCategoryNotIn(filterBy, categoryList);
        verify(eventLogRepository, never()).findByUuid(anyString());
        verify(eventLogRepository, never()).findTop100ByFilterStartingWithAndIdAfterAndCategoryNotIn(anyString(), anyInt(), anyList());
        assertNotNull(events);
    }


    @Test
    public void shouldGetEventForSpecificCategory() throws Exception {
        String uuid = "uuid1";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog lastReadEventLog = new EventLog();
        lastReadEventLog.setId(1000);
        when(eventLogRepository.findByUuid(uuid)).thenReturn(lastReadEventLog);
        when(eventLogRepository.findTop100ByCategoryAndFilterIsNull("addressHierarchy")).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getAddressHierarchyEvents(uuid, null);

        verify(eventLogRepository, times(1)).findByUuid(uuid);
        verify(eventLogRepository, times(1)).findTop100ByCategoryAndIdAfterAndFilterIsNull("addressHierarchy", lastReadEventLog.getId());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterIsNull(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWith(anyString(), anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWithAndIdAfter(anyString(), anyString(), anyInt());

        assertNotNull(events);
    }

    @Test
    public void shouldGetEventForSpecificCategoryForFirstTime() throws Exception {
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogRepository.findTop100ByCategoryAndFilterIsNull("addressHierarchy")).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getAddressHierarchyEvents(null, null);

        verify(eventLogRepository, times(1)).findTop100ByCategoryAndFilterIsNull("addressHierarchy");
        verify(eventLogRepository, never()).findByUuid(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndIdAfterAndFilterIsNull(anyString(), anyInt());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWith(anyString(), anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWithAndIdAfter(anyString(), anyString(), anyInt());
        assertNotNull(events);
    }

    @Test
    public void shouldGetEventForSpecificCategoryWithFilterStartingWith() throws Exception {
        String uuid = "uuid1";
        String filterBy = "303020";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog lastReadEventLog = new EventLog();
        lastReadEventLog.setId(1000);
        when(eventLogRepository.findByUuid(uuid)).thenReturn(lastReadEventLog);
        when(eventLogRepository.findTop100ByCategoryAndFilterStartingWithAndIdAfter("addressHierarchy", filterBy, lastReadEventLog.getId())).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getAddressHierarchyEvents(uuid, filterBy);

        verify(eventLogRepository, times(1)).findByUuid(uuid);
        verify(eventLogRepository, times(1)).findTop100ByCategoryAndFilterStartingWithAndIdAfter("addressHierarchy", filterBy, lastReadEventLog.getId());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterIsNull(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndIdAfterAndFilterIsNull(anyString(), anyInt());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWith(anyString(), anyString());

        assertNotNull(events);
    }


    @Test
    public void shouldGetEventForSpecificCategoryWithFilterStartingWithForFirstTime() throws Exception {
        String filterBy = "303020";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogRepository.findTop100ByCategoryAndFilterStartingWith("addressHierarchy", filterBy)).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getAddressHierarchyEvents(null, filterBy);

        verify(eventLogRepository, times(1)).findTop100ByCategoryAndFilterStartingWith("addressHierarchy", filterBy);
        verify(eventLogRepository, never()).findByUuid(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterIsNull(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndIdAfterAndFilterIsNull(anyString(), anyInt());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWithAndIdAfter(anyString(), anyString(), anyInt());

        assertNotNull(events);
    }
}