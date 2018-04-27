package com.accenture.accademy.spring.security.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Project {
    @Id
    private String id;

    private String name;

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

    @Override
    public String toString() {
        return id;
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
}
