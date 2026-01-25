package com.tf.core_service.model.twoWindings;

import io.swagger.models.auth.In;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

@Data
public class Cost {
    private Double copperCostPerKg = 0.0;
    private Double aluminiumCostPerKg = 0.0;
    private Double coreCostPerKg = 0.0;
    private Double steelCostPerKg  = 0.0;
    private Double oilCostPerKg = 0.0;
    private Double insulationCostPerKg = 0.0;
    private Double radiatorCostPerKg = 0.0;

    // Outputs
    private int totalCondCost = 0;
    private int totalCoreCost = 0;
    private int totalSteelCost = 0;
    private int totalOilCost = 0;
    private int totalInsCost = 0;
    private int totalRadiatorCost = 0;

    private int capitalCost = 0;
}
