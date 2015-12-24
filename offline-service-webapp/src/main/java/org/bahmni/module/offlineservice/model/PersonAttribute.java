package org.bahmni.module.offlineservice.model;

import javax.persistence.*;

@Entity
@Table(name = "person_attribute")
public class PersonAttribute {
    @Id
    @Column(name = "person_attribute_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "value")
    private String value;

    @OneToOne
    @JoinColumn(name = "person_attribute_type_id")
    private PersonAttributeType personAttributeType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PersonAttributeType getPersonAttributeType() {
        return personAttributeType;
    }

    public void setPersonAttributeType(PersonAttributeType personAttributeType) {
        this.personAttributeType = personAttributeType;
    }

}
