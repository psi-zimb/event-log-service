package org.bahmni.module.eventlogservice.mapper.filterEvaluators;

import IT.BaseIntegrationTest;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.Assert.assertEquals;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:testDataSet/addressHierarchyFilterEvaluator/testData.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:testDataSet/truncateTables.sql")
})
public class AddressHierarchyFilterEvaluatorIT extends BaseIntegrationTest{
    @Autowired
    private AddressHierarchyFilterEvaluator addressHierarchyFilterEvaluator;

    @Test
    public void shouldEvaluateFilterForTop3Levels() throws Exception {
        EventLog eventLog = new EventLog();

        addressHierarchyFilterEvaluator.evaluateFilter("parentAddressUuid", eventLog);

        assertEquals(null, eventLog.getFilter());

    }

    @Test
    public void shouldEvaluateFilterForLowerLevels() throws Exception {
        EventLog eventLog = new EventLog();

        addressHierarchyFilterEvaluator.evaluateFilter("childAddressUuid", eventLog);

        assertEquals("19203869", eventLog.getFilter());

    }

    @Test
    public void shouldReturnNullIfAddressHasNoLevelId() throws Exception {
        EventLog eventLog = new EventLog();

        addressHierarchyFilterEvaluator.evaluateFilter("childAddress", eventLog);

        assertEquals(null, eventLog.getFilter());

    }
}