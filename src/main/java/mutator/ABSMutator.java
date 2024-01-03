package mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import visitor.ABSExprCollector;
import visitor.UOIExprCollector;

import java.util.List;

public class ABSMutator extends AbstractMutator {
    private List<NameExpr> mutPoints_name = null;
    private List<UnaryExpr> mutPoints_unary = null;
    private List<CompilationUnit> mutants = new NodeList<>();
    private final UnaryExpr.Operator[] absOps = {
            UnaryExpr.Operator.PLUS, UnaryExpr.Operator.MINUS, UnaryExpr.Operator.BITWISE_COMPLEMENT
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
            NameExpr temp = mp.clone();
            for (UnaryExpr.Operator absOp : absOps) {
                mutants.add(mutateABS(mp, absOp));
            }
            mp.setName(String.valueOf(temp));
        }
        return this.mutants;
    }

    // 对一元表达式进行绝对值变异
    private CompilationUnit mutateABS(NameExpr mp,UnaryExpr.Operator absOp) {
        if(absOp.equals(UnaryExpr.Operator.BITWISE_COMPLEMENT)){
            mp.setName(String.valueOf("0"));
        }else {
            UnaryExpr unaryExpr = new UnaryExpr();
            unaryExpr.setOperator(absOp);
            unaryExpr.setExpression(mp);
            if(absOp.equals(UnaryExpr.Operator.MINUS))
                mp.setName(String.valueOf(unaryExpr));
        }
        return this.origCU.clone();
    }

    public List<CompilationUnit> getMutants() {
        if (mutants.isEmpty())
            System.out.println("Oops, seems no mutation has been conducted yet. Call mutate() first!");
        return mutants;
    }
}
