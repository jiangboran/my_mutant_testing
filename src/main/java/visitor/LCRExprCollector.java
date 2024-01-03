package visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.EnumSet;
import java.util.List;

/**
 * Collect binary expressions involving left-hand side replacement operation
 * @see BinaryExpr
 */
public class LCRExprCollector extends VoidVisitorAdapter<List<BinaryExpr>> {

    private static final EnumSet<BinaryExpr.Operator> LCR_OPERATORS = EnumSet.of(
            BinaryExpr.Operator.AND,
            BinaryExpr.Operator.OR
    );

    @Override
    public void visit(BinaryExpr n, List<BinaryExpr> arg) {
        super.visit(n, arg);
        if (isLCRBinaryExpr(n)) {
            arg.add(n);
        }
    }

    private boolean isLCRBinaryExpr(BinaryExpr binaryExpr) {
        return LCR_OPERATORS.contains(binaryExpr.getOperator());
    }

    public static List<BinaryExpr> collect(CompilationUnit cu) {
        LCRExprCollector collector = new LCRExprCollector();
        List<BinaryExpr> lcrBinaryExprList = List.of();
        collector.visit(cu, lcrBinaryExprList);
        return lcrBinaryExprList;
    }
}
