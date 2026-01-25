package com.tf.core_service.model.twoWindings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LockedAttributes {

    @JsonProperty private CoreLock coreLock;
    @JsonProperty private InnerWindings innerWindings;
    @JsonProperty private OuterWindings outerWindings;

    @Data
    public static class CoreLock {
        @JsonProperty private Boolean coreDia;
        @JsonProperty private Boolean limbHt;
    }

    @Data
    public static class InnerWindings {
        @JsonProperty private Boolean turnsPerPhase;
        @JsonProperty private Boolean conductorSizes;
        @JsonProperty private Boolean noInParallel;
        @JsonProperty private Boolean condBreadth;
        @JsonProperty private Boolean condHeight;
    }

    @Data
    public static class OuterWindings {
        @JsonProperty private Boolean turnsPerPhase;
        @JsonProperty private Boolean conductorSizes;
        @JsonProperty private Boolean noInParallel;
        @JsonProperty private Boolean condBreadth;
        @JsonProperty private Boolean condHeight;
    }
}
