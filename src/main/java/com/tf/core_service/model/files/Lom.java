package com.tf.core_service.model.files;

import com.tf.core_service.model.fabrication.Fabrication;
import lombok.Data;

@Data
public class Lom {
    private int index;
    private String description;
    private String specification;
    private String unit;
    private Double quantity;
    private Double rate;
    private Double cost;
}
