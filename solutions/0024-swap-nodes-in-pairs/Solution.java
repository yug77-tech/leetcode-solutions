/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

/**
 * LeetCode Problem 24: Swap Nodes in Pairs
 * 
 * Iterative solution with dummy node for clean pointer manipulation
 * 
 * Problem:
 * Swap every two adjacent nodes in a linked list.
 * Must swap nodes themselves, not values.
 * 
 * Key Techniques:
 * 1. Dummy node: Simplifies head handling
 * 2. Three-pointer swap: first, second, curr
 * 3. Careful pointer order: Critical for correctness
 * 
 * Algorithm:
 * 1. Create dummy node pointing to head
 * 2. Use curr pointer starting at dummy
 * 3. While there's a pair to swap:
 *    a. Identify first and second nodes
 *    b. Perform three pointer assignments (order matters!)
 *    c. Move curr forward
 * 4. Return dummy.next (new head)
 * 
 * Visual Example: 1 → 2 → 3 → 4
 * 
 * Initial:
 *   dummy → 1 → 2 → 3 → 4
 *   curr    first second
 * 
 * Swap (1, 2):
 *   Step 1: first.next = second.next
 *     1 → 3 (skip over 2)
 *   
 *   Step 2: second.next = first
 *     2 → 1 (reverse link)
 *   
 *   Step 3: curr.next = second
 *     dummy → 2 (point to new first)
 *   
 *   Result: dummy → 2 → 1 → 3 → 4
 *                       curr (move here)
 * 
 * Swap (3, 4):
 *   curr is now at node 1
 *   Repeat same three steps for nodes 3 and 4
 *   Result: dummy → 2 → 1 → 4 → 3
 * 
 * Why dummy node?
 * - Without: need special case for head swap
 * - With: uniform handling for all swaps
 * - dummy.next always points to final head
 * 
 * Why this pointer order?
 * - first.next first: saves second.next before breaking link
 * - second.next second: creates reverse link
 * - curr.next last: connects previous part to swapped pair
 * 
 * Loop condition explained:
 * while (curr.next != null && curr.next.next != null)
 * - curr.next != null: first node exists
 * - curr.next.next != null: second node exists
 * - Both needed to have a pair to swap
 * 
 * Edge cases:
 * 1. Empty list: dummy.next = null, loop never runs
 * 2. Single node: curr.next.next = null, loop never runs
 * 3. Odd length: Last node remains unchanged
 * 
 * Why not swap values?
 * - Problem specifically says "only nodes themselves may be changed"
 * - Good practice: nodes might have complex data
 * - In real systems, nodes might be locked or immutable
 * 
 * Time Complexity: O(n)
 *   - Visit each node once
 *   - Constant operations per pair
 *   - n/2 swaps for n nodes
 * 
 * Space Complexity: O(1)
 *   - Only pointer variables
 *   - No recursion (unlike recursive solution)
 *   - No extra data structures
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        // Dummy node simplifies head handling
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // curr tracks position before the pair to swap
        ListNode curr = dummy;

        // Continue while there's a pair to swap
        while (curr.next != null && curr.next.next != null) {
            // Identify the two nodes to swap
            ListNode first = curr.next;
            ListNode second = first.next;

            // Perform the swap with three pointer assignments
            // Order matters! Each step builds on the previous
            
            // Step 1: first bypasses second
            first.next = second.next;
            
            // Step 2: second points back to first
            second.next = first;
            
            // Step 3: connect previous part to swapped pair
            curr.next = second;

            // Move curr forward to the last swapped node
            // (first is now the second node in the swapped pair)
            curr = first;
        }

        // dummy.next is the new head
        return dummy.next;
    }
}
