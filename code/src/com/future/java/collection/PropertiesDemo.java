package com.future.java.collection;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesDemo {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("email", "test@test.com");

        String email = prop.getProperty("email");
        System.out.println(email);

        prop.remove("email");

        System.out.println(prop.size());

        prop.setProperty("key1", "value1");
        prop.setProperty("key2", "value2");
        prop.setProperty("key3", "value3");

        Iterator iterator = prop.keySet().iterator();
        while (iterator.hasNext()){
            System.out.println(prop.getProperty((String) iterator.next()));
        }

        try(FileWriter outputStream = new FileWriter("prop.properties")) {
            prop.store(outputStream, "There're properteis");
        } catch (IOException e){
            e.printStackTrace();
        }

        // Java 11
//        try(FileWriter writer = new FileWriter("data/prop2.properties", Charset.forName("utf-8"))) {
//            prop.store(writer, "There're properteis");
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        Properties prop2 = new Properties();
        try(FileReader fileReader = new FileReader("prop.properties")){
            prop2.load(fileReader);
            System.out.println(prop2.get("key1"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileOutputStream outputStream = new FileOutputStream("prop.xml")) {
            prop.storeToXML(outputStream, "This is XML", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties xmlProp = new Properties();
        try(FileInputStream inputStream = new FileInputStream("prop.xml")) {
            xmlProp.loadFromXML(inputStream);
            System.out.println(xmlProp.getProperty("key2"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        Class aClass = PropertiesDemo.class;
        InputStream input = aClass.getResourceAsStream("/prop.properties");
        Properties fromClasspath = new Properties();

        try {
            fromClasspath.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
