public class RabinKarpAlgorithm {


	/**
	 * This algorithm returns the starting index of the matching substring.
	 * This method will return -1 if no matching substring is found, or if the input is invalid.
	 */
	public static int rabinKarp(String input, String pattern) {
		int n = input.length();
		int m = pattern.length();
		if (n < m) {
			return -1;
		}

		RollingString rs1 = new RollingString(input.substring(0, m), m);
		RollingString rs2 = new RollingString(pattern, m);

		int hash1 = rs1.hashCode();
		int hash2 = rs2.hashCode();
		for (int i = 0; i < n - m + 1; i++) {
			if (hash1 == hash2) {
				if (rs1.equals(rs2)) {
					return i;
				}
			}
			if (i < n - m) {
				rs1.addChar(input.charAt(i + m));
				hash1 = rs1.hashCode();
			}
		}
		return -1;
	}

}
