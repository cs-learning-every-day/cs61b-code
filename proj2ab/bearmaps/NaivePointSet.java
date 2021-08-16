package bearmaps;

import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class NaivePointSet implements PointSet {
	private List<Point> points;

	public NaivePointSet(List<Point> points) {
		this.points = points;
	}

	@Override
	public Point nearest(double x, double y) {
		Point result = null;
		Point p = new Point(x, y);
		double bestDistance = Double.MAX_VALUE;
		for (Point other : points) {
			double distance = Point.distance(p, other);
			if (Double.compare(distance, bestDistance) < 0) {
				bestDistance = distance;
				result = other;
			}
		}
		return result;
	}
}
