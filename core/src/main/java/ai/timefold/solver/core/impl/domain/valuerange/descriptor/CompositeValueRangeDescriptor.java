package ai.timefold.solver.core.impl.domain.valuerange.descriptor;

import java.util.ArrayList;
import java.util.List;

import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.CountableValueRange;
import ai.timefold.solver.core.api.domain.valuerange.ValueRange;
import ai.timefold.solver.core.impl.domain.valuerange.buildin.composite.CompositeCountableValueRange;
import ai.timefold.solver.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;

/**
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 */
public class CompositeValueRangeDescriptor<Solution_> extends AbstractValueRangeDescriptor<Solution_>
        implements EntityIndependentValueRangeDescriptor<Solution_> {

    protected final List<ValueRangeDescriptor<Solution_>> childValueRangeDescriptorList;
    protected boolean entityIndependent;

    public CompositeValueRangeDescriptor(
            GenuineVariableDescriptor<Solution_> variableDescriptor, boolean addNullInValueRange,
            List<ValueRangeDescriptor<Solution_>> childValueRangeDescriptorList) {
        super(variableDescriptor, addNullInValueRange);
        this.childValueRangeDescriptorList = childValueRangeDescriptorList;
        entityIndependent = true;
        for (ValueRangeDescriptor<Solution_> valueRangeDescriptor : childValueRangeDescriptorList) {
            if (!valueRangeDescriptor.isCountable()) {
                throw new IllegalStateException("The valueRangeDescriptor (" + this
                        + ") has a childValueRangeDescriptor (" + valueRangeDescriptor
                        + ") with countable (" + valueRangeDescriptor.isCountable() + ").");
            }
            if (!valueRangeDescriptor.isEntityIndependent()) {
                entityIndependent = false;
            }
        }
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public boolean isCountable() {
        return true;
    }

    @Override
    public boolean isEntityIndependent() {
        return entityIndependent;
    }

    @Override
    public ValueRange<?> extractValueRange(Solution_ solution, Object entity) {
        List<CountableValueRange<?>> childValueRangeList = new ArrayList<>(childValueRangeDescriptorList.size());
        for (ValueRangeDescriptor<Solution_> valueRangeDescriptor : childValueRangeDescriptorList) {
            childValueRangeList.add((CountableValueRange) valueRangeDescriptor.extractValueRange(solution, entity));
        }
        return doNullInValueRangeWrapping(new CompositeCountableValueRange(childValueRangeList));
    }

    @Override
    public ValueRange<?> extractValueRange(Solution_ solution) {
        List<CountableValueRange<?>> childValueRangeList = new ArrayList<>(childValueRangeDescriptorList.size());
        for (ValueRangeDescriptor<Solution_> valueRangeDescriptor : childValueRangeDescriptorList) {
            EntityIndependentValueRangeDescriptor<Solution_> entityIndependentValueRangeDescriptor =
                    (EntityIndependentValueRangeDescriptor) valueRangeDescriptor;
            childValueRangeList.add((CountableValueRange) entityIndependentValueRangeDescriptor.extractValueRange(solution));
        }
        return doNullInValueRangeWrapping(new CompositeCountableValueRange(childValueRangeList));
    }

    @Override
    public long extractValueRangeSize(Solution_ solution, Object entity) {
        int size = addNullInValueRange ? 1 : 0;
        for (ValueRangeDescriptor<Solution_> valueRangeDescriptor : childValueRangeDescriptorList) {
            size += ((CountableValueRange) valueRangeDescriptor.extractValueRange(solution, entity)).getSize();
        }
        return size;
    }

    @Override
    public long extractValueRangeSize(Solution_ solution) {
        int size = addNullInValueRange ? 1 : 0;
        for (ValueRangeDescriptor<Solution_> valueRangeDescriptor : childValueRangeDescriptorList) {
            EntityIndependentValueRangeDescriptor<Solution_> entityIndependentValueRangeDescriptor =
                    (EntityIndependentValueRangeDescriptor) valueRangeDescriptor;
            size += ((CountableValueRange) entityIndependentValueRangeDescriptor.extractValueRange(solution)).getSize();
        }
        return size;
    }

}
