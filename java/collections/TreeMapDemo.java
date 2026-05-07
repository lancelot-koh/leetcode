package collections;
import java.util.*;

public class TreeMapDemo {
    public static void main(String[] args) {

        // ── TreeMap (sorted by key) ───────────────────────────────────────────
        TreeMap<Integer, String> tmap = new TreeMap<>();
        tmap.put(5, "five");
        tmap.put(2, "two");
        tmap.put(8, "eight");
        tmap.put(1, "one");
        tmap.put(6, "six");
        System.out.println("TreeMap (sorted): " + tmap);

        // Access min / max
        System.out.println("firstKey: " + tmap.firstKey());
        System.out.println("lastKey : " + tmap.lastKey());

        // floor / ceiling  (≤ / ≥)
        System.out.println("floorKey(4)  : " + tmap.floorKey(4));   // 2
        System.out.println("ceilingKey(4): " + tmap.ceilingKey(4)); // 5

        // lower / higher  (strictly < / >)
        System.out.println("lowerKey(5)  : " + tmap.lowerKey(5));   // 2
        System.out.println("higherKey(5) : " + tmap.higherKey(5));  // 6

        // subMap / headMap / tailMap
        System.out.println("subMap(2,6)  : " + tmap.subMap(2, 6));  // [2,6)
        System.out.println("headMap(5)   : " + tmap.headMap(5));     // < 5
        System.out.println("tailMap(5)   : " + tmap.tailMap(5));     // >= 5

        // pollFirstEntry / pollLastEntry
        System.out.println("pollFirst: " + tmap.pollFirstEntry());
        System.out.println("pollLast : " + tmap.pollLastEntry());
        System.out.println("TreeMap after polls: " + tmap);

        // Iterate in sorted order
        System.out.print("Keys in order: ");
        for (int key : tmap.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();

        // Iterate in reverse order
        System.out.print("Keys in reverse: ");
        for (int key : tmap.descendingKeySet()) {
            System.out.print(key + " ");
        }
        System.out.println();

        // getOrDefault, putIfAbsent, merge (frequency count pattern)
        TreeMap<Character, Integer> freq = new TreeMap<>();
        for (char c : "algorithm".toCharArray()) {
            freq.merge(c, 1, Integer::sum);
        }
        System.out.println("Char frequency (sorted): " + freq);

        // ── TreeSet (sorted unique elements) ──────────────────────────────────
        TreeSet<Integer> tset = new TreeSet<>();
        tset.add(10);
        tset.add(3);
        tset.add(7);
        tset.add(1);
        tset.add(10); // duplicate — ignored
        System.out.println("TreeSet (sorted): " + tset);

        System.out.println("first : " + tset.first());
        System.out.println("last  : " + tset.last());
        System.out.println("floor(6)  : " + tset.floor(6));   // 3
        System.out.println("ceiling(6): " + tset.ceiling(6)); // 7
        System.out.println("lower(7)  : " + tset.lower(7));   // 3
        System.out.println("higher(7) : " + tset.higher(7));  // 10

        tset.pollFirst();
        tset.pollLast();
        System.out.println("TreeSet after pollFirst/Last: " + tset);

        // subSet / headSet / tailSet
        TreeSet<Integer> nums = new TreeSet<>(Arrays.asList(1, 3, 5, 7, 9, 11));
        System.out.println("subSet(3,8) : " + nums.subSet(3, 8));  // [3,8)
        System.out.println("headSet(6)  : " + nums.headSet(6));     // < 6
        System.out.println("tailSet(6)  : " + nums.tailSet(6));     // >= 6

        // Descending iteration
        System.out.print("Descending: ");
        for (int x : nums.descendingSet()) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}
