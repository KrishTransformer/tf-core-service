package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.OctcFlange;

public class OctcFlangeFormula {
    public static OctcFlange calculateOctcFlange(Boolean isOCTC, Integer tank_H){
        OctcFlange octcFlange = new OctcFlange();
        if(isOCTC){
            octcFlange.setIsoctc(true);
            octcFlange.setOctcFlange_HPos(0);
            octcFlange.setOctcFlange_VPos(tank_H/2 - 120);
        }else {
            octcFlange.setIsoctc(false);
            octcFlange.setOctcFlange_HPos(0);
            octcFlange.setOctcFlange_VPos(0);
        }
        return octcFlange;
    }
}
