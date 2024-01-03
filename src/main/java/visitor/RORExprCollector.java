package visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.EnumSet;
import java.util.List;

/**
 * Collect binary expressions involving right-hand side replacement operation
 * @see BinaryExpr
 */
public class RORExprCollector extends VoidVisitorAdapter<List<BinaryExpr>> {

    private static final EnumSet<BinaryExpr.Operator> ROR_OPERATORS = EnumSet.of(
            BinaryExpr.Operator.EQUALS,
            BinaryExpr.Operator.NOT_EQUALS,
            BinaryExpr.Operator.LESS,
            BinaryExpr.Operator.LESS_EQUALS,
            BinaryExpr.Operator.GREATER,
            BinaryExpr.Operator.GREATER_EQUALS
    );

    @Override
    public void visit(BinaryExpr n, List<BinaryExpr> arg) {
        super.visit(n, arg);
        if (isRORBinaryExpr(n)) {
            arg.add(n);
        }
    }

    private boolean isRORBinaryExpr(BinaryExpr binaryExpr) {
        return ROR_OPERATORS.contains(binaryExpr.getOperator());
    }

    public static List<BinaryExpr> collect(CompilationUnit cu) {
        RORExprCollector collector = new RORExprCollector();
        List<BinaryExpr> rorBinaryExprList = new NodeList<>();
        collector.visit(cu, rorBinaryExprList);
        return rorBinaryExprList;
    }
}
