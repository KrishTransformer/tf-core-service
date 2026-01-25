package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.DrainVlv;

public class DrainVlvFormulas {
    private static Integer checkDrain_Vlv_Nos(Integer drainVlvNos) {
        if(drainVlvNos<1){
            return 1;
        }else {
            return drainVlvNos;
        }
    }

    public static DrainVlv calculateDrainerVlv(Integer kVA, Boolean inputFromDrainVlv
    , Integer tank_W, Integer tank_H ) {
        DrainVlv drainVlv = new DrainVlv();
        drainVlv.setDrain_Vlv(inputFromDrainVlv);
        Integer drain_Vlv_Nos=checkDrain_Vlv_Nos(1);
        drainVlv.setDrain_Vlv_Nos(drain_Vlv_Nos);
//        drainVlv.setDrain_Vlv_Pipe_L(tank_Flg_W * 1.5);
        drainVlv.setDrain_Vlv1_HPos(0.0);
        drainVlv.setDrain_Vlv2_HPos(0.0);
        drainVlv.setDrain_Vlv_VPos(tank_H/2 - 50);

//        if (kVA <= 315) {
//            drainVlv.setDrain_Vlv_Pipe_ID(20);
//            drainVlv.setDrain_Vlv_Pipe_OD(26.5);
//            drainVlv.setDrain_Vlv_Flg_ID(20);
//            drainVlv.setDrain_Vlv_Flg_OD(100);
//            drainVlv.setDrain_Vlv_Flg_PCD(75);
//            drainVlv.setDrain_Vlv_Flg_Thick(10);
//            drainVlv.setDrain_Vlv_Flg_Bolt_Dia(14);
//            drainVlv.setDrain_Vlv_Flg_Bolt_Nos(4);
//        } else if (kVA > 315 && kVA <= 630) {
//            drainVlv.setDrain_Vlv_Pipe_ID(25);
//            drainVlv.setDrain_Vlv_Pipe_OD(33.1);
//            drainVlv.setDrain_Vlv_Flg_ID(25);
//            drainVlv.setDrain_Vlv_Flg_OD(115);
//            drainVlv.setDrain_Vlv_Flg_PCD(85);
//            drainVlv.setDrain_Vlv_Flg_Thick(10);
//            drainVlv.setDrain_Vlv_Flg_Bolt_Dia(14);
//            drainVlv.setDrain_Vlv_Flg_Bolt_Nos(4);
//        } else if (kVA > 630 && kVA <= 2000) {
//            drainVlv.setDrain_Vlv_Pipe_ID(32);
//            drainVlv.setDrain_Vlv_Pipe_OD(40.1);
//            drainVlv.setDrain_Vlv_Flg_ID(32);
//            drainVlv.setDrain_Vlv_Flg_OD(120);
//            drainVlv.setDrain_Vlv_Flg_PCD(90);
//            drainVlv.setDrain_Vlv_Flg_Thick(12);
//            drainVlv.setDrain_Vlv_Flg_Bolt_Dia(14);
//            drainVlv.setDrain_Vlv_Flg_Bolt_Nos(4);
//        } else {
//            drainVlv.setDrain_Vlv_Pipe_ID(50);
//            drainVlv.setDrain_Vlv_Pipe_OD(59.0);
//            drainVlv.setDrain_Vlv_Flg_ID(50);
//            drainVlv.setDrain_Vlv_Flg_OD(150);
//            drainVlv.setDrain_Vlv_Flg_PCD(115);
//            drainVlv.setDrain_Vlv_Flg_Thick(12);
//            drainVlv.setDrain_Vlv_Flg_Bolt_Dia(18);
//            drainVlv.setDrain_Vlv_Flg_Bolt_Nos(4);
//        }

        if(kVA <= 500){drainVlv.setDrain_Vlv_Type(30);}
        else if(kVA <= 1600){drainVlv.setDrain_Vlv_Type(31);}
        else if(kVA <= 5000){drainVlv.setDrain_Vlv_Type(32);}
        else {drainVlv.setDrain_Vlv_Type(33);}

        return drainVlv;
    }


}
