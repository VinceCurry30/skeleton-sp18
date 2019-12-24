public class ArrayDeque<T> {
    private int size;
    private T[] array;
    private int next;
    private int pre;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        next = 0;
        pre = array.length - 1;
    }

    private void resizeForLarge() {
        T[] a = (T[]) new Object[array.length * 2];
        if (pre == size - 1) {
            pre = -1;
        }
        System.arraycopy(array, pre + 1, a, 0, array.length - (pre + 1));
        System.arraycopy(array, 0, a, array.length - (pre + 1), next);
        array = a;
        pre = array.length - 1;
        next = size;
    }

    private void resizeForSmall() {
        T[] a = (T[]) new Object[array.length / 2];
        if (pre == size - 1) {
            pre = -1;
        }
        if (pre < next) {
            System.arraycopy(array, pre + 1, a, 0, size);
        } else {
            System.arraycopy(array, pre + 1, a, 0, array.length - (pre + 1));
            System.arraycopy(array, 0, a, array.length - (pre + 1), next);
        }
        array = a;
        pre = array.length - 1;
        next = size;
    }

    public void addFirst(T item) {
        if (size == array.length) {
            resizeForLarge();
        }
        array[pre] = item;
        size += 1;
        pre -= 1;
        if (pre < 0) {
            pre = array.length - 1;
        }
    }

    public void addLast(T item) {
        if (size == array.length) {
            resizeForLarge();
        }
        array[next] = item;
        size += 1;
        next += 1;
        if (next >= array.length) {
            next = 0;
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
            if (pre == array.length - 1) {
                pre = -1;
            }
            System.out.print(array[pre + 1] + " ");
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        if (pre == array.length - 1) {
            pre = -1;
        }
        T itemToRemove = array[pre + 1];
        array[pre + 1] = null;
        pre += 1;
        if (array.length >= 16 && size < array.length / 4) {
            resizeForSmall();
        }
        return itemToRemove;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        if (next == 0) {
            next = array.length;
        }
        T itemToRemove = array[next - 1];
        array[next - 1] = null;
        next -= 1;
        if (array.length >= 16 && size < array.length / 4) {
            resizeForSmall();
        }
        return itemToRemove;
    }

    public T get(int index) {
        if (size == 0) {
            return null;
        }
        if (index + pre + 1 >= array.length) {
            index = index - array.length;
        }
        return array[index + pre + 1];
    }
}
