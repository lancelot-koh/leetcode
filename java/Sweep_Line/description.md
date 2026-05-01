# Sweep Line 扫描线

> **Core idea:** Convert interval/event problems into a sequence of discrete events sorted by position (or time). "Sweep" a line across these events, maintaining a running state.
> **核心思想：** 将区间/事件问题转化为按位置（或时间）排序的离散事件序列，用一条"扫描线"逐事件维护当前状态。
>
> Sweep Line = **Event generation** + **Sort** + **Process in order**.
> 扫描线 = **事件生成** + **排序** + **按序处理**。
>
> Complexity: O(n log n) for sort; O(n) sweep.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| maximum concurrent / overlapping | 最大并发数 / 最多重叠 |
| meeting rooms needed | 会议室数量 |
| available time slots | 可用时间段 |
| skyline problem | 天际线问题 |
| overlapping intervals count at point | 某点上重叠区间数 |
| events with start and end | 有起止的事件 |
| employee free time | 员工空闲时间 |

**Sweep Line vs Merge Intervals**

| Goal | Use |
|---|---|
| Merge overlapping intervals → fewer intervals | Merge Intervals (sort by start, greedy) |
| Count overlapping intervals at each point | Sweep Line (or Difference Array) |
| Find gaps between intervals | Sweep Line |
| Skyline (height changes) | Sweep Line + max-heap |

**Sweep Line vs Difference Array**

Both handle range updates. Use Sweep Line when: events have different priorities, or you need a heap/structure to process order. Use Difference Array when: all updates are +val/-val and you just need the final array.

---

## 2. Quick Decision Guide 快速判断

```
Min meeting rooms / max overlapping intervals?    → Pattern 1: Event counter
Find free time between intervals?                 → Pattern 2: Gap finding
Skyline / max height at each x?                  → Pattern 3: Skyline (heap-based)
Count active intervals at each query point?       → Pattern 4: Offline queries
Range updates → reconstruct final values?         → Pattern 5: Difference Array (see PrefixSum)
```

---

## 3. Patterns 模式

---

### Pattern 1 — Event Counter (Meeting Rooms) 事件计数（会议室）

**When:** find the maximum number of simultaneously overlapping intervals = minimum resources needed.
**适用：** 求同时重叠区间数的最大值 = 所需最少资源数。

**Approach A: Split into events 拆分事件**

```java
public int minMeetingRooms(int[][] intervals) {
    int n = intervals.length;
    int[] starts = new int[n], ends = new int[n];
    for (int i = 0; i < n; i++) {
        starts[i] = intervals[i][0];
        ends[i]   = intervals[i][1];
    }
    Arrays.sort(starts);
    Arrays.sort(ends);

    int rooms = 0, maxRooms = 0, j = 0;
    for (int i = 0; i < n; i++) {
        if (starts[i] < ends[j]) rooms++;     // new meeting starts before earliest end
        else                     { rooms--; j++; }  // recycle a room
        maxRooms = Math.max(maxRooms, rooms);
    }
    return maxRooms;
}
```

**Approach B: +1/-1 events 差分事件**

```java
List<int[]> events = new ArrayList<>();
for (int[] iv : intervals) {
    events.add(new int[]{iv[0], 1});   // start event: +1
    events.add(new int[]{iv[1], -1});  // end event:   -1
}
// Sort by time; at same time, process ends before starts (use second element)
events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

int cur = 0, max = 0;
for (int[] e : events) {
    cur += e[1];
    max = Math.max(max, cur);
}
return max;
```

**Key: tie-breaking at same time 同时刻的处理顺序**

- For "min rooms": at same time, process **end before start** (a freed room can be reused)
- For "max overlap": at same time, process **start before end** (overlap includes boundary)
The sort tie-break (`a[1] - b[1]`: -1 before +1) handles end-before-start.

**Variants 变形**

| Variant | Example |
|---|---|
| Meeting rooms II (min rooms) | LC 253 |
| Car pooling (capacity constraint) | LC 1094 |
| Max concurrent tasks | custom |
| Number of flowers in bloom at time t | LC 2251 |

---

### Pattern 2 — Gap Finding / Free Time 空隙查找 / 空闲时间

**When:** find intervals not covered by any given interval.
**适用：** 找所有给定区间未覆盖的空隙。

**Template 模板**

```java
// Flatten all intervals, sort by start, then find gaps
List<int[]> all = new ArrayList<>();
for (List<Interval> person : schedule) all.addAll(person);
all.sort((a, b) -> a[0] - b[0]);

List<int[]> result = new ArrayList<>();
int end = all.get(0)[1];

for (int i = 1; i < all.size(); i++) {
    int[] cur = all.get(i);
    if (cur[0] > end)
        result.add(new int[]{end, cur[0]});   // gap found
    end = Math.max(end, cur[1]);
}
return result;
```

**Variants 变形**

| Variant | Example |
|---|---|
| Employee free time | LC 759 |
| Insert interval → find uncovered | custom |

