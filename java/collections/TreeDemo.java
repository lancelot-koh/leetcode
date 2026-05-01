package collections;

import java.util.TreeMap;
import java.util.TreeSet;

public class TreeDemo {
    public static void main(String[] args) {
        // This is a placeholder for the TreeDemo class.
        // You can implement tree-related functionality here, such as binary trees, AVL trees, etc.
        treeSetDemo();
        treeMapDemo();
    }

    //treeSet demo
    public static void treeSetDemo() {
        // You can implement a demo for TreeSet here, which is a sorted set based on a tree structure.
        // TreeSet operations 
        // - add, remove, contains, size, clear
        // - first, last, headSet, tailSet, subSet

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("Apple");
        treeSet.add("Banana");
        treeSet.add("Cherry");
        System.out.println("TreeSet: " + treeSet);
        System.out.println("Contains 'Banana': " + treeSet.contains("Banana"));
        System.out.println("First element: " + treeSet.first());
        System.out.println("Last element: " + treeSet.last());
        System.out.println("HeadSet (up to 'Cherry'): " + treeSet.headSet("Cherry"));
        System.out.println("TailSet (from 'Banana'): " + treeSet.tailSet("Banana"));
        System.out.println("SubSet (from 'Apple' to 'Cherry'): " + treeSet.subSet("Apple", "Cherry"));
        treeSet.remove("Banana");
        System.out.println("TreeSet after removing 'Banana': " + treeSet);
        treeSet.clear();
        System.out.println("TreeSet after clearing: " + treeSet);
    }

    public static void treeMapDemo() {
        // You can implement a demo for TreeMap here, which is a sorted map based on a tree structure.
        // TreeMap operations 
        // - put, get, remove, containsKey, containsValue, size, clear
        // - firstKey, lastKey, headMap, tailMap, subMap

        // Example code for TreeMap can be added here.
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Apple", 1);
        treeMap.put("Banana", 2);
        treeMap.put("Cherry", 3);
        System.out.println("TreeMap: " + treeMap);
        System.out.println("Value for 'Banana': " + treeMap.get("Banana"));
        System.out.println("First key: " + treeMap.firstKey());
        System.out.println("Last key: " + treeMap.lastKey());
        System.out.println("HeadMap (up to 'Cherry'): " + treeMap.headMap("Cherry"));
        System.out.println("TailMap (from 'Banana'): " + treeMap.tailMap("Banana"));
        System.out.println("SubMap (from 'Apple' to 'Cherry'): " + treeMap.subMap("Apple", "Cherry"));
        treeMap.remove("Banana");
        System.out.println("TreeMap after removing 'Banana': " + treeMap);
        treeMap.clear();
        System.out.println("TreeMap after clearing: " + treeMap);
    }

}
