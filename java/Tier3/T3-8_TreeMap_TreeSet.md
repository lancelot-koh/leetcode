# T3-8 — TreeMap / TreeSet (Ordered Map) 有序映射/集合

> **Core idea:** Java's `TreeMap` and `TreeSet` maintain keys in sorted order using a balanced BST (Red-Black Tree). O(log n) for insert, delete, and all order-sensitive queries: floor, ceiling, first, last, range.
> **核心思想：** Java的`TreeMap`和`TreeSet`用平衡BST（红黑树）按键排序。插入、删除及所有顺序查询（floor/ceiling/first/last/range）均为O(log n)。
>
> Complexity: O(log n) per operation. O(n) space.
> Full reference: `Sweep_Line/description.md`

---

## How It Works — Mental Model 理解模型

`TreeMap` and `TreeSet` are backed by a Red-Black Tree — a self-balancing BST that guarantees the tree height stays O(log n) after every insertion and deletion. The critical capability that HashMap and PriorityQueue cannot provide is **neighbor queries**: given a value `x`, find the largest stored value `≤ x` (`floorKey`) or the smallest stored value `≥ x` (`ceilingKey`) — all in O(log n). PriorityQueue gives you the global min/max in O(1), but cannot answer "what is the nearest stored value to x?" without a full scan. HashMap gives you O(1) lookup by exact key, but has no order at all. `TreeMap` sits between these: O(log n) per operation, but enables the range and neighbor queries that make sweep-line, calendar, and sliding-window-median problems tractable.

**Key invariant:** At all times, the Red-Black Tree maintains all keys in sorted order. Every `put`, `remove`, `floor`, and `ceiling` call leaves the tree balanced, so no sequence of operations can degrade to O(n).

**Common mistake:** Using `TreeSet` when duplicates are possible. `TreeSet` treats equal elements as identical and silently discards them. For a sliding-window median or similar problem where the same value appears multiple times, use `TreeMap<Integer, Integer>` as a multiset (with a count as value) and decrement/remove when evicting.

---

## Step-by-Step Trace 逐步追踪

```
My Calendar I — book intervals without overlap using TreeMap<start, end>

book(10, 20): calendar empty → no prev, no next → add (10,20). calendar={(10,20)}

book(15, 25): ceilingEntry(15) = (10→20); prev.value=20 > start=15 → OVERLAP. Return false.

book(20, 30): floorEntry(20) = (10→20); prev.value=20 <= start=20 (no overlap).
              ceilingEntry(20) = null (20 is in map as start? No, next start after 10 is nothing ≥ 20).
              Add (20,30). calendar={(10,20),(20,30)}

book(5, 15):  floorEntry(5) = null. ceilingEntry(5) = (10→20); next.key=10 < end=15 → OVERLAP. Return false.

book(5, 10):  floorEntry(5) = null. ceilingEntry(5) = (10→20); next.key=10 >= end=10 → no overlap.
              Add (5,10). calendar={(5,10),(10,20),(20,30)}
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Nearest smaller/larger value at query time | LC 220, 729 |
| Sliding window with ordered statistics | LC 220, 480 |
| Sweep line with dynamic max | LC 218, 732 |
| Calendar booking (find gaps) | LC 729, 731, 732 |
| Maintain sorted window + rank queries | LC 480 Sliding Window Median |
| Skyline problem | LC 218 |

**Signal:** need sorted order that changes dynamically; `PriorityQueue` can't do O(log n) arbitrary deletion; need floor/ceiling.

**HashMap vs TreeMap:**
| | HashMap | TreeMap |
|---|---|---|
| Insert/Delete | O(1) avg | O(log n) |
| Sorted order | No | Yes |
| floor/ceiling | No | Yes |
| Iteration order | Unpredictable | Sorted |

---

## Core Templates 核心模板

### TreeMap: floor, ceiling, range

```java
TreeMap<Integer, Integer> map = new TreeMap<>();

map.put(key, value);
map.remove(key);
map.getOrDefault(key, 0);

