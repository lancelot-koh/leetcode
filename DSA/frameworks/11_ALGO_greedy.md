# 🎭 Greedy - Optimal Local Choices

**Make locally optimal choices that lead to global optimum**

---

## Interview Frequency: **5% of problems** ⭐⭐⭐

---

## Core Pattern

```
At each step:
1. Choose the locally optimal choice
2. Hope it leads to global optimum
3. Prove it's correct (or use exchange argument)
```

---

## Templates

### Activity Selection (Most Activities)

```java
public int maxActivities(int[][] activities) {
    Arrays.sort(activities, (a, b) -> a[1] - b[1]);  // Sort by end time
    
    int count = 1;
    int lastEnd = activities[0][1];
    
    for (int i = 1; i < activities.length; i++) {
        if (activities[i][0] >= lastEnd) {
            count++;
            lastEnd = activities[i][1];
        }
    }
    
    return count;
}
```

---

### Interval Scheduling (Minimize Rooms)

```java
public int minMeetingRooms(int[][] intervals) {
    int[] starts = new int[intervals.length];
    int[] ends = new int[intervals.length];
    
    for (int i = 0; i < intervals.length; i++) {
        starts[i] = intervals[i][0];
        ends[i] = intervals[i][1];
    }
    
    Arrays.sort(starts);
    Arrays.sort(ends);
    
    int rooms = 0, maxRooms = 0;
    int i = 0, j = 0;
    
    while (i < intervals.length) {
        if (starts[i] < ends[j]) {
            rooms++;
            maxRooms = Math.max(maxRooms, rooms);
            i++;
        } else {
            rooms--;
            j++;
        }
    }
    
    return maxRooms;
}
```

---

## Key Points

- **Greedy choice:** What's the locally optimal?
- **Proof needed:** Exchange argument or mathematical proof
- **When to use:** Optimal substructure + greedy choice property
- **Common mistakes:** Wrong greedy choice or lack of proof

---

## Examples

- **Activity Selection:** Most non-overlapping
- **Interval Scheduling:** Min meeting rooms
- **Gas Station:** Can reach end
- **Jump Game:** Can reach last index

---

**Master greedy. It's 5% of interviews.** 🚀
