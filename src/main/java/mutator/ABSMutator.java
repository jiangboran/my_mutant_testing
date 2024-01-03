package mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import visitor.ABSExprCollector;

import java.util.List;

public class ABSMutator extends AbstractMutator {
    private List<NameExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();

    public ABSMutator(CompilationUnit cu) {
        super(cu);
    }

    @Override
    public void locateMutationPoints() {
        // Locate mutation points for name operators
        mutPoints = ABSExprCollector.collect(this.origCU);
    }

    @Override
    public List<CompilationUnit> mutate() {
        // Sanity check.
        if (this.mutPoints == null)
            throw new RuntimeException("You must locate mutation points first!");
        // Modify each mutation point.
        for (NameExpr mp : mutPoints) {
            // Generate simple mutation. Each mutant contains only one
            // mutated point.
            mutants.add(mutateABS(mp));
        }
        return this.mutants;
    }

    // 对一元表达式进行绝对值变异
    private CompilationUnit mutateABS(NameExpr mp) {
        // 创建一个表示调用 Math.abs 方法的新 MethodCallExpr
        MethodCallExpr absExpr = new MethodCallExpr(new NameExpr("Math"), "abs");
        absExpr.addArgument(String.valueOf(mp.getName().clone()));

        // 在 AST 中用 ABS 表达式替换原始表达式
        mp.replace(absExpr);

        // 现在 CompilationUnit (CU) 是变异后的版本。返回其克隆。
        CompilationUnit mutatedCU = this.origCU.clone();

        // 为了进行未来的变异，恢复原始表达式
        absExpr.getArguments().clear();
        absExpr.addArgument(String.valueOf(mp.getName().clone()));

        return mutatedCU;
    }

    public List<CompilationUnit> getMutants() {
        if (mutants.isEmpty())
            System.out.println("Oops, seems no mutation has been conducted yet. Call mutate() first!");
        return mutants;
    }
}
