package com.accenture.accademy.spring.security.model;


import javax.persistence.*;

@Entity
public class Employee {
    @Id
    private String id;

    private String name;

    private String password;

    private String currentProjectId;

    public Employee(){
    }

    public Employee(String id, String name){
        this();
        this.id = id;
        this.name = name;
    }

    public Employee(String id, String name, String currentProjectId){
        this(id, name);
        this.currentProjectId = currentProjectId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentProjectId() {
        return currentProjectId;
    }

    public void setCurrentProjectId(String currentProjectId) {
        this.currentProjectId = currentProjectId;
    }

    @Override
    public String toString() {
        return id;
    }
}
