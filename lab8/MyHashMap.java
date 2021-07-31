import java.util.*;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

	private static final int DEFAULT_SIZE = 16;
	private static final double DEFAULT_LOAD_FACTOR = 0.75;


	private class EntryNode<K, V> {
		public K key;
		V value;
		EntryNode<K, V> next;

		public EntryNode(K key, V value, EntryNode<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	private EntryNode<K, V>[] buckets;
	private double loadFactor;
	private int size;


	public MyHashMap() {
		this(DEFAULT_SIZE);
	}

	public MyHashMap(int initialSize) {
		this(initialSize, DEFAULT_LOAD_FACTOR);
	}

	public MyHashMap(int initialSize, double loadFactor) {
		this.buckets = new EntryNode[initialSize];
		this.loadFactor = loadFactor;
		this.size = 0;

	}

	@Override
	public void clear() {
		size = 0;
		// clear buckets
		Arrays.fill(buckets, null);
	}

	@Override
	public boolean containsKey(K key) {
		Objects.requireNonNull(key);
		return get(key) != null;
	}

	@Override
	public V get(K key) {
		Objects.requireNonNull(key);
		int index = hash(key);
		EntryNode<K, V> entryNode = getEntryNode(index, key);
		return entryNode == null ? null : entryNode.value;
	}


	@Override
	public int size() {
		return size;
	}

	@Override
	public void put(K key, V value) {
		int index = hash(key);
		EntryNode<K, V> oldNode = getEntryNode(index, key);
		// already exist
		if (oldNode != null) {
			oldNode.value = value;
			return;
		}

		addEntryNode(key, value, index);
	}

	@Override
	public Set<K> keySet() {
		Set<K> result = new HashSet<>();
		for (int i = 0; i < buckets.length; i++) {
			EntryNode<K, V> node = buckets[i];
			while (node != null) {
				result.add(node.key);
				node = node.next;
			}
		}
		return result;
	}

	@Override
	public V remove(K key) {
		Objects.requireNonNull(key);
		int index = hash(key);
		EntryNode<K, V> removed = removeEntryNode(index, key);
		return removed == null ? null : removed.value;
	}


	@Override
	public V remove(K key, V value) {
		Objects.requireNonNull(key);
		int index = hash(key);
		EntryNode<K, V> removed = removeEntryNode(index, key);
		return removed == null ? null : removed.value;
	}

	@Override
	public Iterator<K> iterator() {
		return keySet().iterator();
	}

	private EntryNode removeEntryNode(int index, K key) {
		EntryNode<K, V> node = buckets[index];
		EntryNode<K, V> prev = null;
		while (node != null) {
			if (node.key.equals(key)) {
				if (prev == null) {
					buckets[index] = node.next;

				} else {
					prev.next = node.next;
				}
				size--;
				return node;
			}
			prev = node.next;
			node = node.next;
		}
		return null;
	}

	private void addEntryNode(K key, V value, int index) {
		EntryNode<K, V> newNode = new EntryNode<>(key, value, null);
		// hash collision
		if (buckets[index] != null) {
			// insert head
			newNode.next = buckets[index];
			buckets[index] = newNode;
		} else {
			buckets[index] = newNode;
		}
		size++;
	}

	private EntryNode<K, V> getEntryNode(int index, K key) {
		EntryNode<K, V> node = buckets[index];
		while (node != null) {
			if (node.key.equals(key)) {
				return node;
			}
			node = node.next;
		}
		return null;
	}

	private int hash(K key) {
		// must return a positive number
		// use & 0x7fffffff
		return (key.hashCode() & 0x7fffffff) % buckets.length;
	}

}
