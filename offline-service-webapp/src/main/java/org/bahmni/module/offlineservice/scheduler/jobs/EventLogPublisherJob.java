package org.bahmni.module.offlineservice.scheduler.jobs;

import org.apache.log4j.Logger;
import org.bahmni.module.offlineservice.mapper.EventRecordsToEventsLogMapper;
import org.bahmni.module.offlineservice.model.EventRecords;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.bahmni.module.offlineservice.repository.EventRecordsRepository;
import org.bahmni.module.offlineservice.repository.EventsLogRepository;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@DisallowConcurrentExecution
@Component("eventLogPublisherJob")
@ConditionalOnExpression("'${enable.scheduling}'=='true'")
public class EventLogPublisherJob implements Job {
    @Autowired
    private EventRecordsRepository eventRecordsRepository;
    @Autowired
    private EventsLogRepository eventsLogRepository;
    @Autowired
    private EventRecordsToEventsLogMapper eventRecordsToEventsLogMapper;

    private static Logger logger = Logger.getLogger(EventLogPublisherJob.class);

    public EventLogPublisherJob() {
    }

    @Override
    public void process() throws InterruptedException {
        EventsLog eventsLog = eventsLogRepository.findFirstByOrderByTimestampDesc();
        List<EventRecords> eventRecords;

        if (eventsLog != null) {
            logger.debug("Reading events which are happened after : " + eventsLog.getTimestamp().toString());
            eventRecords = eventRecordsRepository.findAllEventsAfterTimestamp(eventsLog.getTimestamp());
        } else {
            logger.debug("Reading all events from event_records");
            eventRecords = eventRecordsRepository.findAll();
        }

        logger.debug("Found " + eventRecords.size() + " events.");
        List<EventsLog> eventsLogs = eventRecordsToEventsLogMapper.map(eventRecords);
        eventsLogRepository.save(eventsLogs);
        logger.debug("Copied " + eventRecords.size() + " events to events_log table");
    }
}
