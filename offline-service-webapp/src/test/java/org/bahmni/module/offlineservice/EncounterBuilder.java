package org.bahmni.module.offlineservice;

import org.openmrs.Encounter;
import org.openmrs.Patient;


public class EncounterBuilder {
    private Encounter encounter = new Encounter();

    public Encounter build() {
        return encounter;
    }

    public EncounterBuilder withPatient(Patient patient) {
        encounter.setPatient(patient);
        return this;
    }

}
