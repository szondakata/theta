package hu.bme.mit.theta.analysis.expr;

import static com.google.common.base.Preconditions.checkNotNull;

import hu.bme.mit.theta.core.expr.Expr;
import hu.bme.mit.theta.core.type.BoolType;

final class SimpleExprState implements ExprState {

	private final Expr<? extends BoolType> expr;

	private SimpleExprState(final Expr<? extends BoolType> expr) {
		this.expr = checkNotNull(expr);
	}

	public static SimpleExprState of(final Expr<? extends BoolType> expr) {
		return new SimpleExprState(expr);
	}

	@Override
	public Expr<? extends BoolType> toExpr() {
		return expr;
	}

}