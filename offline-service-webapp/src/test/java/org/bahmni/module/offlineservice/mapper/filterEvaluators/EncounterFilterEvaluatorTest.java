package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.EventsLog;
import org.bahmni.module.offlineservice.model.PersonAttribute;
import org.bahmni.module.offlineservice.repository.PersonAttributeRepository;
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

        EventsLog eventsLog = new EventsLog();
        encounterFilterEvaluator.evaluateFilter(ENCOUNTER_UUID, eventsLog);

        verify(personAttributeRepository, times(1)).findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME);
        assertEquals("Value", eventsLog.getFilter());
    }

    @Test
    public void shouldNotSetFilterIfUuidIsNull() throws Exception {
        PersonAttribute personAttribute = new PersonAttribute();
        personAttribute.setValue("Value");
        when(personAttributeRepository.findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME)).thenReturn(personAttribute);

        EventsLog eventsLog = new EventsLog();
        encounterFilterEvaluator.evaluateFilter(null, eventsLog);

        verify(personAttributeRepository, never()).findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME);
        assertNull(eventsLog.getFilter());
    }

    @Test
    public void shouldNotSetFilterIfAttributeIsNotAvailable() throws Exception {
        when(personAttributeRepository.findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME)).thenReturn(null);

        EventsLog eventsLog = new EventsLog();
        encounterFilterEvaluator.evaluateFilter(ENCOUNTER_UUID, eventsLog);

        verify(personAttributeRepository, times(1)).findByEncounterUuidAndPersonAttributeType(ENCOUNTER_UUID, ATTRIBUTE_TYPE_NAME);
        assertNull(eventsLog.getFilter());
    }

}