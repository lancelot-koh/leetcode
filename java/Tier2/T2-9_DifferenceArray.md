# T2-9 — Difference Array 差分数组

> **Core idea:** For range update problems, store `diff[l] += val` and `diff[r+1] -= val`. A single prefix-sum pass reconstructs the result array. O(1) per update, O(n) reconstruction.
> **核心思想：** 区间更新问题中，只记录`diff[l]+=val`和`diff[r+1]-=val`。一次前缀和还原结果数组。每次更新O(1)，重建O(n)。
>
> Complexity: O(1) per range update, O(n) to reconstruct. Better than O(n) per update on raw array.
> Full reference: `PrefixSum/desciption.md` Pattern 6

---

## How it Works — Mental Model 算法原理

Instead of adding `val` to every element in `[l, r]` (which is O(n) per update), the difference array records only the **boundaries** of the change: `diff[l] += val` marks where the increment starts, and `diff[r+1] -= val` marks where it stops. A single prefix-sum pass over `diff` at the end propagates each boundary change forward, effectively applying all updates at once. This works because prefix-summing a difference array reconstructs the original (or updated) values: each position's value is the sum of all increments that started at or before it and haven't been cancelled yet. The technique trades "many cheap boundary marks + one expensive reconstruction" for "many expensive per-element updates."

**Key invariant:** At any point, `diff[i]` stores the **net change** that begins at position `i`. The actual value at position `i` is `diff[0] + diff[1] + ... + diff[i]` — only computed at the end via prefix sum.

**Common mistake / gotcha:** Forgetting to allocate one extra slot (`n+1` instead of `n`). When updating the last element (`r = n-1`), you write to `diff[r+1] = diff[n]`, which is out of bounds for an array of size `n`. Always allocate `n+1`.

---

## Step-by-Step Trace 执行步骤示意

Example: `getModifiedArray(length=5, updates=[[1,3,2],[0,2,6],[0,4,-2]])`
```
Start: diff=[0,0,0,0,0,0]  (size 6)

Update [1,3,2]: diff[1]+=2, diff[4]-=2 → diff=[0,2,0,0,-2,0]
Update [0,2,6]: diff[0]+=6, diff[3]-=6 → diff=[6,2,0,-6,-2,0]
Update [0,4,-2]:diff[0]-=2, diff[5]-=2 → diff=[4,2,0,-6,-2,-2]  (diff[5] out of result range)

Prefix sum: result[0]=4, result[1]=4+2=6, result[2]=6+0=6, result[3]=6-6=0, result[4]=0-2=-2
Result: [4,6,6,0,-2]
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Many range increment/decrement updates, then read all | LC 1109, 370 |
| Overlapping time intervals: find max overlap | LC 253 (alternative), 1854 |
| Paint a range repeatedly, final colors | LC 732 (simpler version) |
| Booking system: check capacity | LC 1109 |
| Car pooling (multiple pickups/dropoffs) | LC 1094 |

**Signal:** "range update", "add k to every element from l to r", then "query all elements at the end." No random mid-sequence queries.

**When NOT to use:** If you need to query individual positions mid-sequence (random access), use a Segment Tree or BIT instead.

---

## Core Templates 核心模板

### Basic difference array

```java
int[] diff = new int[n + 1];   // size n+1: diff[r+1] may reach index n when r=n-1

// Range update: add val to every position in [l, r] inclusive
diff[l]     += val;     // the increment starts at l
diff[r + 1] -= val;     // the increment ends after r (cancelled at r+1)

// Reconstruct the result array: prefix sum of diff restores actual values
int[] result = new int[n];
result[0] = diff[0];
for (int i = 1; i < n; i++) {
    result[i] = result[i-1] + diff[i];   // carry forward + apply any new boundary change
}
```

### Reconstruct in-place (when diff IS the result)

```java
for (int i = 1; i < diff.length; i++) {
    diff[i] += diff[i-1];
}
// diff[i] now holds the actual value at position i
```

### Event-based overlap counting

```java
int[] diff = new int[maxTime + 2];

