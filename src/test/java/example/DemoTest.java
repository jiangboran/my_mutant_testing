package example;

import compiler.CompileMutants;
import engine.ABSMutationEngine;
import engine.AORMutationEngine;
import engine.LCRMutationEngine;
import engine.UOIMutationEngine;
import executiom.MutantExecution;
import org.junit.Test;

import java.io.IOException;

public class DemoTest {
    @Test
    public void testMutationEngine() throws IOException, InterruptedException {
        String src_file = "C:/Users/21125/Desktop/my_mutant_testing\\src/main/java/example/Calculator.java";
        String pool_dir = "C:\\Users\\21125\\Desktop\\my_mutant_testing\\pool";

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
    public void  testDemoMutantExecution() throws IOException, InterruptedException {
        String testsuite_dir = "C:/Users/21125/Desktop/mutant_test/mutest-demo/testsuite";
        String pool_dir = "C:/Users/21125/Desktop/mutant_test/mutest-demo/src/test/java/edu/nju/mutest/testOutput";
//<testsuite_dir> <pool_dir>
        String[] args = new String[2];
        args[0] = testsuite_dir;
        args[1] = pool_dir;
        MutantExecution.main(args);
    }
}
