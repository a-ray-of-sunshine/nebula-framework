package com.cxy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "sys_permission")
public class Permission extends IdEntity {
    
    @Column(name = "name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}