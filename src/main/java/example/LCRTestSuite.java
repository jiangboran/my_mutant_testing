package example;

/**
 * A test suite for {@link Calculator}
 */
public class LCRTestSuite {

    public static void main(String[] args) {
        testAnd();
        testOr();
    }
    private static void testAnd() {
        boolean oracle = false;
        boolean res = Calculator.and(true, false);
        if (oracle == res)
            System.out.println("[TEST] testAnd() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testAnd() fail (%b, %b)!", oracle, res));
    }

    private static void testOr() {
        boolean oracle = true;
        boolean res = Calculator.or(true, false);
        if (oracle == res)
            System.out.println("[TEST] testOr() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testOr() fail (%b, %b)!", oracle, res));
    }
}
