package org.bahmni.module.offlineservice.repository;


import org.bahmni.module.offlineservice.model.QuartzCronScheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CronJobRepository extends JpaRepository<QuartzCronScheduler, Integer> {
}
