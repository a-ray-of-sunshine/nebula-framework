package com.cxy.tools;

import java.util.ArrayList;
import java.util.List;

import com.cxy.entity.Role;
import com.cxy.entity.User;
import com.nebula.suport.dao.CreateTable;

public class GenerateTable {
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        List entitys = new ArrayList();
        entitys.add(User.class);
        entitys.add(Role.class);
        CreateTable ct = new CreateTable();
        ct.addByEntityClazzs(entitys);
        ct.create();
    }
}