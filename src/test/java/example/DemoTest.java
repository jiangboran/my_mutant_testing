package example;

import compiler.CompileMutants;
import engine.*;
import executiom.MutantExecution;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DemoTest {
    @Test
    public void testMutationEngine() throws IOException, InterruptedException {
        String src_file = "S:\\File\\Study\\Software Testing\\my_mutant_testing\\src\\main\\java\\example\\Calculator.java";
        String pool_dir = "S:\\File\\Study\\Software Testing\\my_mutant_testing\\pool";

        String[] args = new String[2];
        args[0] = src_file;
        args[1] = pool_dir;
        ABSMutationEngine.main(args);
    }

    @Test
    public void testCompileMutants() throws IOException, InterruptedException {
        String pool_dir = "C:/Users/21125/Desktop/my_mutant_testing/pool";

        String[] args = new String[1];
        args[0] = pool_dir;

        CompileMutants.main(args);
    }

    @Test
    public void testMutantExecution() throws IOException, InterruptedException {
//        String testsuite_dir = "C:\\Users\\21125\\Desktop\\my_mutant_testing\\src\\main\\java\\example";
        String testsuite_dir = "C:/Users/21125/Desktop/my_mutant_testing/testsuite";
        String pool_dir = "C:/Users/21125/Desktop/my_mutant_testing/pool";
//<testsuite_dir> <pool_dir>
        String[] args = new String[2];
        args[0] = testsuite_dir;
        args[1] = pool_dir;
        MutantExecution.main(args);
    }

    @Test
    public void testSuite() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("javac ", "C:\\Users\\21125\\Desktop\\my_mutant_testing\\testsuite\\example\\CalculatorTestSuite.java");
        Process p = pb.start();
        // 读取进程输出流

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        // 等待进程执行完成
        try {
            int exitCode = p.waitFor();
            System.out.println("Compilation exit code: " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
