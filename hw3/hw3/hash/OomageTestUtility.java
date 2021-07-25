package hw3.hash;

import java.util.List;

public class OomageTestUtility {
	public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
		/*
		 * Write a utility function that returns true if the given oomages
		 * have hashCodes that would distribute them fairly evenly across
		 * M buckets. To do this, convert each oomage's hashcode in the
		 * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
		 * and ensure that no bucket has fewer than N / 50
		 * Oomages and no bucket has more than N / 2.5 Oomages.
		 */
		int rangeLeft = oomages.size() / 50;
		int rangeRight = (int) (oomages.size() / 2.5);

		int[] buckets = new int[M];
		oomages.forEach(o -> buckets[(o.hashCode() & 0x7FFFFFFF) % M]++);

		for (int bucketSz : buckets) {
			if (bucketSz < rangeLeft || bucketSz > rangeRight) {
				return false;
			}
		}

		return true;
	}
}
