# 🗓️ MEETING ROOM SERIES - Complete Resource Allocation Guide

**Master interval scheduling, resource management, and constraint satisfaction problems**

Step1
资源是什么？

Room
Server
CPU
Machine
Parking Slot

--------------------------------

Step2
资源何时释放？

endTime

--------------------------------

Step3
需要知道什么？

是否重叠？
=> Merge Interval

最多同时占用？
=> Sweep Line

最少资源数？
=> Min Heap(endTime)

资源分配过程？
=> Simulation + Heap

--------------------------------

Step4
释放规则是什么？

start >= end ?

还是别的条件？
---


1. 只是判断/合并区间
=> Sort + start/end comparison

2. 需要知道“谁最早释放资源”
=> MinHeap(endTime)

3. 需要知道“最多同时占用多少资源”
=> Sweep Line

4. 需要真实分配资源编号
=> Two Heaps
   available resources
   occupied resources

5. 有依赖关系
=> Topological Sort

6. 有冷却时间/优先级
=> Heap + Simulation



## 📖 TABLE OF CONTENTS

1. **Core Concept & Mental Model**
2. **Complete Problem Series (1-20)**
3. **Solution Patterns & Templates**
4. **Related Skills & Techniques**
5. **Interview Strategy**

---

# 🧠 CORE CONCEPT & MENTAL MODEL

## The Unified Framework

```
ALL Resource Allocation Problems Share:
┌─────────────────────────────────────────────┐
│  Limited Resource                           │
│  + Time Dimension [start, end]              │
│  + Allocation Strategy                      │
└─────────────────────────────────────────────┘

Examples:
- Meeting Rooms      (resource = room)
- CPU Scheduling     (resource = processor)
- Parking Lots       (resource = parking spot)
- Hospital Beds      (resource = bed)
- Server Assignment  (resource = server)
- Flight Gates       (resource = gate)
```

## Key Mental Model

```
Google's L5/L6 Insight:

NOT: "Meeting Rooms II uses Heap"

BUT: "Resource Allocation uses:
      - Heap when: find earliest-releasing resource
      - Sweep Line when: track peak concurrent usage
      - Topo Sort when: dependencies exist
      - Greedy when: maximize non-overlapping"
```

---

# 📋 COMPLETE PROBLEM SERIES (20 Problems)

## LEVEL 0: INTERVAL FUNDAMENTALS

### Problem 1: Merge Intervals (LeetCode 56)

**Problem:** Merge all overlapping intervals

#### Thinking Process

```
Step 1: UNDERSTAND THE PROBLEM
- We have unordered intervals
- Some intervals overlap, some don't
- We need to combine overlapping ones into single intervals

Step 2: KEY INSIGHT - ORDER MATTERS
Question: "When can we merge two intervals?"
Answer: If they overlap or touch: [1,3] and [2,5] → merge to [1,5]

But to find overlaps efficiently, we need ORDER.

Step 3: HOW TO DETECT OVERLAP EFFICIENTLY?
Without sorting: Compare every pair → O(n²)
With sorting by start: Compare only adjacent pairs → O(n)

Why? If we sort by start time:
[1,3], [2,5], [8,10]
Then [1,3] can only overlap with [2,5] (next one)
NOT with [8,10] (they're too far apart)

Step 4: THE ALGORITHM
1. Sort by start time
2. Iterate through sorted intervals
3. Check if current overlaps with last merged:
   - Current.start <= Last.end? 
   - YES → Merge: extend last.end to max(last.end, current.end)
   - NO → Add current as new interval
```

#### Code Implementation

```java
public int[][] merge(int[][] intervals) {
    if (intervals.length == 0) return new int[0][0];
    
    // Sort by start time: now we can process left-to-right
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    List<int[]> merged = new ArrayList<>();
    merged.add(intervals[0]);  // First interval always goes in
    
    for (int i = 1; i < intervals.length; i++) {
        int[] current = intervals[i];
        int[] last = merged.get(merged.size() - 1);  // Last merged interval
        
        // Check overlap: does current start before last ends?
        if (current[0] <= last[1]) {
            // YES: Merge by extending the end of last interval
            last[1] = Math.max(last[1], current[1]);
        } else {
            // NO: Add current as new separate interval
            merged.add(current);
        }
    }
    
    return merged.toArray(new int[0][]);
}
```

**Why This Works:**
- Sorting by start ensures we never skip potential merges
- Checking only adjacent intervals in sorted order is O(n)
- Greedy approach: merge immediately when possible

**Complexity:** O(n log n) for sorting, O(n) for merging

---

### Problem 2: Insert Interval (LeetCode 57)

**Problem:** Insert new interval into sorted, non-overlapping intervals

#### Thinking Process

```
Step 1: KEY DIFFERENCE FROM PROBLEM 1
- Intervals given are already sorted and non-overlapping
- We can't sort (O(n log n))
- We need O(n) solution
- This means: single pass through intervals, intelligent processing

Step 2: THREE-PHASE THINKING
When inserting newInterval into sorted list, each existing interval falls into:

Phase 1 - BEFORE (No overlap, doesn't affect new interval)
  existing.end < newInterval.start
  → Just add existing as-is, new doesn't change
  Example: [1,2] and newInterval=[5,8]
  → Add [1,2], then handle new

Phase 2 - OVERLAP (Existing overlaps with new)
  existing overlaps with newInterval
  → Must merge: expand newInterval to cover both
  Example: [5,7] and newInterval=[3,6]
  → Merge to [3,7]

Phase 3 - AFTER (No overlap, existing comes after)
  existing.start > newInterval.end
  → All remaining intervals come after new
  → Add the merged new interval, then add remaining ones

Step 3: THE TRICK
Instead of deciding when to insert new:
- Let new interval "grow" by merging with overlapping intervals
- Once no more overlap (existing.start > newEnd):
  - Insert the grown new interval
  - Then add all remaining intervals
```

