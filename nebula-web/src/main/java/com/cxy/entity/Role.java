package com.cxy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "sys_role")
public class Role extends IdEntity {

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}