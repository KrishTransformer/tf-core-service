package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.TapCurrent;

import java.util.ArrayList;

public class TapCurrentFormulas {
    public static TapCurrent calculateTapCurrent(ArrayList<Double> values){
        if(values == null ){
            return null;
        }
        TapCurrent tapCurrent = new TapCurrent();
        for (int i = 0; i < values.size(); i++) {
            tapCurrent.getTapCurrent().put("tapCurrent_" + (i + 1), values.get(i));
        }

        return tapCurrent;

    }
}
