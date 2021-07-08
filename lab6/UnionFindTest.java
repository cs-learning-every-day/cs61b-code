import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class UnionFindTest {
	@Test
	public void test() {
		UnionFind uf = new UnionFind(10);
		uf.union(1, 0);
		uf.union(0, 2);
		uf.union(0, 3);
		uf.union(0, 4);
		uf.union(0, 5);

		assertTrue(uf.connected(0, 5));
		assertTrue(uf.connected(2, 5));
		assertTrue(uf.connected(3, 5));
		assertTrue(uf.connected(1, 4));

		assertEquals(6, uf.sizeOf(0));
		assertEquals(6, uf.sizeOf(1));
		assertEquals(6, uf.sizeOf(2));

		assertEquals(-6, uf.parent(0));
		assertEquals(0, uf.parent(1));

		uf.union(7, 6);
		uf.union(8, 6);
		uf.union(9, 8);

		assertFalse(uf.connected(0, 6));
		assertFalse(uf.connected(0, 9));

		assertTrue(uf.connected(6, 8));
		assertTrue(uf.connected(7, 9));
		assertTrue(uf.connected(6, 9));

		assertEquals(4, uf.sizeOf(6));

		uf.union(0, 7);

		assertTrue(uf.connected(1, 9));
		assertTrue(uf.connected(7, 0));
		assertTrue(uf.connected(8, 0));

		uf.union(0, 9);
	}
}
