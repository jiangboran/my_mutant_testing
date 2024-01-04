package poster.service.impl;

import compiler.CompileMutants;
import engine.*;
import executiom.MutantExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poster.dao.MutationDao;
import poster.pojo.entity.MutationEntity;
import poster.service.MutationService;

import java.io.IOException;

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

        pool = getPools();
        MutationEntity mutationEntity = MutationEntity.builder().type(type).score(score).message(message).pool(pool).build();

//        mutationDao.save(mutationEntity);
        return mutationEntity;
    }

    public String[] MutateByType(String type) throws IOException, InterruptedException {
        String[] mes = new String[3];
        //生成变异体
        mes[0] += MutationCreate(type);
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
        if(type.equals("ABS")){
            return ABSMutationEngine.main(args);
        } else if(type.equals("AOR")){
            return AORMutationEngine.main(args);
        } else if(type.equals("LCR")){
            return LCRMutationEngine.main(args);
        }
        else if(type.equals("ROR")){
            return RORMutationEngine.main(args);
        }
        else if(type.equals("UOI")) {
            return UOIMutationEngine.main(args);
        }
        else if(type.equals("Unary")){
            return UnaryMutationEngine.main(args);
        }
        else if(type.equals("Binary")){
            return BinaryMutationEngine.main(args);
        }
        else if(type.equals("Comprehensive")){
            return ComprehensiveMutationEngine.main(args);
        } else{
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

    public String getPools(){
        String res = new String();
//        S:\File\Study\Software Testing\my_mutant_testing\pool

        return res;
    }
}
