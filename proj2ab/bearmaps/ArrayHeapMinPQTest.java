package bearmaps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQTest {
	public void printHeap(String message, ArrayHeapMinPQ<Integer> heapMinPQ) {
		System.out.println(message);
		System.out.println("-------------------------");
		// 将所有元素向右移动1位
		// PrintHeapDemo.printSimpleHeapDrawing要求从下标1开始
		Object[] oldElements = heapMinPQ.items.stream()
				.map(ArrayHeapMinPQ.PriorityNode::getItem)
				.toArray();

		Object[] newElements = new Object[oldElements.length + 1];

		System.arraycopy(oldElements, 0, newElements, 1, oldElements.length);
		PrintHeapDemo.printSimpleHeapDrawing(
				newElements
		);
	}

	@Test
	public void testAll() {
		ArrayHeapMinPQ<Integer> heapMinPQ = new ArrayHeapMinPQ<>();
		assertEquals(0, heapMinPQ.size());

		heapMinPQ.add(1, 0.2);
		heapMinPQ.add(10, 0.1);
		heapMinPQ.add(12, 0.5);
		heapMinPQ.add(13, 0.4);
		assertEquals(4, heapMinPQ.size());
		printHeap("init:", heapMinPQ);

		int smallest = heapMinPQ.getSmallest();
		assertEquals(10, smallest);

		assertTrue(heapMinPQ.contains(10));
		assertTrue(heapMinPQ.contains(13));


		smallest = heapMinPQ.removeSmallest();
		assertEquals(3, heapMinPQ.size());
		assertEquals(10, smallest);
		printHeap("remove smallest:", heapMinPQ);

		smallest = heapMinPQ.getSmallest();
		assertEquals(1, smallest);

		heapMinPQ.changePriority(13, 0.1);
		smallest = heapMinPQ.getSmallest();
		assertEquals(13, smallest);
		printHeap("change 13 priority to 0.1:", heapMinPQ);


		smallest = heapMinPQ.removeSmallest();
		assertEquals(13, smallest);
		printHeap("remove smallest:", heapMinPQ);


		heapMinPQ.changePriority(12, 0.1);
		smallest = heapMinPQ.getSmallest();
		assertEquals(12, smallest);
		printHeap("change 12 priority to 0.1:", heapMinPQ);

		smallest = heapMinPQ.removeSmallest();
		assertEquals(12, smallest);
		printHeap("remove smallest:", heapMinPQ);

		smallest = heapMinPQ.removeSmallest();
		assertEquals(1, smallest);
		assertEquals(0, heapMinPQ.size());
	}
}
