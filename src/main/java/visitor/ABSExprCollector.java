package visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

/**
 * Collect unary expressions involving absolute value operation
 * @see UnaryExpr
 */
public class ABSExprCollector extends VoidVisitorAdapter<List<UnaryExpr>> {

    @Override
    public void visit(UnaryExpr n, List<UnaryExpr> arg) {
        super.visit(n, arg);
        // Check if the unary expression is a candidate for ABS mutation
        if (isABSUnaryExpr(n)) {
            arg.add(n);
        }
    }

    private boolean isABSUnaryExpr(UnaryExpr unaryExpr) {
        // Include all unary expressions
        return true;
    }

    public static List<UnaryExpr> collect(CompilationUnit cu) {
        ABSExprCollector collector = new ABSExprCollector();
        List<UnaryExpr> absUnaryExprList = List.of();
        collector.visit(cu, absUnaryExprList);
        return absUnaryExprList;
    }
}
