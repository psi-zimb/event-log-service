package org.bahmni.module.eventlogservice.repository;

import org.bahmni.module.eventlogservice.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface EventLogRepository extends JpaRepository<EventLog, Integer> {
    EventLog findFirstByOrderByTimestampDesc();
}
