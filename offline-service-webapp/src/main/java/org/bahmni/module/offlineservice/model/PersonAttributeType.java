package org.bahmni.module.offlineservice.model;

import javax.persistence.*;

@Entity
@Table(name = "person_attribute_type")
public class PersonAttributeType {
    @Id
    @Column(name = "person_attribute_type_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
