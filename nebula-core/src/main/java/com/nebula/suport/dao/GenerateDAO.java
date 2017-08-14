package com.nebula.suport.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.DOMWriter;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nebula.utils.ResourceLoader;

/**
 * 使用 mybatis-generator 生成 sql mapper xml 和 dao 
 */
public class GenerateDAO {

    private static final Logger log = LoggerFactory.getLogger(GenerateDAO.class);
    private List<Class> entityClazzs;
    
    public GenerateDAO(List<Class> entityClazzs) {
        this.entityClazzs = entityClazzs;
    }

    public void generate() {
        try {
        InputStream configurationFile = addTables();
        
        ConfigurationParser cp = new ConfigurationParser(null);
        Configuration config = cp.parseConfiguration(configurationFile);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, null, null);

        myBatisGenerator.generate(null);
        } catch (Exception e) {
            log.error("使用 mybaits-generator 生成 dao 层失败，请检查配置是否正确: {}", e);
        }
        
    }

    @SuppressWarnings("rawtypes")
    private InputStream addTables() {
       Document doc = null;
       List<String> tables = getTables();
    try {
        DOMReader reader = new DOMReader();
        doc = reader.read(ResourceLoader.loadAsXML("mybatis-generator.xml"));
        List selectNodes = doc.selectNodes("/generatorConfiguration/context");
        if(CollectionUtils.isNotEmpty(selectNodes)) {
            Element o = (Element) selectNodes.get(0);
           
            // 添加表名
            for(String table : tables) {
                Element element = DocumentHelper.createElement("table");
                element.addAttribute("tableName", table);
                o.add(element );
           }
            
            // 添加项目路径
            addProjectLocation("javaModelGenerator", o);
            addProjectLocation("sqlMapGenerator", o);
            addProjectLocation("javaClientGenerator", o);
            
       }
        
    } catch (Exception e) {
        System.out.println(e);
    }
    
    DOMWriter writer = new DOMWriter();
      InputStream is = null; 
    try {
        is = getInputStream(writer.write(doc), doc.getDocType().asXML());
    } catch (DocumentException e) {
        e.printStackTrace();
    }
    
    return is;
    }

    @SuppressWarnings("rawtypes")
    private void addProjectLocation(String tag, Element o) {
        String projectlocation = System.getProperty("user.dir");
        List nodes = DocumentHelper.selectNodes(tag, o);
        Element e1 = (Element) nodes.get(0);
        e1.addAttribute("targetProject", projectlocation + "/" + e1.attributeValue("targetProject"));
    }

    private InputStream getInputStream(org.w3c.dom.Document document, String header) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(outputStream);
        try {
            outputStream.write(header.getBytes());
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(document), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<String> getTables() {
        List<String> tables = new ArrayList<String>();
        
        for(Class clazz : entityClazzs){
            Entity entity = (Entity) clazz.getAnnotation(Entity.class);
            if(null != entity){
                tables.add(entity.name());
            }
        }

        return tables;
    }
 
}