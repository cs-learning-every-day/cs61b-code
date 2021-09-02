import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class MyTrieSet implements TrieSet61B {

	private static class Node {
		Character character;
		Node parent;
		HashMap<Character, Node> children;
		boolean word;

		public Node(Node parent) {
			this.parent = parent;
		}
	}

	private Node root;
	private int size;

	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	@Override
	public boolean contains(String key) {
		keyCheck(key);
		return node(key) != null;
	}

	@Override
	public void add(String key) {
		keyCheck(key);

		if (root == null) {
			root = new Node(null);
		}

		Node node = root;
		int len = key.length();
		for (int i = 0; i < len; i++) {
			char c = key.charAt(i);
			boolean emptyChildren = node.children == null;
			Node childNode = emptyChildren ? null : node.children.get(c);
			if (childNode == null) {
				childNode = new Node(node);
				childNode.character = c;
				node.children = emptyChildren ? new HashMap<>() : node.children;
				node.children.put(c, childNode);
			}
			node = childNode;
		}

		node.word = true;
		size++;
	}

	@Override
	public List<String> keysWithPrefix(String prefix) {
		keyCheck(prefix);


		List<String> result = new ArrayList<>();
		help(prefix, node(prefix), result);

		return result;
	}

	@Override
	public String longestPrefixOf(String key) {
		List<String> candidates = keysWithPrefix(key);

		if (candidates.isEmpty()) {
			return "";
		}

		String result = "";
		for (String candidate : candidates) {
			if (candidate.length() > result.length()) {
				result = candidate;
			}
		}
		return result;
	}

	private void help(String prefix, Node node, List<String> result) {
		if (node == null) {
			return;
		}

		if (node.word) {
			result.add(prefix);
		}

		if (node.children == null ||
				node.children.isEmpty()) {
			return;
		}

		Set<Character> keySet = node.children.keySet();
		for (Character ch : keySet) {
			Node childNode = node.children.get(ch);
			help(prefix + ch, childNode, result);
		}
	}


	private Node node(String key) {
		Node node = root;
		int len = key.length();
		for (int i = 0; i < len; i++) {
			if (node == null ||
					node.children == null ||
					node.children.isEmpty()) return null;
			node = node.children.get(key.charAt(i));
		}
		return node;
	}

	private void keyCheck(String key) {
		if (key == null || key.isEmpty()) {
			throw new IllegalArgumentException("key must not be empty");
		}
	}
}
