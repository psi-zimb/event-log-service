package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.PatientBuilder;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class PatientFilterEvaluatorTest {

    @Mock
    private PatientService patientService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldEvaluateFilterForPatient() {
        Patient patient = new PatientBuilder().withAddress1("Hyd").withAddress2("Telangana").build();
        String patientUuid = "d95bf6c9-d1c6-41dc-aecf-1c06bd71358c";
        EventsLog eventLog = new EventsLog();
        when(patientService.getPatientByUuid(patientUuid)).thenReturn(patient);
        PatientFilterEvaluator patientFilterEvaluator = new PatientFilterEvaluator(patientService);
        patientFilterEvaluator.evaluateFilter(patientUuid, eventLog);

        verify(patientService, times(1)).getPatientByUuid(patientUuid);
        assertEquals(patient.getPersonAddress().getAddress1(), eventLog.getFilter());
    }

    @Test
    public void shouldNotSetFilterIfUuidIsNull() throws Exception {
        EventsLog eventLog = new EventsLog();
        PatientFilterEvaluator patientFilterEvaluator = new PatientFilterEvaluator(patientService);
        patientFilterEvaluator.evaluateFilter(null, eventLog);
        patientFilterEvaluator.evaluateFilter(null, eventLog);

        verify(patientService, never()).getPatientByUuid(anyString());
        assertNull(eventLog.getFilter());
    }

}