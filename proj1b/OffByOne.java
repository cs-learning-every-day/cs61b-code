/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class OffByOne implements CharacterComparator {
	@Override
	public boolean equalChars(char x, char y) {
		return Math.abs(x-y) == 1;
	}
}
