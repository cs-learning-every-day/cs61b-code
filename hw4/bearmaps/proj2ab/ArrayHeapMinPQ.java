package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 注意  由局部性最小 -> 全局最小
 *
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@SuppressWarnings("unchecked")
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
	protected List<PriorityNode> items;

	protected class PriorityNode implements Comparable<PriorityNode> {
		private T item;
		private double priority;

		PriorityNode(T e, double p) {
			this.item = e;
			this.priority = p;
		}

		public T getItem() {
			return item;
		}

		public void setItem(T item) {
			this.item = item;
		}

		public double getPriority() {
			return priority;
		}

		public void setPriority(double priority) {
			this.priority = priority;
		}

		@Override
		public int compareTo(PriorityNode o) {
			if (o == null) {
				return -1;
			}
			return Double.compare(this.getPriority(), o.getPriority());
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || o.getClass() != this.getClass()) {
				return false;
			}
			return ((PriorityNode) o).getItem().equals(this.getItem());
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(item);
		}
	}

	public ArrayHeapMinPQ() {
		this.items = new ArrayList<>();
	}

	@Override
	public void add(T item, double priority) {
		itemNotNullCheck(item);
		if (contains(item)) {
			changePriority(item, priority);
			return;
		}

		PriorityNode node = new PriorityNode(item, priority);
		items.add(node);
		shiftUp(items.size() - 1);
	}

	@Override
	public boolean contains(T item) {
		itemNotNullCheck(item);
		return items.contains(new PriorityNode(item, -1));
	}

	@Override
	public T getSmallest() {
		emptyCheck();
		return items.get(0).getItem();
	}

	@Override
	public T removeSmallest() {
		T result = getSmallest();

		PriorityNode lastElement = items.get(size() - 1);
		items.set(0, lastElement);

		items.remove(size() - 1);
		if (size() != 0) {
			shiftDown(0);
		}

		return result;
	}


	@Override
	public int size() {
		return items.size();
	}

	@Override
	public void changePriority(T item, double priority) {
		emptyCheck();
		itemNotNullCheck(item);

		if (!contains(item)) {
			throw new NoSuchElementException();
		}

		PriorityNode node = new PriorityNode(item, priority);
		int size = size();
		int i;
		for (i = 0; i < size; i++) {
			PriorityNode tmpNode = items.get(i);
			if (tmpNode.equals(node)) {
				tmpNode.setPriority(priority);
				break;
			}
		}

		shiftUp(i);
		shiftDown(i);
	}

	private void shiftDown(int index) {
		PriorityNode node = items.get(index);

		int half = items.size() >> 1;
		while (index < half) { // 非叶子节点
			// 必有左，不一定有右
			int childIdx = left(index);
			PriorityNode childNode = items.get(childIdx);
			int rightIdx = right(index);

			if (rightIdx < items.size()) {
				PriorityNode rightNode = items.get(rightIdx);
				// right < left
				if (rightNode.compareTo(childNode) < 0) {
					childNode = rightNode;
				}
			}

			// current <= childNode
			if (node.compareTo(childNode) <= 0) break;

			items.set(index, childNode);
			index = childIdx;
		}
		items.set(index, node);
	}

	/**
	 * 让index位置的元素上滤
	 *
	 * @param index
	 */
	private void shiftUp(int index) {
		PriorityNode node = items.get(index);
		while (index > 0) {
			int parentIndex = parent(index);
			PriorityNode parent = items.get(parentIndex);
			// node >= parent
			if (node.compareTo(parent) >= 0) {
				break;
			}
			// swap
			items.set(index, parent);
			index = parentIndex;
		}
		items.set(index, node);
	}

	private int parent(int childIndex) {
		return (childIndex - 1) >> 1;
	}

	private int left(int parentIndex) {
		return (parentIndex << 1) + 1;
	}

	private int right(int parentIndex) {
		return (parentIndex << 1) + 2;
	}

	private void emptyCheck() {
		if (size() == 0) {
			throw new IndexOutOfBoundsException("Heap is empty!");
		}
	}

	private void itemNotNullCheck(T item) {
		if (item == null) {
			throw new IllegalArgumentException("item must not be null!");
		}
	}
}
