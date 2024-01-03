package visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

/**
 * Collect unary expressions involving absolute value operation
 * @see NameExpr
 */
public class ABSExprCollector extends VoidVisitorAdapter<List<NameExpr>> {

    @Override
    public void visit(NameExpr n, List<NameExpr> arg) {
        super.visit(n, arg);
        // Check if the unary expression is a candidate for ABS mutation
        if (isABSNameExpr(n)) {
            arg.add(n);
        }
    }

    private boolean isABSNameExpr(NameExpr nameExpr) {
        // Include all unary expressions
        return true;
    }

    public static List<NameExpr> collect(CompilationUnit cu) {
        ABSExprCollector collector = new ABSExprCollector();
        List<NameExpr> absNameExprList = new NodeList<>();
        collector.visit(cu, absNameExprList);
        return absNameExprList;
    }
}
