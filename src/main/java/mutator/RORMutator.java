package mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import visitor.BinaryExprCollector;

import java.util.List;

public class RORMutator extends AbstractMutator{
    private List<BinaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();
    private final BinaryExpr.Operator[] rorOps = {
            BinaryExpr.Operator.EQUALS, BinaryExpr.Operator.NOT_EQUALS,
            BinaryExpr.Operator.LESS, BinaryExpr.Operator.LESS_EQUALS,
            BinaryExpr.Operator.GREATER, BinaryExpr.Operator.GREATER_EQUALS
    };
    public RORMutator(CompilationUnit cu) {
        super(cu);
    }

    @Override
    public void locateMutationPoints() {
        mutPoints = BinaryExprCollector.collect(this.origCU);
    }
    @Override
    public List<CompilationUnit> mutate() {
        if (this.mutPoints == null)
            throw new RuntimeException("You must locate mutation points first!");

        for (BinaryExpr mp : mutPoints) {
            BinaryExpr.Operator origOp = mp.getOperator();

            // ROR Mutation
            for (BinaryExpr.Operator rorOp : rorOps) {
                if (origOp.equals(rorOp))
                    continue;
                mutants.add(mutateROR(mp, rorOp));
            }
        }

        return this.mutants;
    }
    private CompilationUnit mutateROR(BinaryExpr mp, BinaryExpr.Operator rorOp) {
        mp.setOperator(rorOp);
        return this.origCU.clone();
    }

    public List<CompilationUnit> getMutants() {
        if (mutants.isEmpty())
            System.out.println("Oops, seems no mutation has been conducted yet. Call mutate() first!");
        return mutants;
    }
}
