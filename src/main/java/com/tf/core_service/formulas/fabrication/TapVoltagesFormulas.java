package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.TapVoltages;


import java.util.ArrayList;

public class TapVoltagesFormulas {
    public static TapVoltages calculateTapVoltages(ArrayList<Integer> values){
        if(values == null ){
            return null;
        }
        TapVoltages result = new TapVoltages();
        for (int i = 0; i < values.size(); i++) {
            result.getTapVoltages().put("tapVoltages_" + (i + 1), values.get(i));
        }

        return result;
    }
}
