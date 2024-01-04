package example;

/**
 * A test suite for {@link Calculator}
 */
public class RORTestSuite {

    public static void main(String[] args) {
        testGreater();
        testLess();
        testEqual();
    }

    private static void testGreater() {
        boolean oracle = true;
        boolean res = Calculator.greater(3, 1);
        if (oracle == res)
            System.out.println("[TEST] testGreater() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testGreater() fail (%b, %b)!", oracle, res));
    }

    private static void testLess() {
        boolean oracle = true;
        boolean res = Calculator.less(1, 3);
        if (oracle == res)
            System.out.println("[TEST] testLess() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testLess() fail (%b, %b)!", oracle, res));
    }

    private static void testEqual() {
        boolean oracle = true;
        boolean res = Calculator.equal(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testEqual() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testEqual() fail (%b, %b)!", oracle, res));
    }

}
