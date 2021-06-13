/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class Palindrome {
	public Deque<Character> wordToDeque(String persiflage) {
		Deque<Character> deque = new LinkedListDeque<>();
		for (char c : persiflage.toCharArray()) {
			deque.addLast(c);
		}
		return deque;
	}

	public boolean isPalindrome(String word, CharacterComparator cc) {
		char[] chars = word.toCharArray();
		int p = 0, q = chars.length - 1;
		while (p < q) {
			if (!cc.equalChars(chars[p], chars[q])) {
				return false;
			}
			p++;
			q--;
		}
		return true;
	}

	// 递归
	public boolean isPalindrome(String word) {
		return isPalindrome(word, 0, word.length() - 1);
	}

	// [start,end]
	private boolean isPalindrome(String word, int start, int end) {
		if (end - start + 1 < 2) return true;
		if (word.charAt(start) != word.charAt(end)) return false;
		return isPalindrome(word, start + 1, end - 1);
	}
	// 迭代
/*	public boolean isPalindrome(String word) {
		char[] chars = word.toCharArray();
		int p = 0, q = chars.length - 1;
		while (p < q) {
			if (chars[p] != chars[q]) {
				return false;
			}
			p++;
			q--;
		}
		return true;
	}*/


}
