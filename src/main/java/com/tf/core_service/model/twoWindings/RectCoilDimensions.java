package com.tf.core_service.model.twoWindings;

import lombok.Data;

@Data
public class RectCoilDimensions {
    private Integer coreWidth = 0;
    private Integer coreDepth = 0;
    public Double coreGap;
    private Integer lvIdWidth = 0;
    private Integer lvIdDepth = 0;
    private Integer lVRadial = 0;
    private Integer lvOdWidth = 0;
    private Integer lvOdDepth = 0;
    public Double lVHVGap;
    private Integer hvIdWidth = 0;
    private Integer hvIdDepth = 0;
    private Integer hvRadial = 0;
    private Integer hvOdWidth = 0;
    private Integer hvOdDepth = 0;
    public Double hVHVGap;
    public Double centerDist = 0.0;
    private String activePartSize = "";
}
