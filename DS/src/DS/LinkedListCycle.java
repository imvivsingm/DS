package DS;

class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

public class LinkedListCycle {

    /**
     * Detects if a cycle exists in the linked list.
     * If yes, removes the cycle.
     *
     * @param head The head of the linked list
     * @return true if cycle was detected and removed, false otherwise
     */
    public static boolean detectAndRemoveCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false; // No cycle possible
        }

        ListNode slow = head;
        ListNode fast = head;

        // Detect cycle using Floyd’s algorithm
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {  // Cycle detected
                removeCycle(head, slow);
                return true;
            }
        }

        return false;  // No cycle detected
    }

    /**
     * Removes the cycle in the linked list.
     * @param head The start of the list
     * @param meetingPoint The node where slow and fast met
     */
    private static void removeCycle(ListNode head, ListNode meetingPoint) {
        ListNode ptr1 = head;
        ListNode ptr2 = meetingPoint;

        // If cycle starts at head, special case
        if (ptr1 == ptr2) {
            while (ptr2.next != ptr1) {
                ptr2 = ptr2.next;
            }
            ptr2.next = null;
            return;
        }

        // Move both pointers at same speed to find cycle start
        while (ptr1.next != ptr2.next) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        // Break the cycle
        ptr2.next = null;
    }

    // Helper to print the list (for testing)
    public static void printList(ListNode head) {
        ListNode temp = head;
        while (temp != null) {
            System.out.print(temp.val + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // Creating a linked list with a cycle
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        // Creating cycle: 5 points back to 3
        head.next.next.next.next.next = head.next.next;

        System.out.println("Cycle detected and removed? " + detectAndRemoveCycle(head));

        // Print list after removing cycle
        printList(head);  // Expected: 1 -> 2 -> 3 -> 4 -> 5 -> null
    }
}

