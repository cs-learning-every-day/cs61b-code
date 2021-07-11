package es.datastructur.synthesizer;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
	/* Index for the next dequeue or peek. */
	private int first;
	/* Index for the next enqueue. */
	private int last;
	/* Variable for the fillCount. */
	private int fillCount;
	/* Array for storing the buffer data. */
	private T[] rb;

	/**
	 * Create a new ArrayRingBuffer with the given capacity.
	 */
	public ArrayRingBuffer(int capacity) {
		rb = (T[]) new Object[capacity];
		first = 0;
		last = 0;
		fillCount = 0;
	}

	@Override
	public int capacity() {
		return rb.length;
	}

	@Override
	public int fillCount() {
		return fillCount;
	}

	/**
	 * Adds x to the end of the ring buffer. If there is no room, then
	 * throw new RuntimeException("Ring buffer overflow").
	 */
	@Override
	public void enqueue(T x) {
		if (isFull()) {
			throw new RuntimeException("Ring buffer overflow");
		}

		rb[last] = x;
		last = index(last + 1);
		fillCount++;
	}

	/**
	 * Dequeue oldest item in the ring buffer. If the buffer is empty, then
	 * throw new RuntimeException("Ring buffer underflow").
	 */
	@Override
	public T dequeue() {
		if (isEmpty()) {
			throw new RuntimeException("Ring buffer underflow");
		}
		T tmp = rb[first];
		first = index(first + 1);
		fillCount--;
		return tmp;
	}

	/**
	 * Return oldest item, but don't remove it. If the buffer is empty, then
	 * throw new RuntimeException("Ring buffer underflow").
	 */
	@Override
	public T peek() {
		if (isEmpty()) {
			throw new RuntimeException("Ring buffer underflow");
		}
		return rb[first];
	}

	private int index(int idx) {
		return idx % capacity();
	}


	// TODO: When you get to part 4, implement the needed code to support
	//       iteration and equals.

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}
		ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
		if (other.fillCount() != this.fillCount()) {
			return false;
		}

		for (int i = 0; i < this.fillCount; i++) {
			T item1 = other.dequeue();
			T item2 = this.dequeue();
			if (!item1.equals(item2)) {
				return false;
			}
			other.enqueue(item1);
			this.enqueue(item2);
		}
		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayRingBufferIterator();
	}

	private class ArrayRingBufferIterator implements Iterator<T> {
		private int index = first;
		private int count = fillCount;

		@Override
		public boolean hasNext() {
			return count != 0;
		}

		@Override
		public T next() {
			if (!hasNext()) throw new NoSuchElementException();

			T t = rb[index];
			index = index(index+1);
			count--;
			return t;
		}
	}
}
