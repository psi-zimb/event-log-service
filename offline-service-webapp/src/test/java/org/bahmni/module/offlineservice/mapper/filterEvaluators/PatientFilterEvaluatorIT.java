package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import IT.BaseIntegrationTest;
import org.bahmni.module.offlineservice.model.EventLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/patientFilterEvaluator/testData.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
})
public class PatientFilterEvaluatorIT extends BaseIntegrationTest {

    @Autowired
    private PatientFilterEvaluator patientFilterEvaluator;

    @Test
    public void shouldEvaluateFilter() throws Exception {
        EventLog eventLog = new EventLog();
        patientFilterEvaluator.evaluateFilter("patient uuid", eventLog);

        assertNotNull(eventLog.getFilter());
        assertEquals("Who Knows", eventLog.getFilter());
    }
}