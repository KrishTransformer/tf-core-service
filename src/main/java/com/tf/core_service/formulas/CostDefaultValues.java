package com.tf.core_service.formulas;

import com.tf.core_service.utils.Constants;
import io.swagger.models.auth.In;

import java.util.Objects;

public class CostDefaultValues {
    public static Double getCuCostPerKg(Double copperCost){
        return Objects.requireNonNullElse(copperCost, 850.0);
    }

    public static Double getAlCostPerKg(Double alCost){
        return Objects.requireNonNullElse(alCost, 235.0);
    }

    public static Double getCoreCostPerKg(Double coreCostPerKg){
        return Objects.requireNonNullElse(coreCostPerKg, 250.0);
    }

    public static Double getSteelCostPerKg(Double steelCostPerKg){
        return Objects.requireNonNullElse(steelCostPerKg, 90.0);
    }

    public static Double getOilCostPerKg(Double oilCostPerKg){
        return Objects.requireNonNullElse(oilCostPerKg, 80.0);
    }

    public static Double getInsulationCostPerKg(Double insulationCostPerKg){
        return Objects.requireNonNullElse(insulationCostPerKg, 170.0);
    }

    public static Double getRadiatorCostPerKg(Double radiatorCostPerKg){
        return Objects.requireNonNullElse(radiatorCostPerKg, 200.0);
    }
}
