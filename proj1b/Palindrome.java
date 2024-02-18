public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (char ch : word.toCharArray()) {
            deque.addLast(ch);
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        for (int i = 0; i < deque.size()/2; i++) {
            if (deque.get(i) != deque.get(deque.size() - 1 - i)) return false;
        }
        return true;
    }
}