// All four neighbor queries return null if no such key exists — always null-check!
Integer lo  = map.floorKey(x);    // largest key ≤ x  (null if none)
Integer hi  = map.ceilingKey(x);  // smallest key ≥ x (null if none)
Integer lo2 = map.lowerKey(x);    // largest key < x  (strictly less)
Integer hi2 = map.higherKey(x);   // smallest key > x (strictly greater)

map.firstKey();                    // global minimum key — throws NoSuchElementException if empty
map.lastKey();                     // global maximum key — throws NoSuchElementException if empty
map.subMap(from, to);             // view of keys in [from, to) — live, reflects future changes
```

### TreeSet: sorted set operations

```java
TreeSet<Integer> set = new TreeSet<>();

set.add(x);
set.remove(x);
set.contains(x);

Integer lo = set.floor(x);         // largest ≤ x
Integer hi = set.ceiling(x);       // smallest ≥ x
set.first();                        // minimum
set.last();                         // maximum
set.headSet(x);                    // elements < x
set.tailSet(x);                    // elements ≥ x
```

### Multiset simulation (for duplicate values)

```java
// Simulate a multiset (sorted bag with duplicates) using count as value
TreeMap<Integer, Integer> multiset = new TreeMap<>();

// Add element: increment count, or insert with count 1
multiset.merge(val, 1, Integer::sum);

// Remove one occurrence: decrement count, remove key entirely when count hits 0
// CRITICAL: must remove the key when count=0, otherwise firstKey()/lastKey() return ghosts
multiset.merge(val, -1, Integer::sum);
if (multiset.get(val) == 0) { multiset.remove(val); }

// Min / Max always accurate because zero-count keys are eagerly removed
int min = multiset.firstKey();
int max = multiset.lastKey();
```

---

## Variants 变形

| Variant | Key idea | Example |
|---|---|---|
| Contains nearby almost duplicate | floor/ceiling within ±t, window of size k | LC 220 |
| Sliding window median | Two TreeMaps (or two heaps) | LC 480 |
| My Calendar I (no double booking) | floorEntry, check overlap | LC 729 |
| My Calendar II (allow double not triple) | Count overlaps with TreeMap diff | LC 731 |
| Skyline problem | TreeMap of height→count; max on add/remove | LC 218 |
| Sweep line max | Event points sorted by x; TreeMap for active values | sweep problems |

---

## Key Examples 关键例题

### Contains Duplicate III (LC 220)
```java
public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
    TreeSet<Long> window = new TreeSet<>();
    for (int i = 0; i < nums.length; i++) {
        long num = (long) nums[i];  // cast to long: nums[i] can be Integer.MIN_VALUE, arithmetic overflows int
        // floor: largest value in window <= num; if |num - floor| <= t, we found a pair
        Long floor = window.floor(num);
        Long ceil  = window.ceiling(num);
        if (floor != null && num - floor <= t) { return true; }
        if (ceil  != null && ceil - num <= t)  { return true; }
        window.add(num);
        // Maintain sliding window of size k: evict the element that just left
        if (i >= k) { window.remove((long) nums[i - k]); }
    }
    return false;
}
```

### My Calendar I (LC 729)
```java
class MyCalendar {
    TreeMap<Integer, Integer> calendar = new TreeMap<>();  // start → end

