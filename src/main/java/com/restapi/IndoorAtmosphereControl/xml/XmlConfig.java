package com.restapi.IndoorAtmosphereControl.xml;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.File;
import java.io.IOException;
import org.jdom2.Document;
@Configuration
public class XmlConfig {

    @Bean
    public Document XmlParser (){
        Document document;
        SAXBuilder builder = new SAXBuilder();

        File xmlConfigFile = new File("./src/main/java/com/restapi/IndoorAtmosphereControl/xml/config.xml");

        try {
            document = builder.build( xmlConfigFile);
        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }

        return document;

    }
}
