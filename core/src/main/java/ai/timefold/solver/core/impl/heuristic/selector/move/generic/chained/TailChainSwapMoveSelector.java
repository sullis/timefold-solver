package ai.timefold.solver.core.impl.heuristic.selector.move.generic.chained;

import java.util.Iterator;

import ai.timefold.solver.core.impl.domain.variable.anchor.AnchorVariableDemand;
import ai.timefold.solver.core.impl.domain.variable.anchor.AnchorVariableSupply;
import ai.timefold.solver.core.impl.domain.variable.descriptor.BasicVariableDescriptor;
import ai.timefold.solver.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;
import ai.timefold.solver.core.impl.domain.variable.inverserelation.SingletonInverseVariableDemand;
import ai.timefold.solver.core.impl.domain.variable.inverserelation.SingletonInverseVariableSupply;
import ai.timefold.solver.core.impl.domain.variable.supply.SupplyManager;
import ai.timefold.solver.core.impl.heuristic.move.Move;
import ai.timefold.solver.core.impl.heuristic.selector.IterableSelector;
import ai.timefold.solver.core.impl.heuristic.selector.common.iterator.AbstractOriginalChangeIterator;
import ai.timefold.solver.core.impl.heuristic.selector.common.iterator.AbstractRandomChangeIterator;
import ai.timefold.solver.core.impl.heuristic.selector.entity.EntitySelector;
import ai.timefold.solver.core.impl.heuristic.selector.move.generic.GenericMoveSelector;
import ai.timefold.solver.core.impl.heuristic.selector.value.ValueSelector;
import ai.timefold.solver.core.impl.solver.scope.SolverScope;

/**
 * Also known as a 2-opt move selector.
 */
public class TailChainSwapMoveSelector<Solution_> extends GenericMoveSelector<Solution_> {

    protected final EntitySelector<Solution_> entitySelector;
    protected final ValueSelector<Solution_> valueSelector;
    protected final boolean randomSelection;

    protected SingletonInverseVariableSupply inverseVariableSupply;
    protected AnchorVariableSupply anchorVariableSupply;

    public TailChainSwapMoveSelector(EntitySelector<Solution_> entitySelector, ValueSelector<Solution_> valueSelector,
            boolean randomSelection) {
        this.entitySelector = entitySelector;
        this.valueSelector = valueSelector;
        this.randomSelection = randomSelection;
        GenuineVariableDescriptor<Solution_> variableDescriptor = valueSelector.getVariableDescriptor();
        boolean isChained = variableDescriptor instanceof BasicVariableDescriptor<Solution_> basicVariableDescriptor
                && basicVariableDescriptor.isChained();
        if (!isChained) {
            throw new IllegalStateException("The selector (%s)'s valueSelector's variableDescriptor (%s) must be chained (%s)."
                    .formatted(this, variableDescriptor, isChained));
        }
        if (!variableDescriptor.getEntityDescriptor().getEntityClass().isAssignableFrom(
                entitySelector.getEntityDescriptor().getEntityClass())) {
            throw new IllegalStateException("The selector (" + this
                    + ") has a valueSelector with a entityClass ("
                    + variableDescriptor.getEntityDescriptor().getEntityClass()
                    + ") which is not equal or a superclass to the entitySelector's entityClass ("
                    + entitySelector.getEntityDescriptor().getEntityClass() + ").");
        }
        phaseLifecycleSupport.addEventListener(entitySelector);
        phaseLifecycleSupport.addEventListener(valueSelector);
    }

    @Override
    public void solvingStarted(SolverScope<Solution_> solverScope) {
        super.solvingStarted(solverScope);
        SupplyManager supplyManager = solverScope.getScoreDirector().getSupplyManager();
        GenuineVariableDescriptor<Solution_> variableDescriptor = valueSelector.getVariableDescriptor();
        inverseVariableSupply = supplyManager.demand(new SingletonInverseVariableDemand<>(variableDescriptor));
        anchorVariableSupply = supplyManager.demand(new AnchorVariableDemand<>(variableDescriptor));
    }

    @Override
    public void solvingEnded(SolverScope<Solution_> solverScope) {
        super.solvingEnded(solverScope);
        inverseVariableSupply = null;
        anchorVariableSupply = null;
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public boolean isCountable() {
        return entitySelector.isCountable() && valueSelector.isCountable();
    }

    @Override
    public boolean isNeverEnding() {
        return randomSelection || entitySelector.isNeverEnding() || valueSelector.isNeverEnding();
    }

    @Override
    public long getSize() {
        if (valueSelector instanceof IterableSelector) {
            return entitySelector.getSize() * ((IterableSelector<Solution_, ?>) valueSelector).getSize();
        } else {
            long size = 0;
            for (Iterator<?> it = entitySelector.endingIterator(); it.hasNext();) {
                Object entity = it.next();
                size += valueSelector.getSize(entity);
            }
            return size;
        }
    }

    @Override
    public Iterator<Move<Solution_>> iterator() {
        final GenuineVariableDescriptor<Solution_> variableDescriptor = valueSelector.getVariableDescriptor();
        if (!randomSelection) {
            return new AbstractOriginalChangeIterator<>(entitySelector, valueSelector) {
                @Override
                protected Move<Solution_> newChangeSelection(Object entity, Object toValue) {
                    return new TailChainSwapMove<>(variableDescriptor, inverseVariableSupply, anchorVariableSupply,
                            entity, toValue);
                }
            };
        } else {
            return new AbstractRandomChangeIterator<>(entitySelector, valueSelector) {
                @Override
                protected Move<Solution_> newChangeSelection(Object entity, Object toValue) {
                    return new TailChainSwapMove<>(variableDescriptor, inverseVariableSupply, anchorVariableSupply,
                            entity, toValue);
                }
            };
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + entitySelector + ", " + valueSelector + ")";
    }

}