#### Code Implementation

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int newStart = newInterval[0];
    int newEnd = newInterval[1];
    
    for (int[] interval : intervals) {
        int start = interval[0];
        int end = interval[1];
        
        // PHASE 1: Existing interval is completely before new interval
        // No overlap, no change to new interval
        if (end < newStart) {
            result.add(interval);  // Just add existing
        }
        // PHASE 3: Existing interval is completely after new interval
        // No more overlap possible (list is sorted)
        else if (start > newEnd) {
            result.add(new int[]{newStart, newEnd});  // Add grown new interval
            newStart = start;  // Now process this existing as the "new" one
            newEnd = end;
        }
        // PHASE 2: Existing interval overlaps with new interval
        // Merge by expanding new interval boundaries
        else {
            newStart = Math.min(newStart, start);
            newEnd = Math.max(newEnd, end);
        }
    }
    
    // Don't forget the final merged interval!
    result.add(new int[]{newStart, newEnd});
    
    return result.toArray(new int[0][]);
}
```

**Why This Works:**
- Because intervals are sorted, we only need one pass
- We grow the new interval as we find overlaps
- Once we hit an interval after new, we know merging is done
- Single pass = O(n) instead of sorting which is O(n log n)

**Complexity:** O(n)

---

### Problem 3: Erase Overlapping Intervals (LeetCode 435)

**Problem:** Minimum intervals to remove for non-overlapping set

#### Thinking Process

```
Step 1: REFRAME THE PROBLEM
"Minimum to remove" = "Maximum to keep"

So instead of asking "which ones to delete?"
Ask "which ones to keep such that they don't overlap?"

Maximum non-overlapping intervals is CLASSIC GREEDY problem.

Step 2: GREEDY CHOICE - WHICH ONE TO KEEP?
When two intervals overlap, which should we keep?

Example: [1,10] and [2,3]
Keep [2,3]: leaves more time for future intervals
Keep [1,10]: blocks more future intervals

The key insight: 
"The interval that ENDS EARLIEST leaves most room for future choices"

This is the EXCHANGE ARGUMENT:
- Any other choice either ends later (blocks more) 
- Or creates a different trade-off that's worse overall

Step 3: WHY SORT BY END TIME?
Sort by start? 
  [1,10], [2,3] → Pick [1,10] → wrong!
  
Sort by end?
  [2,3], [1,10] → Pick [2,3] → correct!

After picking [2,3], we have [1,10] in unsorted order but
since we process by end time, we see [2,3] first and pick it.

Step 4: THE ALGORITHM
1. Sort by END time (earliest end first)
2. Greedily pick earliest-ending interval
3. Skip any interval that overlaps with picked one
4. Count skipped intervals
```

#### Code Implementation

```java
public int eraseOverlapIntervals(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Sort by END time (not start time!)
    // This enables greedy: pick earliest-ending
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    
    int count = 0;  // Intervals to remove
    int prevEnd = intervals[0][1];  // End of last kept interval
    
    for (int i = 1; i < intervals.length; i++) {
        // Check overlap: does current start before previous ends?
        if (intervals[i][0] < prevEnd) {
            // YES: This interval overlaps, we must remove it
            count++;
            // Don't update prevEnd! Keep the earliest-ending
        } else {
            // NO: This interval doesn't overlap, keep it
            prevEnd = intervals[i][1];
        }
    }
    
    return count;
}
```

**Why Greedy Works Here:**
Exchange Argument: Suppose optimal solution picks interval B at position i.
If we pick interval A (earlier-ending) instead:
- A ends before or at same time as B
- All intervals after A that don't overlap with B also don't overlap with A
- So A creates a valid solution too (or better!)

**Complexity:** O(n log n) for sorting

---

## LEVEL 1: MEETING ROOM CORE SERIES

### Problem 4: Meeting Rooms I (LeetCode 252)

**Problem:** Can person attend all meetings?

#### Detailed Understanding

```
REAL-WORLD SCENARIO:
You're a job interviewer with 5 meeting slots today.
You need to decide: "Can I attend ALL interviews without conflicts?"

Example: [[0,30], [5,10], [15,20]]
Meeting 1: 0-30   (0 minutes to 30 minutes)
Meeting 2: 5-10   (5 minutes to 10 minutes) ← overlaps with Meeting 1!
Meeting 3: 15-20  (15 minutes to 20 minutes)

Can you attend all? NO
Why? When Meeting 1 is happening (0-30), Meeting 2 (5-10) also happens
```

#### Thinking Process

```
Step 1: UNDERSTAND "CAN ATTEND"
Person can attend all meetings if:
- No two meetings overlap
- If one ends at time T, next can start at time T (back-to-back OK)
- Or: if one ends at time T, next must start strictly AFTER T

The definition matters! In LeetCode 252:
- [1,5] and [5,6]: DO NOT overlap (one ends, next starts)
- [1,5] and [4,6]: DO overlap (overlap from 4-5)

Step 2: BRUTE FORCE vs OPTIMIZED
Brute Force: Check every pair O(n²)
  for i in 0..n:
    for j in i+1..n:
      check if meetings[i] overlaps meetings[j]
  Problem: slow for large inputs

Optimized: Sort + single pass O(n log n)
  Why sort helps?
  If meetings are in time order [1,5], [3,6], [8,10]:
  - We only check adjacent meetings [1,5] vs [3,6]
  - We don't need to check [1,5] vs [8,10] (obviously no overlap)

Step 3: THE SORTING TRICK
Sort by START time: [1,5], [3,6], [8,10]
Now: if meetings[i] and meetings[i+1] don't overlap,
     and meetings[i+1] and meetings[i+2] don't overlap,
     then meetings[i] and meetings[i+2] DON'T overlap
     (because i < i+1 < i+2 by start time)

Step 4: OVERLAP CONDITION EXPLAINED
Two intervals [a,b] and [c,d] where a ≤ c (a comes first):
They overlap if: c < b (second starts before first ends)

Visual:
a ----b (meeting 1)
  c ----d (meeting 2)
  ↑ c < b? YES → overlap!

a ----b (meeting 1)
     c ----d (meeting 2)
     ↑ c < b? NO → no overlap!

a ----b (meeting 1)
     c ----d (meeting 2, c = b)
     ↑ c < b? NO → no overlap! (back-to-back is OK)
```

#### Code Implementation

```java
public boolean canAttendMeetings(int[][] intervals) {
    if (intervals.length == 0) return true;
    
    // Sort by start time
    // Now we can check overlaps by scanning left-to-right
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    for (int i = 1; i < intervals.length; i++) {
        // Check if current meeting starts before previous one ends
        if (intervals[i][0] < intervals[i - 1][1]) {
            // Found overlap: cannot attend both
            return false;
        }
    }
    
    // No overlaps found: can attend all
    return true;
}
```

**Why This Works:**
- After sorting by start time, meetings are in order
- Only need to check adjacent meetings for overlap
- If [meeting A, meeting B] don't overlap and [B, C] don't overlap,
  then [A, C] automatically don't overlap (A ends before B starts, 
  B ends before C starts)

**Complexity:** O(n log n) for sorting, O(n) for checking

---

### Problem 5: Meeting Rooms II (LeetCode 253)

**Problem:** Minimum meeting rooms needed

#### Detailed Understanding

```
REAL-WORLD SCENARIO:
You're running a conference with flexible room allocation.
Meetings: [0-30], [5-10], [15-20]

