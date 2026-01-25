package com.tf.core_service.model.twoWindings;

import lombok.Data;

@Data
public class Comments {
    private String tapStepComment;
    private String wattPerKgComment;
    private String lvCondInsComment;
    private String hvCondInsComment;
    private String lvInterLayerInsComment;
    private String hvInterLayerInsComment;
    private String lvDuctWidthComment;
    private String hvDuctWidthComment;
    private String lvEndClrComment;
    private String hvEndClrComment;
    private String coreToLvClrComment;
    private String lvToHvClrComment;
    private String hvToHvClrComment;
}
