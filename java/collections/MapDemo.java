package collections;
import java.util.HashMap;
import java.util.Map;

public class MapDemo {
    public static void main(String[] args) {
        // Create a map of strings to integers
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 1);
        map.put("Banana", 2);
        map.put("Cherry", 3);

        // Display the map
        System.out.println("Map: " + map);

        // Accessing values
        System.out.println("Value for 'Apple': " + map.get("Apple"));
        System.out.println("Value for 'Banana': " + map.get("Banana"));

        // Modifying values
        map.put("Banana", 20);
        System.out.println("Modified Map: " + map);

        // Removing entries
        map.remove("Apple");
        System.out.println("Map after removing 'Apple': " + map);

        // Iterating through the map
        System.out.println("Iterating through the Map:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Clear the map
        map.clear();
        System.out.println("Map after clearing: " + map);


        //containsKey, containsValue, size, isEmpty
        map.put("Apple", 1);
        map.put("Banana", 2);
        System.out.println("Contains key 'Apple': " + map.containsKey("Apple"));
        System.out.println("Contains value 2: " + map.containsValue(2));
        System.out.println("Size of the Map: " + map.size());
        System.out.println("Is the Map empty? " + map.isEmpty());


        //putIfAbsent, getOrDefault
        map.putIfAbsent("Cherry", 3);
        System.out.println("Map after putIfAbsent: " + map);
        map.putIfAbsent("Banana", 20); // This will not update the value
        System.out.println("Map after putIfAbsent with existing key: " + map);

        System.out.println("Value for 'Apple' using getOrDefault: " + map.getOrDefault("Apple", 0));
        System.out.println("Value for 'Grapes' using getOrDefault: " + map.getOrDefault("Grapes", 0));
    
        //iterating through keys and values separately
        System.out.println("Iterating through keys:");
        for (String key : map.keySet()) {
            System.out.println(key);
        }

        System.out.println("Iterating through values:");
        for (Integer value : map.values()) {
            System.out.println(value);
        }


        //iterating through entries
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

    }
}
