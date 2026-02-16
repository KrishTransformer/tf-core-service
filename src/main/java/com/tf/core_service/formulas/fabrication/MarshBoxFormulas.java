package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.MarshBox;

public class MarshBoxFormulas {
    public static MarshBox calculateMarshBox(double kVA, Boolean marshBox, Integer tank_H){
        MarshBox data = new MarshBox();
        data.setMarshBox(marshBox);
        if(kVA < 630){data.setMarshBox(false);}
        else {data.setMarshBox(true);}
        data.setMarshBox_HPos(0);

        // The marshalling box's height is 630mm
        // We are making sure that the marshalling box is in the bottom as much as possible.
        // The box must be at the viewer's eye-level.
        if(marshBox){
            if(tank_H <= 700){
                data.setMarshBox_VPos(100);
            }else if(tank_H <= 1000){
                data.setMarshBox_VPos((0));
            } else if (tank_H <= 1500) {
                data.setMarshBox_VPos(-50);
            }else {
                data.setMarshBox_VPos(-((tank_H/2) - 800));
            }
        }

        return data;
    }
}
