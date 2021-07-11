package es.datastructur.synthesizer;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
	@Test
	public void someTest() {
		BoundedQueue<Double> arb = new ArrayRingBuffer<>(4);
		assertTrue(arb.isEmpty());
		assertFalse(arb.isFull());

		arb.enqueue(33.1); // 33.1 null null  null
		arb.enqueue(44.8); // 33.1 44.8 null  null
		arb.enqueue(62.3); // 33.1 44.8 62.3  null
		arb.enqueue(-3.4); // 33.1 44.8 62.3 -3.4

		assertFalse(arb.isEmpty());
		assertTrue(arb.isFull());

		assertEquals(Optional.of(33.1), Optional.ofNullable(arb.dequeue()));
		assertEquals(Optional.of(44.8), Optional.ofNullable(arb.dequeue()));
		assertEquals(Optional.of(62.3), Optional.ofNullable(arb.peek()));

		assertFalse(arb.isFull());
		arb.dequeue();
		arb.dequeue();

		arb.enqueue(1.1);
		arb.enqueue(2.2);
		arb.enqueue(3.3);
		arb.enqueue(4.4);


		for (Double item : arb) {
			System.out.println(item);
		}

		assertEquals(Optional.of(1.1), Optional.ofNullable(arb.peek()));
		ArrayRingBuffer<Integer> arb1 = new ArrayRingBuffer<>(5);
		ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<>(5);
		ArrayRingBuffer<Integer> arb3 = null;

		assertTrue(arb1.equals(arb2));
		assertFalse(arb1.equals(arb3));

		arb1.enqueue(1);
		assertFalse(arb1.equals(arb2));

		arb2.enqueue(1);
		arb2.enqueue(2);
		assertFalse(arb1.equals(arb2));

		arb1.enqueue(2);
		assertTrue(arb1.equals(arb2));
	}
}
