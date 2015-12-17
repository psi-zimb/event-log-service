package org.bahmni.module.offlineservice.scheduler.jobs;

import org.bahmni.module.offlineservice.mapper.EventRecordsToEventsLogMapper;
import org.bahmni.module.offlineservice.model.EventRecords;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.bahmni.module.offlineservice.repository.EventRecordsRepository;
import org.bahmni.module.offlineservice.repository.EventsLogRepository;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

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

    public EventLogPublisherJob() {
    }

    @Override
    public void process() throws InterruptedException {
        EventsLog eventsLog = eventsLogRepository.findFirstByOrderByTimestampDesc();
        List<EventRecords> eventRecords;

        if (eventsLog != null) {
            eventRecords = eventRecordsRepository.findAllEventsAfterTimestamp(eventsLog.getTimestamp());
        } else {
            eventRecords = eventRecordsRepository.findAll();
        }

        List<EventsLog> eventsLogs = eventRecordsToEventsLogMapper.map(eventRecords);
        eventsLogRepository.save(eventsLogs);
    }
}
