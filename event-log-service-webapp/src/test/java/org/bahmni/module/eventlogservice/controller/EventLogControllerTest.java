package org.bahmni.module.eventlogservice.controller;

import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.EventLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
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
        String[] filterBy = new String[]{"0303020","30302001"};
        List<String> filtersList = Arrays.asList(filterBy);

        List<String> categoryList = new ArrayList<String>();
        categoryList.add("addressHierarchy");
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog lastReadEventLog = new EventLog();
        lastReadEventLog.setId(1000);
        when(eventLogRepository.findTop1ByUuid(uuid)).thenReturn(lastReadEventLog);
        when(eventLogRepository.findTop100ByFilterInAndIdAfterAndCategoryNotIn(filtersList, lastReadEventLog.getId(), categoryList)).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getEvents(uuid, filterBy);


        verify(eventLogRepository, times(1)).findTop1ByUuid(uuid);
        verify(eventLogRepository, times(1)).findTop100ByFilterInAndIdAfterAndCategoryNotIn(filtersList, lastReadEventLog.getId(), categoryList);
        verify(eventLogRepository, never()).findTop100ByFilterInAndCategoryNotIn(any(List.class), anyList());
        assertNotNull(events);
    }

    @Test
    public void shouldGetConceptEventLog() throws Exception {
        String uuid = "uuid1";
        String category = "offline-concepts";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog lastReadEventLog = new EventLog();
        lastReadEventLog.setId(1000);
        when(eventLogRepository.findTop1ByUuid(uuid)).thenReturn(lastReadEventLog);
        when(eventLogRepository.findTop100ByCategoryIsAndIdAfter(category, lastReadEventLog.getId())).thenReturn(eventLogs);

        List<EventLog> concepts = eventLogController.getConcepts(uuid);


        verify(eventLogRepository, times(1)).findTop1ByUuid(uuid);
        verify(eventLogRepository, times(1)).findTop100ByCategoryIsAndIdAfter(category, lastReadEventLog.getId());
        verify(eventLogRepository, never()).findTop100ByCategoryIs(anyString());
        assertNotNull(concepts);
    }

    @Test
    public void shouldGetAllEventLogExcludingCategoriesForTheFirstTime() throws Exception {
        String[] filterBy = new String[]{"0303020","30302001"};
        List<String> filtersList = Arrays.asList(filterBy);
        List<String> categoryList = new ArrayList<String>();
        categoryList.add("addressHierarchy");
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogRepository.findTop100ByFilterInAndCategoryNotIn(filtersList, categoryList)).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getEvents(null, filterBy);


        verify(eventLogRepository, times(1)).findTop100ByFilterInAndCategoryNotIn(filtersList, categoryList);
        verify(eventLogRepository, never()).findTop1ByUuid(anyString());
        verify(eventLogRepository, never()).findTop100ByFilterInAndIdAfterAndCategoryNotIn(any(List.class), anyInt(), anyList());
        assertNotNull(events);
    }


    @Test
    public void shouldGetEventForSpecificCategory() throws Exception {
        String uuid = "uuid1";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog lastReadEventLog = new EventLog();
        lastReadEventLog.setId(1000);
        when(eventLogRepository.findTop1ByUuid(uuid)).thenReturn(lastReadEventLog);
        when(eventLogRepository.findTop100ByCategoryAndFilterIsNull("addressHierarchy")).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getAddressHierarchyEvents(uuid, null);

        verify(eventLogRepository, times(1)).findTop1ByUuid(uuid);
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
        verify(eventLogRepository, never()).findTop1ByUuid(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndIdAfterAndFilterIsNull(anyString(), anyInt());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWith(anyString(), anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWithAndIdAfter(anyString(), anyString(), anyInt());
        assertNotNull(events);
    }

    @Test
    public void shouldGetEventForSpecificCategoryWithFilterStartingWith() throws Exception {
        String uuid = "uuid1";
        String[] filterBy = new String[]{"0303020"};
        List<String> filtersList = Arrays.asList(filterBy);
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        EventLog lastReadEventLog = new EventLog();
        lastReadEventLog.setId(1000);
        when(eventLogRepository.findTop1ByUuid(uuid)).thenReturn(lastReadEventLog);
        when(eventLogRepository.findTop100ByCategoryAndFilterStartingWithAndIdAfter("addressHierarchy", filterBy[0], lastReadEventLog.getId())).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getAddressHierarchyEvents(uuid, filterBy);

        verify(eventLogRepository, times(1)).findTop1ByUuid(uuid);
        verify(eventLogRepository, times(1)).findTop100ByCategoryAndFilterStartingWithAndIdAfter("addressHierarchy", filterBy[0], lastReadEventLog.getId());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterIsNull(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndIdAfterAndFilterIsNull(anyString(), anyInt());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWith(anyString(), anyString());

        assertNotNull(events);
    }


    @Test
    public void shouldGetEventForSpecificCategoryWithFilterStartingWithForFirstTime() throws Exception {
        String filterBy = "303020";
        String[] filters = new String[]{filterBy};
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogRepository.findTop100ByCategoryAndFilterStartingWith("addressHierarchy", filterBy)).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getAddressHierarchyEvents(null, filters);

        verify(eventLogRepository, times(1)).findTop100ByCategoryAndFilterStartingWith("addressHierarchy", filterBy);
        verify(eventLogRepository, never()).findTop1ByUuid(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterIsNull(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryAndIdAfterAndFilterIsNull(anyString(), anyInt());
        verify(eventLogRepository, never()).findTop100ByCategoryAndFilterStartingWithAndIdAfter(anyString(), anyString(), anyInt());

        assertNotNull(events);
    }

    @Test
    public void shouldGetAllConceptEventLogsForTheFirstTime() throws Exception {
        String category = "offline-concepts";
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        when(eventLogRepository.findTop100ByCategoryIs(category)).thenReturn(eventLogs);

        List<EventLog> events = eventLogController.getConcepts(null);


        verify(eventLogRepository, times(1)).findTop100ByCategoryIs(category);
        verify(eventLogRepository, never()).findTop1ByUuid(anyString());
        verify(eventLogRepository, never()).findTop100ByCategoryIsAndIdAfter(anyString(), anyInt());
        assertNotNull(events);
    }
    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenFilterByHasMultipleFilters() throws Exception {
        List<EventLog> events = eventLogController.getAddressHierarchyEvents("uuid3", new String[]{"2020", "202020"});
    }
}