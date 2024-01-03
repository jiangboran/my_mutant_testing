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
                    File[] subDirectorys = mutantDir.listFiles((dir, name) -> new File(dir, name).isDirectory());
                    if(subDirectorys != null){

                       for (File subDirectory : subDirectorys){
                           // Locate each source file.
                           File[] sourceFiles = subDirectory.listFiles((dir, name) -> name.endsWith(".java"));
                           if (sourceFiles != null) {
                               for (File sourceFile : sourceFiles) {
                                   // Compile and output to the directory.
                                   System.out.println("javac " + sourceFile.getAbsolutePath());
                                   ProcessBuilder pb = new ProcessBuilder("javac", sourceFile.getAbsolutePath());
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
    }
}