Timeline:
Time 0-5:   1 meeting (A)           → 1 room
Time 5-10:  2 meetings (A, B)       → 2 rooms ← PEAK!
Time 10-15: 1 meeting (A)           → 1 room
Time 15-20: 2 meetings (A, C)       → 2 rooms ← PEAK again
Time 20-30: 1 meeting (A)           → 1 room

Answer: Minimum 2 rooms needed
```

#### Thinking Process - Two Approaches

```
COMPARISON: When to use what approach?

APPROACH A: Heap-based (Simulation)
- Keep track of each room's end time
- For each meeting: Does any room free up before this starts?
- If yes, reuse; if no, allocate new
- Pros: Intuitive, easy to debug
- Cons: More code, O(n log n) with heap

Example thought process:
"Meeting 1 (0-30) needs room A
 Meeting 2 (5-10) starts at 5, room A busy until 30, need room B
 Meeting 3 (15-20) starts at 15, room B free at 10 ≤ 15, reuse room B"

APPROACH B: Two Pointers (Elegant)
- Separate problem into two sorted arrays: starts and ends
- Process chronologically: when does congestion peak?
- Don't track which room, just COUNT
- Pros: O(n) post-sorting, simpler logic
- Cons: Less intuitive at first

Key insight:
Instead of tracking rooms, track meetings!
"How many meetings are active at each moment?"

Step-by-step:
1. Extract all start times: [0, 5, 15]
2. Extract all end times: [10, 20, 30]
3. Sort both
4. Sweep: for each start, has any meeting ended?

Step 1: REFRAME THE QUESTION
"How many rooms do we need?"
= "What's the maximum concurrent meetings?"
= "At what peak time are most meetings happening?"

Step 2: KEY INSIGHT - ROOM REUSE
When meeting A ends at time T, a room becomes available.
Meeting B starting at time T can reuse the room.
So we need to count: at any moment, how many concurrent meetings?

