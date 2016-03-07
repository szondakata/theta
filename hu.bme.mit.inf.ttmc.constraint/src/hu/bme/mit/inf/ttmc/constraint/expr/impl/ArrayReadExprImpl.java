package hu.bme.mit.inf.ttmc.constraint.expr.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import hu.bme.mit.inf.ttmc.constraint.expr.ArrayReadExpr;
import hu.bme.mit.inf.ttmc.constraint.expr.Expr;
import hu.bme.mit.inf.ttmc.constraint.type.ArrayType;
import hu.bme.mit.inf.ttmc.constraint.type.Type;
import hu.bme.mit.inf.ttmc.constraint.utils.ExprVisitor;

public class ArrayReadExprImpl<IndexType extends Type, ElemType extends Type> extends AbstractExpr<ElemType>
		implements ArrayReadExpr<IndexType, ElemType> {

	private final Expr<? extends ArrayType<? super IndexType, ? extends ElemType>> array;
	private final Expr<? extends IndexType> index;

	public ArrayReadExprImpl(final Expr<? extends ArrayType<? super IndexType, ? extends ElemType>> array,
			final Expr<? extends IndexType> index) {

		this.array = checkNotNull(array);
		this.index = checkNotNull(index);
	}

	@Override
	public Expr<? extends ArrayType<? super IndexType, ? extends ElemType>> getArray() {
		return array;
	}

	@Override
	public Expr<? extends IndexType> getIndex() {
		return index;
	}

	@Override
	public ArrayReadExpr<IndexType, ElemType> with(
			Expr<? extends ArrayType<? super IndexType, ? extends ElemType>> array, Expr<? extends IndexType> index) {
		if (this.array == array && this.index == index) {
			return this;
		} else {
			return new ArrayReadExprImpl<>(array, index);
		}
	}

	@Override
	protected int getHashSeed() {
		return 1321;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Read(");
		sb.append(array);
		sb.append(", ");
		sb.append(index);
		sb.append(")");
		return sb.toString();
	}
	
	@Override
	public <P, R> R accept(ExprVisitor<? super P, ? extends R> visitor, P param) {
		return visitor.visit(this, param);
	}

	@Override
	public ArrayReadExpr<IndexType, ElemType> withArray(
			final Expr<? extends ArrayType<? super IndexType, ? extends ElemType>> array) {
		return with(array, getIndex());
	}

	@Override
	public ArrayReadExpr<IndexType, ElemType> withIndex(final Expr<? extends IndexType> index) {
		return with(getArray(), index);
	}

}
