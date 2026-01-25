
package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Lv;

public class LvFormulas {
    public static Lv calculateLv(Integer kVA,
                                 Double  lv_Cond_W,Double lv_Cond_H
            ,Double lv_Cond_Rad,Double lv_Cond_Ax
            ,Double lv_ID,Double lv_OD, Double lv_Volts, Double lv_Wdg_L
                                 , Boolean lvcbFromInput,
                                 Integer current) {

        HvLvCommonVariables hvLvCommonVariables = HvLvCommonMethods.calcuateHvLvCommonVariables(kVA, lvcbFromInput, current);

        Lv lv = new Lv();
        lv.setLv_Ph_Erth(hvLvCommonVariables.getPh2earth());
        lv.setLv_Ph_Ph(hvLvCommonVariables.getPh2ph());
        lv.setLv_Cond_H(lv_Cond_W);
        lv.setLv_Cond_W(lv_Cond_H);
        lv.setLv_Cond_Rad(lv_Cond_Rad);
        lv.setLv_Cond_Ax(lv_Cond_Ax);
        lv.setLv_ID(lv_ID);
        lv.setLv_Volts(lv_Volts);
        lv.setLv_OD(lv_OD);
        lv.setLv_Wdg_L(lv_Wdg_L);
        return lv;
    }
}
