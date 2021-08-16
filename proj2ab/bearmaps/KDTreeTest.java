package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KDTreeTest {

	@Test
	public void testNodeStatusEnum() {
		KDTree kdTree = new KDTree(new ArrayList<>());

		assertEquals(KDTree.NodeStatusEnum.HORIZONTAL,
				kdTree.nextStatus(KDTree.NodeStatusEnum.VERTICAL.getValue()));

		assertEquals(KDTree.NodeStatusEnum.VERTICAL,
				kdTree.nextStatus(KDTree.NodeStatusEnum.HORIZONTAL.getValue()));
	}


	@Test
	public void testRandom() {
		System.out.println((int) (Math.random() * 60 * 10) / 10.0);
		System.out.println(Math.random() * 60 * 10 / 10.0);
		System.out.println(Math.random() * 60);
	}

	@Test
	public void testDataFromPPT() {
//		Use Idea Plugin Java Visualizer
//		https://docs.google.com/presentation/d/1WW56RnFa3g6UJEquuIBymMcu9k2nqLrOE1ZlnTYFebg/edit#slide=id.g54b6045b73_0_38
		Point p1 = new Point(2, 3);
		Point p2 = new Point(4, 2);
		Point p3 = new Point(4, 2);
		Point p4 = new Point(4, 5);
		Point p5 = new Point(3, 3);
		Point p6 = new Point(1, 5);
		Point p7 = new Point(4, 4);


		KDTree kdTree = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));

		Point expected = new Point(1, 5);
		assertEquals(expected, kdTree.nearest(0, 7));
	}

	@Test
	public void testRandomPoints() {
		Set<Point> pointSet = new HashSet<>();


		// random points
		for (int i = 0; i < 100000; i++) {
			// 坐标轴范围 (60, 60) 一个小数
			double x = (int) (Math.random() * 600) / 10.0;
			double y = (int) (Math.random() * 600) / 10.0;
			pointSet.add(new Point(x, y));
		}

		List<Point> pointList = new ArrayList<>(pointSet);

		KDTree kdTree = new KDTree(pointList);
		NaivePointSet naivePointSet = new NaivePointSet(pointList);

		// check
		double naiveRunTime = 0d;
		double kdTreeRunTime = 0d;

		for (int i = 0; i < 10000; i++) {
			double x = (int) (Math.random() * 600) / 10.0;
			double y = (int) (Math.random() * 600) / 10.0;
			Point goal = new Point(x, y);

			long start = System.currentTimeMillis();
			Point p1 = naivePointSet.nearest(x, y);
			long end = System.currentTimeMillis();
			naiveRunTime += (end - start) / 1000.0;

			start = System.currentTimeMillis();
			Point p2 = kdTree.nearest(x, y);
			end = System.currentTimeMillis();
			kdTreeRunTime += (end - start) / 1000.0;

			assertEquals(Point.distance(p1, goal), Point.distance(p2, goal), 0.0000001);
		}

		System.out.println("Naive 10000 queries on 100000 points: " + naiveRunTime + "seconds.");
		System.out.println("kdTree 10000 queries on 100000 points: " + kdTreeRunTime + "seconds.");
		assertTrue(kdTreeRunTime / naiveRunTime < 0.1);
	}
}
