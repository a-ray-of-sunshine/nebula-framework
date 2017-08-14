package com.cxy.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cxy.entity.User;
import com.nebula.suport.dao.GenerateDAO;

public class MybatisGeneratorTest {

    @Test
    public void test() throws Exception {
        List<Class> entityClazzs = new ArrayList<>();
        entityClazzs.add(User.class);
        GenerateDAO gd = new GenerateDAO(entityClazzs);
        gd.generate();
    }
}
