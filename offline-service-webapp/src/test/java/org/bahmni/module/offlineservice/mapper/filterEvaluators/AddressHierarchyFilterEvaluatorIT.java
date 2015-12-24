package org.bahmni.module.offlineservice.mapper.filterEvaluators;

import IT.BaseIntegrationTest;
import org.bahmni.module.offlineservice.model.EventLog;
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
    public void shouldEvaluateFilter() throws Exception {
        EventLog eventLog = new EventLog();

        addressHierarchyFilterEvaluator.evaluateFilter("address uuid", eventLog);

        assertEquals("1920386", eventLog.getFilter());
    }
}