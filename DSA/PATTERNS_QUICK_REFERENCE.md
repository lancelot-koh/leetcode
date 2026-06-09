# 🎯 Complete Patterns Reference - Fast Review & Interview Prep

**Covers ALL 23 patterns from 67 LeetCode problems across 3 files**
**Use before solving, during coding, before interviews**

---

## ARRAY PATTERNS

### 1. SLIDING WINDOW | O(n) time
**When:** "longest/shortest subarray/substring", "at most k", "contains all"

💡 **Core Idea:** Use two pointers to create a "window" that slides right. When window becomes invalid (too long, violates constraint), shrink from left until valid. Only move left when necessary — never backtrack.

🎯 **Why it works:** Like a sliding frame — expand to the right to capture more elements, shrink from left only when you have "too much". Each element enters once (right pointer) and exits once (left pointer) → O(n) instead of O(n²).

🌍 **Real-world analogy:** A camera lens scanning a movie frame by frame. You expand the frame right to see more, contract left when frame gets too wide. Each pixel is "processed" once moving right, once moving left — total O(n).

```
State: (left, right)
Fix: left += 1 only when invalid
Aux: Set/Map/Counter (O(1) check)
```
**Variants:** 
- Fixed Size: Exactly k elements (simple, just slide)
- Variable Size: At most k distinct, longest valid substring
- At Most K: Count substrings with condition ≤ k
- Min Window: Smallest substring containing all targets
- Deque: Max/min in sliding window

---

### 2. PREFIX SUM | O(n) preprocess + O(1) query
**When:** "subarray sum", "range sum", "divisible by k"

💡 **Core Idea:** Precompute cumulative sums once. Then any range query = two lookups (prefix[right] - prefix[left]). The magic: if you want sum=k, look for (currentSum - k) in a map — found it? Match found!

🎯 **Why it works:** Trades O(n) preprocessing for O(1) queries. Instead of summing a range each time (O(n)), just subtract two prefix values. Combined with HashMap, you solve "find subarray with sum k" in one pass.

🌍 **Real-world analogy:** Cumulative distance markers on a highway. To find distance from mile 20 to mile 50, don't measure again — just subtract the two markers (50 - 20). Much faster than measuring each segment.

```
prefix[i] = prefix[i-1] + nums[i]
Query: sum(l, r) = prefix[r+1] - prefix[l]
Map trick: if prefix[i] - k exists in map → found answer
```
**Variants:** 
- 1D Array: Basic prefix, just queries
- HashMap: Find subarray with target sum in one pass
- Modulo: Subarray sum divisible by k
- 2D Array: Range sum in matrix
- Balance: Subarrays with equal 0s and 1s

---

### 3. TWO POINTERS | O(n) time
**When:** "two numbers", "pair", "palindrome", sorted array

💡 **Core Idea:** Use one pointer at each end. Move toward center. If condition is met, move one direction; otherwise move other. The key: you never backtrack because array is sorted — moving in one direction means you can "eliminate" candidates.

🎯 **Why it works:** Sorted array property ensures monotonicity — moving left pointer right = all smaller sums/values; moving right pointer left = all larger. No candidate is ever revisited → O(n).

🌍 **Real-world analogy:** Finding a pair of shoes from left and right end of a line. If current pair is too small, go one step inward from left. If too large, step inward from right. Never go backward.

```
left = 0, right = n-1
while (left < right):
    if condition: right--
    else: left++
```

---

### 4. FAST SLOW POINTER | O(n) time
**When:** "cycle detection", "linked list", "middle element"

💡 **Core Idea:** Two pointers move at different speeds (1x and 2x). In a cycle, they must collide (pigeonhole principle). Once they meet, reset one pointer and move both at 1x to find where cycle enters.

🎯 **Why it works:** In a cycle, the faster pointer "laps" the slower one. The collision point encodes cycle properties. Mathematical trick: distance from collision to entrance = distance from head to entrance.

🌍 **Real-world analogy:** Two runners on a circular track. Fast runner runs 2x speed. They'll eventually meet. Once they meet, you know there's a cycle. To find where the cycle starts, have one start from beginning, both run at same speed — they meet at the cycle entrance.

```
slow += 1, fast += 2
fast == slow → cycle found
Reset slow, both += 1 → find entrance
```

---

### 5. GREEDY | Depends
**When:** "maximize/minimize", "local optimal → global optimal"

💡 **Core Idea:** At each step, make the locally optimal choice (best right now). Hope that leads to globally optimal solution. It doesn't always work — you MUST prove it works for your problem.

🎯 **Why it matters:** When greedy works, it's extremely efficient (often O(n) or O(n log n)). The catch: greedy fails silently. It gives an answer, but not the optimal one. Proof required.

🌍 **Real-world analogy:** Coin change — always take largest coin first? Works for {1,5,10,25}. Fails for {1,3,4} making 6 (greedy gives 4+1+1=3 coins, optimal is 3+3=2 coins). Must verify first.

```
Each step: choose best option now
Key: PROVE greedy works before coding
```

---

### 6. BINARY SEARCH ON ANSWER | O(n log max)
**When:** "minimize maximum", "find threshold", monotonic property

💡 **Core Idea:** Don't search for "the thing" directly. Instead, search for "the threshold" — what's the minimum value where "can I achieve this?" is true? Use monotonicity: if canAchieve(mid)=true for some mid, it's true for all larger values too.

🎯 **Why it works:** Reframe optimization as a decision problem. Answer space is monotonic (if x works, all y>x work). Binary search finds the boundary in O(log n) by elimination.

🌍 **Real-world analogy:** Finding your height. Instead of guessing "I'm 5'10"", ask "Am I taller than 5'?" — YES. "Taller than 6'?" — NO. Binary search the threshold. Much faster than linear guessing.

```
left = 1, right = max
while left < right:
    mid = left + (right - left) / 2
    if canAchieve(mid): right = mid
    else: left = mid + 1
```

---

## TREE & GRAPH PATTERNS

### 7. BFS | O(V+E) time
**When:** "shortest path (unweighted)", "level order", "multi-source"

💡 **Core Idea:** Expand layer by layer outward. All nodes at distance d are processed before distance d+1. First time you reach a node = shortest path (no edge weights). Queue ensures layer-by-layer expansion.

🎯 **Why it works:** Unweighted graphs = all edges cost 1. Expanding outward layer-by-layer guarantees closest nodes are processed first. No backtracking needed.

