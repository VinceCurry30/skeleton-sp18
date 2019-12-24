public class ArrayDeque<T> {
    private int size;
    private T[] array;
    private int next_pos;
    private int pre_pos;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        next_pos = 0;
        pre_pos = array.length - 1;
    }

    private void resize_for_large() {
        T[] a = (T[]) new Object[array.length * 2];
        if (pre_pos == size - 1) {
            pre_pos = -1;
        }
        System.arraycopy(array, pre_pos + 1, a, 0, array.length - (pre_pos + 1));
        System.arraycopy(array, 0, a, array.length - (pre_pos + 1), next_pos);
        array = a;
        pre_pos = array.length - 1;
        next_pos = size;
    }

    private void resize_for_small() {
        T[] a = (T[]) new Object[array.length / 2];
        if (pre_pos == size - 1) {
            pre_pos = -1;
        }
        if (pre_pos < next_pos) {
            System.arraycopy(array, pre_pos + 1, a, 0, size);
        } else {
            System.arraycopy(array, pre_pos + 1, a, 0, array.length - (pre_pos + 1));
            System.arraycopy(array, 0, a, array.length - (pre_pos + 1), next_pos);
        }
        array = a;
        pre_pos = array.length - 1;
        next_pos = size;
    }

    public void addFirst(T item) {
        if (size == array.length) {
            resize_for_large();
        }
        array[pre_pos] = item;
        size += 1;
        pre_pos -= 1;
        if (pre_pos < 0) {
            pre_pos = array.length - 1;
        }
    }

    public void addLast(T item) {
        if (size == array.length) {
            resize_for_large();
        }
        array[next_pos] = item;
        size += 1;
        next_pos += 1;
        if (next_pos >= array.length) {
            next_pos = 0;
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            if (pre_pos == array.length - 1) {
                pre_pos = -1;
            }
            System.out.print(array[pre_pos + 1] + " ");
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        if (pre_pos == array.length - 1) {
            pre_pos = -1;
        }
        T item_to_remove = array[pre_pos + 1];
        array[pre_pos + 1] = null;
        pre_pos += 1;
        if (array.length >= 16 && size < array.length / 4) {
            resize_for_small();
        }
        return item_to_remove;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        if (next_pos == 0) {
            next_pos = array.length;
        }
        T item_to_remove = array[next_pos - 1];
        array[next_pos - 1] = null;
        next_pos -= 1;
        if (array.length >= 16 && size < array.length / 4) {
            resize_for_small();
        }
        return item_to_remove;
    }

    public T get(int index) {
        if (size == 0) {
            return null;
        }
        if (index + pre_pos + 1 >= array.length) {
            index = index - array.length;
        }
        return array[index + pre_pos + 1];
    }
}
