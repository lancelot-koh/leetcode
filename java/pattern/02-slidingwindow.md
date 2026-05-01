

🧠 Sliding Window 全模板（5大模式）

⸻

🟢 模式1：固定窗口（Fixed Window）

👉 特征

窗口大小固定 = k


⸻

👉 题目关键词

size = k
average / sum / count


⸻

👉 模板

int window = 0;

// 1. 初始化前 k 个
for (int i = 0; i < k; i++) {
    window += nums[i];
}

int result = window;

// 2. 滑动窗口
for (int right = k; right < nums.length; right++) {
    window += nums[right];
    window -= nums[right - k];

    result = Math.max(result, window);
}


⸻

👉 代表题
	•	Max Average Subarray
	•	Sliding Window Sum

⸻

🔵 模式2：可变窗口（Longest / Shortest）

⸻

👉 特征

求最长 / 最短


⸻

👉 模板（Longest）

int left = 0;

for (int right = 0; right < n; right++) {
    // expand

    while (invalid) {
        // shrink
        left++;
    }

    result = Math.max(result, right - left + 1);
}


⸻

👉 模板（Minimum）

while (valid) {
    update answer
    shrink
}


⸻

👉 代表题
	•	Longest Substring Without Repeating
	•	Minimum Window Substring

⸻

🟡 模式3：固定窗口 + 频率（Permutation / Anagram）

⸻

👉 特征

window size = 固定
需要匹配频率


⸻

👉 模板

int[] count = new int[26];
int required = p.length();

for (char c : p.toCharArray()) count[c]++;

int left = 0;

for (int right = 0; right < s.length(); right++) {

    if (count[s.charAt(right)] > 0) required--;
    count[s.charAt(right)]--;

    if (right - left + 1 == p.length()) {

        if (required == 0) {
            // found
        }

        if (count[s.charAt(left)] >= 0) required++;
        count[s.charAt(left)]++;

        left++;
    }
}


⸻

👉 代表题
	•	Find All Anagrams
	•	Permutation in String

⸻

🔴 模式4：AtMost Trick（COUNT + exactly K）

⸻

👉 特征

count + exactly K


⸻

👉 核心公式

exactly(K) = atMost(K) - atMost(K-1)


⸻

👉 模板

int atMost(int[] nums, int k) {
    int left = 0, count = 0;

    for (int right = 0; right < nums.length; right++) {
        // expand

        while (invalid) {
            left++;
        }

        count += right - left + 1;
    }

    return count;
}


⸻

👉 代表题
	•	Subarrays with K Distinct
	•	Binary Subarrays With Sum
	•	Nice Subarrays

⸻

🟣 模式5：单调队列（Sliding Window Max）

⸻

👉 特征

求窗口最大 / 最小


⸻

👉 模板

Deque<Integer> dq = new ArrayDeque<>();

for (int i = 0; i < n; i++) {

    // remove out of window
    if (!dq.isEmpty() && dq.peekFirst() == i - k) {
        dq.pollFirst();
    }

    // maintain monotonic
    while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
        dq.pollLast();
    }

    dq.offerLast(i);

    if (i >= k - 1) {
        result[i - k + 1] = nums[dq.peekFirst()];
    }
}


⸻

👉 代表题
	•	Sliding Window Maximum
	•	LC 1438（双队列）

⸻

🔥 一张总图（最重要）

Sliding Window 五大类：

1️⃣ 固定窗口（k大小）
2️⃣ 可变窗口（最长/最短）
3️⃣ 固定窗口 + 频率（anagram）
4️⃣ atMost trick（count + exactly K）
5️⃣ 单调队列（max/min）


⸻

🧠 一眼判断法（面试必用）

⸻

👉 Step 1

是不是 window size 固定？
→ YES → 模式1 / 模式3


⸻

👉 Step 2

是不是 longest / shortest？
→ YES → 模式2


⸻

👉 Step 3

是不是 count + exactly K？
→ YES → 模式4


⸻

👉 Step 4

是不是 max/min？
→ YES → 模式5


⸻

🔥 最重要一句话

先分类，再写代码，不要边想边写

⸻

🚀 你现在的阶段

你已经从：

写题 → 理解 pattern → 抽象模板

👉 进入：

面试级思维


⸻

👉 如果你要更进一步（强烈推荐）

我可以帮你做：

🔥「10题训练（按这5类）」

👉 你做到：

看到题 → 3秒分类 → 直接写模板

这就是 Google level 👍