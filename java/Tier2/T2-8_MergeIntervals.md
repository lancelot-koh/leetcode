# T2-8 — Merge Intervals 合并区间

> **Core idea:** Sort intervals by start time. Merge overlapping intervals by comparing each interval's start with the current merged end. O(n log n) dominated by sorting.
> **核心思想：** 按起始时间排序，将每个区间的起始与当前合并区间的结束比较，合并重叠部分。时间复杂度由排序主导O(n log n)。
>
> Complexity: O(n log n) time, O(n) space for output.
> Full reference: `Greedy/description.md`

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Merge overlapping intervals | LC 56 |
| Insert interval into sorted list | LC 57 |
| Meeting rooms (any overlap?) | LC 252 |
| Meeting rooms II (min rooms) | LC 253 |
| Employee free time | LC 759 |
| Remove covered intervals | LC 1288 |

**Signal:** intervals, meetings, time ranges, overlapping/non-overlapping, scheduling.

---

## Core Templates 核心模板

### Merge overlapping intervals

```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);   // sort by start
List<int[]> merged = new ArrayList<>();

for (int[] interval : intervals) {
    if (merged.isEmpty() || merged.get(merged.size()-1)[1] < interval[0])
        merged.add(interval);                     // no overlap: add new
    else
        merged.get(merged.size()-1)[1] =
            Math.max(merged.get(merged.size()-1)[1], interval[1]);  // merge
}
return merged.toArray(new int[merged.size()][]);
```

### Detect any overlap (meeting rooms I)

```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
for (int i = 1; i < intervals.length; i++)
    if (intervals[i][0] < intervals[i-1][1]) return false;  // overlap
return true;
```

### Minimum meeting rooms (min-heap)

```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);   // sort by start
PriorityQueue<Integer> ends = new PriorityQueue<>();  // track end times

for (int[] interval : intervals) {
    if (!ends.isEmpty() && ends.peek() <= interval[0])
        ends.poll();           // recycle room: meeting ended
    ends.offer(interval[1]);   // assign room with this end time
}
return ends.size();            // rooms in use = answer
```

### Insert interval into sorted non-overlapping list

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> res = new ArrayList<>();
    int i = 0, n = intervals.length;

    // Add all intervals before newInterval
    while (i < n && intervals[i][1] < newInterval[0])
        res.add(intervals[i++]);

    // Merge overlapping intervals
    while (i < n && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    res.add(newInterval);

    // Add remaining
    while (i < n) res.add(intervals[i++]);
    return res.toArray(new int[res.size()][]);
}
```

---

## Variants 变形

| Variant | Key idea | Example |
|---|---|---|
| Merge intervals | Sort by start, expand end | LC 56 |
| Insert interval | 3-phase: before, merge, after | LC 57 |
| Detect overlap | Sort, check adjacent | LC 252 |
| Min rooms needed | Sort by start, min-heap of ends | LC 253 |
| Non-overlapping count | Greedy: keep earliest-ending | LC 435 |
| Remove covered | Sort by start desc end; track maxEnd | LC 1288 |
| Employee free time | Collect all intervals, merge, find gaps | LC 759 |

---

## Key Examples 关键例题

### Merge Intervals (LC 56)
```java
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    List<int[]> res = new ArrayList<>();
    for (int[] iv : intervals) {
        if (res.isEmpty() || res.get(res.size()-1)[1] < iv[0])
            res.add(iv);
        else
            res.get(res.size()-1)[1] = Math.max(res.get(res.size()-1)[1], iv[1]);
    }
    return res.toArray(new int[res.size()][]);
}
```

### Meeting Rooms II (LC 253)
```java
public int minMeetingRooms(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    PriorityQueue<Integer> ends = new PriorityQueue<>();
    for (int[] iv : intervals) {
        if (!ends.isEmpty() && ends.peek() <= iv[0]) ends.poll();
        ends.offer(iv[1]);
    }
    return ends.size();
}
```

### Non-overlapping Intervals (LC 435) — greedy: min removals
```java
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);  // sort by END
    int end = Integer.MIN_VALUE, removed = 0;
    for (int[] iv : intervals) {
        if (iv[0] >= end)
            end = iv[1];   // no overlap: keep this interval
        else
            removed++;     // overlap: remove (keep the one with earlier end)
    }
    return removed;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Sort by start for merging | Makes overlapping intervals adjacent |
| Sort by end for greedy removal | Keep earliest-ending intervals to leave max room |
| `ends.peek() <= iv[0]` | `<=` means meeting that ends exactly when next starts is NOT overlap |
| Min-heap tracks current rooms | Size of heap = rooms currently in use |
| Insert interval: 3 phases | 1) Strictly before, 2) Overlap (merge), 3) Strictly after |
| Overlap condition | `a.start < b.end && b.start < a.end` (open intervals) OR `a.start <= b.end` (sorted, closed) |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 252 Meeting Rooms |
| Medium | LC 56 Merge Intervals |
| Medium | LC 57 Insert Interval |
| Medium | LC 435 Non-overlapping Intervals |
| Medium | LC 253 Meeting Rooms II |
| Hard | LC 759 Employee Free Time |

**Order:** 252 → 56 → 57 → 435 → 253 → 759

---

## One-line Summary

```
Merge intervals = sort by start, then greedily expand the current merged end; min-heap tracks end times for scheduling.
合并区间 = 按起始排序，贪心地扩展当前合并端；最小堆跟踪结束时间用于调度。
```