🌍 **Real-world analogy:** Spreading infection or fire. Start at source. All people at distance 1 get infected today. Tomorrow, all at distance 2. BFS naturally models this spread.

```
Queue<State> q
visited.add(start)
while !q.empty():
    curr = q.poll()
    for neighbor in neighbors:
        if !visited: visited.add(neighbor), q.add(neighbor)
```
**Variants:** 
- Grid (4-dir): Checkerboard movement, visit neighbors
- Graph: Arbitrary edges, connected components
- Word Ladder: String transformation, intermediate words
- State Space: Abstract states (lock combo, chemical reactions)
- Bitmask: Visited nodes encoded as bits
- Multi-source: Multiple starting points simultaneously
- Bidirectional: Search from both start and end

---

### 8. DFS | O(V+E) time
**When:** "connectivity", "path exists", "traversal"

💡 **Core Idea:** Go as deep as possible along one path before backtracking. Mark nodes visited to avoid revisiting. Recursion naturally handles backtracking via call stack.

🎯 **Why it works:** Explores all reachable nodes from start. Mark visited = ensure O(V+E) (each node/edge visited once). Recursion makes code simple.

🌍 **Real-world analogy:** Exploring a maze. Pick one path, go as far as you can. Hit dead end? Backtrack and try another path. DFS naturally models maze exploration.

```
void dfs(node, visited):
    if visited: return
    visited.add(node)
    for neighbor: dfs(neighbor, visited)
```
**Variants:**
- Tree Traversal: Pre/In/Post order, collect values
- Graph Connectivity: Find connected components
- Topological Sort: Order tasks by dependencies
- Cycle Detection: Find cycles in graphs
- Flood Fill: Color regions (like paint bucket)
- Path Finding: All paths between nodes

---

### 9. BACKTRACKING | O(2^n) or O(n!)
**When:** "all possibilities", "permutation/combination/subset"

💡 **Core Idea:** Explore decision tree systematically. Make choice → recurse → undo choice → try next choice. The "undo" is critical: it lets you reuse same path list for different branches without conflicts.

🎯 **Why it works:** By undoing choices, you can explore all 2^n subsets or n! permutations without allocating n copies. One path list serves all branches. Exponential by nature.

🌍 **Real-world analogy:** Building a menu of meal combinations. Choose appetizer → main → dessert → record combo → undo dessert and try next → undo main → try next main. "Undo" lets you reuse the same choices list.

```
void backtrack(path, remaining):
    if done: result.add(path)
    for choice in remaining:
        path.add(choice)
        backtrack(path, remaining - choice)
        path.remove(choice)  ← KEY: undo
```
**Variants:**
- Permutation: All orderings of elements (n!)
- Combination: All subsets of size k
- Subset: All possible subsets (2^n)
- Partition: Split into groups with constraints
- Word Search: Find path in grid matching string
- N-Queens: Place N queens on chessboard safely

---

### 10. HEAP / PRIORITY QUEUE | O(log n) per op
**When:** "top k", "k-th largest", "merge k lists"

💡 **Core Idea:** Use a min-heap of size k. Add new elements. If size exceeds k, pop the smallest. Heap always maintains the k largest elements. Min-heap's root = k-th largest (the smallest of the large k).

🎯 **Why it works:** Heap gives O(log k) insert/delete. You scan n elements once (O(n log k) total). Space = O(k) only. Better than sorting (O(n log n)) if k << n.

🌍 **Real-world analogy:** Grading papers. You want "top 10 best" from 1000 submissions. Keep a list of 10 best. Each new submission: if better than worst-of-10, replace worst. At end, 10 best are sorted.

```
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for num: minHeap.offer(num)
if size > k: minHeap.poll()
// minHeap contains k largest
```

---

### 11. DIJKSTRA | O((V+E) log V)
**When:** "shortest path (weighted, non-negative)"

💡 **Core Idea:** Greedily pick closest unvisited node. Relax edges from it (try shorter paths). Heap ensures you always process the minimum-distance node next. Works only if all weights ≥ 0.

🎯 **Why it works:** Greedy + monotonicity. Once you pop a node from heap, its distance is final (no edge can improve it further, since all weights ≥ 0). Heap + relaxation = O((V+E) log V).

🌍 **Real-world analogy:** Road trip planning. Start at city A. Always drive to the nearest city you haven't visited yet. From there, update distances to remaining cities. Eventually, you've found shortest paths to all cities.

```
PQ<(dist, node)>
dist[all] = INF, dist[src] = 0
while !pq.empty():
    (d, u) = pq.poll()
    if d > dist[u]: continue
    for (v, w) in edges[u]:
        if dist[u] + w < dist[v]:
            dist[v] = dist[u] + w
            pq.add((dist[v], v))
```

---

### 12. MONOTONIC STACK | O(n) time
**When:** "next greater/smaller", "histogram", "temperature"

💡 **Core Idea:** Maintain stack in decreasing order. For each new element: pop all smaller/equal elements (they found their "next greater" = current element). Push current. Stack always has candidates, smallest at top.

🎯 **Why it works:** Each element is pushed once and popped once → O(n). When you pop element x because you see larger element y, you know y is x's next greater. No need to search.

🌍 **Real-world analogy:** Standing in line facing a stage. You check if the person ahead is shorter than you. If yes, you can see the stage through them — remove them from your sightline. Repeat until you're at the front or someone taller is ahead.

```
Stack<Integer> st
for i in n-1 to 0:
    while !st.empty() && st.top() <= nums[i]:
        st.pop()  ← remove smaller
    result[i] = st.empty() ? -1 : st.top()
    st.push(nums[i])
```

---

### 13. TRIE | O(m) where m = word length
**When:** "prefix search", "dictionary", "autocomplete"

💡 **Core Idea:** Tree of characters. Each path from root = a word. Shared prefixes = shared branches (compression). To find all words with prefix "cat", follow the "cat" path, then DFS from there.

🎯 **Why it works:** Traditional HashMap lookup = O(m). Trie lookup also O(m) but with prefix sharing. Benefits: range queries (all words starting with "cat"), autocomplete, spell-checking — all natural.

🌍 **Real-world analogy:** Phone directory. Root = area code. Each branch = next digit. To find all numbers starting with "415", follow "415" path, explore sub-tree. Shared digits = shared branches.

```
class TrieNode:
    children: TrieNode[26]
    isWord: boolean

insert(word):
    node = root
    for c in word:
        if !node.children[c]: 
            node.children[c] = new TrieNode()
        node = node.children[c]
    node.isWord = true
```

---

