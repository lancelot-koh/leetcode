# T4-9 — Deque as Stack / Queue / Heap 双端队列用作栈/队列/堆

> **Core idea:** Java's `ArrayDeque` is the preferred implementation for both stack and queue use cases. It outperforms `Stack` (synchronized) and `LinkedList` (pointer overhead). Understand which end to push/pop for each use case.
> **核心思想：** Java的`ArrayDeque`是栈和队列的首选实现，优于`Stack`（同步开销）和`LinkedList`（指针开销）。掌握不同用途应操作哪端。
>
> Complexity: O(1) amortized for all push/pop/peek operations.

---

## How It Works — Mental Model 直觉理解

`ArrayDeque` is a resizable circular array backed by a single `Object[]`. Operations on both ends (push/pop front, offer/poll back) take O(1) amortized because they just move a head or tail index pointer — no shifting of elements. `Stack` is a subclass of `Vector`, which synchronizes every method; in single-threaded code this overhead is pure waste. `LinkedList` works correctly as both stack and queue, but each node carries two extra pointers (about 16–24 bytes of heap overhead per element), slowing cache performance. The LIFO vs FIFO choice is purely about which end you add and remove: for a stack, always the same end (front); for a queue, add to back and remove from front. Knowing this, the method aliases (push=offerFirst, offer=offerLast, pop=pollFirst, poll=pollFirst) become self-explanatory.

**Key invariant:** For `ArrayDeque` used as a stack, the "top" is always the front (`peekFirst`). For a queue, the "front" to dequeue is also `peekFirst`, but new elements are added to the back with `offerLast`. Only one end changes for each role.

**Common mistake:** Using `a - b` as a `PriorityQueue` comparator. This is concise but overflows when `a` is a large positive number and `b` is a large negative number (or vice versa), producing a wrong ordering. Always use `Integer.compare(a, b)`.

---

## Step-by-Step Trace (Stack vs Queue with same sequence)

```
Operations: push/offer 1, 2, 3; then pop/poll until empty

As Stack (LIFO):  push(1)→[1], push(2)→[2,1], push(3)→[3,2,1]
  pop→3, pop→2, pop→1   output: 3,2,1

As Queue (FIFO): offer(1)→[1], offer(2)→[1,2], offer(3)→[1,2,3]
  poll→1, poll→2, poll→3   output: 1,2,3
```

---

## When to Use 什么时候用

| Use case | Deque API | When |
|---|---|---|
| Stack (LIFO) | `push`/`pop`/`peek` (operate on FRONT) | Expression evaluation, DFS, monotonic stack |
| Queue (FIFO) | `offer`/`poll`/`peek` (add back, remove front) | BFS, sliding window |
| Deque (both ends) | `offerFirst`/`offerLast`/`pollFirst`/`pollLast` | Monotonic deque (T3-7), palindrome check |
| Max-heap | `new PriorityQueue<>(Collections.reverseOrder())` | Top-K, scheduling |

---

## Core Templates 核心模板

### Stack operations (LIFO)

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(val);         // = offerFirst: new element becomes the new front (top of stack)
int top = stack.pop();   // = pollFirst: removes and returns the front element (top of stack)
int peek = stack.peek(); // = peekFirst: view top without removing

// Never use java.util.Stack — it's synchronized and slow
```

### Queue operations (FIFO)

```java
Deque<Integer> queue = new ArrayDeque<>();
// Or: Queue<Integer> queue = new LinkedList<>(); (acceptable but slower)

queue.offer(val);          // = offerLast: new element joins the back of the queue
int front = queue.poll();  // = pollFirst: removes and returns the element that has waited longest (front)
int peek = queue.peek();   // = peekFirst: view front without removing
```

### Priority Queue (min-heap and max-heap)

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// Custom comparator
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

pq.offer(val);   // insert O(log n)
pq.poll();       // remove min/max O(log n)
pq.peek();       // view min/max O(1)
```

---

## API Reference Java API参考

```java
// ArrayDeque — DO use
Deque<T> dq = new ArrayDeque<>();

dq.push(x)        // offerFirst — stack push
dq.pop()          // pollFirst  — stack pop
dq.peek()         // peekFirst  — stack/queue peek

dq.offerFirst(x)  // add to front
dq.offerLast(x)   // add to back (queue offer)
dq.pollFirst()    // remove from front (queue poll)
dq.pollLast()     // remove from back
dq.peekFirst()    // view front
dq.peekLast()     // view back

dq.isEmpty()
dq.size()
```

---

## Common Patterns 常见模式

### Monotonic Stack (see T2-4)
```java
Deque<Integer> stack = new ArrayDeque<>();  // stores indices
// push: stack.push(i)
// pop: stack.pop()
// peek: stack.peek()
```

### BFS Queue
```java
Queue<int[]> queue = new LinkedList<>();
// or Deque<int[]> queue = new ArrayDeque<>();
queue.offer(new int[]{0, 0});
while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    // ...
}
```

### Expression Evaluation (two stacks)
```java
Deque<Integer> nums = new ArrayDeque<>();
Deque<Character> ops = new ArrayDeque<>();
// Process tokens: push numbers; for operators, apply pending higher-precedence ops
```

### Browser History (two stacks)
```java
Deque<String> back = new ArrayDeque<>();
Deque<String> forward = new ArrayDeque<>();
// visit: forward.clear(); back.push(current); current = url
// back: forward.push(current); current = back.pop()
// forward: back.push(current); current = forward.pop()
```

---

## PriorityQueue Pitfalls 优先队列陷阱

| Pitfall | Fix |
|---|---|
| `a - b` comparator overflow | Use `Integer.compare(a, b)` |
| Can't remove arbitrary element efficiently | Use `TreeMap` for O(log n) arbitrary removal |
| No O(1) decrease-key | Use lazy deletion: push new entry, skip stale on pop |
| Default is MIN-heap | For max-heap: `Collections.reverseOrder()` or `(a,b) -> b - a` |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 232 Implement Queue using Stacks |
| Easy | LC 225 Implement Stack using Queues |
| Medium | LC 155 Min Stack |
| Medium | LC 20 Valid Parentheses |
| Hard | LC 224 Basic Calculator |

**Order:** 20 → 232 → 225 → 155 → 224

---

## One-line Summary

```
Prefer ArrayDeque over Stack/LinkedList; push/pop for LIFO, offer/poll for FIFO; PriorityQueue for heap with O(log n) insert/remove.
优先用ArrayDeque而非Stack/LinkedList；push/pop做LIFO，offer/poll做FIFO；PriorityQueue做O(log n)堆操作。
```
