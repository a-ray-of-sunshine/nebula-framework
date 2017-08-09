package com.cxy.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;

public class GenerateDao extends ClassPathScanningCandidateComponentProvider implements ApplicationContextAware{

    private static final Logger log = LoggerFactory.getLogger(GenerateDao.class);
    private ApplicationContext applicationContext;
    private ResourceLoader resourceLoader;
    private String basePackage = "com.cxy.entity";
	private ResourcePatternResolver resourcePatternResolver;
    private String resourcePattern = "**/*.class";
    private String annotationName = "javax.persistence.Entity";
    
    public GenerateDao(Environment environment) {
        super(false, environment);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        generate();
    }

    private void generate() {
        resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource("mybatis-generator.xml");
        
        System.out.println(System.getProperty("user.dir"));

        try {
        InputStream configurationFile = addTables(resource.getInputStream());
        
        ConfigurationParser cp = new ConfigurationParser(null);
            Configuration config = cp.parseConfiguration(configurationFile);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, null, null);

            myBatisGenerator.generate(null);    
        } catch (Exception e) {
            log.error("使用 mybaits-generator 生成 dao 层失败，请检查配置是否正确: {}", e);
        }
        
    }

    @SuppressWarnings("rawtypes")
    private InputStream addTables(InputStream resource) {
       List<String> tables = getTables();
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder documentBuilder;
       Document doc = null;
    try {
        factory.setValidating(false);
        documentBuilder = factory.newDocumentBuilder();
        
        DOMReader reader = new DOMReader();
        doc = reader.read(documentBuilder.parse(resource));
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

    private List<String> getTables() {
        
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(this.getResourceLoader());
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
        		resolveBasePackage(basePackage) + '/' + this.resourcePattern;
        List<String> tables = new ArrayList<String>();
        try {
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            for(Resource entityClass : resources) {
                MetadataReader metadataReader = this.getMetadataReaderFactory().getMetadataReader(entityClass); 
                AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
                if(metadata.hasAnnotation(annotationName)) {
                    Map<String, Object> attributes = metadata.getAnnotationAttributes(annotationName);
                    String table = ConvertUtils.convert(attributes.get("name"));
                    if(StringUtils.isNotBlank(table)) {
                        tables.add(table);
                    }
                }
            }

            log.info("扫描到以下资源： {}", (Object)resources);
        } catch (IOException e) {
        }
        
        return tables;
    }
    
}