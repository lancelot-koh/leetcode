# ⚙️ Dynamic Programming - Complete Master Guide

**The systematic framework that turns DP from mysterious to mastery**

---

## 📍 The DP Reality Check

### How Most People Approach DP
```
See DP problem
  ↓
"Um... I'll try something?"
  ↓
Random recurrence attempt
  ↓
Confused about base cases
  ↓
Frustrated
```

### Google L5 Approach
```
See DP problem
  ↓
Clarify → State → Transitions → Metric → Aggregation
  ↓
Code in 10 minutes
```

**The difference? A systematic 8-step framework applied consistently.**

---

## 🎯 The Universal DP Problem-Solving Framework

### 1. CLARIFICATION

**Ask these questions FIRST:**

| Question | Why It Matters | Example |
|----------|---|---|
| **What exactly are we optimizing?** | Determines aggregation type | Min coins vs. count ways? |
| **What's the constraint?** | Affects state definition | Unlimited items vs. limited? |
| **Can input be empty?** | Base case handling | Empty array, amount=0? |
| **Any special properties?** | Algorithm selection | Sorted? Duplicates allowed? |
| **What's the output format?** | State dimension | Single number vs. actual items? |

**Example: Coin Change**
```
Q: Given coins and amount, find minimum coins needed?
Q: Can each coin be used multiple times?
Q: What if amount is impossible?
Q: Return count or the coins themselves?

Answers clarify: unlimited items, return -1 if impossible, return count only
```

---

### 2. IDENTIFY THE ENTITY

**Ask: What represents a subproblem?**

| DP Variant | Entity | Meaning |
|---|---|---|
| **1D Linear** | `i` | First i elements / position i |
| **2D Sequence** | `(i, j)` | First i of A, first j of B |
| **2D Grid** | `(row, col)` | Position in grid |
| **State Machine** | `(i, state)` | Position i in specific state |
| **Interval** | `(left, right)` | Subarray from left to right |
| **Tree** | `node` or `(node, state)` | Current node in tree |
| **Bitmask** | `mask` | Which items selected? |

**Example: Coin Change**
```
Entity: amount (represents "make this amount")
State: dp[amount] = minimum coins to make this amount
```

**Example: Stock Trading**
```
Entity: (day, holding)
State: dp[i][0] = max profit on day i if NOT holding
       dp[i][1] = max profit on day i if HOLDING
```

---

### 3. IDENTIFY THE RELATIONSHIPS

**Ask: How do subproblems relate to each other?**

| Relationship | Pattern | Example |
|---|---|---|
| **Linear Dependency** | `dp[i] depends on dp[i-1], dp[i-2]` | Climbing stairs |
| **2D Dependency** | `dp[i][j] depends on dp[i-1][j], dp[i][j-1]` | Unique paths |
| **Interval Shrinking** | `dp[l][r] depends on dp[l+1][r], dp[l][r-1]` | Burst balloons |
| **Multi-choice** | `dp[i] depends on all dp[i-coin]` | Coin change |
| **State Transition** | `dp[i][s] depends on dp[i-1][prev_state]` | Stock trading |

**Example: Coin Change**
```
To make amount A:
  - Use coin 1: need amount A-1 → dp[A] comes from dp[A-1]
  - Use coin 2: need amount A-2 → dp[A] comes from dp[A-2]
  - Use coin 5: need amount A-5 → dp[A] comes from dp[A-5]
  - etc.

dp[A] depends on multiple previous amounts
```

---

### 4. IDENTIFY THE GOAL

**Ask: What metric am I ultimately optimizing?**

| Goal | Metric | Aggregation | Example |
|---|---|---|---|
| **Count ways** | Number of paths/solutions | `+` (addition) | Climbing stairs |
| **Minimize cost** | Lowest value | `Math.min()` | Coin change, edit distance |
| **Maximize value** | Highest value | `Math.max()` | Max profit, longest sequence |
| **Check reachable** | Existence (true/false) | `||` (OR) | Word break, jump game |

**Example: Coin Change**
```
Goal: Minimize coins used
Metric: Count of coins
Aggregation: Math.min() to pick best option
```

**Example: Climbing Stairs**
```
Goal: Count all possible ways
Metric: Number of ways
Aggregation: + (addition) to sum all paths
```

---

### 5. IDENTIFY THE STATE & TRANSITIONS

**Step 5a: Define State**

```
State = minimum information needed to compute answer from this point
```

**Questions to ask:**
- What changes as we progress through the problem?
- What information must I track to compute next state?
- Is it 1D, 2D, or multi-dimensional?

**Example: Coin Change**
```
What changes? The remaining amount
Information needed? Only the current amount
State: dp[amount] = min coins to make this amount
```

**Step 5b: Identify Transitions**

```
Transition = how to compute current state from previous states
```

