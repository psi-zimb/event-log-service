package org.bahmni.module.eventlogservice.mapper;

import org.apache.log4j.Logger;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.model.EventRecords;
import org.bahmni.module.eventlogservice.scheduler.ScheduledTasks;
import org.bahmni.module.eventlogservice.web.helper.OpenMRSWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EventRecordsToEventLogMapper {

    private static final String UUID_PATTERN = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";
    private JdbcTemplate jdbcTemplate;
    private Pattern pattern;
    private OpenMRSWebClient openMRSWebClient;

    @Value("${bahmni.filter.uri}")
    private String bahmniEventLogFilterURL;

    private static Logger logger = Logger.getLogger(ScheduledTasks.class);

    @Autowired
    public EventRecordsToEventLogMapper(JdbcTemplate jdbcTemplate, OpenMRSWebClient openMRSWebClient) {
        this.openMRSWebClient = openMRSWebClient;
        pattern = Pattern.compile(UUID_PATTERN);
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<EventLog> map(List<EventRecords> eventRecords) {
        ArrayList<EventLog> eventLogs = new ArrayList<EventLog>();
        for (EventRecords eventRecord : eventRecords) {
            EventLog eventLog = new EventLog(eventRecord.getUuid(), eventRecord.getTimestamp(), eventRecord.getObject(), eventRecord.getCategory(), null);
            if(!eventRecord.getCategory().contains("concepts"))
                evaluateFilter(eventLog);
            if ((eventRecord.getCategory().equalsIgnoreCase("all-concepts"))) {
                if(isOfflineConceptEvent(getConceptUuidFromUrl(eventLog.getObject()))){
                    eventLog.setCategory("offline-concepts");
                }
                else {
                    eventLog.setCategory("concepts");
                }
            }
            eventLogs.add(eventLog);
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

    private void evaluateFilter(EventLog eventLog) {
        URI uri = bahmniFilterURL(eventLog);
        if(uri != null) {
            String response = openMRSWebClient.get(uri);
            if (response != null && !response.isEmpty())
                eventLog.setFilter(response);
        }
    }

    private URI  bahmniFilterURL(EventLog eventLog) {
        String object = eventLog.getObject();
        String url = "";
        Matcher matcher = pattern.matcher(object);
        URI uri = null;
        if (matcher.find()) {
            String uuid = matcher.group(0);
            url = bahmniEventLogFilterURL + eventLog.getCategory() + "/" + uuid;
        }
        if(!url.isEmpty()) {
            try {
                uri = new URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                logger.error("Invalid url to for event log filter : "+ url);
            }
        }
        return uri;
    }

    class ConceptRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt(1);
        }
    }

    protected void setBahmniEventLogFilterURL(String bahmniEventLogFilterURL) {
        this.bahmniEventLogFilterURL = bahmniEventLogFilterURL;
    }
}