NOT: "How many different meetings are there?" (that's just n)
BUT: "How many at the same time?" (that's the peak)

Step 3: TWO POINTERS ALGORITHM EXPLAINED

Intuition: Imagine a timeline
- When meeting STARTS: +1 active meeting
- When meeting ENDS: -1 active meeting
- Maximum value during sweep = answer

Process:
```
Starts: [0, 5, 15]     (meetings begin)
Ends:   [10, 20, 30]   (meetings finish)

Sort both independently ✓

Sweep left to right:
Position 0: start=0, end=10
  0 < 10? YES → this start comes before this end
  Active++  → active = 1
  Move start pointer

Position 1: start=5, end=10
  5 < 10? YES → this start comes before this end
  Active++  → active = 2, max = 2
  Move start pointer

Position 2: start=15, end=10
  15 < 10? NO → this start is after this end
  Active--  → active = 1
  Move end pointer

Position 3: start=15, end=20
  15 < 20? YES → this start comes before this end
  Active++  → active = 2, max = 2
  Move start pointer

Continue until both pointers exhausted
```

Result: max = 2 ✓

Step 4: WHY TWO POINTERS WORKS
The algorithm answers: "At each moment, how many meetings active?"
- If end <= start: someone freed a room, don't need new one
- If start < end: new person arrives before room frees, need new room
- Maximum rooms needed = maximum concurrent value

Step 5: COMPARISON TABLE
```
                Heap              Two Pointers
Time            O(n log n)        O(n log n) sort + O(n) sweep
Space           O(n)              O(n)
Code Length     ~20 lines         ~15 lines
Intuition       Room mgmt         Event counting
When to use     Track rooms       Count only
```
```

#### Code Implementation (Two Pointers)

```java
public int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Separate starts and ends
    int[] starts = new int[intervals.length];
    int[] ends = new int[intervals.length];
    
    for (int i = 0; i < intervals.length; i++) {
        starts[i] = intervals[i][0];
        ends[i] = intervals[i][1];
    }
    
    // Sort each independently
    Arrays.sort(starts);  // [0, 5, 10, 15] ...
    Arrays.sort(ends);    // [5, 6, 10, 11] ...
    
    int rooms = 0;        // Current rooms in use
    int endIdx = 0;       // Pointer to next possible room to free
    
    // Process each meeting's start time
    for (int start : starts) {
        // Check: Can we reuse a room?
        // A room is reusable if it freed BEFORE this meeting starts
        if (endIdx < ends.length && ends[endIdx] <= start) {
            // YES: This room ends before/at new meeting start
            endIdx++;  // This room is now available again
        } else {
            // NO: Need a new room
            rooms++;
        }
    }
    
    return rooms;
}
```

**Example Walkthrough:**
```
Meetings: [[0,30], [5,10], [15,20]]
Starts:   [0, 5, 15]
Ends:     [10, 20, 30]

Process start=0:
  ends[0]=10 > 0? YES → Need new room
  rooms=1

Process start=5:
  ends[0]=10 > 5? YES → Need new room
  rooms=2

Process start=15:
  ends[0]=10 <= 15? YES → Reuse room (endIdx++)
  ends[1]=20 > 15? YES → Need new room
  rooms=2 (no change)

Answer: 2 rooms needed ✓
```

**Why Two Pointers is Clever:**
- We don't track individual rooms, just the count
- Sorting separately lets us ask: "At this start time, 
  has any room freed?"
- Moving endIdx ensures we don't double-count freed rooms

**Complexity:** O(n log n) for sorting, O(n) for scanning

---

### Problem 6: Meeting Rooms III (LeetCode 2402)

**Problem:** Which room is used most?

#### Detailed Understanding

```
REAL-WORLD SCENARIO:
A company has 3 meeting rooms: Room 0, Room 1, Room 2
Meetings scheduled: [1,3], [2,5], [4,8], [5,10]

Challenge: Some meeting start times might not match room availability!
- Meeting 1 at time 2, but all rooms busy? Delay it to first available!
- Track which room gets used most across all rescheduled meetings

Process:
Meeting [1,3]:  Room 0 free at 0, use it. Now room 0 free at 3
Meeting [2,5]:  Room 0 free at 3 > 2, need another. Room 1 free at 0.
                Use Room 1. Now room 1 free at 5
Meeting [4,8]:  Room 0 free at 3 ≤ 4? YES. Use Room 0. Now Room 0 free at 8
                (But check others too - we want earliest)
...

Track:
Room 0: 2 meetings
Room 1: 1 meeting
Room 2: ...

Answer: Room 0 (most used)
```

#### Thinking Process - Key Differences

```
PROBLEM 5 vs PROBLEM 6 COMPARISON:

Problem 5: "Minimum rooms?"
- Question: Count (how many rooms)
- Assumption: Rooms are infinite (but what's minimum?)
- Constraint: None (any room works)
- Strategy: Track concurrent count

Problem 6: "Which room most used?"
- Question: Identification (which room, and count usage)
- Assumption: Exactly n rooms (0 to n-1)
- Constraint: Meetings scheduled might NOT start on time!
  - If room isn't free, meeting must wait
- Strategy: SIMULATE room assignments

Step 1: WHAT'S DIFFERENT FROM PROBLEM 5?
Problem 5: Count rooms needed (resource counting)
Problem 6: ASSIGN specific rooms, track usage

We need to:
1. Know which room is available when (and how long it's available for)
2. Process meetings in START TIME order
3. Count each room's usage
4. Find room with highest count

Step 2: WHY SIMULATION NEEDED?
Compare:
Problem 5: Meeting [4,8]. Check: is any room free by time 4? YES/NO
Problem 6: Meeting [4,8]. Room 0 free at 5, Room 1 free at 3, Room 2 free at 4
           Choose Room 2 (frees earliest)
           If you pick wrong room, tracking fails!

So we must:
- Track each room's actual free time
- Assign dynamically as we process meetings
- Count assignments per room

Step 3: HOW TO CHOOSE BEST ROOM?
Greedy choice: Use room that frees EARLIEST
Why? Minimizes delays, spreads meetings naturally

Rule: "Always use the room that becomes free first"
Tie-breaker: If two rooms free at same time, pick lower room number

This screams PRIORITY QUEUE (min heap) because:
- Need to find "earliest free" repeatedly
- Heap gives O(log n) retrieval
- Process meetings sequentially = O(m) iterations
- Total: O(m log n) where m = meetings, n = rooms

Step 4: THE ALGORITHM
1. Sort meetings by start time
2. Create heap of (free_time, room_id) - all start at (0, 0..n-1)
3. For each meeting:
   - Get room with earliest free_time from heap
   - Actual meeting start = max(requested_start, room_free_time)
   - Actual meeting end = actual_start + duration
   - Increment room's count
   - Put room back with new free_time

Step 5: EXAMPLE
Meetings: [[0,30], [5,10], [15,20]]
Rooms: 2 (room 0, room 1)

Process [0,30]:
  Heap: [(0,0), (0,1)]
  Pop (0,0) → Room 0 starts at 0, ends at 30
  Count[0]++
  Heap: [(0,1), (30,0)]

Process [5,10]:
  Heap: [(0,1), (30,0)]
  Pop (0,1) → Room 1 free at 0, meeting at 5, ends at 10
  Count[1]++
  Heap: [(10,1), (30,0)]

Process [15,20]:
  Heap: [(10,1), (30,0)]
  Pop (10,1) → Room 1 free at 10, meeting at 15, ends at 25
  Count[1]++
  Heap: [(25,1), (30,0)]

Most used: Room 1 (count=2)
```

#### Code Implementation

```java
public int mostBooked(int n, long[][] meetings) {
    // Sort meetings by start time
    Arrays.sort(meetings, (a, b) -> Long.compare(a[0], b[0]));
    
    // Min heap: (free_time, room_number)
    // Room with earliest free_time appears first
    PriorityQueue<long[]> available = new PriorityQueue<>(
        (a, b) -> {
            if (a[0] != b[0]) return Long.compare(a[0], b[0]);
            return Long.compare(a[1], b[1]);  // Tie-break by room number
        }
    );
    
    // Initially all rooms are free at time 0
    for (int i = 0; i < n; i++) {
        available.offer(new long[]{0L, i});
    }
    
    // Count how many meetings each room hosts
    long[] roomCount = new long[n];
    
    // Process meetings in order
    for (long[] meeting : meetings) {
        long start = meeting[0];
        long end = meeting[1];
        long duration = end - start;
        
        // Get the room that becomes free earliest
        long[] earliest = available.poll();
        long freeTime = earliest[0];
        long room = earliest[1];
        
        // This room hosts this meeting
        roomCount[(int)room]++;
        
        // Calculate when this room will be free after this meeting
        // If room is free after requested start: room_free = end
        // If room is free before requested start: room_free = start + duration
        long roomFreeAfter = Math.max(freeTime, start) + duration;
        
        // Put room back into heap with new free time
        available.offer(new long[]{roomFreeAfter, room});
    }
    
    // Find room with most meetings
    long maxCount = 0;
    int mostUsedRoom = 0;
    for (int i = 0; i < n; i++) {
        if (roomCount[i] > maxCount) {
            maxCount = roomCount[i];
            mostUsedRoom = i;
        }
    }
    
    return mostUsedRoom;
}
```

**Why This Works:**
- Sorting by start ensures meetings are processed chronologically
- Heap always gives us the earliest-available room (greedy choice)
- Simulation naturally handles delayed meetings
- Tracking count is straightforward

**Complexity:** O(m log n) where m = meetings, n = rooms

---

## LEVEL 2: SWEEP LINE TECHNIQUE

### Problem 7: Maximum Concurrent Meetings

**Problem:** Most meetings happening at same time?

#### Thinking Process

```
Step 1: REFRAME: "MAX CONCURRENT" = "MAX OVERLAPPING"
This is the same as Problem 5 answer, but asked differently.

Problem 5: "How many rooms minimum?" = Max overlapping at any point
Problem 7: "What's the max concurrent?" = Same answer!

But different approach: SWEEP LINE

Step 2: WHY SWEEP LINE?
Problem 5 uses Two Pointers approach.
Problem 7 teaches SWEEP LINE approach (better for some variants).

Key insight: Convert interval problem to event problem.

Step 3: INTERVAL → EVENT TRANSFORMATION
Instead of thinking about intervals:
[1, 5], [2, 6], [4, 7]

Think about events:
Time 1: +1 meeting starts
Time 2: +1 meeting starts
Time 4: +1 meeting starts
Time 5: -1 meeting ends
Time 6: -1 meeting ends
Time 7: -1 meeting ends

Step 4: SWEEP LINE ALGORITHM
1. Convert each interval [start, end] into two events:
   - (start, +1) : meeting starts
   - (end, -1) : meeting ends

2. Sort all events by time
   - If same time: ends BEFORE starts (end = -1, start = +1)
   - Why? [0,5] ends and [5,7] starts: they don't overlap

3. Sweep through events:
   - Keep running count of concurrent meetings
   - Track maximum seen

Think of sweeping a vertical line through time:
At each point in time, count = how many meetings are active

Step 5: IMPLEMENTATION DETAIL
When same time, process END FIRST (-1 first):
Because [0,5] and [5,7]: at time 5, first one ends, then second starts
So they're not concurrent.

Actually, need to check problem: does [0,5] and [5,6] count as overlap?
If yes: process START first (+1 before -1)
If no: process END first (-1 before +1)

Most problems treat end=5, start=5 as NO overlap.
```

#### Code Implementation

```java
public int maxConcurrentMeetings(int[][] intervals) {
    // Convert intervals to events
    List<int[]> events = new ArrayList<>();
    
    for (int[] interval : intervals) {
        int start = interval[0];
        int end = interval[1];
        
        // +1 when meeting starts
        events.add(new int[]{start, 1});
        
        // -1 when meeting ends
        events.add(new int[]{end, -1});
    }
    
    // Sort events by time
    // If same time: process ENDS first (-1 < +1)
    // This ensures [0,5] and [5,7] don't count as concurrent
    events.sort((a, b) -> {
        if (a[0] != b[0]) return a[0] - b[0];  // By time
        return a[1] - b[1];  // By type (-1 before +1)
    });
    
    // Sweep through events, tracking concurrent count
    int maxConcurrent = 0;
    int current = 0;
    
    for (int[] event : events) {
        // Add the delta (either +1 or -1)
        current += event[1];
        
        // Update max
        maxConcurrent = Math.max(maxConcurrent, current);
    }
    
    return maxConcurrent;
}
```

**Example Walkthrough:**
```
Intervals: [0,30], [5,10], [15,20]

Events (after conversion):
(0, +1), (5, +1), (10, -1), (15, +1), (20, -1), (30, -1)

Sweep:
Time 0: current = 0 + 1 = 1, max = 1
Time 5: current = 1 + 1 = 2, max = 2
Time 10: current = 2 - 1 = 1, max = 2
Time 15: current = 1 + 1 = 2, max = 2
Time 20: current = 2 - 1 = 1, max = 2
Time 30: current = 1 - 1 = 0, max = 2

Answer: 2 ✓
```

**Why This Works:**
- Events represent "state changes" in time
- Current count = how many meetings are active
- Maximum count = minimum rooms needed
- O(n log n) for sorting events

**When to Use Sweep Line:**
- Track "maximum something" at any point in time
- Track "busy time" vs "free time"
- Convert complex intervals to simple events
- Common for: Peak traffic, peak CPU, peak concurrent users

**Complexity:** O(n log n) for sorting, O(n) for sweeping

---

### Problem 8: Number of Airplanes in the Sky (LintCode 391)

**Same pattern as Problem 7**

---

### Problem 9: My Calendar III (LeetCode 732)

**Problem:** Maximum overlaps at any point

```java
public int maxEvents(int[][] bookings) {
    Map<Integer, Integer> events = new TreeMap<>();
    
    for (int[] booking : bookings) {
        int start = booking[0];
        int end = booking[1];
        
        events.put(start, events.getOrDefault(start, 0) + 1);
        events.put(end + 1, events.getOrDefault(end + 1, 0) - 1);
    }
    
    int maxOverlap = 0;
    int current = 0;
    
    for (int delta : events.values()) {
        current += delta;
        maxOverlap = Math.max(maxOverlap, current);
    }
    
    return maxOverlap;
}
```

**Pattern:** "Max overlap/concurrent" → Sweep Line

---

## LEVEL 3: FREE TIME & ADVANCED

### Problem 10: Employee Free Time (LeetCode 759)

**Problem:** Find all common free time slots

#### Thinking Process

```
Step 1: UNDERSTAND "COMMON FREE TIME"
Multiple employees, each with busy schedule (intervals).
Find times when ALL employees are free (no one is busy).

Step 2: KEY INSIGHT - GAPS BETWEEN BUSY TIMES
If we merge all busy times across all employees:
- Merged intervals = times when at least one person is busy
- Gaps between merged intervals = times when NO ONE is busy
- Those gaps = COMMON FREE TIME

Step 3: ALGORITHM
1. Collect all intervals from all employees
2. Merge overlapping intervals (like Problem 1)
3. Find gaps between merged intervals
4. Those gaps are the free times

Why does this work?
- If merged interval is [0,10], [15,20]
- Gap is [10,15]
- At any time in [10,15], what's the status?
  - Person A: not busy in [0,10], so free after 10
  - Person B: not busy in [0,10], so free after 10
  - Person A: busy starting 15, so free until 15
  - Person B: busy starting 15, so free until 15
  - So in [10,15], everyone is free!
```

#### Code Implementation

```java
public List<int[]> employeeFreeTime(List<List<int[]>> schedule) {
    // Step 1: Collect all intervals
    List<int[]> allIntervals = new ArrayList<>();
    for (List<int[]> employeeSchedule : schedule) {
        allIntervals.addAll(employeeSchedule);
    }
    
    // Step 2: Sort by start time
    Collections.sort(allIntervals, (a, b) -> a[0] - b[0]);
    
    // Step 3: Merge all intervals (from Problem 1)
    List<int[]> merged = new ArrayList<>();
    merged.add(allIntervals.get(0));
    
    for (int i = 1; i < allIntervals.size(); i++) {
        int[] current = allIntervals.get(i);
        int[] last = merged.get(merged.size() - 1);
        
        if (current[0] <= last[1]) {
            last[1] = Math.max(last[1], current[1]);
        } else {
            merged.add(current);
        }
    }
    
    // Step 4: Find gaps between merged intervals
    List<int[]> freeTime = new ArrayList<>();
    for (int i = 1; i < merged.size(); i++) {
        // Gap is from end of interval i-1 to start of interval i
        freeTime.add(new int[]{merged.get(i - 1)[1], merged.get(i)[0]});
    }
    
    return freeTime;
}
```

**Why This Works:**
- Merging combines all busy times
- Gaps automatically represent free times
- Free time = no one is busy = correct answer

**Complexity:** O(n log n) for sorting, O(n) for merging

---

### Problem 11: Task Scheduler (LeetCode 621)

**Problem:** Schedule tasks with cooldown period (n time units between same tasks)

#### Detailed Understanding

```
REAL-WORLD SCENARIO:
You're a CPU scheduler. Tasks: [A, A, A, B, B, C], cooldown n=2
- Task A appears 3 times
- Task B appears 2 times  
- Task C appears 1 time
- Between two A's, at least 2 time units must pass (can run other tasks)

Constraint: Can't do A twice in a row, must have 2 units between A's

Example schedule:
Time 0: A (do first A)
Time 1: B (do B, spacing out A)
Time 2: C (do C, spacing out A)
Time 3: A (now we can do second A, cooldown satisfied)
Time 4: B (do B)
Time 5: A (do final A)

Total time: 6

Compare with no cooldown: 6 tasks → 6 time units
With cooldown: might need idle time!

Harder example: [A, A, A, A, A, B], n=2
Time 0: A
Time 1: B (must insert something, run B)
Time 2: ❌ (can't run A yet, must wait 1 more)
Time 3: A
Time 4: idle (or other work)
Time 5: A (but only B left, run B)
Total time: 5 + idle...

Key: Frequent task (A appears most) drives the schedule!
```

#### Thinking Process - The Insight

```
Step 1: UNDERSTAND THE CONSTRAINT
Cooldown period means:
- If I do task A at time t
- I can't do task A again until time t + n + 1
- Reason: I need n other tasks (or idles) in between

Visual:
n = 2 (need 2 units between A's)
A _ _ A
  ↑  ↑ (2 time units minimum)

n = 3 (need 3 units between A's)
A _ _ _ A
  ↑    ↑ (3 time units minimum)

Step 2: GREEDY INSIGHT - DO FREQUENT TASKS FIRST
Key observation: The task that appears most constrains everything else!

Compare:
Scenario 1: [A, A, A, A, A, B, B], n=2
- A appears 5 times, B appears 2 times
- If we do A _ _ A _ _ A _ _ A _ _ A:
  This forces structure (A's are bottleneck)

Scenario 2: [A, B, C, D, E, F], n=2
- Each appears once, different task
- We can just: A B C D E F (no idle needed)

Insight: The MOST frequent task determines minimum time!

Why greedy works:
- If task A (most frequent) needs X time units
- Adding other tasks can only help, never hurt
- Doing A first spreads other tasks around A's naturally

Step 3: ALGORITHM - BATCH EXECUTION EXPLAINED
Process in batches of (n+1) different tasks:

Why (n+1)?
If I do: A, B, C (3 tasks, n=2)
  Time: A(0) B(1) C(2) → gap before next A
  Next A at time 3, which is 2 units after first A ✓
  Between them: exactly 2 other tasks

General: A, Task2, Task3, ..., TaskN, Task(N+1), A
         ↑                                      ↑
         Time 0                          Time n+1
         Gap = n units ✓

Step 4: EXAMPLE WALKTHROUGH
Tasks: [A, A, A, B, B, C], n=2
Frequencies: A=3, B=2, C=1

Batch 1:
  MaxHeap: [3, 2, 1]
  Do 3 tasks (A, B, C): pop A(2), B(1), C(0)
  Time += 3
  Put back A, B (C is done)
  MaxHeap: [2, 1]

Batch 2:
  Do 2 tasks (A, B): pop A(1), B(0)
  Time += 3 (full batch even with 2 tasks, because n=2 means n+1=3)
  Put back A
  MaxHeap: [1]

Batch 3:
  Do 1 task (A): pop A(0)
  Time += 1 (final batch, only 1 task)
  MaxHeap: empty

Total time: 3 + 3 + 1 = 7

Visual schedule:
Time 0: A (freq 3→2)
Time 1: B (freq 2→1)
Time 2: C (freq 1→0)
Time 3: A (freq 2→1)
Time 4: B (freq 1→0)
Time 5: idle (batch needs 3 slots, only 2 tasks)
Time 6: A (freq 1→0)

Step 5: WHEN THERE'S NO IDLE
If task diversity is high:
Tasks: [A, B, C, D, E, F], n=2
Different tasks > n+1 (6 > 3)
Schedule: A B C D E F (no idle!)
Time = 6 (just task count)

Formula: max(total_tasks, (maxFreq - 1) * (n + 1) + countMaxFreq)
- total_tasks: base case (no idle scenario)
- (maxFreq - 1) * (n + 1) + countMaxFreq:
  - maxFreq - 1: number of "batches" for most frequent task
  - Each batch needs n+1 slots
  - countMaxFreq: tasks with max frequency at the end
```

#### Code Implementation

```java
public int leastInterval(char[] tasks, int n) {
    // Step 1: Count frequency of each task
    Map<Character, Integer> taskCount = new HashMap<>();
    for (char task : tasks) {
        taskCount.put(task, taskCount.getOrDefault(task, 0) + 1);
    }
    
    // Step 2: Put all frequencies in max heap
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
    maxHeap.addAll(taskCount.values());
    
    int time = 0;
    
    // Step 3: Process tasks in batches
    while (!maxHeap.isEmpty()) {
        // Temporary list to store tasks from this batch
        List<Integer> temp = new ArrayList<>();
        int count = 0;
        
        // Execute up to (n+1) different tasks
        while (count < n + 1 && !maxHeap.isEmpty()) {
            // Pop task with most remaining instances
            int freq = maxHeap.poll();
            
            // Execute one instance, store remaining count
            temp.add(freq - 1);
            count++;
        }
        
        // Put back tasks that still have remaining instances
        for (int freq : temp) {
            if (freq > 0) {
                maxHeap.offer(freq);
            }
        }
        
        // Time increment:
        // If heap not empty: this batch took (n+1) time
        // If heap empty: this was final batch, took only count time
        time += maxHeap.isEmpty() ? count : n + 1;
    }
    
    return time;
}
```

**Example Walkthrough:**
```
Tasks: [A, A, A, B, B, C], n = 2
Frequencies: A=3, B=2, C=1

Batch 1:
  Pop: A (3), B (2), C (1)
  Remaining: A=2, B=1, C=0
  Time += 3 (executed 3 tasks, n+1=3)
  
Batch 2:
  Pop: A (2), B (1)
  Remaining: A=1, B=0
  Time += 3 (executed 2 tasks but batch is n+1=3, so idle 1)
  
Batch 3:
  Pop: A (1)
  Remaining: A=0
  Time += 1 (final batch, no need for full n+1)

Total time = 3 + 3 + 1 = 7
```

**Why This Works:**
- Greedy: always do most frequent task first
- Avoids idle time by spreading high-frequency tasks
- Max heap efficiently finds "next best" task

**Complexity:** O(n) where n = number of tasks
(Each task processed once, heap operations on 26 letters max)

---

### Problem 12: Single Threaded CPU (LeetCode 1834)

**Problem:** Execute tasks in optimal order to minimize total time

#### Thinking Process

```
Step 1: UNDERSTAND THE CONSTRAINT
- Each task has enqueue_time and processing_time
- Task can't run until enqueue_time arrives
- Goal: Find order to minimize total execution time

Step 2: KEY INSIGHT - GREEDY AT EACH MOMENT
"At any point, execute the SHORTEST available task"

Why? 
- Shorter tasks don't block longer ones
- Reduces total wait time
- This is classic "shortest job first" scheduling

Step 3: CHALLENGE - SIMULATION
We can't just sort by processing_time.
Why? A task with short process_time might not be available yet.

Example:
Task 1: enqueue=0, process=10
Task 2: enqueue=5, process=1

If we do Task 1 first (arrives first), Task 1 finishes at 10.
At time 10, Task 2 is available (arrived at 5).
We do Task 2, finishes at 11.
Total: 11

Could we do differently? No, Task 2 isn't available at time 0.

Step 4: SIMULATION ALGORITHM
Maintain two pointers:
- i: pointer to tasks (sorted by enqueue time)
- pq: min heap of AVAILABLE tasks (by process time)

At each step:
1. Add all tasks that have enqueued by current time to heap
2. If heap has tasks: do shortest one
   - Increment time by process duration
3. If heap empty: jump time to next task's enqueue time

Step 5: WHY SORT BY ENQUEUE TIME FIRST?
Because we process tasks chronologically.
As we advance time, we encounter enqueued tasks in order.

Then within available tasks, we use min heap to pick shortest.
```

#### Code Implementation

```java
public int[] getOrder(int[][] tasks) {
    // Step 1: Add task indices, convert to indexed format
    int[][] indexed = new int[tasks.length][3];
    for (int i = 0; i < tasks.length; i++) {
        indexed[i] = new int[]{
            tasks[i][0],    // enqueue time
            tasks[i][1],    // process time
            i               // original index (for result)
        };
    }
    
    // Step 2: Sort by enqueue time
    // This way we process tasks in chronological order
    Arrays.sort(indexed, (a, b) -> a[0] - b[0]);
    
    // Step 3: Min heap of available tasks
    // Order by: process time (shortest first)
    // Tie-break by: original index (smaller index first)
    PriorityQueue<int[]> pq = new PriorityQueue<>(
        (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]
    );
    
    int[] result = new int[tasks.length];
    int resultIdx = 0;
    long currentTime = 0;
    int taskIdx = 0;  // Pointer to next task to enqueue
    
    // Step 4: Simulate execution
    while (resultIdx < tasks.length) {
        // Add all tasks that have enqueued by now
        while (taskIdx < tasks.length && indexed[taskIdx][0] <= currentTime) {
            // (process_time, original_index)
            pq.offer(new int[]{indexed[taskIdx][1], indexed[taskIdx][2]});
            taskIdx++;
        }
        
        // Check if any task is available
        if (pq.isEmpty()) {
            // No available tasks, jump to next enqueue time
            currentTime = indexed[taskIdx][0];
        } else {
            // Execute shortest available task
            int[] task = pq.poll();
            int processTime = task[0];
            int taskOriginalIndex = task[1];
            
            // Advance time by process duration
            currentTime += processTime;
            
            // Record this task in result
            result[resultIdx++] = taskOriginalIndex;
        }
    }
    
    return result;
}
```

**Example Walkthrough:**
```
Tasks: [[1,2], [2,4], [3,2], [4,1]]
Indices: 0,     1,     2,     3

After sorting by enqueue time:
indexed = [[1,2,0], [2,4,1], [3,2,2], [4,1,3]]

Simulation:
currentTime=0, heap empty
  → Jump to next: currentTime=1

currentTime=1
  → Task 0 (enqueue=1) becomes available
  → heap = [(2,0)]
  → Execute task 0: time += 2 → currentTime=3
  → result[0] = 0

currentTime=3
  → Task 1 (enqueue=2, process=4): available
  → Task 2 (enqueue=3, process=2): available
  → heap = [(2,2), (4,1)]
  → Execute task 2 (shorter): time += 2 → currentTime=5
  → result[1] = 2

currentTime=5
  → Task 1 (process=4): available
  → Task 3 (enqueue=4, process=1): available
  → heap = [(1,3), (4,1)]
  → Execute task 3: time += 1 → currentTime=6
  → result[2] = 3

currentTime=6
  → Task 1: available
  → heap = [(4,1)]
  → Execute task 1: time += 4 → currentTime=10
  → result[3] = 1

Result: [0, 2, 3, 1] ✓
```

**Why This Works:**
- Sorting ensures chronological processing
- Min heap always picks shortest available task (greedy)
- Simulation handles delayed availability naturally
- Jumping over idle time avoids unnecessary loops

**Complexity:** O(n log n) for sorting, O(n log n) for heap operations

---

### Problem 13-20: Related Problems

- **Process Tasks Using Servers** (LeetCode 1882): Two heaps (like MR III)
- **Course Schedule** (LeetCode 207): Topological sort
- **Course Schedule II** (LeetCode 210): Order output
- **Parallel Courses** (LeetCode 1136): Level BFS
- **Skyline Problem** (LeetCode 218): Sweep line + heap
- **Car Pooling** (LeetCode 1094): Difference array
- **Flight Bookings** (LeetCode 1109): Difference array + prefix
- **Refueling Stops** (LeetCode 871): Max heap greedy

---

# 🔧 SOLUTION PATTERNS

## Pattern 1: Two Pointers
Sort by start time, track pointer to earliest-ending resource

## Pattern 2: Greedy Selection  
Sort by end time, always pick earliest-ending interval

## Pattern 3: Min/Max Heap
Track "next best" repeatedly with priority queue

## Pattern 4: Sweep Line
Convert intervals to events, accumulate delta

## Pattern 5: Topological Sort
Track indegree, process when indegree=0

## Pattern 6: Difference Array
Add at start, subtract at end, compute prefix sum

---

# 🏆 RECOGNITION FRAMEWORK

See "Max concurrent" or "Peak"?
→ **Sweep Line** or **Heap**

See "Minimum room/resource"?
→ **Heap** (earliest-releasing)

See "Dependencies"?
→ **Topological Sort**

See "Max non-overlapping"?
→ **Greedy** (sort by end)

See "Range updates"?
→ **Difference Array**

---

# 📈 COMPLEXITY REFERENCE

| Problem | Time | Space | Approach |
|---------|------|-------|----------|
| Merge | O(n log n) | O(n) | Sort + Merge |
| Insert | O(n) | O(n) | Three phases |
| Erase | O(n log n) | O(1) | Greedy end |
| MR I | O(n log n) | O(1) | Sort + Check |
| MR II | O(n log n) | O(n) | Two Pointers |
| MR III | O(n log n) | O(n) | Heap |
| MaxConcurrent | O(n log n) | O(n) | Sweep |
| TaskScheduler | O(n log n) | O(n) | Max Heap |
| CPU | O(n log n) | O(n) | Sort + Heap |

---

# 🧭 HOW TO THINK ABOUT PROBLEMS (Thinking Frameworks)

## Universal Thinking Pattern

When you see a resource scheduling problem, ask these questions in order:

### Step 1: Clarify the Goal
```
Is the problem asking for:
- YES/NO answer? (Can attend all?) → Check for conflicts
- COUNT? (How many rooms?) → Track concurrent usage
- ORDER? (Which room #?) → Simulate and assign
- MAXIMUM/MINIMUM? (Max concurrent?) → Track peaks
```

### Step 2: Identify the Constraint
```
What limits us?
- Time dimension [start, end]? → Sort + process
- Cooldown between repeated? → Greedy most frequent
- Dependencies exist? → Topological sort
- Need earliest available? → Min heap
- Need most important? → Max heap
```

### Step 3: Choose Data Structure (Not Code!)
```
Problem asks "how many concurrent?"
  ↓
Insight: "At any point in time, what's active?"
  ↓
Data structure: Two pointers or sweep line
  ↓
Why? Track state changes over time efficiently

Problem asks "which room to assign?"
  ↓
Insight: "Which room frees first?"
  ↓
Data structure: Min heap of free times
  ↓
Why? Quickly find earliest-available resource

Problem asks "max meetings to attend?"
  ↓
Insight: "Which one should I keep?"
  ↓
Data structure: Greedy (sort by end time)
  ↓
Why? Earliest-ending leaves most room
```

### Step 4: Algorithm Template

Once you know the data structure, the algorithm almost writes itself:

**For Heap-based Problems:**
```
1. Sort meetings by start time
2. Create heap of available resources
3. For each meeting:
   - Get best resource from heap
   - Use it, update availability
   - Put back in heap
4. Return result from heap state
```

**For Greedy Problems:**
```
1. Sort by END time (not start!)
2. Iterate once
3. Keep track of last picked interval
4. At each step: pick if no conflict
5. Count/collect picked intervals
```

**For Sweep Line Problems:**
```
1. Convert intervals to events (start=+1, end=-1)
2. Sort events by time
3. Sweep through, accumulating delta
4. Track maximum (or final state)
5. Return tracked value
```

---

## Problem-Solving Checklist

### Before Coding
- ☐ Clarified the goal (count/order/yes-no/max-min)
- ☐ Identified constraints (time/cooldown/dependency/resource)
- ☐ Chose data structure (heap/sort/two-pointer/sweep-line)
- ☐ Traced through example by hand
- ☐ Thought about edge cases (empty, single, all overlap)

### While Coding
- ☐ Used correct sorting (start/end/value?)
- ☐ Compared intervals correctly (< or <=?)
- ☐ Handled empty states (no tasks/no rooms?)
- ☐ Processed remaining items after loop (final interval?)

### After Coding
- ☐ Traced example again with code
- ☐ Checked off-by-one errors
- ☐ Verified complexity matches expectations
- ☐ Tested edge cases

---

## Why These Patterns Work

```
Heap Approach:
- Streaming: Process items one by one
- Always pick "best" from current options
- Each decision is locally optimal
- Best for: assignment, scheduling with constraints

Greedy Approach:
- Sorting enables global view
- One choice blocks/enables others
- Earliest-ending leaves most options open
- Best for: maximize non-overlapping, minimize removals

Sweep Line Approach:
- Convert spatial problem to temporal
- Track state changes efficiently
- Elegant for "peak" and "count" problems
- Best for: maximum concurrent, busy time tracking

Topological Sort Approach:
- Handle dependencies
- Process in valid order
- Ensures prerequisites met
- Best for: scheduling with dependencies
```

---

## Common Mistakes to Avoid

### Mistake 1: Wrong Sort Key
```
❌ WRONG: Sort by start for maximum non-overlapping
✅ RIGHT: Sort by END for maximum non-overlapping

❌ WRONG: Sort by end for minimum rooms
✅ RIGHT: Sort by START for rooms, then check ends
```

### Mistake 2: Wrong Overlap Check
```
❌ WRONG: current.start <= previous.end
✅ RIGHT: current.start < previous.end
(Depends on problem: does [0,5] and [5,6] overlap?)
```

### Mistake 3: Forgot Final Item
```
❌ WRONG: Return result after loop, forgot last interval
✅ RIGHT: Insert final merged/grown interval after processing
```

### Mistake 4: Heap Over-Engineering
```
❌ WRONG: Using heap when two-pointers work fine
✅ RIGHT: Two-pointers for counting, heap for assignment

O(n) two-pointers < O(n log n) heap when possible
```

### Mistake 5: Greedy Without Proof
```
❌ WRONG: Use greedy without understanding why it works
✅ RIGHT: Understand exchange argument:
   "Why is earliest-ending better than any other choice?"
```

---

**Internalize these patterns. Once you see the structure, the code follows naturally.** 🎯

**Master these 20 problems and you'll ace 80% of Google/Facebook resource scheduling questions.** 🚀