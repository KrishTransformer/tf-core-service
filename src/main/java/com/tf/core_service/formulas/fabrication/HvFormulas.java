
package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Hv;

public class HvFormulas {
    public static Hv calculateHv(Integer kVA, boolean hvcbFromInput,
                                 Double hv_ID, Double hv_OD, Double hv_Wdg_L, Integer current) {

        HvLvCommonVariables hvLvCommonVariables = HvLvCommonMethods.calcuateHvLvCommonVariables(kVA, hvcbFromInput, current);

        Hv hv = new Hv();
        hv.setHv_ID(hv_ID);
        hv.setHv_OD(hv_OD);
        hv.setHv_Wdg_L(hv_Wdg_L);
        hv.setHv_Ph_Ph(hvLvCommonVariables.getPh2ph());
        hv.setHv_Ph_Erth(hvLvCommonVariables.getPh2earth());
        hv.setHv_Ph_Ph_Incl_cap(40);   //TODO:  Refer to 'bushing_Pitch_p2p' from 'Bushing_Data.txt'
        return hv;
    }
}
