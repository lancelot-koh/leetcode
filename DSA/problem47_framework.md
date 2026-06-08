# Problem 47: Shortest Path to Get All Keys (LC864) - Bitmask BFS Framework

## 7-Section Framework

* 建模 Modeling: 问题本质是在二维网格中从起点出发，收集所有钥匙后回到目标位置的最短路径问题。关键创新是将"已收集的钥匙集合"编码为位掩码，作为状态的一部分，使得同一位置可能有多个不同的状态（取决于已收集的钥匙数）。
  **Modeling:** The problem essence is finding the shortest path in a 2D grid starting from an initial position to collect all keys and reach the destination. The key innovation is encoding the "set of collected keys" as a bitmask and including it as part of the state, allowing the same position to have multiple different states depending on which keys have been collected.

* 状态 State: 状态定义为三元组(行, 列, 钥匙掩码)，其中钥匙掩码用一个整数的不同位表示是否收集了对应的钥匙(a-z)。状态空间大小为O(M × N × 2^K)，其中M和N是网格尺寸，K是钥匙总数(≤6)。每个状态代表在特定位置且持有特定钥匙集合的配置。
  **State:** State is defined as a triplet (row, col, keyMask), where keyMask uses different bits of an integer to represent whether the corresponding key (a-z) has been collected. State space size is O(M × N × 2^K) where M, N are grid dimensions and K is total keys (≤6). Each state represents a configuration at a specific position with a specific set of keys.

* 辅助数据结构 Aux Structure: 使用队列(Queue)实现BFS遍历，存储待处理的状态三元组。使用三维布尔数组visited[row][col][keyMask]或三维整数数组dist[row][col][keyMask]追踪已访问的状态，避免重复处理。可选的距离数组存储到达每个状态的最短步数。
  **Aux Structure:** Use a Queue to implement BFS traversal, storing state triplets to process. Use a 3D boolean array visited[row][col][keyMask] or 3D integer array dist[row][col][keyMask] to track visited states and avoid reprocessing. Optional distance array stores the shortest steps to reach each state.

* 状态转移 Transition: 从当前状态(行, 列, 掩码)探索四个方向的邻接单元格。若遇到钥匙字符(a-f)，使用位或操作更新掩码：nextMask = currentMask | (1 << (cell - 'a'))。若遇到门字符(A-F)，使用位与操作检查是否有对应钥匙：if ((currentMask & (1 << (cell - 'A'))) == 0) continue。转移后的新状态加入队列，直到到达目标时掩码包含所有钥匙。
  **Transition:** From current state (row, col, mask), explore four adjacent cells. If encountering a key character (a-f), update mask using bitwise OR: nextMask = currentMask | (1 << (cell - 'a')). If encountering a door character (A-F), check if the corresponding key exists using bitwise AND: if ((currentMask & (1 << (cell - 'A'))) == 0) continue. New states after transition are enqueued; process continues until the destination is reached with all keys in the mask.

* 选择算法 Solver: 采用BFS(广度优先搜索)算法，因为BFS能保证找到最短路径。状态空间虽然是三维的，但由于K≤6，总状态数不超过M×N×64，在BFS框架内完全可以处理。关键特性：位掩码压缩了钥匙的布尔状态，使得状态转移中的集合操作变成O(1)的位运算。
  **Solver:** Use BFS (Breadth-First Search) algorithm because it guarantees finding the shortest path. Although the state space is 3D, since K ≤ 6, the total state count doesn't exceed M×N×64, which is fully manageable within BFS framework. Key characteristic: bitmask compresses the boolean state of keys, making set operations in state transitions O(1) bitwise operations.

* 复杂度分析 Complexity: 时间复杂度O(M × N × 2^K × 4) = O(M × N × 2^K)，其中每个状态最多被访问一次(2^K种掩码)，从每个状态探索4个方向。空间复杂度O(M × N × 2^K)用于visited数组和队列。由于K≤6，2^K≤64，所以即使在较大网格上(M,N≤100)也能在可接受的时间内运行。单位操作：位掩码的OR/AND/比较都是O(1)。
  **Complexity:** Time O(M × N × 2^K × 4) = O(M × N × 2^K), where each state is visited at most once (2^K masks), and we explore 4 directions from each state. Space O(M × N × 2^K) for visited array and queue. Since K ≤ 6, 2^K ≤ 64, so even on larger grids (M,N ≤ 100) execution time is acceptable. Unit operations: bitmask OR/AND/comparison are all O(1).

* 不变量 Invariant: (1) 每个BFS层中的所有状态都在同一距离步数内可达；(2) 位掩码中的每一位独立代表对应钥匙的收集状态，不存在冗余或矛盾；(3) 在有效的状态转移中，掩码只能增加(通过收集钥匙)不能减少，因此钥匙集合是单调递增的；(4) 如果状态(r,c,mask)已被访问，则不会再次入队，保证每个状态最多处理一次；(5) 到达目标时检查掩码值是否等于全钥匙掩码(1<<K)-1，这是唯一的成功终止条件。
  **Invariant:** (1) All states in each BFS layer are reachable within the same step count; (2) Each bit in the bitmask independently represents the collection state of the corresponding key with no redundancy or contradiction; (3) In valid state transitions, the mask can only increase (by collecting keys) not decrease, so the key set is monotonically increasing; (4) If state (r,c,mask) is already visited, it won't be enqueued again, ensuring each state is processed at most once; (5) When reaching the destination, check if the mask equals the full key mask (1<<K)-1, which is the only successful termination condition.
