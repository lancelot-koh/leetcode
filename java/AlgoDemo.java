import java.util.ArrayDeque;
import java.util.Deque;

public class AlgoDemo {
    public static void main(String[] args) {
        AlgoDemo demo = new AlgoDemo();
        System.out.println(demo.checkInclusion("ab", "eidbaooo")); // true


        System.out.println(demo.characterReplacement("AABABBA", 1)); // 4   
        
        System.out.println(demo.numSubarrayProductLessThanK(new int[]{10, 5, 2, 6}, 100)); // 8

        System.out.println(java.util.Arrays.toString(demo.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3))); // [3,3,5,5,6,7]
    }


    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] count = new int[26];  

        // 初始化：s1 +1，window -1
        for (int i = 0; i < s1.length(); i++) {
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }

        if (allZero(count)) return true;

        // 滑动窗口
        for (int i = s1.length(); i < s2.length(); i++) {
            // 新字符进入窗口
            count[s2.charAt(i) - 'a']--;

            // 左边字符离开窗口
            count[s2.charAt(i - s1.length()) - 'a']++;

            if (allZero(count)) return true;
        }

        return false;
    }

    private boolean allZero(int[] count) {
        for (int num : count) {
            if (num != 0) return false;
        }
        return true;
    }


    public int characterReplacement(String s, int k) {
        int size = s.length();
        int[] count = new int[26];

        int left = 0;
        int maxFreq = 0;
        int max = 0;

        for(int right = 0; right < size; right++) {
            char c = s.charAt(right);

            //increase count of current char and update max frequency
            count[c - 'A']++;
            //update max frequency of any char in the current window
            maxFreq = Math.max(maxFreq, count[c - 'A']);

            //if the number of chars to change exceeds k, shrink the window
            while(right - left + 1 - maxFreq > k) {
                //shrink the window from the left
                count[s.charAt(left) - 'A']--;
                left++;
            }
            //update max length of the valid window
            max = Math.max(max, right - left + 1);

        }
        return max;
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int count = 0;

        int left = 0;
        int maxProd = 1;
        int right = 0;

       for (right = 0; right < nums.length; right++) {
            int curr  = nums[right];
            maxProd = maxProd * curr;

            while (maxProd >= k) {
                maxProd = maxProd / nums[left];
                left++;
            }
            // each time we find a valid window, all subarrays ending with right and starting from left to right are valid
            count += right - left + 1;

        }

        return count;
    }


    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for(int i = 0; i < n; i++) {

            // Remove indices that are out of the current window
            if (!deque.isEmpty() && deque.peekFirst() == i - k) {
                deque.pollFirst();
            }

            // Remove indices whose corresponding values are less than nums[i]
            while(!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add the current index to the deque
            deque.offerLast(i);

            // The front of the deque is the largest element for the current window
            if (i - k + 1 >= 0) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }

        }

        return result;
    }
}
