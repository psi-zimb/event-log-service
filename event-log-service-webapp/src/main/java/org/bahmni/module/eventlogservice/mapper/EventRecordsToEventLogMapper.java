package org.bahmni.module.eventlogservice.mapper;

import org.bahmni.module.eventlogservice.mapper.filterEvaluators.AddressHierarchyFilterEvaluator;
import org.bahmni.module.eventlogservice.mapper.filterEvaluators.EncounterFilterEvaluator;
import org.bahmni.module.eventlogservice.mapper.filterEvaluators.FilterEvaluator;
import org.bahmni.module.eventlogservice.mapper.filterEvaluators.PatientFilterEvaluator;
import org.bahmni.module.eventlogservice.model.EventRecords;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EventRecordsToEventLogMapper {

    private static final String UUID_PATTERN = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";
    private final HashMap<String, FilterEvaluator> filterEvaluators;
    private JdbcTemplate jdbcTemplate;
    private Pattern pattern;

    @Autowired
    public EventRecordsToEventLogMapper(PatientFilterEvaluator patientFilterEvaluator, EncounterFilterEvaluator encounterFilterEvaluator, AddressHierarchyFilterEvaluator addressHierarchyFilterEvaluator, JdbcTemplate jdbcTemplate) {
        filterEvaluators = new HashMap<String, FilterEvaluator>();
        filterEvaluators.put("patient", patientFilterEvaluator);
        filterEvaluators.put("encounter", encounterFilterEvaluator);
        filterEvaluators.put("addressHierarchy", addressHierarchyFilterEvaluator);
        pattern = Pattern.compile(UUID_PATTERN);
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EventLog> map(List<EventRecords> eventRecords) {
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        for (EventRecords eventRecord : eventRecords) {
            EventLog eventLog = new EventLog(eventRecord.getUuid(), eventRecord.getTimestamp(), eventRecord.getObject(), eventRecord.getCategory(), null);
            evaluateFilter(eventRecord, eventLog);
            if ((eventRecord.getCategory().equalsIgnoreCase("all-concepts") && isOfflineConceptEvent(getConceptUuidFromUrl(eventLog.getObject())))
                    || eventRecord.getCategory().equalsIgnoreCase("offline-concepts")) {
                eventLog.setCategory("offline-concepts");
                eventLogs.add(eventLog);
            }
            else if (!eventRecord.getCategory().equalsIgnoreCase("all-concepts")){
                eventLogs.add(eventLog);
            }
        }
        return eventLogs;
    }

    private boolean isOfflineConceptEvent(String eventUuid) {
        String SQL = "select count(c.uuid) " +
                "from concept_set cs " +
                "  join concept_name cn on cs.concept_set = cn.concept_id " +
                "  join concept c on cs.concept_id = c.concept_id " +
                "where cn.name = \"Offline Concepts\" and cn.concept_name_type='FULLY_SPECIFIED'" +
                "and c.uuid='" + eventUuid + "'";
        int count = (Integer) this.jdbcTemplate.queryForObject(SQL, new ConceptRowMapper());
        return count > 0;
    }

    private String getConceptUuidFromUrl(String eventUrl) {
        String searchTerm = "/concept/";
        return eventUrl.substring(eventUrl.indexOf(searchTerm) + searchTerm.length(), eventUrl.indexOf("?"));
    }

    private void evaluateFilter(EventRecords eventRecord, EventLog eventLog) {
        String object = eventRecord.getObject();
        Matcher matcher = pattern.matcher(object);
        if (matcher.find() && filterEvaluators.get(eventRecord.getCategory()) != null) {
            filterEvaluators.get(eventRecord.getCategory()).evaluateFilter(matcher.group(0), eventLog);
        }
    }

    class ConceptRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt(1);
        }
    }
}