**Questions to ask:**
- Which previous states contribute to current state?
- How many choices do I have?
- What's the recurrence relation?

**Example: Coin Change**
```
Choices: Which coin to use?
For each coin c:
  dp[amount] = min(dp[amount - c] + 1)

Transition formula:
dp[i] = min(dp[i - coin] + 1 for all coins if i >= coin)
```

---

### 6. IDENTIFY AGGREGATION TYPE

**Ask: How do I combine multiple options?**

## The 4 Core Aggregation Types

### **Type 1: COUNT DP** (Aggregation: `+`)

```
Goal: How many different ways?
Aggregation: dp[i] += all contributions
Formula: dp[i] = dp[i-1] + dp[i-2] + ...
Metric: Number of paths/solutions
```

**When to use:**
- "How many ways?"
- "How many paths?"
- "Count combinations"
- "Number of sequences"

**Code Template:**
```java
public int countWays(int n, int[] choices) {
    int[] dp = new int[n + 1];
    dp[0] = 1;  // Base: one way to make 0
    
    for (int i = 1; i <= n; i++) {
        for (int choice : choices) {
            if (i >= choice) {
                dp[i] += dp[i - choice];  // Add all paths
            }
        }
    }
    return dp[n];
}
```

**Example: Climbing Stairs**
```
dp[i] = dp[i-1] + dp[i-2]  (sum all ways to reach)
```

---

### **Type 2: MIN/MAX DP** (Aggregation: `Math.min()` / `Math.max()`)

```
Goal: What's the best (smallest/largest)?
Aggregation: dp[i] = optimize(all choices)
Formula: dp[i] = Math.min/max(dp[i-choice] + cost)
Metric: Minimum/maximum value
```

**When to use:**
- "What's the minimum?"
- "What's the maximum?"
- "Find the best solution"
- "Optimize value"

**Code Template:**
```java
public int minimize(int n, int[] choices) {
    int[] dp = new int[n + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;  // Base case
    
    for (int i = 1; i <= n; i++) {
        for (int choice : choices) {
            if (i >= choice && dp[i - choice] != Integer.MAX_VALUE) {
                dp[i] = Math.min(dp[i], dp[i - choice] + 1);
            }
        }
    }
    return dp[n] == Integer.MAX_VALUE ? -1 : dp[n];
}
```

**Example: Coin Change**
```
dp[i] = min(dp[i-coin] + 1)  (pick minimum)
```

---

### **Type 3: REACHABLE DP** (Aggregation: `||`)

```
Goal: Is it possible/reachable?
Aggregation: dp[i] = true if ANY path leads to it
Formula: dp[i] = dp[i-1] || dp[i-2] || ...
Metric: Boolean (possible or not)
```

**When to use:**
- "Is it possible?"
- "Can we reach?"
- "Does path exist?"
- "Check feasibility"

**Code Template:**
```java
public boolean canReach(int n, int[] choices) {
    boolean[] dp = new boolean[n + 1];
    dp[0] = true;  // Base: we start here
    
    for (int i = 1; i <= n; i++) {
        for (int choice : choices) {
            if (i >= choice && dp[i - choice]) {
                dp[i] = true;  // At least one path exists
                break;  // Early exit: only need one path
            }
        }
    }
    return dp[n];
}
```

**Example: Word Break**
```
dp[i] = true if we can break s[0...i)
dp[i] = any(dp[j] && s[j...i) in dictionary)
```

---

### **Type 4: COMPLEX DP** (Aggregation: Hybrid / Multi-dimensional)

```
Goal: Multiple dimensions, states, or constraints
Aggregation: Depends (min/max/+ with 2D+ states)
Formula: dp[i][j][...] computed from neighbors
Metric: Varies by problem
```

**When to use:**
- 2D DP (comparing sequences)
- State machine DP (multiple states)
- Tree DP (node-based)
- Bitmask DP (subset selection)

**Code Template:**
```java
public int complexDP(int[] input1, int[] input2) {
    int n = input1.length;
    int m = input2.length;
    int[][] dp = new int[n + 1][m + 1];
    
    // Base cases
    for (int i = 0; i <= n; i++) dp[i][0] = /* value */;
    for (int j = 0; j <= m; j++) dp[0][j] = /* value */;
    
    // Transitions
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            // Compute from neighbors
            dp[i][j] = /* combine dp[i-1][j], dp[i][j-1], dp[i-1][j-1] */;
        }
    }
    
    return dp[n][m];
}
```

**Example: Edit Distance**
```
dp[i][j] = min cost to transform s1[0...i) to s2[0...j)
           = min(replace, insert, delete, match)
```

---

### 7. OPTIMIZATION & IMPLEMENTATION

**Before coding, decide:**

