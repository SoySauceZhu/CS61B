public class LinkedListDeque<T> {
    private static class Node<T> {
        T item;
        Node<T> last;
        Node<T> next;

        Node() {
        }

        Node(T item, Node<T> last, Node<T> next) {
            this.item = item;
            this.last = last;
            this.next = next;
        }
    }

    private Node<T> sentinel;

    private int size;

    public LinkedListDeque() {
        sentinel = new Node<>();
        sentinel.next = sentinel;
        sentinel.last = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node<T> node = new Node<>(item, sentinel, sentinel.next);
        sentinel.next.last = node;
        sentinel.next = node;
        size++;
    }

    public void addLast(T item) {
        Node<T> node = new Node<>(item, sentinel.last, sentinel);
        sentinel.last.next = node;
        sentinel.last = node;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.last = sentinel;
        size--;
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }

        T item = sentinel.last.item;
        sentinel.last = sentinel.last.last;
        sentinel.last.next = sentinel;
        size--;
        return item;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }

        Node<T> ptr = sentinel.next;
        for (int i = 0; i < index; i++) {
            ptr = ptr.next;
        }
        return ptr.item;
    }

    public void printDeque() {
        Node<T> ptr = sentinel.next;
        while (ptr != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        System.out.println();
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return helper(index, sentinel.next);
    }

    private T helper(int index, Node<T> ptr) {
        if (index == 0) {
            return ptr.item;
        }
        return helper(index - 1, ptr.next);
    }

    public static void main(String[] args) {
        LinkedListDeque<String> list = new LinkedListDeque<>();
        list.addFirst("A");
        list.addFirst("B");
        list.addLast("1");
        list.addLast("2");
        list.addFirst("C");
        for (int i = 0; i < 5; i++) {
            System.out.println(list.getRecursive(i));

        }
        list.printDeque();
    }
}
