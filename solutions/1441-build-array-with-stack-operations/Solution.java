import java.util.*;

/**
 * LeetCode Problem 1441: Build an Array with Stack Operations
 * 
 * Optimized solution with pre-allocated ArrayList capacity
 * 
 * Algorithm:
 * 1. Iterate through stream [1, n]
 * 2. Always Push current number
 * 3. If current number matches target[j], increment j
 * 4. Otherwise, Pop the number we just pushed
 * 5. Stop when we've built entire target array
 * 
 * Optimization:
 * - Pre-allocate ArrayList with capacity 2*target.length (max operations needed)
 * - Early break immediately after completing target
 * 
 * Time Complexity: O(n) - iterate through stream until target is built
 * Space Complexity: O(target.length) - result list size
 */
class Solution {
    public List<String> buildArray(int[] target, int n) {
        
        List<String> result = new ArrayList<>(2 * target.length);
        
        int j = 0;
        
        for (int i = 1; i <= n; i++) {
            
            result.add("Push");
            
            if (i == target[j]) {
                j++;
                
                if (j == target.length) {
                    break;
                }
            } else {
                result.add("Pop");
            }
        }
        
        return result;
    }
}
