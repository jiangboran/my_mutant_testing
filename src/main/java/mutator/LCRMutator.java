package mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import visitor.LCRExprCollector;

import java.util.List;

public class LCRMutator extends AbstractMutator{
    private List<BinaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();
    private final BinaryExpr.Operator[] lcrOps = {
            BinaryExpr.Operator.AND, BinaryExpr.Operator.OR
    };
    public LCRMutator(CompilationUnit cu) {
        super(cu);
    }

    @Override
    public void locateMutationPoints() {
        mutPoints = LCRExprCollector.collect(this.origCU);
    }
    @Override
    public List<CompilationUnit> mutate() {
        if (this.mutPoints == null)
            throw new RuntimeException("You must locate mutation points first!");

        for (BinaryExpr mp : mutPoints) {
            BinaryExpr.Operator origOp = mp.getOperator();
            // LCR Mutation
            for (BinaryExpr.Operator lcrOp : lcrOps) {
                mutants.add(mutateLCR(mp, lcrOp));
            }

        }
        return this.mutants;
    }
    private CompilationUnit mutateLCR(BinaryExpr mp, BinaryExpr.Operator lcrOp) {
        mp.setOperator(lcrOp);
        return this.origCU.clone();
    }
    public List<CompilationUnit> getMutants() {
        if (mutants.isEmpty())
            System.out.println("Oops, seems no mutation has been conducted yet. Call mutate() first!");
        return mutants;
    }
}