| Aspect | Decision |
|--------|----------|
| **Approach** | Top-down (recursion + memo) vs. Bottom-up (iteration) |
| **Space** | Can we optimize? Rolling arrays? |
| **Edge cases** | Empty input? Impossible cases? Overflow? |

**Code Checklist:**
```
□ State clearly defined with comments
□ Base cases explicitly handled
□ Transitions match state definition
□ Aggregation matches goal (+ / min / max / ||)
□ Handle invalid cases (return -1, false, etc.)
□ Optimize space if possible
□ Test with examples
```

---

### 8. COMMON ISSUES & DEBUGGING

**If Wrong Answer:**
```
❌ "My answer is 0 or negative when it shouldn't be"
   → Check: Did you initialize base cases?
   → Check: Is the state definition right?

❌ "My answer is off by one"
   → Check: Off-by-one in loop bounds?
   → Check: Did you handle empty input?

❌ "Some test cases fail"
   → Check: Did you validate transitions?
   → Check: Did you handle impossible cases?
```

**If Time Limit Exceeded:**
```
❌ "Solution is too slow"
   → Check: Complexity analysis correct? O(n²) vs O(n)?
   → Check: Are you recomputing same subproblems?
   → Check: Can you use memoization or optimization?
```

**If Memory Limit Exceeded:**
```
❌ "Solution uses too much memory"
   → Check: Can you use rolling array?
   → Check: Do you need the full dp table?
   → Check: Can you optimize 2D to 1D?
```

---

## 🗺️ DP Variants Map

| Variant | State Pattern | Use Case | Difficulty | Google Frequency |
|---------|---|---|---|---|
| **Linear DP** | `dp[i]` | 1D sequence problems | Easy | ⭐⭐⭐⭐⭐ |
| **Sequence DP** | `dp[i][j]` | Compare two sequences | Medium | ⭐⭐⭐⭐⭐ |
| **Grid DP** | `dp[i][j]` | 2D grid paths | Medium | ⭐⭐⭐⭐ |
| **Knapsack** | `dp[i][w]` | Selection with capacity | Medium | ⭐⭐⭐⭐ |
| **State Machine** | `dp[i][s]` | Multiple discrete states | Medium | ⭐⭐⭐⭐ |
| **Interval DP** | `dp[l][r]` | Range/interval problems | Hard | ⭐⭐ |
| **Tree DP** | `dp[node]` | Tree recursion | Medium | ⭐⭐⭐ |
| **Bitmask DP** | `dp[mask]` | Subset/assignment | Hard | ⭐⭐ |

---

## 💡 Real Problem Examples

### Example 1: Climbing Stairs (LC70)

**1. CLARIFICATION**
```
Q: Can climb 1 or 2 steps at a time?
Q: How many ways to reach step n?
Q: Answer can be large?
A: Yes / Count all ways / Use modulo if needed
```

**2. ENTITY**
```
Entity: step number (i)
Meaning: Number of ways to reach step i
```

**3. RELATIONSHIP**
```
To reach step i, must come from:
  - Step i-1 (take 1 step) 
  - Step i-2 (take 2 steps)
```

**4. GOAL**
```
Goal: Count all possible ways
Metric: Number of paths
Aggregation: Addition (+)
```

**5. STATE & TRANSITIONS**
```
State: dp[i] = ways to reach step i
Transition: dp[i] = dp[i-1] + dp[i-2]
Base: dp[0]=1, dp[1]=1
```

**6. AGGREGATION**
```
Type: COUNT DP (addition)
All paths must be counted
```

**Complete Code:**
```java
class Solution {
    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        
        return dp[n];
    }
}
```

---

### Example 2: Coin Change (LC322)

**1. CLARIFICATION**
```
Q: Each coin can be used unlimited times?
Q: Find minimum number of coins?
Q: What if amount is impossible?
A: Yes / Count / Return -1
```

**2. ENTITY**
```
Entity: amount
Meaning: Min coins to make this amount
```

**3. RELATIONSHIP**
```
To make amount A, can use any coin C:
  A = C + (A-C)  ← need to solve subproblem
```

**4. GOAL**
```
Goal: Minimize coins used
Metric: Number of coins
Aggregation: Minimum (Math.min())
```

**5. STATE & TRANSITIONS**
```
State: dp[i] = min coins to make amount i
Transition: dp[i] = min(dp[i-coin] + 1 for all coins)
Base: dp[0] = 0
```

**6. AGGREGATION**
```
Type: MIN/MAX DP
Pick the best (minimum) from all choices
```

**Complete Code:**
```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
```

---

### Example 3: Best Stock Trading II (LC122)

**1. CLARIFICATION**
```
Q: Can do unlimited buy/sell transactions?
Q: Can't hold multiple stocks?
Q: Can buy/sell same day?
A: Yes / Yes / No
```