### 14. UNION FIND | O(α(n)) ≈ O(1)
**When:** "connected components", "cycle detection", "grouping"

💡 **Core Idea:** Each element has a parent. To check if two elements are in same group, find their root parent. To merge groups, make one root point to other root. Path compression (flattening) keeps tree flat.

🎯 **Why it works:** With path compression + union by rank, nearly every operation is O(1). Initially thought to be the answer to "are A and B connected?" and "connect them" in one structure.

🌍 **Real-world analogy:** Company organizational chart. Employees report to managers. To check if two people are in same department, trace up to department head. To merge departments, make one head report to other head.

```
class UnionFind:
    find(x): return x if parent[x]==x else find(parent[x])
    union(x, y): 
        px, py = find(x), find(y)
        parent[px] = py
```

---

## DP PATTERNS

### 15. BASIC DP | O(n)
**When:** "optimal value", "overlapping subproblems"

💡 **Core Idea:** Define dp[i] = answer at position i. Build from small to large. Each dp[i] depends only on previous states (dp[i-1], etc.). Overlapping subproblems = reuse computation.

🎯 **Why it works:** Instead of recalculating same subproblems exponentially, compute each once, store, reuse. Trade memory for time → exponential → polynomial.

🌍 **Real-world analogy:** Climbing stairs. Reaching step 5 = either from step 4 (1 step) or step 3 (2 steps). Reaching step 4 already computed? Reuse it. Don't recalculate.

```
dp[i] = best value at position i
dp[i] = f(dp[i-1], ...)
Answer: dp[n-1]
```
**Examples:** Climbing Stairs, House Robber, Stock Trading

---

### 16. DP ON GRAPH | O(V+E)
**When:** "graph traversal", "dependency"

💡 **Core Idea:** Apply DP to graph structure. Use memoization: dp[node] = optimal from this node. DFS explores, memoization prevents recalculation. Works best on DAGs (directed acyclic graphs).

🎯 **Why it works:** Like basic DP but on graph instead of array. Memoization handles arbitrary dependency order. DFS+memo = clean, natural code.

🌍 **Real-world analogy:** Project dependencies. Task A depends on B and C. Best way to finish A = finish B and C first. Memoize: "best time to finish B" already calculated? Reuse.

```
Topological sort + DFS memo
dp[node] = optimal value from node
```

---

### 17. INTERVAL DP | O(n³)
**When:** "range optimization", "burst balloons"

💡 **Core Idea:** dp[i][j] = optimal for interval [i,j]. Try every split point k: solve left [i,k], solve right [k+1,j], combine. Two dimensions = ranges, one loop = split points.

🎯 **Why it works:** Range problems often decompose: solve left part, right part, combine. Three nested loops = O(n³) but covers all subproblems.

🌍 **Real-world analogy:** Cutting a cake into pieces. Best way to cut [0,10] = try cutting at 3 (solve [0,3] and [4,10]), try cutting at 5 (solve [0,5] and [6,10]), pick best.

```
dp[i][j] = optimal for interval [i, j]
for k in i..j:
    dp[i][j] = min/max(dp[i][k], dp[k+1][j], cost)
```

---

### 18. STATE MACHINE DP | O(n * states)
**When:** "stock trading", "state transitions", "limited states"

💡 **Core Idea:** Add a state dimension: dp[i][state] = optimal at position i in this state. State transitions are explicit (buy→hold, hold→sell, etc.). Track all states across time.

🎯 **Why it works:** Problems with finite, clear states (buy/sell/hold, 0-holds/1-holds/2-holds, etc.). DP naturally tracks each state evolution. Clean, systematic.

🌍 **Real-world analogy:** Stock trading. Day i, state="holding". Optimal profit = either carry from day i-1 holding, or buy today. Day i, state="sold". Optimal = carry from holding (sold), or do nothing (already sold).

```
dp[i][state] = optimal at day i, state
State transitions are clear (buy/sell/hold)
```

---

### 19. BITMASK DP | O(2^n * n)
**When:** "visit all nodes", "subset", "TSP"

💡 **Core Idea:** Use bit to represent which nodes visited (mask). dp[mask][i] = optimal having visited nodes in mask, currently at node i. Iterate all 2^n masks, all nodes, try all transitions.

🎯 **Why it works:** Bitmask naturally encodes "visited set". DP tracks all possible masks and positions. Solves traveling salesman, visiting all nodes in optimal cost.

🌍 **Real-world analogy:** Traveling salesman. Mask = "which cities visited". Currently at city i. Next, go to city j (if not visited yet). Update: "having visited mask | city j, now at j" = new cost.

```
dp[mask][i] = optimal visiting mask, currently at i
For each mask:
    for each node i in mask:
        for next node j:
            newMask = mask | (1 << j)
            dp[newMask][j] = min(dp[newMask][j], dp[mask][i] + cost)
```

---

### 20. MEMOIZED DFS (TOP-DOWN DP) | Depends
**When:** "recursive structure", "overlapping subproblems"

💡 **Core Idea:** Write recursive solution naturally. Before computing state, check memo: already solved? Return. Otherwise compute, store in memo, return. DFS explores, memo prevents re-exploration.

🎯 **Why it works:** More intuitive than bottom-up DP. Natural recursion + memoization = avoids exponential blowup. Only computes reachable states (vs. bottom-up which computes all).

🌍 **Real-world analogy:** Asking friends for advice. Call friend A. A says "ask B and C". You memoize "A says B+C". Later, friend D also says "ask B and C". You recall memo: already asked B and C, just reuse answer.

```
memo[state] = -1  // uncomputed
dfs(state):
    if memo[state] != -1: return memo[state]
    result = compute...
    return memo[state] = result
```

---

## DATA STRUCTURE PATTERNS

### 21. MONOTONIC QUEUE/DEQUE | O(n)
**When:** "sliding window max", "deque optimization"

💡 **Core Idea:** Like monotonic stack but with deque (double-ended). Remove expired elements from front (outside window), remove smaller elements from back. Queue maintains max/min in window in decreasing/increasing order.

🎯 **Why it works:** Deque allows removal from both ends. Front = oldest (may expire). Back = smaller (useless if new larger element exists). Each element added once, removed once → O(n).

🌍 **Real-world analogy:** Movie theater. You maintain list of "tallest people". New person enters: if shorter than last person, add to back. If taller, remove shorter people from back. People at front age-out (leave theater) from window.

```
Same as Monotonic Stack but with deque
Remove from both ends: front (expired), back (smaller)
```

---

