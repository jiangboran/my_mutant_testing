package mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import visitor.ABSExprCollector;

import java.util.List;
import java.util.Optional;

public class ABSMutator extends AbstractMutator {
    private List<NameExpr> mutPoints_name = null;
    private List<NameExpr> mutPoints_unary = null;
    private List<CompilationUnit> mutants = new NodeList<>();
    private final UnaryExpr.Operator[] absOps = {
            UnaryExpr.Operator.PLUS, UnaryExpr.Operator.MINUS
    };
    IntegerLiteralExpr zeroExpr = new IntegerLiteralExpr("0");

    public ABSMutator(CompilationUnit cu) {
        super(cu);
    }

    @Override
    public void locateMutationPoints() {
        // Locate mutation points for name operators
        mutPoints_name = ABSExprCollector.collect(this.origCU);
    }

    @Override
    public List<CompilationUnit> mutate() {
        // Sanity check.
        if (this.mutPoints_name == null)
            throw new RuntimeException("You must locate mutation points first!");
        // Modify each mutation point.
        for (NameExpr mp : mutPoints_name) {
            // Generate simple mutation. Each mutant contains only one
            // mutated point.
            NameExpr tmp = mp.clone();
//                    new NameExpr(String.valueOf(mp));
            for (UnaryExpr.Operator absOp : absOps) {
                mutants.add(mutateABS(tmp,mp, absOp));
            }
            mutants.add(mutateABS(mp,0));
        }
        return this.mutants;
    }

    // 对一元表达式进行绝对值变异
    private CompilationUnit mutateABS(NameExpr tmp,NameExpr mp, UnaryExpr.Operator absOp) {
        // Clone the original CompilationUnit]
        UnaryExpr unaryExpr = new UnaryExpr();
        unaryExpr.setOperator(absOp);
        unaryExpr.setExpression(tmp);
        mp.setName(String.valueOf(unaryExpr));
        CompilationUnit mutatedCU = this.origCU.clone();
        return mutatedCU;
    }
    private CompilationUnit mutateABS(NameExpr mp, int zero) {
        // Replace NameExpr instances equal to mp
        mp.setName(String.valueOf(zeroExpr));
        CompilationUnit mutatedCU = this.origCU.clone();
        return mutatedCU;
    }

    public List<CompilationUnit> getMutants() {
        if (mutants.isEmpty())
            System.out.println("Oops, seems no mutation has been conducted yet. Call mutate() first!");
        return mutants;
    }
}