**2. ENTITY**
```
Entity: (day, holding_state)
Meaning: Max profit at day i if in state
  State 0: NOT holding stock
  State 1: HOLDING stock
```

**3. RELATIONSHIP**
```
If NOT holding on day i:
  - Already not holding day i-1 (rest)
  - OR was holding, sold today

If HOLDING on day i:
  - Already holding day i-1 (rest)
  - OR bought today (must have not held)
```

**4. GOAL**
```
Goal: Maximize profit
Metric: Profit amount
Aggregation: Maximum (Math.max())
```

**5. STATE & TRANSITIONS**
```
State:
  dp[i][0] = max profit on day i if NOT holding
  dp[i][1] = max profit on day i if HOLDING

Transitions:
  dp[i][0] = max(dp[i-1][0], dp[i-1][1] + price[i])
  dp[i][1] = max(dp[i-1][1], dp[i-1][0] - price[i])

Base:
  dp[0][0] = 0
  dp[0][1] = -price[0]
```

**6. AGGREGATION**
```
Type: COMPLEX DP with state machine
Pick best option (maximum) for each state
```

**Complete Code:**
```java
class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length < 2) return 0;
        
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        
        for (int i = 1; i < prices.length; i++) {
            // Not holding: either already not holding, or sell today
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
            
            // Holding: either already holding, or buy today
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
        }
        
        return dp[prices.length - 1][0];
    }
}
```

---

## 🎬 The DP Decision Tree

```
See DP problem
  │
  ├─ What's the goal?
  │
  ├─ Count ways? ────────→ Type 1: COUNT DP (use +)
  │
  ├─ Min/Max value? ────→ Type 2: MIN/MAX DP (use Math.min/max)
  │
  ├─ Check reachable? ──→ Type 3: REACHABLE DP (use ||)
  │
  └─ Multiple dimensions?
     ├─ Two sequences? ──→ Sequence DP (2D)
     ├─ Grid? ──────────→ Grid DP (2D)
     ├─ State machine? ─→ State Machine DP (2D+)
     ├─ Tree? ──────────→ Tree DP
     └─ Subset? ────────→ Bitmask DP
```

---

## 📋 Interview Cheat Sheet

**BEFORE solving any DP problem:**

```
□ Step 1: CLARIFICATION
  □ What exactly is being optimized?
  □ Any constraints on input?
  □ What about edge cases?

□ Step 2: IDENTIFY ENTITY
  □ What represents a subproblem?
  □ What's changing?

□ Step 3: IDENTIFY RELATIONSHIPS
  □ How do subproblems depend on each other?

□ Step 4: IDENTIFY GOAL
  □ Count? Min? Max? Reachable?
  □ This determines aggregation type!

□ Step 5: STATE & TRANSITIONS
  □ What's my dp[i] (or dp[i][j])?
  □ What's the recurrence formula?

□ Step 6: AGGREGATION TYPE
  □ Type 1: COUNT (use +)
  □ Type 2: MIN/MAX (use Math.min/max)
  □ Type 3: REACHABLE (use ||)
  □ Type 4: COMPLEX (combine above)

□ Step 7: CODE
  □ Initialize base cases
  □ Implement transitions
  □ Return answer

□ Step 8: DEBUG
  □ Test with small examples
  □ Check edge cases
```

---

## 🚀 47 Essential DP Problems by Type

### Type 1: COUNT DP
LC70, LC62, LC63, LC509, LC377, LC91, LC39, LC1611

### Type 2: MIN/MAX DP  
LC322, LC53, LC300, LC1014, LC121, LC122, LC123, LC188, LC309, LC714, LC312, LC1143, LC97, LC10, LC44, LC72, LC87, LC115

### Type 3: REACHABLE DP
LC139, LC140, LC55, LC45, LC279, LC1411, LC2466, LC1269

### Type 4: COMPLEX DP
LC198, LC213, LC256, LC265, LC64, LC174, LC221, LC85, LC1092, LC516, LC337, LC1372, LC377, LC943

---

## 📈 7-Day Practice Plan

**Day 1:** LC70, LC62 (COUNT DP - understand addition aggregation)
**Day 2:** LC322, LC53 (MIN/MAX DP - understand Math.min/max)
**Day 3:** LC139, LC55 (REACHABLE DP - understand OR aggregation)
**Day 4:** LC121, LC122 (STATE MACHINE - add state dimension)
**Day 5:** LC64, LC72 (2D DP - handle 2D state space)
**Day 6:** LC198, LC309 (COMPLEX - combine concepts)
**Day 7:** Review + Space optimization

---

## 🏆 Why This Framework Works

Once this becomes automatic, you:
- ✅ Solve ANY DP problem systematically
- ✅ Understand WHY (not just code)
- ✅ Handle follow-ups confidently
- ✅ Interview at Google level

---

**Master the 8-step framework. Master DP. Master interviews.** 🚀
