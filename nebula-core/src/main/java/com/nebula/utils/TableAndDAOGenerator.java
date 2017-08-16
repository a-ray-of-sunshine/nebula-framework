package com.nebula.utils;

import java.util.ArrayList;
import java.util.List;

import com.nebula.suport.dao.CreateTable;
import com.nebula.suport.dao.GenerateDAO;

public class TableAndDAOGenerator {
    
    public static void generate(Class clazz){
       List<Class> list = new ArrayList<>();
       CreateTable ct = new CreateTable();
       ct.add(clazz);
       
       list.add(clazz);
       GenerateDAO gd = new GenerateDAO(list);
       
       ct.create();
       gd.generate();
    }
    
    public static void generate(List<Class> classes){
       CreateTable ct = new CreateTable();
       ct.addByEntityClazzs(classes);
       GenerateDAO gd = new GenerateDAO(classes);
       ct.create();
       gd.generate();
    }
}