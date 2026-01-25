package com.tf.core_service.model.twoWindings;

import lombok.Data;

@Data
public class CoilDimensions {
    private Integer coreDia = 0;
    public Double coreGap;
    private Integer lVID = 0;
    private Integer lVRadial = 0;
    private Integer lVOD = 0;
    public Double lVHVGap;
    private Integer hVID = 0;
    private Integer hVRadial = 0;
    private Integer hVOD = 0;
    public Double hVHVGap;
    private String activePartSize = "";
}
