package org.bahmni.module.eventlogservice.repository;


import org.bahmni.module.eventlogservice.model.EventRecords;
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
