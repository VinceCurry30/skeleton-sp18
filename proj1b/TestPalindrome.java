import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    CharacterComparator offbyone = new OffByOne();
    CharacterComparator offby5 = new OffByN(5);

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        assertTrue(palindrome.isPalindrome("flake", offbyone));
        assertFalse(palindrome.isPalindrome("horse", offbyone));
        assertTrue(palindrome.isPalindrome("a", offbyone));
        assertTrue(palindrome.isPalindrome("", offbyone));
        assertTrue(palindrome.isPalindrome("ABCB", offbyone));
        assertTrue(palindrome.isPalindrome("&%", offbyone));
    }

    @Test
    public void testIsPalindromeOffBy5() {
        assertTrue(palindrome.isPalindrome("bidding", offby5));
        assertFalse(palindrome.isPalindrome("flake", offby5));
    }
}
