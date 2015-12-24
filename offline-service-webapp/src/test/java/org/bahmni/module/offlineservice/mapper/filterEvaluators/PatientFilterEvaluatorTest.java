package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import org.bahmni.module.offlineservice.model.EventLog;
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

public class PatientFilterEvaluatorTest {
    public static final String ATTRIBUTE_TYPE_NAME = "Catchment number";
    public static final String PATIENT_UUID = "patientUuid";
    @InjectMocks
    private PatientFilterEvaluator patientFilterEvaluator = new PatientFilterEvaluator();

    @Mock
    private PersonAttributeRepository personAttributeRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldEvaluateFilterForPatient() {
        PersonAttribute personAttribute = new PersonAttribute();
        personAttribute.setValue("Value");
        when(personAttributeRepository.findByPersonUuidAndPersonAttributeType(PATIENT_UUID, ATTRIBUTE_TYPE_NAME)).thenReturn(personAttribute);

        EventLog eventLog = new EventLog();
        patientFilterEvaluator.evaluateFilter(PATIENT_UUID, eventLog);

        verify(personAttributeRepository, times(1)).findByPersonUuidAndPersonAttributeType(PATIENT_UUID, ATTRIBUTE_TYPE_NAME);
        assertEquals("Value", eventLog.getFilter());
    }

    @Test
    public void shouldNotSetFilterIfUuidIsNull() throws Exception {
        EventLog eventLog = new EventLog();
        patientFilterEvaluator.evaluateFilter(null, eventLog);

        verify(personAttributeRepository, never()).findByPersonUuidAndPersonAttributeType(PATIENT_UUID, ATTRIBUTE_TYPE_NAME);
        assertNull("Should be null", eventLog.getFilter());
    }

    @Test
    public void shouldNotSetFilterIfAttributeIsNotAvailable() throws Exception {
        when(personAttributeRepository.findByPersonUuidAndPersonAttributeType(PATIENT_UUID, ATTRIBUTE_TYPE_NAME)).thenReturn(null);

        EventLog eventLog = new EventLog();
        patientFilterEvaluator.evaluateFilter(PATIENT_UUID, eventLog);

        verify(personAttributeRepository, times(1)).findByPersonUuidAndPersonAttributeType(PATIENT_UUID, ATTRIBUTE_TYPE_NAME);
        assertNull(eventLog.getFilter());
    }
}