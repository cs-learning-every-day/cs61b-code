import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Jenny Huang on 3/12/19.
 */
public class TestMyTrieSet {

    @Test
    public void myTest() {
        MyTrieSet t = new MyTrieSet();
        t.add("cat");
        t.add("cast");
        t.add("dog");
        t.add("does");
        t.add("doggy");
        t.add("add");

        List<String> p1 = t.keysWithPrefix("d");
        List<String> p2 = t.keysWithPrefix("do");

        assertEquals(3, p1.size());
        assertEquals(p1, p2);

        assertEquals("doggy",t.longestPrefixOf("d"));
        assertEquals("cast",t.longestPrefixOf("ca"));
        assertEquals("add",t.longestPrefixOf("add"));
    }

    // assumes add/contains work
    @Test
    public void sanityClearTest() {
        MyTrieSet t = new MyTrieSet();
        for (int i = 0; i < 455; i++) {
            t.add("hi" + i);
            //make sure put is working via contains
            assertTrue(t.contains("hi" + i));
        }
        t.clear();
        for (int i = 0; i < 455; i++) {
            assertFalse(t.contains("hi" + i));
        }
    }

    // assumes add works
    @Test
    public void sanityContainsTest() {
        MyTrieSet t = new MyTrieSet();
        assertFalse(t.contains("waterYouDoingHere"));
        t.add("waterYouDoingHere");
        assertTrue(t.contains("waterYouDoingHere"));
    }

    // assumes add works
    @Test
    public void sanityPrefixTest() {
        String[] saStrings = new String[]{"same", "sam", "sad", "sap"};
        String[] otherStrings = new String[]{"a", "awls", "hello"};

        MyTrieSet t = new MyTrieSet();
        for (String s: saStrings) {
            t.add(s);
        }
        for (String s: otherStrings) {
            t.add(s);
        }

        List<String> keys = t.keysWithPrefix("sa");
        for (String s: saStrings) {
            assertTrue(keys.contains(s));
        }
        for (String s: otherStrings) {
            assertFalse(keys.contains(s));
        }
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestMyTrieSet.class);
    }



}
