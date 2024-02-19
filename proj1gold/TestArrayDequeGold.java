import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestArrayDequeGold {
    StudentArrayDeque<Integer> input = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<>();

    @Test
    public void Test1() {
        for (int i = 0; i < 1000; i++) {
            Integer input_ = null;
            Integer expected_ = null;
            if (input.isEmpty() || expected.isEmpty()) {
                if (one2two() == 1) {
                    input.addFirst(i);
                    expected.addFirst(i);
                }
                if (one2two() == 2) {
                    input.addLast(i);
                    expected.addLast(i);
                }
            } else {
                if (one2four() == 1) {
                    input_ = input.removeFirst();
                    expected_ = expected.removeFirst();
                    assertEquals("Index is " + i + ", expected(" + expected.size()
                            + "), input(" + expected.size() + ")", expected_, input_);
                }
                if (one2four() == 2) {
                    input_ = input.removeLast();
                    expected_ = expected.removeLast();
                    assertEquals("Index is " + i + ", \nexpected: size(" + expected.size()
                            + ") \ninput: size(" + expected.size() + ")", expected_, input_);
                }
                if (one2four() == 3) {
                    input.addLast(i);
                    expected.addLast(i);
                }
                if (one2four() == 4) {
                    input.addFirst(i);
                    expected.addFirst(i);
                }
            }
        }
    }

    private static int one2four() {
        return StdRandom.uniform(1, 5);
    }

    private static int one2two() {
        return StdRandom.uniform(1, 3);
    }

}