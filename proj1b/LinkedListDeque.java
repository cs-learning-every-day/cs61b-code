import java.util.NoSuchElementException;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class LinkedListDeque<T> implements Deque<T> {
	private static class Node<T> {
		public T item;
		public Node<T> prev;
		public Node<T> next;

		public Node() {
			this(null, null, null);
		}

		public Node(T item, Node<T> prev, Node<T> next) {
			this.item = item;
			this.prev = prev;
			this.next = next;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("prev:");
			if (prev == null) {
				sb.append("null");
			} else {
				sb.append(prev.item);
			}
			sb.append(",item:");
			sb.append(item);
			sb.append(",next:");
			if (next == null) {
				sb.append("null");
			} else {
				sb.append(next.item);
			}
			sb.append("}");
			return sb.toString();
		}
	}

	private int size;
	// prev是头节点 next是尾节点
	private Node<T> sentinel;

	public LinkedListDeque() {
		size = 0;
		sentinel = new Node<>();
	}

	public LinkedListDeque(LinkedListDeque other) {
		size = 0;
		sentinel = new Node<>();

		for (int i = 0; i < other.size(); i++) {
			addLast((T) other.get(i));
		}
	}

	@Override
	public void addFirst(T item) {
		Node<T> head = sentinel.prev;
		if (head == null) {
			head = new Node<>(item, sentinel, null);
			sentinel.next = head;
			sentinel.prev = head;
		} else {
			Node<T> newNode = new Node<>(item, sentinel, head);
			sentinel.prev = newNode;
			head.prev = newNode;
		}
		size++;
	}

	@Override
	public void addLast(T item) {
		Node<T> last = sentinel.next;
		if (last == null) {
			last = new Node<>(item, sentinel, null);
			sentinel.next = last;
			sentinel.prev = last;
		} else {
			Node<T> newNode = new Node<>(item, last, sentinel);
			sentinel.next = newNode;
			last.next = newNode;
		}
		size++;
	}

	@Override
	public T removeFirst() {
		if (size == 0) throw new NoSuchElementException();

		Node<T> head = sentinel.prev;
		T item = head.item;
		if (size == 1) {
			sentinel.next = null;
			sentinel.prev = null;
		} else {
			head.next.prev = sentinel;
			sentinel.prev = head.next;
		}

		size--;
		return item;
	}

	@Override
	public T removeLast() {
		if (size == 0) throw new NoSuchElementException();

		Node<T> last = sentinel.next;
		T item = last.item;
		if (size == 1) {
			sentinel.next = null;
			sentinel.prev = null;
		} else {
			sentinel.next = last.prev;
			last.prev.next = sentinel;
		}

		size--;
		return item;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) return null;

		Node<T> node = sentinel.prev;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node.item;
	}

	public T getRecursive(int index) {
		if (index < 0 || index >= size) return null;
		return getRecursive(0, index, sentinel.prev);
	}

	private T getRecursive(int curIndex, int targetIndex, Node<T> node) {
		if (curIndex == targetIndex) {
			return node.item;
		}
		return getRecursive(curIndex + 1, targetIndex, node.next);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void printDeque() {
		if (size() == 0) {
			System.out.println("Deque is Empty");
		} else {
			Node<T> node = sentinel.prev;
			System.out.print("[");
			for (int i = 0; i < size; i++) {
				System.out.print(node);
				node = node.next;
				System.out.print(",");
			}
			System.out.print("]");
		}
		System.out.println();
	}
}
