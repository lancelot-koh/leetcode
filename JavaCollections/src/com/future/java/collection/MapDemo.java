package com.future.java.collection;

import java.util.*;
import java.util.stream.Stream;

public class MapDemo {
    /**
     * Map Implementations
     * java.util.HashMap - C
     * java.util.Hashtable - C
     * java.util.EnumMap
     * java.util.IdentityHashMap
     * java.util.LinkedHashMap
     * java.util.Properties
     * java.util.TreeMap - C
     * java.util.WeakHashMap
     */

    public static void main(String[] args) {
        Map map1 = new HashMap();
        Map mao2 = new TreeMap();

        Map<String, String> gMap = new HashMap<String, String>();
        for(String key: gMap.keySet()){

        }

        for(String value: gMap.values()){

        }


        Map<String, String> map = new HashMap<>();

        map.put("key1", "element 1");
        map.put("key2", "element 2");
        map.put("key3", "element 3");

        //type conflict
//        map.put("key", 123);

        map.put(null, "null as key");
        System.out.println(map.get(null));

        map.put("D", null);
        System.out.println(map.get("D"));


        Map<String, String> mapA = new HashMap<>();
        mapA.put("key1", "value1");
        mapA.put("key2", "value2");

        Map<String, String> mapB = new HashMap<>();
        mapB.putAll(mapA);

        for(String s: mapB.keySet()) {
            System.out.println(s);
        }

        // Get or Default value
        String v = map.getOrDefault("KeyX","KeyX doesn't exists!");
        System.out.println(v);

        map.put("KeyX","item X");
        String v2 = map.getOrDefault("KeyX","KeyX doesn't exists!");
        System.out.println(v2);

        System.out.println("Contains key KeyD: "+map.containsKey("KeyD"));
        System.out.println("Contains key KeyX: "+map.containsKey("KeyX"));
        System.out.println("Contains value item X1: " + map.containsValue("item X1"));
        System.out.println("Contains value item X: " + map.containsValue("item X"));


        System.out.println("==========iterator=============");
        // loop
        Iterator<String> iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            Object key = iterator.next();
            System.out.println(map.get(key));
        }

        System.out.println("============for each====================");
        // for each
        for(String s: map.keySet()){
            System.out.println(map.get(s));
        }


        System.out.println("==============stream===================");
        Stream<String> stream = map.keySet().stream();
        stream.forEach((item) -> {
            System.out.println(map.get(item));
        });

        // value is same
        // map.values()

        // entrySet
        for(Map.Entry<String, String> entry: map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
        }


        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator2 = entries.iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, String> entry = iterator2.next();
            String key = entry.getKey();
            String value = entry.getValue();
        }

        map.remove(null);

        map.clear();


        // replace

        map.replace("keyZ", "xxxxxx");
        map.put("KeyZ","key Z");
        map.replace("KeyZ", "yyyyyy");

        int mapCount = map.size();

        // compute
        map.compute("KeyZ", (key, value) ->
                value == null ? null :
                        value.toString().toUpperCase());
        for(String key: map.keySet()) {
            if(key.equals("KeyZ")){
                System.out.println(map.get(key));
            }
        }


        map.computeIfAbsent("123", (key) -> "abc");
        for(String key: map.keySet()) {
            if(key.equals("123")){
                System.out.println(map.get(key));
            }
        }

        map.computeIfPresent("123", (key, value) ->
                value == null ? null :
                        value.toString() + "abc");

        for(String key: map.keySet()) {
            if(key.equals("123")){
                System.out.println(map.get(key));
            }
        }

        map.merge("XYZ", "XYZ",
                (oldValue, newValue) -> newValue + "-abc");

        for(String key: map.keySet()) {
            if(key.equals("XYZ")){
                System.out.println(map.get(key));
            }
        }

        map.merge("XYZ", "XYZ",
                (oldValue, newValue) -> newValue + "-abc");

        for(String key: map.keySet()) {
            if(key.equals("XYZ")){
                System.out.println(map.get(key));
            }
        }

        map.merge("XYZ", "XYZ",
                (oldValue, newValue) -> newValue + "-abc");

        for(String key: map.keySet()) {
            if(key.equals("XYZ")){
                System.out.println(map.get(key));
            }
        }

    }
}
