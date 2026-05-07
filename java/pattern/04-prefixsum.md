# Prefix Sum 前缀和全模板（4大模式）

---

## 模式1：一维前缀和（Range Sum Query）

**特征**：多次查询区间 `[l, r]` 的和

**模板**

```java
// build — O(n)
int[] prefix = new int[n + 1];
for (int i = 0; i < n; i++) {
    prefix[i + 1] = prefix[i] + nums[i];
}

// query [l, r] — O(1)
int rangeSum = prefix[r + 1] - prefix[l];
```

**代表题**
- 303. Range Sum Query - Immutable
- 1480. Running Sum of 1d Array

---

## 模式2：前缀和 + HashMap（子数组和等于 K）

**特征**：找和为目标值的子数组个数 / 是否存在

**核心思想**

```
sum[i..j] = K
⟺ prefix[j] - prefix[i-1] = K
⟺ prefix[i-1] = prefix[j] - K
```

**模板（计数）**

```java
Map<Integer, Integer> map = new HashMap<>();
map.put(0, 1); // empty prefix
int sum = 0, count = 0;

for (int num : nums) {
    sum += num;
    count += map.getOrDefault(sum - k, 0);
    map.put(sum, map.getOrDefault(sum, 0) + 1);
}

return count;
```

**代表题**
- 560. Subarray Sum Equals K
- 974. Subarray Sums Divisible by K（`sum % k` 作 key）
- 525. Contiguous Array（0→-1 转换）

---

## 模式3：二维前缀和（2D Range Sum）

**特征**：矩阵中矩形区域求和

**模板**

```java
// build — O(m*n)
int[][] prefix = new int[m + 1][n + 1];
for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        prefix[i][j] = matrix[i-1][j-1]
                     + prefix[i-1][j]
                     + prefix[i][j-1]
                     - prefix[i-1][j-1];
    }
}

// query rectangle (r1,c1) to (r2,c2) — O(1)
int sum = prefix[r2+1][c2+1]
        - prefix[r1][c2+1]
        - prefix[r2+1][c1]
        + prefix[r1][c1];
```

**代表题**
- 304. Range Sum Query 2D - Immutable
- 1314. Matrix Block Sum

---

## 模式4：差分数组（Range Update）

**特征**：对区间 `[l, r]` 批量加减，最后还原

**核心思想**

```
diff[l]     += val   →  从 l 开始加 val
diff[r + 1] -= val   →  到 r+1 停止
前缀和还原 diff → 结果数组
```

**模板**

```java
// build diff array
int[] diff = new int[n + 1];

// range update [l, r] += val — O(1) each
diff[l]     += val;
diff[r + 1] -= val;

// restore — O(n)
int[] result = new int[n];
int running = 0;
for (int i = 0; i < n; i++) {
    running  += diff[i];
    result[i] = running;
}
```

**代表题**
- 1094. Car Pooling
- 1109. Corporate Flight Bookings
- 253. Meeting Rooms II（差分 + 扫描线）

---

## 一张总图

| # | 模式 | 适用场景 | 时间复杂度 |
|---|------|----------|-----------|
| 1 | 一维前缀和 | 区间查询和 | 预处理 O(n)，查询 O(1) |
| 2 | 前缀和 + HashMap | 子数组和等于 K | O(n) |
| 3 | 二维前缀和 | 矩阵矩形查询 | 预处理 O(mn)，查询 O(1) |
| 4 | 差分数组 | 区间批量更新 | 更新 O(1)，还原 O(n) |

---

## 一眼判断法

```
多次查询区间和？              → 模式1  一维前缀和
子数组和 == K，求个数？       → 模式2  前缀和 + HashMap
矩阵矩形区域求和？            → 模式3  二维前缀和
区间批量加减，最后查结果？    → 模式4  差分数组
```

---

## 常见变体速查

| 题目变体 | 处理技巧 |
|----------|----------|
| 子数组和能被 K 整除 | `sum % k` 作 HashMap 的 key |
| 0/1 数组，0和1个数相等 | 把 0 替换为 -1，转化为和为 0 |
| 二维子矩阵和等于 K | 枚举上下行 + 一维前缀和 + HashMap |

> **最重要一句话：** 前缀和把"区间问题"变成"两点作差"，差分把"区间更新"变成"两点打标记"
