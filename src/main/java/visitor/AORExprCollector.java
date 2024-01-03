package visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.EnumSet;
import java.util.List;

/**
 * Collect binary expressions involving absolute value operation
 * @see BinaryExpr
 */
public class AORExprCollector extends VoidVisitorAdapter<List<BinaryExpr>> {

    private static final EnumSet<BinaryExpr.Operator> AOR_OPERATORS = EnumSet.of(
            BinaryExpr.Operator.PLUS,
            BinaryExpr.Operator.MINUS,
            BinaryExpr.Operator.MULTIPLY,
            BinaryExpr.Operator.DIVIDE
    );

    @Override
    public void visit(BinaryExpr n, List<BinaryExpr> arg) {
        super.visit(n, arg);
        if (isABSBinaryExpr(n)) {
            arg.add(n);
        }
    }

    private boolean isABSBinaryExpr(BinaryExpr binaryExpr) {
        return AOR_OPERATORS.contains(binaryExpr.getOperator());
    }

    public static List<BinaryExpr> collect(CompilationUnit cu) {
        AORExprCollector collector = new AORExprCollector();
        List<BinaryExpr> absBinaryExprList = List.of();
        collector.visit(cu, absBinaryExprList);
        return absBinaryExprList;
    }
}