    public boolean book(int start, int end) {
        // prev: the last booked interval that starts at or before `start`
        Map.Entry<Integer, Integer> prev = calendar.floorEntry(start);
        // next: the first booked interval that starts at or after `start`
        Map.Entry<Integer, Integer> next = calendar.ceilingEntry(start);

        // prev overlaps if its end > our start (intervals share time)
        // next overlaps if its start < our end (our interval bleeds into next)
        if ((prev == null || prev.getValue() <= start) &&
            (next == null || next.getKey() >= end)) {
            calendar.put(start, end);
            return true;
        }
        return false;
    }
}
```

### The Skyline Problem (LC 218)
```java
public List<List<Integer>> getSkyline(int[][] buildings) {
    List<int[]> events = new ArrayList<>();
    for (int[] b : buildings) {
        events.add(new int[]{b[0], -b[2]});  // start: negative height
        events.add(new int[]{b[1],  b[2]});  // end: positive height
    }
    events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

    TreeMap<Integer, Integer> heights = new TreeMap<>();
    heights.put(0, 1);  // ground level
    int prevMax = 0;
    List<List<Integer>> result = new ArrayList<>();

    for (int[] e : events) {
        if (e[1] < 0) {
            // Building start: add its height to the active set (count occurrences for duplicates)
            heights.merge(-e[1], 1, Integer::sum);
        } else {
            // Building end: remove one instance of its height from the active set
            heights.merge(e[1], -1, Integer::sum);
            if (heights.get(e[1]) == 0) { heights.remove(e[1]); }  // purge zero-count entry
        }
        // lastKey() gives the current skyline height in O(log n) — the power of TreeMap
        int curMax = heights.lastKey();
        if (curMax != prevMax) {  // only emit a point when the skyline actually changes
            result.add(List.of(e[0], curMax));
            prevMax = curMax;
        }
    }
    return result;
}
```

### Sliding Window Median (LC 480)
```java
class Solution {
    TreeMap<Integer, Integer> lo = new TreeMap<>(), hi = new TreeMap<>();
    int loSize = 0, hiSize = 0;

    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] res = new double[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            add(nums[i]);
            if (i >= k) { remove(nums[i - k]); }
            if (i >= k - 1) { res[i - k + 1] = getMedian(k); }
        }
        return res;
    }

    void add(int num) {
        if (!lo.isEmpty() && num <= lo.lastKey()) { lo.merge(num, 1, Integer::sum); loSize++; }
        else { hi.merge(num, 1, Integer::sum); hiSize++; }
        balance();
    }

    void remove(int num) {
        if (!lo.isEmpty() && num <= lo.lastKey()) { lo.merge(num, -1, Integer::sum); if (lo.get(num) == 0) lo.remove(num); loSize--; }
        else { hi.merge(num, -1, Integer::sum); if (hi.get(num) == 0) hi.remove(num); hiSize--; }
        balance();
    }

    void balance() {
        while (loSize > hiSize + 1) { int v = lo.lastKey(); lo.merge(v, -1, Integer::sum); if (lo.get(v) == 0) lo.remove(v); loSize--; hi.merge(v, 1, Integer::sum); hiSize++; }
        while (hiSize > loSize)     { int v = hi.firstKey(); hi.merge(v, -1, Integer::sum); if (hi.get(v) == 0) hi.remove(v); hiSize--; lo.merge(v, 1, Integer::sum); loSize++; }
    }

    double getMedian(int k) {
        return k % 2 == 0 ? ((double) lo.lastKey() + hi.firstKey()) / 2 : lo.lastKey();
    }
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `floorKey` returns null | Always null-check before comparing |
| Multiset with TreeMap | Use `TreeMap<Integer,Integer>` with count; remove key when count reaches 0 |
| Duplicate values in TreeSet | `TreeSet` treats equal elements as same; use TreeMap with count or `(a,b) -> a <= b ? -1 : 1` comparator trick |
| Long overflow in LC 220 | Cast to `long` before arithmetic: `(long) nums[i]` |
| Skyline: start before end tie-break | Sort by `(x, height)` — negative height for starts so they process before ends at same x |
| `subMap(from, true, to, false)` | Half-open range query; check inclusive/exclusive params |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 729 My Calendar I |
| Hard | LC 220 Contains Duplicate III |
| Hard | LC 731 My Calendar II |
| Hard | LC 480 Sliding Window Median |
| Hard | LC 218 The Skyline Problem |
| Hard | LC 732 My Calendar III |

**Order:** 729 → 731 → 220 → 732 → 218 → 480

---

## One-line Summary

```
TreeMap/TreeSet = O(log n) sorted dynamic set; floor/ceiling/first/last enable range and neighbor queries that HashMap cannot.
TreeMap/TreeSet = O(log n)有序动态集合；floor/ceiling/first/last支持HashMap无法实现的范围和邻值查询。
```
