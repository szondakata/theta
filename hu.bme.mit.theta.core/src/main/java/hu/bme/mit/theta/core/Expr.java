package hu.bme.mit.theta.core;

import static com.google.common.collect.ImmutableList.toImmutableList;

import java.util.List;
import java.util.function.Function;

import hu.bme.mit.theta.core.model.Valuation;

public interface Expr<ExprType extends Type> {

	int getArity();

	ExprType getType();

	LitExpr<ExprType> eval(Valuation val);

	List<? extends Expr<?>> getOps();

	Expr<ExprType> withOps(List<? extends Expr<?>> ops);

	public default Expr<ExprType> map(final Function<? super Expr<?>, ? extends Expr<?>> function) {
		return withOps(getOps().stream().map(op -> function.apply(op)).collect(toImmutableList()));
	}

}