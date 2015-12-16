package org.bahmni.module.offlineservice.scheduler.jobs;

public interface Job {
    void process() throws InterruptedException;
}
