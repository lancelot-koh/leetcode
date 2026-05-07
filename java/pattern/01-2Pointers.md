# Two Pointers 双指针全模板（5大模式）

---

## 模式1：对撞指针（Opposite Direction）

**特征**
- 数组有序 / 可以排序
- `left` 从左，`right` 从右

**模板**

```java
int left = 0, right = n - 1;

while (left < right) {
    int sum = nums[left] + nums[right];

    if (sum == target) {
        return ...;
    } else if (sum < target) {
        left++;
    } else {
        right--;
    }
}
```

**代表题**
- Two Sum II
- 3Sum（内层）
- Container With Most Water

---

## 模式2：同向指针（Fast & Slow）

**特征**
- 一个快，一个慢
- 用于"过滤 / 压缩 / 移动"

**模板**

```java
int slow = 0;

for (int fast = 0; fast < n; fast++) {
    if (valid(nums[fast])) {
        nums[slow] = nums[fast];
        slow++;
    }
}
```

**代表题**
- Move Zeroes
- Remove Duplicates
- Remove Element

---

## 模式3：子序列匹配（Subsequence）

**特征**
- 两个字符串
- 一个扫描，一个匹配

**模板**

```java
int i = 0, j = 0;

while (i < s.length() && j < t.length()) {
    if (s.charAt(i) == t.charAt(j)) {
        i++;
    }
    j++;
}

return i == s.length();
```

**代表题**
- Is Subsequence

---

## 模式4：回文 / 对称（Palindrome）

**特征**
- 从两端向中间收拢

**模板**

```java
int left = 0, right = s.length() - 1;

while (left < right) {
    if (s.charAt(left) != s.charAt(right)) {
        return false;
    }
    left++;
    right--;
}
return true;
```

**代表题**
- Valid Palindrome
- Longest Palindromic Substring（扩展）

---

## 模式5：Partition / 三路划分（Dutch Flag）

**特征**
- 分类问题（0 / 1 / 2）

**模板**

```java
int low = 0, mid = 0, high = n - 1;

while (mid <= high) {
    if (nums[mid] == 0) {
        swap(nums, low++, mid++);
    } else if (nums[mid] == 1) {
        mid++;
    } else {
        swap(nums, mid, high--);
    }
}
```

**代表题**
- Sort Colors

---

## 一张总图

| # | 模式 | 指针方向 | 关键词 |
|---|------|----------|--------|
| 1 | 对撞指针 | `left` → ← `right` | 有序数组、两数之和 |
| 2 | 同向快慢 | `slow` → `fast` → | 删除、压缩、去重 |
| 3 | 子序列匹配 | `i` → `j` → | 两字符串、顺序匹配 |
| 4 | 回文检查 | `left` → ← `right` | 对称、回文 |
| 5 | 三路划分 | `low` `mid` `high` | 分类、0/1/2 |

---

## 一眼判断法（面试关键）

```
数组有序？            → YES → 模式1 对撞指针
需要删除/移动/压缩？  → YES → 模式2 fast/slow
字符串匹配顺序？      → YES → 模式3 子序列
对称/回文？           → YES → 模式4 双向 inward
分类问题？            → YES → 模式5 Dutch Flag
```

---

## 与 Sliding Window 的区别

| 技术 | 本质 |
|------|------|
| Two Pointers | 位置移动 |
| Sliding Window | 区间管理 |

> **记住：** Sliding Window = Two Pointers + 区间约束

**最重要一句话：** Two Pointers 是"位置策略"，Sliding Window 是"区间策略"
