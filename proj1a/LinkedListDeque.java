public class LinkedListDeque<T> {
    private class Node {
        private T item;
        private Node pre;
        private Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            pre = p;
            next = n;
        }
    }

    private Node sentinel;
    private T sentinel_value;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(sentinel_value, sentinel, sentinel);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.pre = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        sentinel.pre = new Node(item, sentinel.pre, sentinel);
        sentinel.pre.pre.next = sentinel.pre;
        size += 1;
    }

    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node current = sentinel;
        while (current.next != sentinel) {
            current = current.next;
            System.out.print(current.item + " ");
        }
    }

    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        size -= 1;
        T item_to_remove = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        return item_to_remove;
    }

    public T removeLast() {
        if (sentinel.next == sentinel) {
            return null;
        }
        size -= 1;
        T item_to_remove = sentinel.pre.item;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        return item_to_remove;
    }

    public T get(int index) {
        if (sentinel.next == sentinel) {
            return null;
        }
        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            if (current == sentinel) {
                return null;
            }
            current = current.next;
        }
        return current.item;
    }

    public T getRecursive(int index) {
        if (sentinel.next == sentinel) {
            return null;
        }
        if (index == 0) {
            return sentinel.next.item;
        }
        return getRecursive(index - 1);
    }
}