---

### Pattern 3 — Skyline (Height Changes) 天际线（高度变化）

**When:** given buildings as [left, right, height], find the skyline (outline of height changes).
**适用：** 给定建筑 [左, 右, 高]，求天际线轮廓。

**Key insight 核心原理**

Create two events per building: `(left, -height)` = start (negative = enter), `(right, height)` = end (positive = leave). Sort events. Use a max-heap of active heights. When the max height changes, record a skyline point.

```java
public List<List<Integer>> getSkyline(int[][] buildings) {
    List<int[]> events = new ArrayList<>();
    for (int[] b : buildings) {
        events.add(new int[]{b[0], -b[2]});   // start: negative height
        events.add(new int[]{b[1],  b[2]});   // end:   positive height
    }
    // Sort: same x → starts before ends (negative before positive), taller before shorter
    events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

    TreeMap<Integer, Integer> heightCount = new TreeMap<>();
    heightCount.put(0, 1);   // ground level always present
    int prevMax = 0;
    List<List<Integer>> result = new ArrayList<>();

    for (int[] e : events) {
        if (e[1] < 0) {  // building starts: add height
            heightCount.merge(-e[1], 1, Integer::sum);
        } else {         // building ends: remove height
            int h = e[1];
            if (heightCount.merge(h, -1, Integer::sum) == 0) heightCount.remove(h);
        }
        int curMax = heightCount.lastKey();
        if (curMax != prevMax) {
            result.add(List.of(e[0], curMax));
            prevMax = curMax;
        }
    }
    return result;
}
```

**Why TreeMap instead of heap? 为什么用TreeMap而非堆？**

A heap doesn't support efficient removal of arbitrary elements. TreeMap with a count supports O(log n) add and remove of any height.
堆不支持高效删除任意元素。带计数的TreeMap支持 O(log n) 添加和删除任意高度。

---

### Pattern 4 — Offline Query Processing 离线查询处理

**When:** multiple point queries ("how many intervals contain point x?"); process all queries together with the events.
**适用：** 多个点查询（"有多少区间包含x？"），将查询与事件一起排序处理。

```java
// Add query points as events too
events.add(new int[]{queryX, 0, queryIdx});  // type 0 = query
events.sort((a, b) -> a[0] - b[0]);

int active = 0;
int[] answers = new int[queries.length];
for (int[] e : events) {
    if      (e[1] == 1)  active++;            // interval start
    else if (e[1] == -1) active--;            // interval end
    else                 answers[e[2]] = active;  // query
}
```

---

## 4. Advanced Skills 进阶技能

### Skill 1 — Event Encoding 事件编码

Design events carefully:

```java
// General event: {time, type}
// type: -1 = end, 0 = query, 1 = start
// This sort order ensures: ends processed before starts at same time (or vice versa)
events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
```

### Skill 2 — Tie-breaking Matters 同时刻顺序很重要

At the same timestamp, the order you process events changes the answer:

| Rule | When to apply |
|---|---|
| End before start | "min rooms" — can reuse freed room immediately |
| Start before end | "max overlap" — count point as overlapping at boundary |
| Taller start before shorter start | Skyline — see taller building first |

### Skill 3 — TreeMap for Dynamic Max/Min 动态最大/最小值用TreeMap

When you need to: add heights, remove heights, always query current max:

```java
TreeMap<Integer, Integer> tm = new TreeMap<>();
tm.merge(height, 1, Integer::sum);    // add
if (tm.merge(height, -1, Integer::sum) == 0) tm.remove(height);  // remove
int curMax = tm.lastKey();            // O(log n)
```

### Skill 4 — Difference Array for Simple Range Updates 简单区间更新用差分数组

If events are just +1/-1 at fixed integer positions (not floating point), difference array is O(n) and simpler than sweep line. See `PrefixSum/desciption.md` Pattern 6.

---

## 5. Interview Script 面试话术

**English:**
> I'd use the sweep line technique. I create two events per interval — a start event and an end event — then sort all events by time. As I sweep through, I maintain a counter (or heap) of active intervals. The answer changes each time an event is processed. Tie-breaking at the same timestamp is critical: for minimum rooms, I process ends before starts so a freed room can be reused immediately.

**中文：**
> 我会用扫描线。每个区间生成两个事件（开始和结束），按时间排序后逐一处理，维护当前活跃区间数（或堆）。每次处理事件时更新答案。同时刻的处理顺序很关键：求最少会议室时，先处理结束事件，这样释放的房间可以立即复用。

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Event counter | 253, 1094, 2251 |
| 2. Free time / gaps | 759 |
| 3. Skyline | 218 |
| 4. Offline queries | 2251, custom |

**Recommended order:** 253 → 1094 → 759 → 218 → 2251

---

## 7. One-line Summary 一句话总结

```
Sweep Line = turn intervals into sorted events; process them left-to-right maintaining running state.
扫描线 = 把区间转为排序事件，从左到右处理，维护运行状态。
```
