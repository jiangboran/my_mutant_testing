package example;

public class Calculator {

    private Calculator() {}

    public static int add(int a, int b) {return a + b;}

    public static int subtract(int a, int b) {return a - b;}

    public static int multiply(int a, int b) {return a * b;}

    public static int divide(int a, int b) {return a / b;}

    public static int remainder(int a, int b) {return a % b;}

    public static boolean and(boolean a, boolean b){return a&&b;}

    public static boolean or(boolean a, boolean b){return a||b;}

    public static boolean greater(int a, int b){return a > b;}

    public static boolean less(int a, int b){return a < b;}

    public static boolean equal(int a, int b){return a == b;}

}