for (int[] booking : bookings) {
    int l = booking[0], r = booking[1], seats = booking[2];
    diff[l]     += seats;
    diff[r + 1] -= seats;
}

// Prefix sum gives occupancy at each time unit
int[] answer = new int[n];
int cur = 0;
for (int i = 1; i <= n; i++) {
    cur += diff[i];
    answer[i-1] = cur;
}
```

---

## Variants 变形

| Variant | Key idea | Example |
|---|---|---|
| Range add, read all | Diff array + prefix sum | LC 370, 1109 |
| Car pooling (capacity check) | Build diff on trip routes; check max ≤ capacity | LC 1094 |
| Max overlap of intervals | Diff array; find peak of prefix sum | Sweep line variant |
| 2D difference array | 4-corner update: `diff[r1][c1]++`, etc. | LC 2132 |
| Circular range update | Handle wrap-around (two updates if r < l) | interview variant |

---

## Key Examples 关键例题

### Corporate Flight Bookings (LC 1109)
```java
public int[] corpFlightBookings(int[][] bookings, int n) {
    int[] diff = new int[n + 1];
    for (int[] b : bookings) {
        diff[b[0] - 1] += b[2];             // 1-indexed → 0-indexed
        if (b[1] < n) { diff[b[1]] -= b[2]; }   // r+1 might be out of range
    }
    int[] answer = new int[n];
    answer[0] = diff[0];
    for (int i = 1; i < n; i++) {
        answer[i] = answer[i-1] + diff[i];
    }
    return answer;
}
```

### Car Pooling (LC 1094)
```java
public boolean carPooling(int[][] trips, int capacity) {
    int[] diff = new int[1001];   // max location = 1000; no +1 needed since alight is exclusive
    for (int[] t : trips) {
        diff[t[1]] += t[0];       // t[0] passengers board at location t[1]
        diff[t[2]] -= t[0];       // t[0] passengers alight at location t[2] (they are NOT on car here)
    }
    int cur = 0;
    for (int d : diff) {
        cur += d;
        if (cur > capacity) { return false; }  // check at every stop; no need to reconstruct full array
    }
    return true;
}
```

### Range Addition (LC 370 — premium)
```java
public int[] getModifiedArray(int length, int[][] updates) {
    int[] diff = new int[length + 1];
    for (int[] u : updates) {
        diff[u[0]] += u[2];
        diff[u[1] + 1] -= u[2];
    }
    int[] result = new int[length];
    result[0] = diff[0];
    for (int i = 1; i < length; i++) {
        result[i] = result[i-1] + diff[i];
    }
    return result;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `diff[r+1]` needs extra slot | Allocate `n+1` or `maxVal+1` to avoid index out of bounds |
| 1-indexed input → subtract 1 | LC 1109 uses 1-indexed flights: `b[0]-1`, `b[1]` (for `b[1]+1-1`) |
| Off-by-one on `r+1` | Update closes AFTER position `r`, so decrement at `r+1` |
| No random queries mid-process | Diff array gives final snapshot only; use Segment Tree for point queries |
| Board at start, alight at end | Car pooling: passengers are ON the car from `from` (inclusive) to `to` (exclusive) |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 1109 Corporate Flight Bookings |
| Medium | LC 1094 Car Pooling |
| Medium | LC 370 Range Addition (premium) |
| Medium | LC 1854 Maximum Population Year |
| Hard | LC 2132 Stamping the Grid (2D diff array) |

**Order:** 1109 → 1094 → 1854 → 370 → 2132

---

## One-line Summary

```
Difference array = mark range boundaries with ±val; one prefix-sum pass reconstructs all values in O(n).
差分数组 = 在区间边界标记±val；一次前缀和O(n)还原所有值。
```
