package org.bahmni.module.eventlogservice.model;

import javax.persistence.*;

@Entity
@Table(name = "address_hierarchy_entry")
public class AddressHierarchyEntry {
    @Id
    @Column(name = "address_hierarchy_entry_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_generated_id")
    private String userGeneratedId;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "level_id")
    private Integer levelId;

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

    public String getUserGeneratedId() {
        return userGeneratedId;
    }

    public void setUserGeneratedId(String userGeneratedId) {
        this.userGeneratedId = userGeneratedId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }
}
