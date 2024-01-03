package mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import visitor.AORExprCollector;

import java.util.List;

import static com.github.javaparser.ast.expr.BinaryExpr.Operator.*;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.DIVIDE;

public class AORMutator extends AbstractMutator{
    private List<BinaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();

    private final BinaryExpr.Operator[] aorOps = {
            PLUS, MINUS, MULTIPLY, DIVIDE, REMAINDER
    };
    public AORMutator(CompilationUnit cu) {
        super(cu);
    }
    @Override
    public void locateMutationPoints() {
        mutPoints = AORExprCollector.collect(this.origCU);
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

        }

        return this.mutants;
    }
    private CompilationUnit mutateAOR(BinaryExpr mp, BinaryExpr.Operator aorOp) {
        mp.setOperator(aorOp);
        return this.origCU.clone();
    }
    public List<CompilationUnit> getMutants() {
        if (mutants.isEmpty())
            System.out.println("Oops, seems no mutation has been conducted yet. Call mutate() first!");
        return mutants;
    }
}
