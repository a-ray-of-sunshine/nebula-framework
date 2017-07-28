package com.cxy.test;

import java.io.File;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;

public class MybatisGeneratorTest {

    @Test
    public void test() throws Exception {

        ConfigurationParser cp = new ConfigurationParser(null);
        File configurationFile = new File("D:\\workspace\\nebula-framework\\nebula-web/src/main/resources/mybatis-generator.xml");
        Configuration config = cp.parseConfiguration(configurationFile);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, null, null);

        myBatisGenerator.generate(null);
    }
}
