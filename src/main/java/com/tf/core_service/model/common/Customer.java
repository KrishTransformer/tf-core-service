package com.tf.core_service.model.common;

import lombok.Data;

@Data
public class Customer {

    private String customerName;
    private String emailSubscription;
    private String location;
    private String orders;
    private float amountSpent;
}
