/**
 * LeetCode Problem 169: Majority Element
 * 
 * Boyer-Moore Voting Algorithm - Optimal Solution
 * 
 * Problem:
 * Find element that appears more than n/2 times.
 * Guaranteed to exist.
 * 
 * Key Insight:
 * The majority element appears MORE times than all other elements combined!
 * We can "cancel out" pairs of different elements, and majority will survive.
 * 
 * Algorithm (Voting with Cancellation):
 * 1. Maintain a candidate and count
 * 2. When count=0, pick new candidate
 * 3. When element matches candidate: vote for it (count++)
 * 4. When element differs: vote against it (count--)
 * 5. Final candidate must be the majority
 * 
 * Visual Example: [2,2,1,1,1,2,2]
 * 
 * Step by step:
 *   2: candidate=2, count=1
 *   2: candidate=2, count=2
 *   1: candidate=2, count=1  (cancel one 2)
 *   1: candidate=2, count=0  (cancel another 2)
 *   1: candidate=1, count=1  (new candidate)
 *   2: candidate=1, count=0  (cancel the 1)
 *   2: candidate=2, count=1  (new candidate)
 * 
 * Result: 2 (which appears 4 times > 7/2)
 * 
 * Why This Works:
 * - Imagine pairing majority elements with non-majority elements
 * - Since majority > n/2, even after pairing all non-majority elements
 * - Some majority elements remain unpaired
 * - These unpaired ones ensure majority becomes final candidate
 * 
 * Proof:
 * Let M = count of majority element, N = count of all others
 * Given: M > n/2, therefore M > N (since M + N = n)
 * After cancellation: M - N elements remain, all are majority
 * So candidate at end = majority element
 * 
 * Time Complexity: O(n) - single pass through array
 * Space Complexity: O(1) - only two variables
 * 
 * This is optimal! Answers the follow-up perfectly.
 */
class Solution {
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;

        for (int num : nums) {
            // If count is 0, we need a new candidate
            if (count == 0) {
                candidate = num;
            }

            // Vote for or against current candidate
            if (num == candidate) {
                count++;  // Vote for
            } else {
                count--;  // Vote against (cancel)
            }
        }

        return candidate;
    }
}
