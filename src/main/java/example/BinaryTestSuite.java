package example;

/**
 * A test suite for {@link Calculator}
 */
public class BinaryTestSuite {

    public static void main(String[] args) {
        testAdd();
        testMul();
        testSub();
        testDiv();
        testRem();
        testAnd();
        testOr();
        testGreater();
        testLess();
        testEqual();
    }

    private static void testAdd() {
        int oracle = 4;
        int res = Calculator.add(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testAdd() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testAdd() fail (%d, %d)!", oracle, res));
    }

    private static void testSub() {
        int oracle = 2;
        int res = Calculator.subtract(5, 3);
        if (oracle == res)
            System.out.println("[TEST] testSub() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testSub() fail (%d, %d)!", oracle, res));
    }

    private static void testMul() {
        int oracle = 4;
        int res = Calculator.multiply(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testMul() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testMul() fail (%d, %d)!", oracle, res));
    }

    private static void testDiv() {
        int oracle = 1;
        int res = Calculator.divide(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testDiv() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testDiv() fail (%d, %d)!", oracle, res));
    }

    private static void testRem() {
        int oracle = 0;
        int res = Calculator.remainder(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testRem() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testRem() fail (%d, %d)!", oracle, res));
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
