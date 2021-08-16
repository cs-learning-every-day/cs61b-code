package bearmaps;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class KDTree implements PointSet {
	private Node root;

	public KDTree(List<Point> points) {
		// init tree
		for (Point point : points) {
			root = insert(point, root, NodeStatusEnum.HORIZONTAL.getValue());
		}
	}

	protected NodeStatusEnum nextStatus(int statusValue) {
		int nextStatusValue = (statusValue + 1) % NodeStatusEnum.values().length;
		return NodeStatusEnum.toType(nextStatusValue);
	}

	private int nextStatusValue(int statusValue) {
		return (statusValue + 1) % NodeStatusEnum.values().length;
	}

	private int pointCompareWithDirection(Point p1, Point p2, int statusValue) {
		if (statusValue == NodeStatusEnum.HORIZONTAL.getValue()) {
			return Double.compare(p1.getX(), p2.getX());
		} else if (statusValue == NodeStatusEnum.VERTICAL.getValue()) {
			return Double.compare(p1.getY(), p2.getY());
		} else {
			throw new IllegalArgumentException();
		}
	}

	// BST
	private Node insert(Point point, Node node, int statusValue) {
		if (node == null) {
			return new Node(point, statusValue);
		}

		if (point.equals(node.getPoint())) {
			return node;
		}

		int cmp = pointCompareWithDirection(point, node.getPoint(), statusValue);
		if (cmp < 0) { // left
			node.setLeft(insert(point, node.getLeft(), nextStatusValue(statusValue)));
		} else { // right
			node.setRight(insert(point, node.getRight(), nextStatusValue(statusValue)));
		}

		return node;
	}

	@Override
	public Point nearest(double x, double y) {
		Point p = new Point(x, y);
		return nearest(p, root, root).getPoint();
	}

	private Node nearest(Point goal, Node node, Node best) {
		if (node == null) {
			return best;
		}

		double curDistance = Point.distance(goal, node.getPoint());
		double bestDistance = Point.distance(goal, best.getPoint());
		if (Double.compare(curDistance, bestDistance) < 0) {
			best = node;
		}

		// Inefficient
		// best = nearest(goal, node.getLeft(), best);
		// best = nearest(goal, node.getRight(), best);
		Node goodSide = null;
		Node badSide = null;

		int cmp = pointCompareWithDirection(goal, node.getPoint(), node.getStatusValue());
		if (cmp < 0) { // goal < node
			goodSide = node.getLeft();
			badSide = node.getRight();
		} else {
			goodSide = node.getRight();
			badSide = node.getLeft();
		}

		best = nearest(goal, goodSide, best);
		if (isUseful(node, goal, best)) {
			best = nearest(goal, badSide, best);
		}
		return best;
	}

	private boolean isUseful(Node node, Point goal, Node best) {
		double curDistance = 0d;
		double bestDistance = Point.distance(goal, best.getPoint());

		if (node.getStatusValue() == NodeStatusEnum.HORIZONTAL.getValue()) {
			curDistance = Point.distance(goal, new Point(node.getPoint().getX(), goal.getY()));
		} else if (node.getStatusValue() == NodeStatusEnum.VERTICAL.getValue()) {
			curDistance = Point.distance(goal, new Point(goal.getX(), node.getPoint().getY()));
		}
		return Double.compare(curDistance, bestDistance) < 0;
	}

	// 2-d
	protected enum NodeStatusEnum {
		HORIZONTAL(0, "左右"),
		VERTICAL(1, "上下");

		private final int value;

		NodeStatusEnum(int value, String description) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static NodeStatusEnum toType(int value) {
			return Stream.of(NodeStatusEnum.values())
					.filter(n -> n.value == value)
					.findAny()
					.orElse(null);
		}
	}

	private static class Node {
		private Point point;
		private Node left;
		private Node right;
		private int statusValue;

		public Node(Point point, int status) {
			this(point, null, null, status);
		}

		public Node(Point point, Node left, Node right, int status) {
			this.point = point;
			this.left = left;
			this.right = right;
			this.statusValue = status;
		}

		public int getStatusValue() {
			return statusValue;
		}

		public void setStatusValue(int statusValue) {
			this.statusValue = statusValue;
		}

		public Point getPoint() {
			return point;
		}

		public void setPoint(Point point) {
			this.point = point;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}
	}
}
