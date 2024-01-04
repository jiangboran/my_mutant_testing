package poster.service.impl;

import compiler.CompileMutants;
import engine.*;
import executiom.MutantExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poster.dao.MutationDao;
import poster.pojo.entity.MutationEntity;
import poster.service.MutationService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MutationServiceImpl implements MutationService {
    private final MutationDao mutationDao;
    @Override
    public MutationEntity getMutationEntity(String type) throws IOException, InterruptedException {
        String score = "Score:";
        String message = "Message:\n";
        String pool = "Pool:\n";
        String[] tmp = MutateByType(type);
        message += tmp[0] + tmp[1];
        score += tmp[2];

        pool += getPools();

//        mutationDao.save(mutationEntity);
        return MutationEntity.builder().type(type).score(score).message(message).pool(pool).build();
    }

    public String[] MutateByType(String type) throws IOException, InterruptedException {
        String[] mes = new String[3];
        //生成变异体
        mes[0] = MutationCreate(type);
        //编译
        MutantsCompile();
        //测试
        String[] tmp = MutationExecution();
        mes[1] = tmp[0];
        mes[2] = tmp[1];
        return mes;
    }
    public String MutationCreate(String type) throws IOException {
        String src_file = "S:\\File\\Study\\Software Testing\\my_mutant_testing\\src\\main\\java\\example\\Calculator.java";
        String pool_dir = "S:\\File\\Study\\Software Testing\\my_mutant_testing\\pool";

        String[] args = new String[2];
        args[0] = src_file;
        args[1] = pool_dir;
        switch (type) {
            case "ABS":
                return ABSMutationEngine.main(args);
            case "AOR":
                return AORMutationEngine.main(args);
            case "LCR":
                return LCRMutationEngine.main(args);
            case "ROR":
                return RORMutationEngine.main(args);
            case "UOI":
                return UOIMutationEngine.main(args);
            case "Unary":
                return UnaryMutationEngine.main(args);
            case "Binary":
                return BinaryMutationEngine.main(args);
            case "Comprehensive":
                return ComprehensiveMutationEngine.main(args);
            default:
                return "TYPE_NOT_FOUND\n";
        }
    }
    public void MutantsCompile() throws IOException, InterruptedException {
        String pool_dir = "S:\\File\\Study\\Software Testing\\my_mutant_testing\\pool";

        String[] args = new String[1];
        args[0] = pool_dir;

        CompileMutants.main(args);
    }
    public String[] MutationExecution() throws IOException, InterruptedException {
        String testsuite_dir = "S:\\File\\Study\\Software Testing\\testsuite";
        String pool_dir = "S:\\File\\Study\\Software Testing\\my_mutant_testing\\pool";
//<testsuite_dir> <pool_dir>
        String[] args = new String[2];
        args[0] = testsuite_dir;
        args[1] = pool_dir;
        return MutantExecution.main(args);
    }

    public static String getPools() {
        StringBuilder result = new StringBuilder();
        String pool_dir = "S:\\File\\Study\\Software Testing\\my_mutant_testing\\pool";
        File poolDir = new File(pool_dir);
        File[] muts = poolDir.listFiles();
        if (muts != null) {
            for(File mut : muts){
                result.append(mut.getName() + "/");
                File directory = Objects.requireNonNull(mut.listFiles())[0];
                if (directory.exists() && directory.isDirectory()) {
                    List<File> javaFiles = listJavaFiles(directory);

                    for (File javaFile : javaFiles) {
                        try {
                            String content = new String(Files.readAllBytes(javaFile.toPath()));
                            result.append(javaFile.getName()).append("\n").append(content).append("\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                            // 在实际应用中，你可能需要根据具体情况处理异常
                        }
                    }
                } else {
                    result.append("Directory does not exist\n");
                }
            }
        }
//        File directory = new File(pool_dir);
        return result.toString();
    }

    private static List<File> listJavaFiles(File directory) {
        return Arrays.asList(Objects.requireNonNull(directory.listFiles((dir, name) -> name.endsWith(".java"))));
    }
}
