package org.bahmni.module.offlineservice.scheduler.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component("eventLogPublisherJob")
@ConditionalOnExpression("'${enable.scheduling}'=='true'")
public class EventLogPublisherJob implements Job {

    public EventLogPublisherJob() {
    }

    @Override
    public void process() throws InterruptedException {

    }
}
