package org.bahmni.module.offlineservice.repository;


import org.bahmni.module.offlineservice.model.EventRecords;
import org.bahmni.module.offlineservice.model.QuartzCronScheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface EventRecordsRepository extends JpaRepository<EventRecords, Integer> {
    @Query("select er from EventRecords er where er.timestamp > :timestamp")
    List<EventRecords> findAllEventsAfterTimestamp(@Param("timestamp") Date timestamp);
}
