package mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import visitor.BinaryExprCollector;

import java.util.List;

import static com.github.javaparser.ast.expr.BinaryExpr.Operator.*;

public class BinaryMutator extends AbstractMutator {

    private List<BinaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();

    private final BinaryExpr.Operator[] aorOps = {
            PLUS, MINUS, MULTIPLY, DIVIDE
    };

    private final BinaryExpr.Operator[] lcrOps = {
            BinaryExpr.Operator.AND, BinaryExpr.Operator.OR
    };

    private final BinaryExpr.Operator[] rorOps = {
            BinaryExpr.Operator.EQUALS, BinaryExpr.Operator.NOT_EQUALS,
            BinaryExpr.Operator.LESS, BinaryExpr.Operator.LESS_EQUALS,
            BinaryExpr.Operator.GREATER, BinaryExpr.Operator.GREATER_EQUALS
    };

    public BinaryMutator(CompilationUnit cu) {
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

            // AOR Mutation
            for (BinaryExpr.Operator aorOp : aorOps) {
                if (origOp.equals(aorOp))
                    continue;
                mutants.add(mutateAOR(mp, aorOp));
            }

            // LCR Mutation
            for (BinaryExpr.Operator lcrOp : lcrOps) {
                if (origOp.equals(lcrOp))
                    continue;
                mutants.add(mutateLCR(mp, lcrOp));
            }

            // ROR Mutation
            for (BinaryExpr.Operator rorOp : rorOps) {
                if (origOp.equals(rorOp))
                    continue;
                mutants.add(mutateROR(mp, rorOp));
            }
        }

        return this.mutants;
    }

    private CompilationUnit mutateAOR(BinaryExpr mp, BinaryExpr.Operator aorOp) {
        mp.setOperator(aorOp);
        return this.origCU.clone();
    }

    private CompilationUnit mutateLCR(BinaryExpr mp, BinaryExpr.Operator lcrOp) {
        mp.setOperator(lcrOp);
        return this.origCU.clone();
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
