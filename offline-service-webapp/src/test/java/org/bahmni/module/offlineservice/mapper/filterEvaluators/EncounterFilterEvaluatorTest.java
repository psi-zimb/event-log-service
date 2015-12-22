package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.EncounterBuilder;
import org.bahmni.module.offlineservice.PatientBuilder;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class EncounterFilterEvaluatorTest {
    private EncounterFilterEvaluator encounterFilterEvaluator;

    @Mock
    EncounterService encounterService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testEvaluateFilter() {
        Patient patient = new PatientBuilder().withAddress1("Hyd").withAddress2("Telangana").build();
        Encounter encounter = new EncounterBuilder().withPatient(patient).build();
        String encounterUuid = "d95bf6c9-d1c6-41dc-aecf-1c06bd71358c";
        EventsLog eventLog = new EventsLog();
        when(encounterService.getEncounterByUuid(encounterUuid)).thenReturn(encounter);
        encounterFilterEvaluator = new EncounterFilterEvaluator(encounterService);

        encounterFilterEvaluator.evaluateFilter(encounterUuid, eventLog);

        verify(encounterService, times(1)).getEncounterByUuid(encounterUuid);
        assertEquals(patient.getPersonAddress().getAddress1(), eventLog.getFilter());
    }

    @Test
    public void shouldNotSetFilterIfUuidIsNull() throws Exception {
        EventsLog eventLog = new EventsLog();

        encounterFilterEvaluator = new EncounterFilterEvaluator(encounterService);
        encounterFilterEvaluator.evaluateFilter(null, eventLog);

        verify(encounterService, never()).getEncounterByUuid(anyString());
        assertNull(eventLog.getFilter());
    }
}