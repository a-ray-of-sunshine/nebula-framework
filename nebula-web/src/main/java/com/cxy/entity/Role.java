package com.cxy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.ColumnDefault;

@Entity(name = "sys_role")
public class Role extends IdEntity {

    @Column(name = "name")
    private String name;
    
    @Column(name = "power")
    @ColumnDefault("view")
    private String power;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
    
}