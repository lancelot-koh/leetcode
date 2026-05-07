sliding window:
1
    Map<Integer> map = new HashMap<>();
    map.put(0, -1);
    int left = 0;
    for(int right = 0; i < nums.length; i++) {
        int currVal = nums[right];
        while(condition) {
            // shrink window
            // remove from set
            left++;
        }

        or 
        if (condition) {
            // shrink window by map to update left
            left = Math.max(left, map.get(currVal) + 1)
        }
        //set.add(currVal);
        //map.push(currval, right)
        max = Math.max(max, right - left + 1);
    }



two pointers:
int left = 0;
int right = nums.length - 1;
while(left < right) {
    int sum = nums[left] + nums[right];
    if (sum == target) {
        return xxx;
    } else if (sum < target) {
        left++;
    } else {
        right--
    }
}


for(int k = 0; k < nums.length; k++) {
    if (k > 0 && nums[k] == nums[k- 1]) continue;
    int left = 0;
    int righit = nums.length - 1;

    while(left < right) {
        int sum = nums[k] + nums[left] + nums[right];
        if (sum == target) {
            while(left < right && nums[left] = nums[left - 1]) left++;
            while(left < right && nums[right] = nums[right + 1]) right--; 

            left++;
            right--;
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
}

int left = 0, right = height.length - 1;
while(left < right) {
    
    max = Math.max(max, xxxx)
}


Monotonic stack:
LinkedList<Integer> stack = new LinkedList<>();
int index = 0;
for(int i = 0; i < nums.length; i++) {
    while(!stack.isEmpty && nums[i] > nums[stack.peek()]) {
        int idx = stack.pop();
        ...
    }
    stack.push(i);
}



prefix sum + hashMap
HashMap<Character, Integer> map = new HashMap<>();
map.put(0, 1) or map.put(0, -1);

int sum = 0;
for(int i = 0; i < nums.length; i++) {
    sum += nums[i];
    if (map.get(sum - target)) {
        //min = Math.min()
    }

    while( map.get(xxx) === target) {

    }
}


sliding window pattern:
    subarray
    substring
    contiguous
    longest
    shortest
    at most
    without repeating

for (right) {
    while (invalid) {
        left++;
    }

    update answer
}

Variant A:
Set shrink:
while(duplicate)

Variant B:
Map jump:
left = max(...)

Variant C:
if (window size > k)

Variant D:
At most K distinct:
while(map.size() > k)


Prefix Sum + HashMap
    count subarray
    sum equals k
    continuous sum
    range sum

sum += nums[i]
if (map contains sum-target)
map update


Two Pointers
    sorted array
    pair sum
    palindrome
    container


Monotonic Stack
    next greater
    nearest smaller
    daily temperatures
    histogram

while(stack invalid)
    pop

push

Union Find
    connectivity
    grouping
    cycle detection
    merge sets
