# Monotonic Stack 单调栈全模板（4大模式）

---

## 模式1：Next Greater Element

**特征**：找每个元素右侧第一个更大的值

**模板**

```java
int[] result = new int[n];
Arrays.fill(result, -1);
Deque<Integer> stack = new ArrayDeque<>(); // stores indices

for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
        result[stack.pop()] = nums[i];
    }
    stack.push(i);
}
```

**代表题**
- Next Greater Element I / II
- Daily Temperatures

---

## 模式2：Previous Greater / Smaller Element

**特征**：找每个元素左侧第一个更大 / 更小的值

**模板（Previous Smaller）**

```java
int[] left = new int[n];
Deque<Integer> stack = new ArrayDeque<>(); // stores indices

for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
        stack.pop();
    }
    left[i] = stack.isEmpty() ? -1 : stack.peek();
    stack.push(i);
}
```

**代表题**
- 84. Largest Rectangle in Histogram（左右边界）
- 85. Maximal Rectangle

---

## 模式3：区间贡献（Left + Right Boundary）

**特征**：每个元素作为最值，向两侧扩展到边界

**模板**

```java
int[] left  = new int[n];  // index of previous smaller
int[] right = new int[n];  // index of next smaller

// previous smaller
Deque<Integer> stack = new ArrayDeque<>();
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
        stack.pop();
    }
    left[i] = stack.isEmpty() ? -1 : stack.peek();
    stack.push(i);
}

// next smaller
stack.clear();
for (int i = n - 1; i >= 0; i--) {
    while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
        stack.pop();
    }
    right[i] = stack.isEmpty() ? n : stack.peek();
    stack.push(i);
}

// contribution of each element as minimum
int result = 0;
for (int i = 0; i < n; i++) {
    result += nums[i] * (i - left[i]) * (right[i] - i);
}
```

**代表题**
- 84. Largest Rectangle in Histogram
- 907. Sum of Subarray Minimums
- 2104. Sum of Subarray Ranges

---

## 模式4：单调栈 + 计算面积（Histogram）

**特征**：直方图类，用栈维护递增序列，弹出时计算面积

**模板**

```java
Deque<Integer> stack = new ArrayDeque<>();
int maxArea = 0;

for (int i = 0; i <= n; i++) {
    int h = (i == n) ? 0 : heights[i];

    while (!stack.isEmpty() && heights[stack.peek()] > h) {
        int height = heights[stack.pop()];
        int width  = stack.isEmpty() ? i : i - stack.peek() - 1;
        maxArea = Math.max(maxArea, height * width);
    }

    stack.push(i);
}
```

**代表题**
- 84. Largest Rectangle in Histogram
- 42. Trapping Rain Water

---

## 一张总图

| # | 模式 | 栈维护 | 弹出时机 |
|---|------|--------|----------|
| 1 | Next Greater | 递减栈（索引） | 遇到更大元素 |
| 2 | Previous Smaller | 递增栈（索引） | 遇到更小元素 |
| 3 | 区间贡献 | 左右各扫一次 | 边界收缩 |
| 4 | 直方图面积 | 递增栈（索引） | 遇到更矮柱子 |

---

## 一眼判断法

```
找右侧第一个更大？          → 模式1  单调递减栈，从左到右
找左侧边界？                → 模式2  单调递增栈，从左到右
每个元素作为最值的贡献？    → 模式3  左右各扫一次求边界
直方图 / 接雨水面积？       → 模式4  递增栈 + 弹出计算
```

---

## 栈的选择口诀

```
求 Next Greater  → 维护单调递减栈
求 Next Smaller  → 维护单调递增栈
弹出 = 找到了答案
```

> **最重要一句话：** 栈里存的是"还没找到答案的候选者"，遇到能成为答案的元素就弹出结算
