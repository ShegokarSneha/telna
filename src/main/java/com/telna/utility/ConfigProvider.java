package com.telna.utility;

import java.io.InputStream;
import java.util.Properties;

public class ConfigProvider {

    private static Properties properties;

    public static Properties getInstance(){
        if(properties == null){
            properties = loadProperties();
            return properties;
        }else{
            return properties;
        }
    }

    private static Properties loadProperties(){
        Properties properties = new Properties();
        try(InputStream inputStream = ConfigProvider.class.getClassLoader().getResourceAsStream("properties/config.properties")){
            properties.load(inputStream);
            LogMapper.info("config.properties File Found.....");
        }catch (Exception e){
            LogMapper.error("config.properties File Not Found.....");
        }
        return properties;
    }

    public static String getAsString(String key){
        return getInstance().getProperty(key);
    }
}
