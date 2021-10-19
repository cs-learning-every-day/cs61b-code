import edu.princeton.cs.algs4.Queue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestSortAlgs {
	private Queue<Integer> q1 = new Queue<>();
	private Queue<Integer> q2 = new Queue<>();
	private Queue<Integer> q3 = new Queue<>();

	@Before
	public void initData() {
		q1.enqueue(1);
		q1.enqueue(2);
		q1.enqueue(3);
		q1.enqueue(4);
		q1.enqueue(5);
		q1.enqueue(6);
		q1.enqueue(7);

		q2.enqueue(7);
		q2.enqueue(5);
		q2.enqueue(6);
		q2.enqueue(4);
		q2.enqueue(3);
		q2.enqueue(2);
		q2.enqueue(1);

		for (int i = 0; i < 100; i++) {
			q3.enqueue((int) (Math.random()*1000));
		}
	}

    @Test
    public void testQuickSort() {
		Queue<Integer> res1 = QuickSort.quickSort(q1);
		assertTrue(isSorted(res1));

		Queue<Integer> res2 = QuickSort.quickSort(q2);
		assertTrue(isSorted(res2));

		Queue<Integer> res3 = QuickSort.quickSort(q3);
		assertTrue(isSorted(res3));
    }

    @Test
    public void testMergeSort() {

		Queue<Integer> res1 = MergeSort.mergeSort(q1);
        assertTrue(isSorted(res1));

		Queue<Integer> res2 = MergeSort.mergeSort(q2);
		assertTrue(isSorted(res2));

		Queue<Integer> res3 = MergeSort.mergeSort(q3);
		assertTrue(isSorted(res3));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
