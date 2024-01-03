package mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import visitor.UnaryExprCollector;

import java.util.List;

import static com.github.javaparser.ast.expr.BinaryExpr.Operator.*;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.DIVIDE;

public class ABSMutator extends AbstractMutator{
    private List<UnaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();
    private final UnaryExpr.Operator[] absOps = {
            UnaryExpr.Operator.PLUS, UnaryExpr.Operator.MINUS
    };
    public ABSMutator(CompilationUnit cu) {
        super(cu);
    }


    @Override
    public void locateMutationPoints() {
        // Locate mutation points for unary operators
        mutPoints = UnaryExprCollector.collect(this.origCU);
    }

    @Override
    public List<CompilationUnit> mutate() {
        // Sanity check.
        if (this.mutPoints == null)
            throw new RuntimeException("You must locate mutation points first!");
        // Modify each mutation point.
        for (UnaryExpr mp : mutPoints) {
            // This is a polluted operation. So we preserve the original
            // operator for recovering.
            UnaryExpr.Operator origOp = mp.getOperator();

            // Generate simple mutation. Each mutant contains only one
            // mutated point.
            // ABS Mutation
            for (UnaryExpr.Operator absOp : absOps) {
                mutants.add(mutateABS(mp, absOp));
            }

            // Recovering
            mp.setOperator(origOp);
        }
        return this.mutants;
    }
    private CompilationUnit mutateABS(UnaryExpr mp, UnaryExpr.Operator absOp) {
        UnaryExpr absExpr = new UnaryExpr();
        absExpr.setOperator(absOp);
        absExpr.setExpression(mp.getExpression().clone());
        mp.setExpression(absExpr);
        return this.origCU.clone();
    }
    public List<CompilationUnit> getMutants() {
        if (mutants.isEmpty())
            System.out.println("Oops, seems no mutation has been conducted yet. Call mutate() first!");
        return mutants;
    }
}
