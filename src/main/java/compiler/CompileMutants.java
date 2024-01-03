package compiler;

import java.io.File;
import java.io.IOException;

public class CompileMutants {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 1) {
            System.out.println("<prepare-mutest>: <POOL_DIR>");
            System.exit(1);
        }

        // Mutant pool dir
        String poolDir = args[0];
        System.out.println("[LOG] Source POOL_DIR : <POOL_DIR>");

        // Compile mutants
        File poolDirFile = new File(poolDir);
        File[] mutantDirs = poolDirFile.listFiles();

        if (mutantDirs != null) {
            for (File mutantDir : mutantDirs) {
                if (mutantDir.isDirectory()) {
                    String mutantDirPath = mutantDir.getAbsolutePath();

                    // Locate each source file.
                    File[] sourceFiles = mutantDir.listFiles((dir, name) -> name.endsWith(".java"));

                    if (sourceFiles != null) {
                        for (File sourceFile : sourceFiles) {
                            // Compile and output to the directory.
                            System.out.println("javac -d " + mutantDirPath + " " + sourceFile.getAbsolutePath());
                            ProcessBuilder pb = new ProcessBuilder("javac", "-d", mutantDirPath, sourceFile.getAbsolutePath());
                            pb.redirectErrorStream(true);
                            Process p = pb.start();
                            p.waitFor();
                        }
                    }
                }
            }
        }
    }
}
