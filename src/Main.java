public class Main {

    public static void main(String[] args) {

        //shortened example from https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/

        //7 is a child of 3, rest is self explanatory
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node7 = new Node();
        Node node8 = new Node();

        //connect the wires
        node1.val=1;
        node1.prev=null;
        node1.next=node2;
        node1.child=null;

        node2.val=2;
        node2.prev=node1;
        node2.next=node3;
        node2.child=null;

        node3.val=3;
        node3.prev=node2;
        node3.next=node4;
        node3.child=node7;

        node4.val=4;
        node4.prev=node3;
        node4.next=null;
        node4.child=null;

        node7.val=7;
        node7.prev=null;
        node7.next=node8;
        node7.child=null;

        node8.val=8;
        node8.prev=node7;
        node8.next=null;
        node8.child=null;

        System.out.println( flatten(node1));

    }

    public static Node flatten(Node head) {
        //pay close attention, head is never re-assigned to next or prev, we always pass head,next to recursive function, inside flattentail function
        flattentail(head);
        return head;
    }

    // flattentail: flatten the node "head" and return the tail of its child (if exists)
    // the tail means the last node after flattening "head"

    // Five situations:
    // 1. null - no need to flatten, just return it
    // 2. no child, no next - no need to flatten, it is the last element, just return it
    // 3. no child, next - no need to flatten, go next
    // 4. child, no next - flatten the child and done
    // 5. child, next - flatten the child, connect it with the next, go next

    private static Node flattentail(Node head) {
        if (head == null) return head; // CASE 1

        //if there is child or no child
        if (head.child == null) {

            //if no child no next return head [last element of level]
            if (head.next == null) return head; // CASE 2

            //if there is next, recurse on it [no child]
            return flattentail(head.next); // CASE 3
        }
        else {

            //copy child node and set head child to null
            Node child = head.child;
            head.child = null;

            //save a copy of next, we need this to figure out how to connect childTail t next depending if next is null
            Node next = head.next;

            //get where child ends
            Node childtail = flattentail(child);

            //head next now should be child [not childTail, that is what we return]
            head.next = child;
            child.prev = head;

            //if there is next we need to connect childTail to next, and recurse on next
            if (next != null) { // CASE 5
                childtail.next = next;
                next.prev = childtail;
                return flattentail(next);
            }

            //if there is no next, chilTail is what we have to return
            return childtail; // CASE 4

        }
    }

    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

}
