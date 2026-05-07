# T2-8 — Merge Intervals 合并区间

> **Core idea:** Sort intervals by start time. Merge overlapping intervals by comparing each interval's start with the current merged end. O(n log n) dominated by sorting.
> **核心思想：** 按起始时间排序，将每个区间的起始与当前合并区间的结束比较，合并重叠部分。时间复杂度由排序主导O(n log n)。
>
> Complexity: O(n log n) time, O(n) space for output.
> Full reference: `Greedy/description.md`

---

## How it Works — Mental Model 算法原理

Sorting by start time clusters all potentially overlapping intervals together. After sorting, two intervals can only overlap if the next interval's start is at or before the current merged interval's end — you never need to look back further than the last merged interval. When you find an overlap, you extend the current interval's end to the maximum of the two ends (not just the incoming end, because the incoming interval might be fully contained). When there is no overlap, you start a fresh interval. Without sorting first, a late-starting interval could overlap an early one and you would need to check all previous intervals — O(n) per interval instead of O(1).

**Key invariant:** At every step, `result.get(result.size()-1)` holds a merged interval whose start is ≤ the start of every unprocessed interval (because we sorted). Any future overlap can only extend its end, never its start.

**Common mistake / gotcha:** When checking for an overlap, using strict `<` instead of `<=` for the end comparison: `result.last.end < interval.start` means touching intervals (e.g., `[1,3]` and `[3,5]`) would be treated as non-overlapping. Whether touching intervals merge depends on the problem definition — read the problem carefully.

---

## Step-by-Step Trace 执行步骤示意

Example: `merge([[1,3],[2,6],[8,10],[15,18]])`
```
After sort: [[1,3],[2,6],[8,10],[15,18]]
Step 1: result=[], process [1,3]  → result empty → add → result=[[1,3]]
Step 2: process [2,6]  → 2 ≤ 3 (overlap) → max(3,6)=6 → result=[[1,6]]
Step 3: process [8,10] → 8 > 6 (no overlap) → add new → result=[[1,6],[8,10]]
Step 4: process [15,18]→ 15 > 10 (no overlap)→ add new → result=[[1,6],[8,10],[15,18]]
```

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
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);   // sort by start: ensures overlapping intervals are adjacent
List<int[]> merged = new ArrayList<>();

for (int[] interval : intervals) {
    if (merged.isEmpty() || merged.get(merged.size()-1)[1] < interval[0]) {
        merged.add(interval);                     // no overlap: start a fresh merged interval
    } else {
        // Overlap: extend the end of the current merged interval
        // Use max because interval might be fully contained inside current (e.g., [1,10] and [2,5])
        merged.get(merged.size()-1)[1] =
            Math.max(merged.get(merged.size()-1)[1], interval[1]);
    }
}
return merged.toArray(new int[merged.size()][]);
```

### Detect any overlap (meeting rooms I)

```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
for (int i = 1; i < intervals.length; i++) {
    if (intervals[i][0] < intervals[i-1][1]) { return false; }  // overlap
}
return true;
```

### Minimum meeting rooms (min-heap)

```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);   // sort by start to process meetings in order
PriorityQueue<Integer> ends = new PriorityQueue<>();  // min-heap of end times of active meetings

for (int[] interval : intervals) {
    // If the earliest-ending meeting is done by the time this one starts, reuse that room
    if (!ends.isEmpty() && ends.peek() <= interval[0]) {
        ends.poll();           // recycle room: that meeting ended (or ended exactly as this starts)
    }
    ends.offer(interval[1]);   // occupy a room; record when this meeting ends
}
return ends.size();            // each element in heap = one room currently in use
```

### Insert interval into sorted non-overlapping list

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> res = new ArrayList<>();
    int i = 0, n = intervals.length;

    // Add all intervals before newInterval
    while (i < n && intervals[i][1] < newInterval[0]) {
        res.add(intervals[i++]);
    }

    // Merge overlapping intervals
    while (i < n && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    res.add(newInterval);

    // Add remaining
    while (i < n) { res.add(intervals[i++]); }
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
        if (res.isEmpty() || res.get(res.size()-1)[1] < iv[0]) {
            res.add(iv);
        } else {
            res.get(res.size()-1)[1] = Math.max(res.get(res.size()-1)[1], iv[1]);
        }
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
        if (!ends.isEmpty() && ends.peek() <= iv[0]) { ends.poll(); }
        ends.offer(iv[1]);
    }
    return ends.size();
}
```

### Non-overlapping Intervals (LC 435) — greedy: min removals
```java
public int eraseOverlapIntervals(int[][] intervals) {
    // Sort by END time: greedily keep the interval that ends earliest,
    // leaving the most room for future intervals (activity selection greedy)
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    int end = Integer.MIN_VALUE, removed = 0;
    for (int[] iv : intervals) {
        if (iv[0] >= end) {
            end = iv[1];   // no overlap: keep this interval, advance the end boundary
        } else {
            removed++;     // overlap: discard this one (it ends later, so keeping it hurts more)
        }
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
