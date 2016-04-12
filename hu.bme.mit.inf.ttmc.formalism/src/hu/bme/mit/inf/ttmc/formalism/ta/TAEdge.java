package hu.bme.mit.inf.ttmc.formalism.ta;

import java.util.Collection;

import hu.bme.mit.inf.ttmc.core.expr.Expr;
import hu.bme.mit.inf.ttmc.core.type.BoolType;
import hu.bme.mit.inf.ttmc.formalism.common.Edge;
import hu.bme.mit.inf.ttmc.formalism.common.decl.ClockDecl;

public interface TAEdge extends Edge<TALoc, TAEdge> {

	public Expr<? extends BoolType> getGuard();

	public Collection<ClockDecl> getResets();
}