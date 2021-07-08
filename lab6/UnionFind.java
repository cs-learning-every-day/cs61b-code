import java.util.Arrays;

public class UnionFind {

	// TODO - Add instance variables?
	private final int[] parents;
	private final int[] sizes;

	/* Creates a UnionFind data structure holding n vertices. Initially, all
	   vertices are in disjoint sets. */
	public UnionFind(int n) {
		this.parents = new int[n];
		this.sizes = new int[n];
		Arrays.fill(parents, -1);
		Arrays.fill(sizes, 1);
	}

	/* Throws an exception if v1 is not a valid index. */
	private void validate(int vertex) {
		if (vertex < 0 || vertex >= parents.length) throw new IndexOutOfBoundsException();
	}

	/* Returns the size of the set v1 belongs to. */
	public int sizeOf(int v1) {
		int root = find(v1);
		return sizes[root];
	}

	/* Returns the parent of v1. If v1 is the root of a tree, returns the
	   negative size of the tree for which v1 is the root. */
	public int parent(int v1) {
		validate(v1);
		return parents[v1] < 0 ? -sizes[v1] : parents[v1];
	}

	/* Returns true if nodes v1 and v2 are connected. */
	public boolean connected(int v1, int v2) {
		return find(v1) == find(v2);
	}

	/* Connects two elements v1 and v2 together. v1 and v2 can be any valid
	   elements, and a union-by-size heuristic is used. If the sizes of the sets
	   are equal, tie break by connecting v1's root to v2's root. Unioning a
	   vertex with itself or vertices that are already connected should not
	   change the sets but may alter the internal structure of the data. */
	public void union(int v1, int v2) {
		int pV1 = find(v1);
		int pV2 = find(v2);

		int szV1 = sizeOf(pV1);
		int szV2 = sizeOf(pV2);


		if (szV1 > szV2) {
			parents[pV2] = pV1;
			sizes[pV1] += sizes[pV2];
		} else {
			parents[pV1] = pV2;
			sizes[pV2] += sizes[pV1];
		}
	}

	/* Returns the root of the set V belongs to. Path-compression is employed
	   allowing for fast search-time. */
	public int find(int vertex) {
		validate(vertex);
		if (parents[vertex] != -1) {
			parents[vertex] = find(parents[vertex]);
			return parents[vertex];
		}
		return vertex;
	}

}
