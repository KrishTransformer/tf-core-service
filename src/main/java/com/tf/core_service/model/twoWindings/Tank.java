package com.tf.core_service.model.twoWindings;

import lombok.Data;

@Data
public class Tank {
    private Integer tankLength = 0;
    private Integer tankWidth = 0;
    private Integer tankHeight = 0;
    private Double tankCapacity = 0.0;
    private Double tankWallThickness = 0.0;
    private Double tankLidThickness = 0.0;
    private Double tankBottomThickness = 0.0;
    private Double frameThickness = 0.0;
    private String overallDimension = "";
    private String tankDimension = "";
    public Integer tankLoss;
}
