package ai.timefold.solver.core.impl.domain.lookup;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import ai.timefold.solver.core.api.domain.common.DomainAccessType;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.config.util.ConfigUtils;
import ai.timefold.solver.core.impl.domain.common.accessor.MemberAccessor;
import ai.timefold.solver.core.impl.domain.common.accessor.MemberAccessorFactory;

public final class ClassAndPlanningIdComparator implements Comparator<Object> {

    private final MemberAccessorFactory memberAccessorFactory;
    private final DomainAccessType domainAccessType;
    private final boolean failFastIfNoPlanningId;
    private final Map<Class, MemberAccessor> decisionCache = new HashMap<>();

    public ClassAndPlanningIdComparator(MemberAccessorFactory memberAccessorFactory,
            DomainAccessType domainAccessType, boolean failFastIfNoPlanningId) {
        this.memberAccessorFactory = memberAccessorFactory;
        this.domainAccessType = domainAccessType;
        this.failFastIfNoPlanningId = failFastIfNoPlanningId;
    }

    @Override
    public int compare(Object a, Object b) {
        if (a == null) {
            return b == null ? 0 : -1;
        } else if (b == null) {
            return 1;
        }
        Class<?> aClass = a.getClass();
        Class<?> bClass = b.getClass();
        if (aClass != bClass) {
            return aClass.getName().compareTo(bClass.getName());
        }
        MemberAccessor aMemberAccessor = decisionCache.computeIfAbsent(aClass, this::findMemberAccessor);
        MemberAccessor bMemberAccessor = decisionCache.computeIfAbsent(bClass, this::findMemberAccessor);
        if (failFastIfNoPlanningId) {
            if (aMemberAccessor == null) {
                throw new IllegalArgumentException("The class (" + aClass
                        + ") does not have a @" + PlanningId.class.getSimpleName() + " annotation.\n"
                        + "Maybe add the @" + PlanningId.class.getSimpleName() + " annotation.");
            }
            if (bMemberAccessor == null) {
                throw new IllegalArgumentException("The class (" + bClass
                        + ") does not have a @" + PlanningId.class.getSimpleName() + " annotation.\n"
                        + "Maybe add the @" + PlanningId.class.getSimpleName() + " annotation.");
            }
        } else {
            if (aMemberAccessor == null) {
                if (bMemberAccessor == null) {
                    if (a instanceof Comparable comparable) {
                        return comparable.compareTo(b);
                    } else { // Return 0 to keep original order.
                        return 0;
                    }
                } else {
                    return -1;
                }
            } else if (bMemberAccessor == null) {
                return 1;
            }
        }
        Comparable aPlanningId = (Comparable) aMemberAccessor.executeGetter(a);
        Comparable bPlanningId = (Comparable) bMemberAccessor.executeGetter(b);
        if (aPlanningId == null) {
            throw new IllegalArgumentException("The planningId (" + aPlanningId
                    + ") of the member (" + aMemberAccessor + ") of the class (" + aClass
                    + ") on object (" + a + ") must not be null.\n"
                    + "Maybe initialize the planningId of the original object before solving..");
        }
        if (bPlanningId == null) {
            throw new IllegalArgumentException("The planningId (" + bPlanningId
                    + ") of the member (" + bMemberAccessor + ") of the class (" + bClass
                    + ") on object (" + a + ") must not be null.\n"
                    + "Maybe initialize the planningId of the original object before solving..");
        }
        // If a and b are different classes, this method would have already returned.
        return aPlanningId.compareTo(bPlanningId);
    }

    private MemberAccessor findMemberAccessor(Class<?> clazz) {
        return ConfigUtils.findPlanningIdMemberAccessor(clazz, memberAccessorFactory, domainAccessType);
    }
}
