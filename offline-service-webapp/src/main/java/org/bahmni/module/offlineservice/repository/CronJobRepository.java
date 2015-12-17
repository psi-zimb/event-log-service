package org.bahmni.module.offlineservice.repository;


import org.bahmni.module.offlineservice.model.QuartzCronScheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CronJobRepository extends JpaRepository<QuartzCronScheduler, Integer> {
}
