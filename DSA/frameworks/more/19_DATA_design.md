# 💾 Design - Build Data Structures

**Design LRU, LFU, and other managers**

---

## Interview Frequency: **5% of problems** ⭐⭐⭐

---

## LRU Cache Template

```java
class LRUCache {
    Map<Integer, Integer> cache;
    int capacity;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }
    
    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }
    
    public void put(int key, int value) {
        cache.put(key, value);
    }
}
```

---

## Examples

- **LRU Cache:** Least recently used
- **LFU Cache:** Least frequently used
- **Min Stack:** Min value accessible
- **Twitter:** Feed system

---

**Master Design. It's 5% of interviews.** 🚀
