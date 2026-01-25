package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.BuchRely;
import com.tf.core_service.model.fabrication.Cons;

public class BuchRelyFormulas {

    public static BuchRely calculateBuchRely(Integer kVA) {
        BuchRely buchRely = new BuchRely();

        //TODO: What if the user wants the BuchRealy and yet the capacity is lees than 750?
        //We might have to let the user choose. (Ask TVR and NKC)

        ///Also, these values match the ones that is given by Nagabhushana Sir -(APK, HVR) (27/5/25)
        if (kVA <= 750) {
            buchRely.setBuch_Rely(false);
            buchRely.setBuch_Rely_Vlv(false);
        } else if (kVA > 750 && kVA <= 1000) {
            buchRely.setBuch_Rely(true);
            buchRely.setBuch_Rely_Vlv(true);
            buchRely.setBuch_Rely_L(128);
            buchRely.setBuch_Rely_W(205);
            buchRely.setBuch_Rely_H(250);
            buchRely.setBuch_Rely_Flg_OD(111); //Changed from square to round. (was 78) (sqrt(Sq(78) * 2) (Pythagoras theorem)
            buchRely.setBuch_Rely_Flg_PCD(72);
            buchRely.setBuch_Rely_Flg_Thick(10);
            buchRely.setBuch_Rely_Flg_Bolt_Dia(10);
            buchRely.setBuch_Rely_Flg_Bolt_Nos(4);

            buchRely.setBuch_Rely_Vlv_Nos(1);
            buchRely.setBuch_Rely_Vlv_H(150);
            buchRely.setBuch_Rely_Vlv_L(80);
            buchRely.setBuch_Rely_Vlv_Flg_OD(115);
            buchRely.setBuch_Rely_Vlv_Flg_PCD(85);
            buchRely.setBuch_Rely_Vlv_Flg_Thick(10);
            buchRely.setBuch_Rely_Vlv_Flg_Bolt_Dia(14);
            buchRely.setBuch_Rely_Vlv_Flg_Bolt_Nos(4);

        } else if (kVA > 1000 && kVA <= 10000) {
            buchRely.setBuch_Rely(true);
            buchRely.setBuch_Rely_Vlv(true);
            buchRely.setBuch_Rely(true);
            buchRely.setBuch_Rely_Vlv(true);
            buchRely.setBuch_Rely_L(184);
            buchRely.setBuch_Rely_W(205);
            buchRely.setBuch_Rely_H(250);
            buchRely.setBuch_Rely_Flg_OD(150);
            buchRely.setBuch_Rely_Flg_PCD(115);
            buchRely.setBuch_Rely_Flg_Thick(16);
            buchRely.setBuch_Rely_Flg_Bolt_Dia(18);
            buchRely.setBuch_Rely_Flg_Bolt_Nos(4);

            buchRely.setBuch_Rely_Vlv_Nos(1);
            buchRely.setBuch_Rely_Vlv_H(240);
            buchRely.setBuch_Rely_Vlv_L(110);
            buchRely.setBuch_Rely_Vlv_Flg_OD(150);
            buchRely.setBuch_Rely_Vlv_Flg_PCD(125);
            buchRely.setBuch_Rely_Vlv_Flg_Thick(16);
            buchRely.setBuch_Rely_Vlv_Flg_Bolt_Dia(18);
            buchRely.setBuch_Rely_Vlv_Flg_Bolt_Nos(4);

        } else {
            buchRely.setBuch_Rely(true);
            buchRely.setBuch_Rely_Vlv(true);
            buchRely.setBuch_Rely(true);
            buchRely.setBuch_Rely_Vlv(true);
            buchRely.setBuch_Rely_L(215);
            buchRely.setBuch_Rely_W(220);
            buchRely.setBuch_Rely_H(270);
            buchRely.setBuch_Rely_Flg_OD(185);
            buchRely.setBuch_Rely_Flg_PCD(145);
            buchRely.setBuch_Rely_Flg_Thick(16);
            buchRely.setBuch_Rely_Flg_Bolt_Dia(18);
            buchRely.setBuch_Rely_Flg_Bolt_Nos(4);

            buchRely.setBuch_Rely_Vlv_Nos(2);
            buchRely.setBuch_Rely_Vlv_H(300);
            buchRely.setBuch_Rely_Vlv_L(150);
            buchRely.setBuch_Rely_Vlv_Flg_OD(200);
            buchRely.setBuch_Rely_Vlv_Flg_PCD(160);
            buchRely.setBuch_Rely_Vlv_Flg_Thick(16);
            buchRely.setBuch_Rely_Vlv_Flg_Bolt_Dia(18);
            buchRely.setBuch_Rely_Vlv_Flg_Bolt_Nos(8);
        }

        return buchRely;

    }

}
