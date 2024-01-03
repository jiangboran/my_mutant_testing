package mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.UnaryExpr;
import visitor.UOIExprCollector;

import java.util.List;

public class UOIMutator extends AbstractMutator{
    private List<UnaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();
    private final UnaryExpr.Operator[] uoiOps = {
            UnaryExpr.Operator.PLUS, UnaryExpr.Operator.MINUS
    };
    public UOIMutator(CompilationUnit cu) {
        super(cu);
    }
    @Override
    public void locateMutationPoints() {
        // Locate mutation points for unary operators
        mutPoints = UOIExprCollector.collect(this.origCU);
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

            // UOI Mutation
            for (UnaryExpr.Operator uoiOp : uoiOps) {
                // Skip self
                if (origOp.equals(uoiOp))
                    continue;
                // Mutate
                mutants.add(mutateUOI(mp, uoiOp));
            }

            // Recovering
            mp.setOperator(origOp);
        }
        return this.mutants;
    }
    private CompilationUnit mutateUOI(UnaryExpr mp, UnaryExpr.Operator uoiOp) {
        mp.setOperator(uoiOp);
        // Now the CU is a mutated one. Return its clone.
        return this.origCU.clone();
    }
    public List<CompilationUnit> getMutants() {
        if (mutants.isEmpty())
            System.out.println("Oops, seems no mutation has been conducted yet. Call mutate() first!");
        return mutants;
    }
}
