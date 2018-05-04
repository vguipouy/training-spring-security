package com.accenture.accademy.spring.security.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Project entity which will be persisted in the database.
 * Entity annotation defines the class as a persisted Entity.
 * Default table name is class name : PROJECT
 */
@Entity
public class Project {
    // @Id annotation defines the entity identifier, similar to primary key in a table
    @Id
    private String id;

    private String name;

    // Example of a relation between Employee and Project
    @ManyToOne(fetch= FetchType.LAZY)
    private Employee lead;

    public Project(){
    }

    public Project(String id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public Project(String id, String name, Employee lead) {
        this(id, name);
        this.lead = lead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getLead() {
        return lead;
    }

    public void setLead(Employee lead) {
        this.lead = lead;
    }

    @Override
    public String toString() {
        return id;
    }
}
