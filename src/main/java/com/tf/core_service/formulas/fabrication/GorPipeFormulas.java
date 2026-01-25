package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.GorPipe;
import io.swagger.v3.oas.models.security.SecurityScheme;

public class GorPipeFormulas {

    public static GorPipe calculateGorPipe(Boolean buchholz_Relay, Boolean single_Valve, Boolean valve_Type1, Integer kVA, Integer topCover_L, Integer tank_L, Integer lvb_VPos){
        GorPipe gorPipe = new GorPipe();
        gorPipe.setBuchholz_Relay(buchholz_Relay);
        gorPipe.setSingle_Valve(single_Valve);
        gorPipe.setValve_Type1(valve_Type1);

        if(buchholz_Relay){
            if(kVA <= 1000){
                if(single_Valve){
                    if(valve_Type1){
                        gorPipe.setGorPipe_Type(42);
                        gorPipe.setGorPipe_Ass_L(509.0);
                        gorPipe.setGorPipe_Ass_H(467.5);
                    }
                    else {
                        gorPipe.setGorPipe_Type(43);
                        gorPipe.setGorPipe_Ass_L(669.5);
                        gorPipe.setGorPipe_Ass_H(290.5);
                    }
                }
                else{
                    if(valve_Type1){
                        gorPipe.setGorPipe_Type(44);
                        gorPipe.setGorPipe_Ass_L(611.5);
                        gorPipe.setGorPipe_Ass_H(482.5);
                    }
                    else {
                        gorPipe.setGorPipe_Type(45);
                        gorPipe.setGorPipe_Ass_L(786.0);
                        gorPipe.setGorPipe_Ass_H(313.0);
                    }
                }
                gorPipe.setBuchholz_Type(1);
                gorPipe.setGorPipe_CutOut_Dia(35);
            }
            else {
                if(single_Valve){
                    gorPipe.setGorPipe_Type(46);
                    gorPipe.setGorPipe_Ass_L(695.5);
                    gorPipe.setGorPipe_Ass_H(497.0);
                }
                else{
                    gorPipe.setGorPipe_Type(47);
                    gorPipe.setGorPipe_Ass_L(932.5);
                    gorPipe.setGorPipe_Ass_H(517.5);
                }
                gorPipe.setBuchholz_Type(2);
                gorPipe.setGorPipe_CutOut_Dia(62);
            }
        }else {
            gorPipe.setGorPipe_Type(41);
            gorPipe.setGorPipe_Ass_L(306.0);
            gorPipe.setGorPipe_Ass_H(300.0);
        }

        //The positions on the tank cover is as given below.
        if(tank_L < 800){
            gorPipe.setGorPipe_Hpos(0);
            gorPipe.setGorPipe_Vpos(lvb_VPos);
        }
        else {
            gorPipe.setGorPipe_Hpos(tank_L/2 - 150);
            if(buchholz_Relay){
                gorPipe.setGorPipe_Hpos(tank_L/2 - 200);
            }
            gorPipe.setGorPipe_Vpos(0);
        }

        //if(gorPipe.getGorPipe_Hpos() + gorPipe.getGorPipe_Ass_L() < topCover_L/2){
          //  gorPipe.setGorPipe_Hpos((int) (topCover_L/2 - gorPipe.getGorPipe_Ass_L() + 50));
        //}



        return gorPipe;
    }

}
