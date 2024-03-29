package executiom;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class for executing test suite against mutants
 */
public class MutantExecution {


    // Use fixed test suite in this class.
    static String TEST_SUITE_FQN = "example.CalculatorTestSuite";


    public static String[] main(String[] args) throws IOException, InterruptedException {

        if (args.length != 2) {
            // Require param for specifying test suite.
            System.out.println("executiom.MutantExecution: <testsuite_dir> <mutant_pool_dir>");
            System.exit(0);
        }

        String[] res = {"",""};

        File tsDir = new File(args[0]);
        File mutPoolDir = new File(args[1]);
        System.out.println("[LOG] Test suite dir: " + tsDir.getAbsolutePath());
        System.out.println("[LOG] Mutant pool dir: " + mutPoolDir.getAbsolutePath());

        // Locate all mutants
        File[] fns = mutPoolDir.listFiles();
        if (fns == null) {
//            System.out.println("[LOG] Find no mutants!");
            res[0] = "[LOG] Find no mutants!";
            System.exit(0);
        }
        List<File> mutDirs = Arrays.stream(fns)
                .filter(f -> !f.getName().startsWith("."))
                .collect(Collectors.toList());
        int mutNum = mutDirs.size();
//        System.out.printf("[LOG] Locate %d mutants\n", mutNum);
        // Execute each mutant
//        System.out.println("[LOG] Start to execute mutants...");

        res[0] += "[LOG] Locate " + mutNum + " mutants\n" + "[LOG] Start to execute mutants...\n";

        int killedCnt = 0;
        for (File mutDir : mutDirs) {
            System.out.println("[LOG] -------------------------------------------------");
            res[0] += "[LOG] -------------------------------------------------\n";

            String mutName = mutDir.getName();
            System.out.println("[LOG] Execute " + mutName);

            res[0] += "[LOG] Execute " +  mutName + "\n";
            boolean killed = execute(tsDir, mutDir);
            if (killed) {
                killedCnt++;
                System.out.println("[LOG] Killed " + mutName);

                res[0] += "[LOG] Killed " + mutName+ "\n";
            } else{
                System.out.println("[LOG] Survived " + mutName);
                res[0] += "[LOG] Survived " + mutName+ "\n";
            }

        }


        // Calculate mutation score
        System.out.println("[LOG] ======================================================\n");
        System.out.printf("[LOG] Stats: %d/%d(#killed/#total), score=%.2f\n",
                killedCnt, mutNum, calScore(killedCnt, mutNum));

        res[0] += "[LOG] ======================================================\n" +
                "[LOG] Stats: " + killedCnt + "/" + mutNum + "(#killed/#total)" + calScore(killedCnt,mutNum)+ "\n";

        res[1] += calScore(killedCnt,mutNum);
        return res;
    }

    private static String concateClassPath(String... paths) {
        StringBuilder cpBuilder = new StringBuilder();
        // Suitable for Unix system.
        for (int i = 0; i < paths.length; i++) {
            cpBuilder.append(paths[i]);
            if (i != paths.length - 1)
                cpBuilder.append(';');
        }
        return cpBuilder.toString();
    }

    /**
     * It executes once.
     *
     * @param tsDir  test suite dir, part of the classpath
     * @param mutDir mutant class dir, part of the classpath
     * @return whether the mutant is killed.
     */
    private static boolean execute(File tsDir, File mutDir) throws IOException, InterruptedException {

        // Build class path.
        String cp = concateClassPath(tsDir.getAbsolutePath(), mutDir.getAbsolutePath(),"C:\\Users\\21125\\Desktop\\my_mutant_testing\\target\\classes");

        // Construct executor
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", cp, TEST_SUITE_FQN);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        // Wait for execution to finish, or we cannot get exit value.
        p.waitFor();

        // Read execution info
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) break;
            System.out.println(line);
        }

        // 0 means survived, not 0 means killed.
        return p.exitValue() != 0;
    }

    private static double calScore(int killedCnt, int totalNum) {
        return ((double) killedCnt / (double) totalNum) * 100;
    }
}