### 22. SEGMENT TREE | O(log n) query/update
**When:** "range query", "point update"

💡 **Core Idea:** Binary tree where each node = aggregate of range (sum, min, max). To query [l,r], combine left/right subtrees. To update one element, update path from leaf to root. Logarithmic depth → O(log n).

🎯 **Why it works:** Tree structure allows divide-and-conquer. Query ranges by combining node aggregates (not sum all). Update propagates logarithmically up.

🌍 **Real-world analogy:** Regional weather reports. Leaf = one city's temp. Node = average of left/right region. To get average temp of region [city 10 - city 20], combine nodes (not sum all 10 cities). To update city 15, update its path up.

```
Build tree recursively
Query: combine left subtree + right subtree
Update: path from leaf to root
```

---

### 23. INTERVAL SWEEP LINE | O(n log n)
**When:** "overlapping intervals", "meetings", "skyline"

💡 **Core Idea:** Convert intervals to events (start/end). Sort events. Sweep left to right. At each event, update "active" count. Sweep line naturally handles overlaps without checking all pairs.

🎯 **Why it works:** Naively checking all pairs = O(n²). Sweep line + events = O(n log n) sort + O(n) sweep. Events encode overlaps implicitly.

🌍 **Real-world analogy:** Concert venue. Each band = interval [start_time, end_time]. Convert to events. Sweep time forward. Count how many bands playing simultaneously. No need to check every pair.

```
Create events (start/end)
Sort by time
Process events, maintain active count
```

---

## 🔍 VARIANTS DEEP DIVE (40+ Pattern Variants)

### SLIDING WINDOW VARIANTS

#### 1A. FIXED SIZE WINDOW | O(n)
**Idea:** Maintain exactly k elements. Slide right, then move left. Simple version of sliding window.
**When:** "Average of subarrays of size k", "Max of all subarrays of size k"
**Code:** `while right-left+1 > k: left++`

#### 1B. VARIABLE SIZE WINDOW | O(n)
**Idea:** Find longest substring satisfying condition. Expand right until invalid, then shrink left.
**When:** "Longest substring without repeating chars", "Longest substring with k distinct chars"
**Code:** Track condition in map/set, shrink when violated

#### 1C. AT MOST K VARIANT | O(n)
**Idea:** Count subarrays/substrings where condition ≤ k. Use: `count(≤k) - count(≤k-1)`
**When:** "Number of substrings with at most k distinct characters"
**Insight:** Reduces to two "at most" problems

#### 1D. MINIMUM WINDOW SUBSTRING | O(n)
**Idea:** Find smallest window containing all target characters. Expand to include all, shrink to minimize.
**When:** "Smallest substring containing all chars from another string"
**Trick:** Track required characters and formed characters count

#### 1E. SLIDING WINDOW WITH DEQUE | O(n)
**Idea:** Deque maintains candidates for max/min in current window.
**When:** "Sliding window maximum", "Sliding window median"
**Structure:** Deque stores indices, remove expired (left) and smaller (right)

---

### PREFIX SUM VARIANTS

#### 2A. PREFIX SUM WITH HASHMAP | O(n)
**Idea:** If `prefix[i] - k` exists, found subarray with sum=k.
**When:** "Subarray sum equals k", "Maximum subarray sum divisible by k"
**Trick:** Store prefix sums in map, look up (current - target)

#### 2B. 2D PREFIX SUM | O(n×m)
**Idea:** `prefix[i][j] = sum of rectangle [0,0] to [i,j]`. Query: combine 4 rectangles.
**When:** "Range sum query in 2D matrix"
**Formula:** `sum(r1,c1,r2,c2) = prefix[r2][c2] - prefix[r1-1][c2] - prefix[r2][c1-1] + prefix[r1-1][c1-1]`

#### 2C. PREFIX SUM MODULO | O(n)
**Idea:** If two prefixes have same (prefix % k), subarray between them divisible by k.
**When:** "Subarray sum divisible by k"
**Insight:** Pigeonhole: if k+1 prefixes exist, two must share remainder

#### 2D. CONTINUOUS SUBARRAY WITH BALANCE | O(n)
**Idea:** Treat 0→-1. Find subarrays with sum=0 (equal 0s and 1s).
**When:** "Max length contiguous subarray with equal 0s and 1s"
**Mapping:** 0→-1, problem becomes "find longest subarray with sum=0"

#### 2E. CUMULATIVE FREQUENCY | O(n)
**Idea:** Similar to prefix but tracks frequency of each value encountered.
**When:** "Count pairs with specific XOR result"
**Usage:** XOR problems often combined with prefix/cumulative frequency

---

### TWO POINTERS VARIANTS

#### 3A. SORTED PAIR SUM | O(n)
**Idea:** Left at start, right at end. Move toward center based on sum.
**When:** "Two sum sorted array", "3Sum", "4Sum"
**Key:** Never backtrack, monotonicity of sum as pointers move

