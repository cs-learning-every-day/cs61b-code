/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface Deque<T> {
	void addFirst(T item);

	void addLast(T item);

	T removeFirst();

	T removeLast();

	T get(int index);

	default boolean isEmpty() {
		return size() == 0;
	}

	int size();

	void printDeque();
}
