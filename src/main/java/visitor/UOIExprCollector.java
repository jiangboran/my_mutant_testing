package visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.EnumSet;
import java.util.List;

/**
 * Collect unary expressions involving unary operator replacement operation
 * @see UnaryExpr
 */
public class UOIExprCollector extends VoidVisitorAdapter<List<UnaryExpr>> {

    private static final EnumSet<UnaryExpr.Operator> UOI_OPERATORS = EnumSet.of(
            UnaryExpr.Operator.PLUS,
            UnaryExpr.Operator.MINUS
    );

    @Override
    public void visit(UnaryExpr n, List<UnaryExpr> arg) {
        super.visit(n, arg);
        if (isUOIUnaryExpr(n)) {
            arg.add(n);
        }
    }

    private boolean isUOIUnaryExpr(UnaryExpr unaryExpr) {
        return UOI_OPERATORS.contains(unaryExpr.getOperator());
    }

    public static List<UnaryExpr> collect(CompilationUnit cu) {
        UOIExprCollector collector = new UOIExprCollector();
        List<UnaryExpr> uoiUnaryExprList = new NodeList<>();
        collector.visit(cu, uoiUnaryExprList);
        return uoiUnaryExprList;
    }
}
