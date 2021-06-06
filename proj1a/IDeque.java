/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface IDeque<T> {
	void addFirst(T item);

	void addLast(T item);

	T removeFirst();

	T removeLast();

	T get(int index);

	boolean isEmpty();

	int size();

	void printDeque();
}
