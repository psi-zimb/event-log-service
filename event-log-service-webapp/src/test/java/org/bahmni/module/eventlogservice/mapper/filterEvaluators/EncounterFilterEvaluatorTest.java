package org.bahmni.module.eventlogservice.mapper.filterEvaluators;

import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.model.PersonAttribute;
import org.bahmni.module.eventlogservice.repository.PersonAttributeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class EncounterFilterEvaluatorTest {
    public static final String ATTRIBUTE_TYPE_NAME = "Catchment number";
    public static final String ENCOUNTER_UUID = "encounterUuid";
    @InjectMocks
    private EncounterFilterEvaluator encounterFilterEvaluator = new EncounterFilterEvaluator();

    @Mock
    private PersonAttributeRepository personAttributeRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldEvaluateFilterForEncounter() {
        PersonAttribute personAttribute = new PersonAttribute();
        personAttribute.setValue("Value");
        when(personAttributeRepository.findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME)).thenReturn(personAttribute);

        EventLog eventLog = new EventLog();
        encounterFilterEvaluator.evaluateFilter(ENCOUNTER_UUID, eventLog);

        verify(personAttributeRepository, times(1)).findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME);
        assertEquals("Value", eventLog.getFilter());
    }

    @Test
    public void shouldNotSetFilterIfUuidIsNull() throws Exception {
        PersonAttribute personAttribute = new PersonAttribute();
        personAttribute.setValue("Value");
        when(personAttributeRepository.findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME)).thenReturn(personAttribute);

        EventLog eventLog = new EventLog();
        encounterFilterEvaluator.evaluateFilter(null, eventLog);

        verify(personAttributeRepository, never()).findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME);
        assertNull(eventLog.getFilter());
    }

    @Test
    public void shouldNotSetFilterIfAttributeIsNotAvailable() throws Exception {
        when(personAttributeRepository.findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME)).thenReturn(null);

        EventLog eventLog = new EventLog();
        encounterFilterEvaluator.evaluateFilter(ENCOUNTER_UUID, eventLog);

        verify(personAttributeRepository, times(1)).findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME);
        assertNull(eventLog.getFilter());
    }

}