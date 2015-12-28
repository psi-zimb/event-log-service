package org.bahmni.module.eventlogservice.scheduler.jobs;

public interface Job {
    void process() throws InterruptedException;
}
