public class BubbleGrid {
	private int[][] grid;
	private UnionFind uf;
	private int gridRowNum;
	private int gridColNum;
	private int flag;

	/* Create new BubbleGrid with bubble/space locations specified by grid.
	 * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
	 * 0's denote a space. */
	public BubbleGrid(int[][] grid) {
		assert grid != null && grid.length != 0;

		this.grid = grid;
		this.gridRowNum = grid.length;
		this.gridColNum = grid[0].length;
		this.flag = this.gridRowNum * this.gridColNum;
		updateUnionFind();
	}

	private void updateUnionFind() {
		uf = new UnionFind(gridRowNum * gridColNum + 1);
		// 最顶部
		for (int i = 0; i < gridColNum; i++) {
			if (grid[0][i] == 1) {
				uf.union(index(0, i), flag);
			}
		}
		// 连接
		for (int i = 1; i < gridRowNum; i++) {
			for (int j = 0; j < gridColNum; j++) {
				if (grid[i][j] == 1) {
					int idx = index(i, j);
					// top
					if (grid[i - 1][j] == 1 &&
							uf.connected(flag, index(i - 1, j))) {
						uf.union(idx, flag);
						continue;
					}
					// down
					if (i + 1 < gridRowNum &&
							grid[i + 1][j] == 1 &&
							uf.connected(flag, index(i + 1, j))) {
						uf.union(idx, flag);
						continue;
					}
					// left
					if (j - 1 >= 0 &&
							grid[i][j - 1] == 1 &&
							uf.connected(flag, index(i, j - 1))) {
						uf.union(idx, flag);
						continue;
					}
					// right
					if (j + 1 < gridColNum &&
							grid[i][j + 1] == 1 &&
							uf.connected(flag, index(i, j + 1))) {
						uf.union(idx, flag);
					}
				}
			}
		}

	}

	private int index(int row, int col) {
		return row * this.gridColNum + col;
	}


	/* Returns an array whose i-th element is the number of bubbles that
	 * fall after the i-th dart is thrown. Assume all elements of darts
	 * are unique, valid locations in the grid. Must be non-destructive
	 * and have no side-effects to grid. */
	public int[] popBubbles(int[][] darts) {
		int[] result = new int[darts.length];

		for (int i = 0; i < darts.length; i++) {
			int size = uf.sizeOf(flag); // old size
			int rowIdx = darts[i][0];
			int colIdx = darts[i][1];

			if (grid[rowIdx][colIdx] == 0 || size == 1) { // contains flag
				result[i] = 0;
			} else {
				grid[rowIdx][colIdx] = 0;
				updateUnionFind();
				result[i] = size - uf.sizeOf(flag) - 1;
			}

		}

		return result;
	}
}
