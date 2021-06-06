import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class ArrayDeque<T> implements IDeque<T>, Iterable<T> {
	private static final int DEFAULT_CAPACITY = 8;
	private T[] items;
	private int size;
	private int firstIndex;
	private int lastIndex;

	public ArrayDeque() {
		size = 0;
		items = (T[]) new Object[DEFAULT_CAPACITY];
		firstIndex = DEFAULT_CAPACITY >> 1;
		lastIndex = DEFAULT_CAPACITY >> 1;
	}

	public ArrayDeque(int capacity) {
		size = 0;
		items = (T[]) new Object[capacity];
		firstIndex = capacity >> 1;
		lastIndex = capacity >> 1;
	}

	@Override
	public void addFirst(T item) {

		if (size == 0) {
			items[firstIndex] = item;
			size++;
			return;
		}

		if (size == items.length) {
			resize(items.length << 1);
		}

		firstIndex = fixIndex(firstIndex - 1);
		if (firstIndex < 0) {
			firstIndex = items.length - 1;
		}
		items[firstIndex] = item;
		size++;
	}


	@Override
	public void addLast(T item) {

		if (size == 0) {
			items[lastIndex] = item;
			size++;
			return;
		}

		if (size == items.length) {
			resize(items.length << 1);
		}
		lastIndex = fixIndex(lastIndex + 1);
		items[lastIndex] = item;
		size++;
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) return null;

		T item = items[firstIndex];

		items[firstIndex] = null;
		firstIndex = fixIndex(firstIndex + 1);
		size--;
		if (size > 0 && size == items.length / 4) resize(items.length >> 1);
		return item;
	}

	@Override
	public T removeLast() {
		if (isEmpty()) return null;

		T item = items[lastIndex];

		items[lastIndex] = null;
		lastIndex = fixIndex(lastIndex - 1);

		if (lastIndex < 0) lastIndex = items.length - 1;

		size--;
		if (size > 0 && size == items.length / 4) resize(items.length >> 1);
		return item;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) return null;
		return items[fixIndex(firstIndex + index)];
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void printDeque() {
		System.out.print("[");
		for (int i = 0; i < size; i++) {
			System.out.print(items[fixIndex(firstIndex + i)]);
			System.out.print(",");
		}
		System.out.print("]");
		System.out.println();
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayDequeIterator();
	}

	private int fixIndex(int index) {
		return index % items.length;
	}

	private void resize(int newCapacity) {
		T[] newArray = (T[]) new Object[newCapacity];
		int idx = 0;
		for (int i = 0; i < size; i++) {
			idx = fixIndex(firstIndex + i);
			newArray[i] = items[idx];
		}
		items = newArray;
		lastIndex = size - 1;
		firstIndex = 0;
	}

	private class ArrayDequeIterator implements Iterator<T> {
		private int currentIndex = 0;

		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		@Override
		public T next() {
			if (!hasNext()) throw new NoSuchElementException();
			T item = items[fixIndex(firstIndex + currentIndex)];
			currentIndex++;
			return item;
		}
	}

	public static void main(String[] args) {
		ArrayDeque<Integer> deque = new ArrayDeque<>(2);
		deque.addLast(6);
		deque.addFirst(1);
		deque.addFirst(2);
		deque.addFirst(3);
		deque.addLast(100);
		deque.addFirst(4);
		deque.addLast(5);


		deque.printDeque();

		System.out.println("Get Index 0:" + deque.get(0));
		System.out.println("Get Index 4:" + deque.get(4));
		System.out.println("Get Index 5:" + deque.get(5));

		System.out.println("Remove First:" + deque.removeFirst());
		System.out.println("Remove First:" + deque.removeFirst());
		for (Integer item : deque) {
			System.out.print(item+",");
		}
		System.out.println();
		System.out.println("Remove Last:" + deque.removeLast());
		System.out.println("Remove First:" + deque.removeFirst());
		System.out.println("Remove Last:" + deque.removeLast());
		System.out.println("Remove First:" + deque.removeFirst());
		System.out.println("Remove Last:" + deque.removeLast());

		System.out.println(deque.isEmpty());

		System.out.println();
	}
}
