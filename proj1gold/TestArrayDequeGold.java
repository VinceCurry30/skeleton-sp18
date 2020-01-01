import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testRandomly() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> happy = new ArrayDequeSolution<>();
        String errorMessage = "";

        for (int i = 0; i < 100; i++) {
            int intBetweenZeroAndThree = StdRandom.uniform(4);
            if (intBetweenZeroAndThree == 0) {
                sad.addFirst(i);
                happy.addFirst(i);
                errorMessage += "addFirst(" + i + ")\n";
                assertEquals(errorMessage, happy.getFirst(), sad.get(0));
            }
            if (intBetweenZeroAndThree == 1) {
                sad.addLast(i);
                happy.addLast(i);
                errorMessage += "addLast(" + i + ")\n";
                assertEquals(errorMessage, happy.getLast(), sad.get(sad.size() - 1));
            }
            if (sad != null && happy != null) {
                if (intBetweenZeroAndThree == 2) {
                    Integer sadFirst = sad.removeFirst();
                    Integer happyFirst = happy.removeFirst();
                    errorMessage += "removeFirst()\n";
                    assertEquals(errorMessage, happyFirst, sadFirst);
                }
                if (intBetweenZeroAndThree == 3) {
                    Integer sadLast = sad.removeLast();
                    Integer happyLast = happy.removeLast();
                    errorMessage += "removeLast()\n";
                    assertEquals(errorMessage, happyLast, sadLast);
                }
            }
        }

    }

}
