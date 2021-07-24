import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
	private Node root;
	private int size;

	private class Node {
		public K key;
		public V value;
		public Node left;
		public Node right;
		public Node parent;

		public Node(K key, V value, Node parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		public boolean hasTwoChildren() {
			return left != null && right != null;
		}

		public boolean isLeftChild() {
			return parent != null && parent.left == this;
		}

		public boolean isRightChild() {
			return parent != null && parent.right == this;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		public Node sibling() {
			if (isLeftChild()) {
				return parent.right;
			}

			if (isRightChild()) {
				return parent.left;
			}
			return null;
		}
	}

	public BSTMap() {
		root = null;
		size = 0;
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	@Override
	public boolean containsKey(K key) {
		checkKeyNotNull(key);
		return get(key) != null;
	}

	@Override
	public V get(K key) {
		checkKeyNotNull(key);
		Node node = get(root, key);
		return node != null ? node.value : null;
	}

	private Node get(Node node, K key) {
		if (node == null) return null;
		int cmp = node.key.compareTo(key);
		if (cmp == 0) {
			return node;
		} else if (cmp < 0) {
			return get(node.right, key);
		} else {
			return get(node.left, key);
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void put(K key, V value) {
		checkKeyNotNull(key);
		root = put(root, key, value, root);
		size++;
	}

	private Node put(Node node, K key, V value, Node parent) {
		if (node == null) {
			return new Node(key, value, parent);
		}
		int cmp = node.key.compareTo(key);
		if (cmp == 0) {
			node.value = value;
		} else if (cmp < 0) {
			node.right = put(node.right, key, value, node);
		} else {
			node.left = put(node.left, key, value, node);
		}
		return node;
	}

	private void checkKeyNotNull(K k) {
		if (k == null) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<>();
		keySet(set, root);
		return set;
	}

	private void keySet(Set<K> set, Node node) {
		if (node == null) return;
		set.add(node.key);
		keySet(set, node.left);
		keySet(set, node.right);
	}

	@Override
	public V remove(K key) {
		if (key == null) return null;

		Node deletedNode = get(root, key);
		if (deletedNode == null) return null;

		return removeNode(deletedNode);
	}

	@Override
	public V remove(K key, V value) {
		throw new UnsupportedOperationException();
	}

	private V removeNode(Node node) {
		V value = node.value;
		if (node.hasTwoChildren()) {
			// 前驱
			Node pre = precursor(node);
			node.key = pre.key;
			node = pre;
		}
		// deletedNode 度必要是0或1
		Node parent = node.parent;
		Node replacement = node.left != null ? node.left : node.right;

		if (replacement != null) { // 度为1
			replacement.parent = node.parent;
			if (parent == null) { // 根节点
				root = replacement;
			} else if (node == parent.left) {
				parent.left = replacement;
			} else {
				parent.right = replacement;
			}
		} else if (parent == null) { // 度为0 并且是根节点
			root = null;
		} else { // 度为0
			if (node == parent.left) {
				parent.left = null;
			} else if (node == parent.right) {
				parent.right = null;
			}
		}

		size--;
		return value;
	}

	private Node precursor(Node node) {
		// 前驱节点在左子树当中
		Node p = node.left;
		if (p != null) {
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}
		// 从父节点、祖父节点中寻找前驱节点
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}
		return node.parent;
	}

	private Node successor(Node node) {
		// 后继节点在右子树当中
		Node p = node.right;
		if (p != null) {
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}
		// 从父节点、祖父节点中寻找后继节点
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}
		return node.parent;
	}

	@Override
	public Iterator<K> iterator() {
		throw new UnsupportedOperationException();
	}
}
