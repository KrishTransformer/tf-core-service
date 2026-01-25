package com.tf.core_service.formulas;

import com.tf.core_service.model.twoWindings.LockedAttributes;
import java.util.Objects;

public class LockedAttributesDefaultValues {

    private static Boolean def(Boolean value) {
        return Objects.requireNonNullElse(value, false);
    }

    public static LockedAttributes checkLockedAttributes(LockedAttributes locks) {
        if (locks == null) {
            locks = new LockedAttributes();
        }

        LockedAttributes.CoreLock coreLocks =
                Objects.requireNonNullElse(locks.getCoreLock(), new LockedAttributes.CoreLock());
        coreLocks.setCoreDia(def(coreLocks.getCoreDia()));
        coreLocks.setLimbHt(def(coreLocks.getLimbHt()));
        locks.setCoreLock(coreLocks);


        LockedAttributes.InnerWindings inner =
                Objects.requireNonNullElse(locks.getInnerWindings(), new LockedAttributes.InnerWindings());
        inner.setTurnsPerPhase(def(inner.getTurnsPerPhase()));
        inner.setConductorSizes(def(inner.getConductorSizes()));
        inner.setNoInParallel(def(inner.getNoInParallel()));
        inner.setCondBreadth(def(inner.getCondBreadth()));
        inner.setCondHeight(def(inner.getCondHeight()));
        locks.setInnerWindings(inner);


        LockedAttributes.OuterWindings outer =
                Objects.requireNonNullElse(locks.getOuterWindings(), new LockedAttributes.OuterWindings());
        outer.setTurnsPerPhase(def(outer.getTurnsPerPhase()));
        outer.setConductorSizes(def(outer.getConductorSizes()));
        outer.setNoInParallel(def(outer.getNoInParallel()));
        locks.setOuterWindings(outer);

        return locks;
    }
}
