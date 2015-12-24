package org.bahmni.module.offlineservice.model;

import javax.persistence.*;

@Entity
@Table(name = "encounter")
public class Encounter {
    @Id
    @Column(name = "encounter_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Person person;

    @Column(name = "uuid")
    private String uuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
