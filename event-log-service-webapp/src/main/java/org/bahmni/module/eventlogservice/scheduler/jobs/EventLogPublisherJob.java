package org.bahmni.module.eventlogservice.scheduler.jobs;

import org.apache.log4j.Logger;
import org.bahmni.module.eventlogservice.fetcher.EventLogFetcher;
import org.bahmni.module.eventlogservice.model.EventLog;
import org.bahmni.module.eventlogservice.repository.EventLogRepository;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@DisallowConcurrentExecution
@Component("eventLogPublisherJob")
@ConditionalOnExpression("'${enable.scheduling}'=='true'")
public class EventLogPublisherJob implements Job {
    private EventLogRepository eventLogRepository;
    private EventLogFetcher eventLogFetcher;

    private static Logger logger = Logger.getLogger(EventLogPublisherJob.class);

    @Autowired
    public EventLogPublisherJob(EventLogRepository eventLogRepository, EventLogFetcher eventLogFetcher) {
        this.eventLogRepository = eventLogRepository;
        this.eventLogFetcher = eventLogFetcher;
    }

    @Override
    public void process() throws InterruptedException {
        EventLog eventLog = eventLogRepository.findFirstByOrderByIdDesc();
        List<EventLog> eventLogs;
        String lastReadEventUuid = eventLog != null ? eventLog.getUuid(): "";
        logger.debug("Reading events which happened after event with uuid: " + lastReadEventUuid);
        try {
            eventLogs = eventLogFetcher.fetchEventLogsAfter(lastReadEventUuid);
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.debug("Found " + eventLogs.size() + " events.");
        eventLogRepository.save(eventLogs);
        logger.debug("Copied " + eventLogs.size() + " events to events_log table");
    }
}
