public class ArrayDeque<T> {
    private T[] array;
    private int size;
    private int front;
    private int rear;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        front = 0;
        rear = 0;
        size = 0;
    }

    public void resize(int opacity) {
        T[] T = (T[]) new Object[opacity];
        if (front == 0) {
            System.arraycopy(array, 0, T, 0, size);
        } else {
            System.arraycopy(array, front, T, 0, array.length - front);
            System.arraycopy(array, 0, T, array.length - front, rear);
        }
        array = T;
        front = 0;
        rear = size;
    }

    public void addFirst(T item) {
        if (front == 0) {
            front = array.length;
        }
        front--;
        array[front] = item;
        size++;
        if (size >= array.length) {
            resize(array.length * 2);
        }
    }

    public void addLast(T item) {
        if (rear == array.length) {
            rear = 0;
        }
        array[rear] = item;
        rear++;
        size++;
        if (size >= array.length) {
            resize(array.length * 2);
        }
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
        T item = array[front];
        array[front] = null;
        front++;
        if (front == array.length) {
            front = 0;
        }
        size--;

        if (size < 0.3 * array.length && array.length > 16) {
            resize((int) (array.length * 0.5));
        }
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        rear--;
        T item = array[rear];
        array[rear] = null;
        if (rear == 0) {
            rear = array.length;
        }
        size--;

        if (size < 0.3 * array.length && array.length > 16) {
            resize((int) (array.length * 0.5));
        }
        return item;
    }

    public T get(int index) {
        if (index > size) {
            return null;
        }
        if (index < array.length - front) {
            return array[front + index];
        } else {
            return array[index - array.length + front];
        }
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> list = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            list.addLast(i);
            if (i % 10 == 0) {
                System.out.println(list.get(i));
            }
        }

        for (int i = 0; i < 99; i++) {
            System.out.println(list.removeLast());
        }
    }
}
