/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class OffByN implements CharacterComparator {
	private int n;

	public OffByN(int n) {
		this.n = n;
	}

	@Override
	public boolean equalChars(char x, char y) {
		return Math.abs(x - y) == n;
	}
}
