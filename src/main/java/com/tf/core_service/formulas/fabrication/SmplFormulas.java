package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.SmplVlv;

public class SmplFormulas {

    private static Integer checkSmpl_Vlv_Nos(Integer smplVlvNos) {
        if(smplVlvNos<1){
            return 1;
        }else {
            return smplVlvNos;
        }
    }


    public static SmplVlv calculateSmplVlv(Boolean smpl_Vlv, Integer smplVlvNos, Integer tank_W, Integer tank_H, Boolean isOCTC) {
        SmplVlv smplVlv = new SmplVlv();

        smplVlv.setSmpl_Vlv(smpl_Vlv);
        Integer smpl_Vlv_Nos=checkSmpl_Vlv_Nos(smplVlvNos);
        smplVlv.setSmpl_Vlv_Nos(smpl_Vlv_Nos);
//        smplVlv.setSmpl_Vlv_Type();

//        smplVlv.setSmpl_Vlv_Pipe_ID(15);
//        smplVlv.setSmpl_Vlv_Pipe_OD(20.3);
//        smplVlv.setSmpl_Vlv_Pipe_L(30);

        smplVlv.setSmpl_Vlv1_Hpos(0);
        smplVlv.setSmpl_Vlv1_Vpos(tank_H/2 - 50);

//        smplVlv.setSmpl_Vlv2_Hpos(100);
//        smplVlv.setSmpl_Vlv2_Vpos(100);
//        smplVlv.setSmpl_Vlv3_Hpos(150);
//        smplVlv.setSmpl_Vlv3_Vpos(150);

        return smplVlv;
    }

}



















