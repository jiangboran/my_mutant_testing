package visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

/**
 * Collect unary expressions
 * @see UnaryExpr
 */
public class UnaryExprCollector extends VoidVisitorAdapter<List<UnaryExpr>> {

    @Override
    public void visit(UnaryExpr n, List<UnaryExpr> arg) {
        super.visit(n, arg);
        arg.add(n);
    }

    public static List<UnaryExpr> collect(CompilationUnit cu) {
        UnaryExprCollector collector = new UnaryExprCollector();
        List<UnaryExpr> unaryExprList = new NodeList<>();
        collector.visit(cu, unaryExprList);
        return unaryExprList;
    }
}
