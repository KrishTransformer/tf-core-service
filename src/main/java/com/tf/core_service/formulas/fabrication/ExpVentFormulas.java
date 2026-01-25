package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.BuchRely;
import com.tf.core_service.model.fabrication.ExpVent;

import java.util.Objects;

public class ExpVentFormulas {

    private static Integer calculateExpVentH(Double cons_V_Pos, Integer cons_Dia ) {
        return (int) ((cons_V_Pos + (cons_Dia / 2) + 2.5 + 10) / 10) * 10;
    }

//The Lines below are for Suveer sir's Exp vent.

//    public static ExpVent calculateExpVent(Integer kVA, Double cons_V_Pos, Integer cons_Dia) {
//        ExpVent expVent = new ExpVent();
//
//        if (kVA <= 1000) {
//            expVent.setExp_Vent_Type("welded");
//        } else {
//            expVent.setExp_Vent_Type("detachable");
//        }
//        expVent.setExp_Vent_H(calculateExpVentH(cons_V_Pos, cons_Dia));
//
//        expVent.setExp_Vent(true);
//        if (kVA <= 500) {
//            expVent.setExp_Vent_ID(40);
//            expVent.setExp_Vent_Thick(2.9);
//        } else if (kVA > 500 && kVA <= 2000) {
//            expVent.setExp_Vent_ID(50);
//            expVent.setExp_Vent_Thick(2.9);
//        } else if (kVA > 2000 && kVA <= 5000) {
//            expVent.setExp_Vent_ID(65);
//            expVent.setExp_Vent_Thick(3.25);
//        } else if (kVA > 5000 && kVA <= 10000) {
//            expVent.setExp_Vent_ID(80);
//            expVent.setExp_Vent_Thick(3.25);
//        } else {
//            expVent.setExp_Vent_ID(100);
//            expVent.setExp_Vent_Thick(3.65);
//        }
//        expVent.setExp_Vent_Flg_W(25);
//        expVent.setExp_Vent_Flg_Thick(5);
//        expVent.setExp_Vent_Flg_Hole_Dia(12);
//        expVent.setExp_Vent_Flg_Hole_Nos(6);
//        expVent.setExp_Vent_Flg_Hole_PCD(expVent.getExp_Vent_ID() + (2 * expVent.getExp_Vent_Thick()) + 25);
//        expVent.setExp_Vent_Flg_Pipe1_L(75);
//
//        expVent.setExp_Vent_Olg_L1(132);
//        expVent.setExp_Vent_Olg_Stud_Row_Nos(2);
//        expVent.setExp_Vent_Olg_L2(132);
//        expVent.setExp_Vent_Olg_W(62);
//        expVent.setExp_Vent_Olg_W2(46);
//        expVent.setExp_Vent_Olg_Stud_Dia(6);
//        expVent.setExp_Vent_Olg_Stud_L(20);
//        expVent.setExp_Vent_Olg_Sup_L(expVent.getExp_Vent_Olg_L1());
//        expVent.setExp_Vent_Olg_Sup_W(expVent.getExp_Vent_Olg_W());
//        expVent.setExp_Vent_Olg_Sup_Thick(3.15);
//        expVent.setExp_Vent_Olg_Sup_Pipe_ID(15);
//        expVent.setExp_Vent_Olg_Sup_Pipe_OD(19);
//        expVent.setExp_Vent_Olg_Sup_Pipe_L(15);
//        expVent.setExp_Vent_Olg_Sup_Pipe_Pitch(90);
//
//        expVent.setExp_Vent_Flg_Pipe2_L(50); //(expVent.getExp_Vent_Olg_L1() + 50); The default value was 50. Hence we changed it.
//
//
//        return expVent;
//
//    }

    public static ExpVent calculateExpVent(Boolean exp_Vent, Boolean exp_Vent_With_OI, Double cons_V_Pos,
                                      Integer cons_Dia, Integer tank_L, Integer tank_W, String cons_Pos, Integer hvb_Neutral_Vpos){
        ExpVent expVent = new ExpVent();

        expVent.setExp_Vent(tank_L >= 800 && exp_Vent);
        expVent.setExp_Vent_With_OI(exp_Vent_With_OI);
        expVent.setExp_Vent_H(calculateExpVentH(cons_V_Pos, cons_Dia));
        if(Objects.equals(cons_Pos,"left")){
            expVent.setExp_Vent_Hpos((tank_L/2) - 140);
            expVent.setExp_Vent_Vpos((tank_W/2) - 50);
        } else{
            if(hvb_Neutral_Vpos < 0){
                expVent.setExp_Vent_Hpos(-(tank_L/2) - 50);
            }else{
                expVent.setExp_Vent_Hpos((tank_L/2) - 50);
            }

            expVent.setExp_Vent_Vpos((tank_W/2) - 50);
        }


        return expVent;
    }
}
