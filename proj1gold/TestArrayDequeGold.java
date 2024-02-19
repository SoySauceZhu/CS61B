import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestArrayDequeGold {
    StudentArrayDeque<Integer> input = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<>();
    StringBuilder log = new StringBuilder();

    @Test
    public void Test1() {
        for (int i = 0; i < 1000; i++) {
            Integer input_ = null;
            Integer expected_ = null;
            if (input.isEmpty() || expected.isEmpty()) {
                if (one2two() == 1) {
                    input.addFirst(i);
                    expected.addFirst(i);
                    log.append("addFirst(").append(i).append(")\n");
                }
                if (one2two() == 2) {
                    input.addLast(i);
                    expected.addLast(i);
                    log.append("addLast(").append(i).append(")\n");
                }
            } else {
                if (one2four() == 1) {
                    input_ = input.removeFirst();
                    expected_ = expected.removeFirst();
                    log.append("removeFirst()\n");
                    assertEquals(log.toString(), expected_, input_);
                }
                if (one2four() == 2) {
                    input_ = input.removeLast();
                    expected_ = expected.removeLast();
                    log.append("removeLast()\n");
                    assertEquals(log.toString(), expected_, input_);
                }
                if (one2four() == 3) {
                    input.addLast(i);
                    expected.addLast(i);
                    log.append("addLast(").append(i).append(")\n");
                }
                if (one2four() == 4) {
                    input.addFirst(i);
                    expected.addFirst(i);
                    log.append("addFirst(").append(i).append(")\n");
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