#### 3B. CONTAINER WITH MOST WATER | O(n)
**Idea:** Two pointers at ends. Always move smaller pointer inward (larger pointer won't help).
**When:** "Maximum area between vertical lines"
**Insight:** Greedy: moving larger pointer only shrinks area, move smaller

#### 3C. PALINDROME CHECK | O(n)
**Idea:** Check if characters match from both ends inward.
**When:** "Valid palindrome", "Longest palindromic substring (with center expansion)"
**Variant:** Skip non-alphanumeric characters, case-insensitive

#### 3D. PARTITION/SORT-LIKE MOVEMENT | O(n)
**Idea:** Use two pointers to partition array in-place (like quicksort partition).
**When:** "Sort by color", "Move zeros to end", "Partition array by pivot"
**Pattern:** Left finds element that shouldn't be here, right finds replacement

#### 3E. MERGE SORTED ARRAYS | O(n+m)
**Idea:** Two pointers, one per array. Compare and merge backward.
**When:** "Merge two sorted arrays in-place"
**Trick:** Merge from end to avoid overwriting

---

### BFS VARIANTS

#### 7A. GRID BFS (4-DIRECTIONAL) | O(V+E)
**Idea:** Each cell is node, 4 neighbors (up/down/left/right).
**When:** "Number of islands", "Rotting oranges", "Shortest path in grid"
**Code:** For each unvisited cell, BFS spreads to 4 neighbors

#### 7B. GRAPH BFS | O(V+E)
**Idea:** BFS on arbitrary graph (adjacency list/matrix).
**When:** "Clone graph", "Connected components in graph", "Shortest path"
**Structure:** Build adjacency list from edges first

#### 7C. WORD LADDER BFS | O(wordLen × vocabulary)
**Idea:** Nodes = words, edges = 1 character difference. Find shortest transformation.
**When:** "Word ladder", "Word ladder II (all paths)"
**Optimization:** Pre-build graph of similar words (letter patterns)

#### 7D. STATE SPACE BFS | O(states × transitions)
**Idea:** Abstract states (not physical), transitions between states.
**When:** "Open the lock (combination lock states)", "Sliding puzzle", "Chemical reactions"
**Technique:** State = encoded string/tuple, transition = valid moves

#### 7E. BITMASK BFS | O(2^n × n)
**Idea:** State = (bitmask of visited nodes, current node). Traverse all subsets.
**When:** "Shortest path visiting all nodes", "Travelling salesman (shortest)"
**Mask:** Bit i=1 means node i visited

#### 7F. MULTI-SOURCE BFS | O(V+E)
**Idea:** Multiple starting points simultaneously. Add all sources to queue.
**When:** "Rotting oranges (multiple rotten)", "Walls and gates (all gates source)"
**Key:** All sources at distance 0, expand together layer-by-layer

#### 7G. BIDIRECTIONAL BFS | O(V+E)
**Idea:** Search from start and end simultaneously. Meet in middle.
**When:** "Word ladder (faster than unidirectional)", "Shortest path with constraints"
**Optimization:** Can be 2× faster if branching factor is high

---

### DFS VARIANTS

#### 8A. TREE TRAVERSAL | O(n)
**Idea:** Visit all nodes in specific order (Pre/In/Post).
**When:** "Binary tree traversal", "Build expressions from tree"
**Orders:**
- Pre-order: root → left → right (root processed first)
- In-order: left → root → right (root processed middle)
- Post-order: left → right → root (root processed last)

#### 8B. GRAPH CONNECTIVITY | O(V+E)
**Idea:** Find connected components by DFS from each unvisited node.
**When:** "Number of connected components", "Is graph connected"
**Optimization:** Mark all reachable nodes with same component ID

#### 8C. TOPOLOGICAL SORT (DFS) | O(V+E)
**Idea:** DFS, record finish time. Reverse finish order = topological order.
**When:** "Course schedule", "Build order", "Alien dictionary"
**Also called:** DFS-based topological sort (vs. Kahn's BFS)

#### 8D. CYCLE DETECTION (DFS) | O(V+E)
**Idea:** Track state: unvisited (0), visiting (1), visited (2). Back edge (→1) = cycle.
**When:** "Has cycle in directed graph", "Can finish all courses"
**Key:** Back edge (to node currently in stack) = cycle proof

#### 8E. FLOOD FILL | O(V+E)
**Idea:** DFS from starting cell, change color, spread to same-color neighbors.
**When:** "Flood fill (paint bucket)", "Number of islands (alternative to BFS)"
**Insight:** Simple DFS, natural for fill problems

#### 8F. PATH COLLECTION | O(V+E)
**Idea:** DFS tracking path, when reach destination, collect/record path.
**When:** "All paths from A to B", "Path sum", "Word search in board"
**Pattern:** Add to path → recurse → remove from path (backtracking)

---

### BACKTRACKING VARIANTS

#### 9A. PERMUTATION | O(n!)
**Idea:** Generate all orderings. Use boolean array or swap for in-place.
**When:** "Generate all permutations"
**Code:** For each unused number, use it, recurse, unuse it

#### 9B. COMBINATION | O(2^n)
**Idea:** Choose k elements from n. Start from index i, avoid duplicates by always moving forward.
**When:** "Combinations of k", "Subset sum", "Partition set"
**Pruning:** Skip index to avoid repeated combinations

#### 9C. SUBSET | O(2^n)
**Idea:** For each element, choose to include or exclude.
**When:** "All subsets", "Subset with specific property"
**Variants:** Include/exclude, or iterative (add element to all existing subsets)

#### 9D. PERMUTATION WITH DUPLICATES | O(n!)
**Idea:** Sort first, skip duplicate choices at same level.
**When:** "Permutations with duplicate elements"
**Trick:** After sorting, if nums[i]==nums[i-1] and nums[i-1] not used, skip

#### 9E. WORD SEARCH IN BOARD | O(n×m × 4^wordLen)
**Idea:** DFS from each cell, try 4 directions, mark visited, undo.
**When:** "Word search in board", "Word search II (with Trie)"
**Optimization:** Use Trie for word search II (multiple queries)

#### 9F. N-QUEENS | O(n!)
**Idea:** Place queens row by row. Check column and diagonal conflicts.
**When:** "N-Queens placement problem"
**Pruning:** Check diagonal: `|row1 - row2| == |col1 - col2|`

#### 9G. SUDOKU SOLVER | O(9^(81))
**Idea:** Try digits 1-9 in empty cells, backtrack if invalid.
**When:** "Solve sudoku"
**Pruning:** Check row, column, 3×3 box constraints

---

### DYNAMIC PROGRAMMING VARIANTS

#### 15A. 1D DP (LINEAR) | O(n)
**Idea:** `dp[i]` = optimal at position i, depends on `dp[i-1]` or previous states.
**When:** "Climbing stairs", "House robber", "Max subarray"
**Optimization:** Can optimize to O(1) space if only last few states needed

#### 15B. UNBOUNDED KNAPSACK | O(n×W)
**Idea:** Each item can be used unlimited times. `dp[w]` = max value with capacity w.
**When:** "Coin change (max coins)", "Unbounded knapsack"
**Key:** `for item: for w: dp[w] = max(dp[w], dp[w-weight[item]] + value[item])`

#### 15C. 0-1 KNAPSACK | O(n×W)
**Idea:** Each item at most once. Build 2D table or optimize to 1D (backwards).
**When:** "Partition equal subset sum", "Target sum with +/-"
**Optimization:** 1D backward: `for i: for w in reverse: dp[w] = max(dp[w], dp[w-weight[i]] + value[i])`

#### 15D. LONGEST COMMON SUBSEQUENCE (LCS) | O(n×m)
**Idea:** `dp[i][j]` = LCS length of `s1[0..i]` and `s2[0..j]`.
**When:** "Longest common subsequence", "Edit distance"
**Formula:** If `s1[i]==s2[j]`: `dp[i][j] = dp[i-1][j-1] + 1` else `dp[i][j] = max(dp[i-1][j], dp[i][j-1])`

#### 15E. LONGEST INCREASING SUBSEQUENCE (LIS) | O(n log n)
**Idea:** `dp[i]` = length of LIS ending at i. Optimize with binary search.
**When:** "Longest increasing subsequence"
**Optimization:** Maintain sorted array of ending values, binary search to update

#### 15F. HOUSE ROBBER WITH CONSTRAINT | O(n)
**Idea:** Can't rob adjacent houses. `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`
**When:** "House robber", "House robber II (circular)", "House robber III (tree)"
**Variants:** Linear, circular, tree structure

#### 15G. STOCK TRADING (MULTIPLE VARIANTS) | O(n)
**Idea:** `dp[i][state]` = max profit at day i in state (hold/sold).
**When:** "Buy/sell stock (with/without fees)", "With cooldown", "At most K transactions"
**States:** Holding/not holding, or more granular (0 hold, 1 hold, 2 hold, etc.)

---

### ADVANCED DP VARIANTS

#### 17A. INTERVAL DP | O(n³)
**Idea:** `dp[i][j]` = optimal for interval [i,j]. Try all split points k.
**When:** "Burst balloons", "Remove boxes", "Optimal matrix chain multiplication"
**Pattern:** Divide interval at each position, combine results

#### 17B. GAME DP (MINIMAX) | O(n × states)
**Idea:** `dp[state]` = best outcome for current player (or worst for opponent).
**When:** "Game theory (nim, stones, etc.)", "Predict winner"
**Formula:** Current player maximizes, opponent minimizes

#### 18A. STATE MACHINE DP | O(n × states)
**Idea:** Explicit states, transitions between states tracked in DP.
**When:** "Stock trading", "Meetings with cooldown"
**States:** Usually 2-3 explicit states with clear transitions

#### 19A. BITMASK DP | O(2^n × n)
**Idea:** `dp[mask][i]` = optimal with visited set=mask, currently at i.
**When:** "Travelling salesman (shortest path visiting all)", "Assign tasks to workers"
**Update:** For each new node j, `newMask = mask | (1<<j)`, update dp[newMask][j]

#### 20A. MEMOIZED DFS (TOP-DOWN) | Depends
**Idea:** Write recursive solution, cache results.
**When:** "Recursive structure", "Overlapping subproblems", "State space complex"
**Advantage:** Explore only reachable states (vs. bottom-up all states)

#### 19B. LONGEST PATH IN DAG | O(V+E)
**Idea:** Topological sort + DP. `dp[node]` = longest path from node.
**When:** "Longest path in DAG", "Course schedule III"
**Key:** Process in topological order, update distances

---

### GREEDY VARIANTS

#### 5A. JUMP GAME | O(n)
**Idea:** Track farthest reachable position. Can reach end? Yes if farthest ≥ n-1.
**When:** "Jump game (can reach end)", "Jump game II (min jumps)"
**Greedy:** At each step, extend farthest position

#### 5B. GAS STATION | O(n)
**Idea:** Greedy: if can't reach station, no valid start before current. Try next.
**When:** "Gas station (valid route)", "Refueling"
**Insight:** If A→B fails, no station A'<B can reach B

#### 5C. INTERVAL SCHEDULING | O(n log n)
**Idea:** Sort by end time, greedily pick non-overlapping intervals.
**When:** "Meeting rooms", "Interval scheduling maximization"
**Greedy:** Always pick earliest ending (leaves most room for others)

#### 5D. ACTIVITY SELECTION | O(n log n)
**Idea:** Same as interval scheduling. Sort by end, greedily select.
**When:** "Maximum activities", "Non-overlapping meetings"
**Proof:** Greedy earliest-end is optimal

#### 5E. HUFFMAN CODING | O(n log n)
**Idea:** Build tree by always combining two smallest frequencies.
**When:** "Minimize weighted path length", "Optimal prefix codes"
**Structure:** Use min-heap, repeatedly merge two smallest

---

### GREEDY + BINARY SEARCH

#### 6A. BINARY SEARCH ON ANSWER | O(n log max)
**Idea:** Search for threshold. Answer is binary (yes/no at threshold).
**When:** "Minimize maximum", "Maximize minimum", "Find threshold"
**Pattern:** If canAchieve(mid)=true, answer ≤ mid; search left

#### 6B. BINARY SEARCH OPTIMIZATION | O(n log n)
**Idea:** Binary search to optimize DP/greedy state transitions.
**When:** "Longest increasing subsequence (with binary search)", "Patience sorting"
**Optimization:** Instead of linear scan, use binary search on candidate array

---

### MONOTONIC STRUCTURES

#### 12A. MONOTONIC STACK (NEXT GREATER/SMALLER) | O(n)
**Idea:** Stack in decreasing order. For each element, pop all smaller, answer = top.
**When:** "Next greater element", "Daily temperatures", "Next permutation"
**Key:** Each element pushed once, popped once → O(n)

#### 12B. MONOTONIC STACK (HISTOGRAM) | O(n)
**Idea:** Find largest rectangle in histogram. Use monotonic stack of indices.
**When:** "Largest rectangle in histogram", "Maximal rectangle"
**Trick:** For each height, binary search or monotonic stack for left/right extent

#### 21A. MONOTONIC DEQUE (SLIDING WINDOW MAX) | O(n)
**Idea:** Deque maintains indices of max candidates in window.
**When:** "Sliding window maximum", "Sliding window median"
**Structure:** Remove from front (expired), remove from back (smaller values useless)

#### 21B. MONOTONIC DEQUE (OPTIMIZATION) | O(n)
**Idea:** Deque stores potential optimal next states.
**When:** "Min cost to connect all ropes", "Trapping rain water II"
**Insight:** Deque limits search space to candidates

---

### TRIE VARIANTS

#### 13A. TRIE (PREFIX SEARCH) | O(m)
**Idea:** Navigate tree by characters. At each node, explore subtree.
**When:** "Autocomplete", "Starts with prefix", "Dictionary"
**Optimization:** Mark "is word" to distinguish actual words from prefixes

#### 13B. TRIE (WORD SEARCH) | O(m × 4^len)
**Idea:** DFS in grid + Trie for valid words. Prune branches not in Trie.
**When:** "Word search II", "Boggle"
**Optimization:** Trie + DFS (vs. naive checking all words in every path)

#### 13C. TRIE (AUTOCOMPLETION WITH RANKING) | O(m)
**Idea:** Trie + count/frequency at each node for ranking results.
**When:** "Search suggestions with ranking", "Typeahead"
**Enhancement:** Store frequency/popularity at each node

---

### UNION FIND VARIANTS

#### 14A. UNION FIND (CONNECTED COMPONENTS) | O(α(n))
**Idea:** Group nodes, query if two in same group.
**When:** "Number of islands (on graph)", "Connected components"
**Use:** When edges given (not grid), or multiple queries needed

#### 14B. UNION FIND (CYCLE DETECTION) | O(α(n))
**Idea:** If find(u)==find(v) before union, edge creates cycle.
**When:** "Cycle detection in undirected graph", "Redundant connection"
**Technique:** Check before each union if already connected

#### 14C. UNION FIND WITH WEIGHTED (DISTANCE) | O(α(n))
**Idea:** Store distance from parent in union-find structure.
**When:** "Equations with variables (equal/not equal)", "Friend groups with ranks"
**Enhancement:** Track distance to root, adjust when path compress

#### 14D. UNION FIND (BIPARTITE) | O(α(n))
**Idea:** For each node, maintain "enemy" pointer alongside "friend".
**When:** "Bipartite graph check", "Possible friendships"
**Technique:** Each node has friend-union and enemy-union

---

### HEAP VARIANTS

#### 10A. TOP K ELEMENTS | O(n log k)
**Idea:** Min-heap of size k. Add elements, remove smallest when exceeds k.
**When:** "K largest elements", "K closest points"
**Optimization:** O(n log k) better than O(n log n) if k << n

#### 10B. MERGE K SORTED LISTS | O(n log k)
**Idea:** Min-heap of list heads. Pop min, add next from same list.
**When:** "Merge k sorted lists", "Find median in stream"
**Technique:** One element per list in heap at a time

#### 10C. MEDIAN IN STREAM | O(log n)
**Idea:** Max-heap (left) + min-heap (right). Keep balanced.
**When:** "Find median from data stream", "Online statistics"
**Balance:** Left.size == right.size or left.size == right.size + 1

#### 10D. SLIDING WINDOW WITH HEAP | O(n log n)
**Idea:** Heap + lazy deletion (mark expired, skip when popped).
**When:** "Sliding window median", "Sliding window average"
**Trick:** Mark expired, check top before using

---

### SEGMENT TREE & ADVANCED STRUCTURES

#### 22A. SEGMENT TREE (RANGE SUM) | O(log n)
**Idea:** Tree where each node = sum of range. Query combines left/right subtree.
**When:** "Range sum with point updates", "Mutable range query"
**Update:** Update leaf, propagate change up to root

#### 22B. SEGMENT TREE (LAZY PROPAGATION) | O(log n)
**Idea:** Segment tree + lazy flags. Don't update all nodes immediately, propagate on demand.
**When:** "Range update + range query", "Interval assignment"
**Optimization:** O(log n) for both update and query (vs. O(n) update without lazy)

#### 22C. FENWICK TREE (BINARY INDEXED TREE) | O(log n)
**Idea:** Implicit tree using index bits. More compact than segment tree.
**When:** "Range sum + point update" (simpler than segment tree)
**Code:** Shorter, O(1) space vs. O(n) for segment tree

#### 22D. SQRT DECOMPOSITION | O(√n)
**Idea:** Divide array into √n blocks. Each block stores aggregate.
**When:** "Range query + point update", "Offline queries"
**Advantage:** Easier to implement than segment tree, sufficient for many problems

---

### INTERVAL TECHNIQUES

#### 23A. SWEEP LINE (OVERLAPPING INTERVALS) | O(n log n)
**Idea:** Convert intervals to events (start/end). Sort events. Sweep and track active count.
**When:** "Meeting rooms (max concurrent)", "Skyline problem"
**Technique:** Increment on start, decrement on end

#### 23B. SKYLINE PROBLEM | O(n log n)
**Idea:** Treat buildings as start/end events. At each critical point, find max height.
**When:** "Skyline outline", "Merge intervals with heights"
**Complexity:** O(n log n) with events + multiset/heap for heights

#### 23C. INTERVAL MERGING | O(n log n)
**Idea:** Sort by start, merge overlapping intervals.
**When:** "Merge intervals", "Insert interval", "Merge interval lists"
**Greedy:** If next.start ≤ current.end, overlap; merge

---

### SPECIALIZED PATTERNS

#### MULTISOURCE BFS | O(V+E)
**Idea:** Multiple starting points. Add all to queue initially.
**When:** "0-1 BFS variant", "Multi-source shortest distance"
**Example:** "Rotting oranges" (all rotten oranges start simultaneously)

#### BIDIRECTIONAL BFS | O(V+E)
**Idea:** Search from both start and end. Meet in middle.
**When:** "Word ladder (faster)", "Shortest path with constraints"
**Optimization:** Can be 2× faster than unidirectional

#### A* SEARCH | O(E)
**Idea:** Dijkstra + heuristic. Explore nodes with lowest f=g+h.
**When:** "Pathfinding with heuristic", "8-puzzle problem"
**Heuristic:** Admissible heuristic makes A* optimal

#### SIMULATION | Depends
**Idea:** Model problem as process, simulate step-by-step.
**When:** "Robot walk", "Game simulations", "Step-by-step process"
**Technique:** Straightforward but requires careful state tracking

#### COORDINATE COMPRESSION | O(n log n)
**Idea:** Map large coordinates to small range (1..n).
**When:** "Discretization of events", "Reduce space complexity"
**Use:** When actual values don't matter, only relative order

#### CUSTOM DATA STRUCTURE | Depends
**Idea:** Design DS optimized for specific operations.
**When:** "Design URL shortener", "Design LRU cache", "Design file system"
**Technique:** Combine multiple simple structures (hash + linked list, heap + hash, etc.)

---



| Pattern | Time | Space | When |
|---------|------|-------|------|
| Sliding Window | O(n) | O(1) | Subarray/substring |
| Prefix Sum | O(n) + O(1) | O(n) | Range sum query |
| Two Pointers | O(n) | O(1) | Sorted pair |
| Fast Slow | O(n) | O(1) | Cycle detection |
| Greedy | O(n) | O(1) | Local → global |
| Binary Search | O(log n) | O(1) | Monotonic |
| BFS | O(V+E) | O(V) | Shortest (unweighted) |
| DFS | O(V+E) | O(h) | Connectivity |
| Backtracking | O(2^n) | O(n) | All combinations |
| Heap | O(log n) | O(n) | Top k |
| Dijkstra | O(VlogV) | O(V) | Shortest (weighted) |
| Monotonic Stack | O(n) | O(n) | Next greater |
| Trie | O(m) | O(26m) | Prefix search |
| Union Find | O(α(n)) | O(n) | Components |
| DP | O(n) | O(1) | Optimal value |
| Bitmask DP | O(2^n*n) | O(2^n*n) | Visit all |
| Segment Tree | O(log n) | O(n) | Range query |
| Sweep Line | O(nlogn) | O(n) | Intervals |

---

## 📋 ALL PROBLEMS BY PATTERN

### **common_pattern1.md (28 problems)**
- BFS Grid: LC200 (Islands), LC994 (Rotting Oranges)
- BFS Graph: LC133 (Clone), LC127 (Word Ladder)
- BFS State: LC752 (Lock), LC818 (Race Car)
- BFS Resource: LC787 (Flights)
- BFS Bitmask: LC847 (All Nodes)
- DFS Tree: LC200, LC112, LC543, LC261
- Backtracking: LC46-47 (Perms), LC39-40 (Combos), LC78-90 (Subsets), LC79 (Word Search)
- Heap: LC215, LC347, LC253
- Dijkstra: LC787
- Union Find: (mentioned, detailed in problems)

### **common_pattern2.md (28 problems)**
- Monotonic Stack: LC739, LC496, LC84
- Monotonic Queue: LC239
- Trie: LC212, LC1268
- Greedy: LC55, LC134
- Two Pointers: LC42, LC167
- Fast Slow: LC141, LC142, LC202
- Bitmask DP: LC847, LC864
- Memoized DFS: LC329
- DP on Graph: LC1462
- Interval DP: LC312
- State Machine DP: LC121, LC122, LC309, LC714
- Segment Tree: LC307
- Interval Sweep: LC252, LC253, LC218

### **common_pattern3.md (11 problems)**
- Sliding Window (Fixed): LC643
- Sliding Window (Variable): LC3
- Sliding Window (At Most K): LC424
- Sliding Window (Min Window): LC76
- Sliding Window (Deque): LC239
- Prefix Sum (Basic): LC303
- Prefix Sum (HashMap): LC560
- Prefix Sum (Modulo): LC974, LC523
- Prefix Sum (2D): LC304
- Prefix Sum (Balance): LC525

---

## ⚡ QUICK DECISION TREE

```
See problem keywords?
├─ "longest/shortest subarray/substring" → SLIDING WINDOW
├─ "sum", "range", "divisible", "continuous" → PREFIX SUM
├─ "shortest path" + "unweighted" → BFS
├─ "shortest path" + "weighted, non-negative" → DIJKSTRA
├─ "optimal value", "overlapping subproblems" → DP (pick variant)
├─ "all possibilities", "permutation/combination" → BACKTRACKING
├─ "next greater/smaller", "histogram" → MONOTONIC STACK
├─ "cycle", "linked list", "middle" → FAST SLOW POINTER
├─ "prefix search", "dictionary", "autocomplete" → TRIE
├─ "top k", "k-th largest" → HEAP
├─ "connected components", "cycle in graph" → UNION FIND
├─ "two numbers", "pair", "sorted array" → TWO POINTERS
└─ "maximize/minimize", "local optimal" → GREEDY
```

---

## 🎯 STUDY PLAN

### **5-Minute Quick Review** (Before Solving)
1. Identify problem keywords
2. Use Decision Tree to pick pattern
3. Scan code template in this file

### **30-Minute Deep Dive** (Learning New Pattern)
1. Read pattern description + key insight
2. Study code template
3. Trace through 1 example problem
4. Write code from memory (no looking)

### **60-Minute Practice** (Master Pattern)
1. Solve 2-3 problems from pattern
2. Code WITHOUT looking at template
3. Check complexity + correctness
4. Note any tricks or gotchas

### **Before Interview** (Preparation)
1. Review Complexity Cheat Sheet (2 min)
2. Pick TOP 6 patterns (Sliding Window, Prefix Sum, BFS, DP, Backtracking, Greedy)
3. Practice 1-2 problems from each
4. Be able to write code from MEMORY

---

## ⚠️ COMMON MISTAKES (by pattern)

| Pattern | ❌ Mistake | ✅ Fix |
|---------|-----------|--------|
| Sliding Window | left pointer backtracks | left only moves right (→ O(n²) if not) |
| Prefix Sum | forget to initialize prefix[0]=0 | always initialize sentinel |
| Two Pointers | move both pointers wrong direction | understand problem, move based on condition |
| Fast Slow | forgot to reset slow for cycle entrance | reset slow=head, fast=fast.next step by step |
| BFS | visited check too late (revisit) | check visited BEFORE adding to queue |
| DFS | forgot base case | always check termination condition |
| Backtracking | forgot to undo choice | MUST remove from path before returning |
| DP | wrong state definition | clearly define what dp[i] means |
| Heap | forget to handle ties | check == not just < or > |
| Monotonic Stack | push before checking | pop all smaller first, THEN push |

---

## 💡 PATTERN INSIGHTS

**Why Sliding Window is O(n):**
- left pointer never backtracks
- each element visited at most twice (once by right, once by left)

**Why Prefix Sum + HashMap works:**
- prefix[i] - prefix[j] = sum(j+1...i)
- if (currentSum - k) exists in map → found answer

**Why BFS guarantees shortest (unweighted):**
- processes nodes level by level
- first time reaching a node = shortest path

**Why Backtracking finds all solutions:**
- explores every branch (choice)
- undoes choice to explore other branches
- like depth-first search of decision tree

**Why Greedy works (when it does):**
- each local optimal choice leads to global optimum
- once you commit to choice, never revisit
- must PROVE this works for problem

---

## 📚 HOW TO USE THIS FILE

### **Scenario 1: "What pattern is this?"**
→ Use Decision Tree to identify

### **Scenario 2: "How do I code pattern X?"**
→ Find pattern section, use code template

### **Scenario 3: "What's the complexity?"**
→ Check Complexity Cheat Sheet table

### **Scenario 4: "Why doesn't my code work?"**
→ Check Common Mistakes table

### **Scenario 5: "I'm preparing for interview"**
→ Follow Study Plan section

---

## 🔗 CROSS-REFERENCE TO PROBLEM FILES

- **common_pattern1.md**: BFS (7 variations), DFS, Backtracking (3), Heap, Dijkstra
- **common_pattern2.md**: Monotonic Stack, Trie, Greedy, Two Pointers, Fast Slow, Bitmask DP, Various DP
- **common_pattern3.md**: Sliding Window (5), Prefix Sum (5)

