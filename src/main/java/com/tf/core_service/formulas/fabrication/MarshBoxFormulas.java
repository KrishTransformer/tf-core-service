package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.MarshBox;

public class MarshBoxFormulas {
    public static MarshBox calculateMarshBox(Boolean marshBox, Integer tank_H){
        MarshBox data = new MarshBox();
        data.setMarshBox(marshBox);
        if(marshBox){
            data.setMarshBox_HPos(0);
            if(tank_H/2 <= 1500){
                data.setMarshBox_VPos(0);
            }else {
                data.setMarshBox_VPos((tank_H/2) - 1500);
            }
        }

        return data;
    }
}
