package engine;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import mutator.RORMutator;
import mutator.Mutator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * source-level rormutation engine using javaparser.
 */
public class RORMutationEngine {

    public static String main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("engine.RORMutationEngine: <source_java_file> <mutant_pool_dir>");
            System.exit(0);
        }

        // Read in original program(s).
        File srcFile = new File(args[0]);
        File outDir = new File(args[1]);
        System.out.println("[LOG] Source file: " + srcFile.getAbsolutePath());
        System.out.println("[LOG] Output dir: " + outDir.getAbsolutePath());

// Initialize mutator(s).
        CompilationUnit cu = StaticJavaParser.parse(srcFile);
        Mutator rorMutator = new RORMutator(cu);

// Locate mutation points.

        rorMutator.locateMutationPoints();

// Fire off mutation and collect mutants.
        List<CompilationUnit> mutCUs = new ArrayList<>(rorMutator.mutate());



        System.out.printf("[LOG] Generate %d mutants.\n", mutCUs.size());

        // Preserve to local.
        preserveToLocal(outDir, srcFile, cu, mutCUs);

        return "[LOG] Generate " + mutCUs.size() + "mutants.\n";
    }


    /**
     * Write mutants to disk.
     */
    private static void preserveToLocal(
            File outDir,
            File srcFile,
            CompilationUnit cu,
            List<CompilationUnit> mutants) throws IOException {

        // Recreate outDir if it is existed.
        if (outDir.exists()) {
            FileUtils.forceDelete(outDir);
            System.out.println("[LOG] Delete existing outDir." );
        }
        boolean mkdirs = outDir.mkdirs();
        if (mkdirs)
            System.out.println("[LOG] Create outDir: " + outDir.getAbsolutePath());


        // Path relevant to package info
        String packPath = "";

        // Get file name.
        String srcFileName = srcFile.getName();

        // Get package info.
        Optional<PackageDeclaration> opPD = cu.getPackageDeclaration();
        if (opPD.isPresent()) {
            // Turn package info like 'edu.nju.ise' to path 'edu/nju/ise/'
            String packInfo = opPD.get().getName().asString();
            packPath = packInfo.replace('.', '/');
        }

        // Write mutant to local.
        String pattern = "mut-%d/%s";
        for (int i = 0 ; i < mutants.size(); i++) {
            // Create directory to preserve the mutant
            File srcFileDir = new File(outDir, String.format(pattern, i + 1, packPath));
            mkdirs = srcFileDir.mkdirs();
            if (mkdirs)
                System.out.println("[LOG] Create src file dir: " + srcFileDir.getAbsolutePath());
            // Write mutant to local.
            File mutSrcFile = new File(srcFileDir, srcFileName);
            FileWriter fw = new FileWriter(mutSrcFile);
            fw.write(mutants.get(i).toString());
            fw.close();
        }

    }


}