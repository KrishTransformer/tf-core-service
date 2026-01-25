package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.FillVlv;

public class FillVlvFormulas {
    public static FillVlv calculateFillerVlv(Integer kVA, Boolean fill_Vlv_FromInput
    , Integer tank_W, Integer tank_H) {

        FillVlv fillVlv = new FillVlv();
        fillVlv.setFill_Vlv(fill_Vlv_FromInput);
        fillVlv.setFill_Vlv_Nos(1);
//        fillVlv.setFill_Vlv_Pipe_L(tank_Flg_W * 1.5);
        fillVlv.setFill_Vlv_HPos (0.0);
        fillVlv.setFill_Vlv_VPos(tank_H/2 - 120);

//        if (kVA <= 315) {
//            fillVlv.setFill_Vlv_Pipe_ID(20);
//            fillVlv.setFill_Vlv_Pipe_OD(26.5);
//            fillVlv.setFill_Vlv_Flg_ID(20);
//            fillVlv.setFill_Vlv_Flg_OD(100);
//            fillVlv.setFill_Vlv_Flg_PCD(75);
//            fillVlv.setFill_Vlv_Flg_Thick(10);
//            fillVlv.setFill_Vlv_Flg_Bolt_Dia(14);
//            fillVlv.setFill_Vlv_Flg_Bolt_Nos(4);
//        } else if (kVA > 315 && kVA <= 630) {
//            fillVlv.setFill_Vlv_Pipe_ID(25);
//            fillVlv.setFill_Vlv_Pipe_OD(33.1);
//            fillVlv.setFill_Vlv_Flg_ID(25);
//            fillVlv.setFill_Vlv_Flg_OD(115);
//            fillVlv.setFill_Vlv_Flg_PCD(85);
//            fillVlv.setFill_Vlv_Flg_Thick(10);
//            fillVlv.setFill_Vlv_Flg_Bolt_Dia(14);
//            fillVlv.setFill_Vlv_Flg_Bolt_Nos(4);
//        } else if (kVA > 630 && kVA <= 2000) {
//            fillVlv.setFill_Vlv_Pipe_ID(32);
//            fillVlv.setFill_Vlv_Pipe_OD(40.1);
//            fillVlv.setFill_Vlv_Flg_ID(32);
//            fillVlv.setFill_Vlv_Flg_OD(120);
//            fillVlv.setFill_Vlv_Flg_PCD(90);
//            fillVlv.setFill_Vlv_Flg_Thick(12);
//            fillVlv.setFill_Vlv_Flg_Bolt_Dia(14);
//            fillVlv.setFill_Vlv_Flg_Bolt_Nos(4);
//        } else {
//            fillVlv.setFill_Vlv_Pipe_ID(50);
//            fillVlv.setFill_Vlv_Pipe_OD(59.0);
//            fillVlv.setFill_Vlv_Flg_ID(50);
//            fillVlv.setFill_Vlv_Flg_OD(150);
//            fillVlv.setFill_Vlv_Flg_PCD(115);
//            fillVlv.setFill_Vlv_Flg_Thick(12);
//            fillVlv.setFill_Vlv_Flg_Bolt_Dia(18);
//            fillVlv.setFill_Vlv_Flg_Bolt_Nos(4);
//        }

        if(kVA <= 500){fillVlv.setFill_Vlv_Type(30);}
        else if(kVA <= 1600){fillVlv.setFill_Vlv_Type(31);}
        else if(kVA <= 5000){fillVlv.setFill_Vlv_Type(32);}
        else {fillVlv.setFill_Vlv_Type(33);}

        return fillVlv;
    }

}
