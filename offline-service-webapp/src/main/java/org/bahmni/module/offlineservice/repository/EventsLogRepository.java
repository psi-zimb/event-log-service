package org.bahmni.module.offlineservice.repository;

import org.bahmni.module.offlineservice.model.EventRecords;
import org.bahmni.module.offlineservice.model.EventsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EventsLogRepository extends JpaRepository<EventsLog, Integer> {
    EventsLog findFirstByOrderByTimestampDesc();
}
