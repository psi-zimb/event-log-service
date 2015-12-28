package org.bahmni.module.eventlogservice.repository;


import org.bahmni.module.eventlogservice.model.QuartzCronScheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CronJobRepository extends JpaRepository<QuartzCronScheduler, Integer> {
}
