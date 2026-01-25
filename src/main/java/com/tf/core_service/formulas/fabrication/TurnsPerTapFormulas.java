package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.TurnsPerTap;

import java.util.ArrayList;

public class TurnsPerTapFormulas {
    public static TurnsPerTap calculateTurnsPerTap(ArrayList<Integer> values){
        if(values == null ){
            return null;
        }
        TurnsPerTap result = new TurnsPerTap();
            for (int i = 0; i < values.size(); i++) {
                result.getTurnsPerTap().put("turnsPerTap_" + (i + 1), values.get(i));
            }

        return result;
    }
}