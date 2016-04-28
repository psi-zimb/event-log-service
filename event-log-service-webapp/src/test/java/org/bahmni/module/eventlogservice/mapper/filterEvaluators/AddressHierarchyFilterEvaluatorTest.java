package org.bahmni.module.eventlogservice.mapper.filterEvaluators;

import org.bahmni.module.eventlogservice.model.AddressHierarchyEntry;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.AddressHierarchyEntryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddressHierarchyFilterEvaluatorTest {
    @InjectMocks
    private AddressHierarchyFilterEvaluator addressHierarchyFilterEvaluator = new AddressHierarchyFilterEvaluator();

    @Mock
    private AddressHierarchyEntryRepository addressHierarchyEntryRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldEvaluateFilterForAddressHierarchy() throws Exception {
        AddressHierarchyEntry addressHierarchyEntry = new AddressHierarchyEntry();
        addressHierarchyEntry.setUserGeneratedId("Geocode");
        when(addressHierarchyEntryRepository.findByUuid("addressUuid")).thenReturn(addressHierarchyEntry);
        EventLog eventLog = new EventLog();

        addressHierarchyFilterEvaluator.evaluateFilter("addressUuid", eventLog);

        verify(addressHierarchyEntryRepository, times(1)).findByUuid("addressUuid");
        assertNotNull(eventLog.getFilter());
        assertEquals(addressHierarchyEntry.getUserGeneratedId(), eventLog.getFilter());
    }

    @Test
    public void shouldNotSetFilterIfUuidIsNull() throws Exception {
        EventLog eventLog = new EventLog();

        addressHierarchyFilterEvaluator.evaluateFilter(null, eventLog);

        verify(addressHierarchyEntryRepository, never()).findByUuid("addressUuid");
        assertNull(eventLog.getFilter());
    }
}