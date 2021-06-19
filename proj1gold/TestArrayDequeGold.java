import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class TestArrayDequeGold {
	@Test
	public void test() {
		ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
		StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();

		int rand = StdRandom.uniform(1000);
		ads.addFirst(rand);
		sad.addFirst(rand);
		assertEquals("addFirst(" + rand + ")", ads.get(0), sad.get(0));

		rand = StdRandom.uniform(1000);
		ads.addLast(rand);
		sad.addLast(rand);
		assertEquals("addLast(" + rand + ")", ads.get(1), sad.get(1));

		int actual = ads.removeFirst();
		int expected = sad.removeLast();
		assertEquals("removeFirst()", expected, actual);

		actual = ads.removeLast();
		expected = sad.removeLast();
		assertEquals("removeLast()", expected, actual);
	}
}
