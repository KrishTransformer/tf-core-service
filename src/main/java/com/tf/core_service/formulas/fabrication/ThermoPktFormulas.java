package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.ThermoPkt;

public class ThermoPktFormulas {
    public static Integer thermoPkt1Hpos(Integer tank_L, Integer hvb_Ptch){
        return tank_L < 800 ? hvb_Ptch/2 :(tank_L/2) - 50;
    }

    public static Integer thermoPkt1Vpos(Integer tank_W, Integer tank_L){
        return tank_L < 800? -((tank_W/2) - 50): (tank_W/2) - 50;
    }

    public static Integer thermoPkt2Hpos(Integer tank_L, Integer hvb_Ptch){
        return tank_L < 800 ? hvb_Ptch/2  :(tank_L/2) - 50;
    }

    public static Integer thermoPkt2Vpos(Integer tank_W, Integer tank_L){
        return tank_L < 800? -((tank_W/2) - 150): (tank_W/2) - 150;
    }

    public static ThermoPkt calculateThermoPkt(Boolean thermoPkt1, Boolean thermoPkt2, Integer tank_L, Integer tank_W, Integer hvb_Ptch){
        ThermoPkt thermoPkt = new ThermoPkt();
        thermoPkt.setThermoPkt1(thermoPkt1);
        if(thermoPkt1){
            thermoPkt.setThermoPkt1_Hole_Dia(23);
            thermoPkt.setThermoPkt1_Hpos(thermoPkt1Hpos(tank_L, hvb_Ptch));
            thermoPkt.setThermoPkt1_Vpos(thermoPkt1Vpos(tank_W, tank_L));
        }else{
            thermoPkt.setThermoPkt1_Hole_Dia(0);
            thermoPkt.setThermoPkt1_Hpos(0);
            thermoPkt.setThermoPkt1_Vpos(0);
        }
        thermoPkt.setThermoPkt2(thermoPkt2);
        if(thermoPkt2) {
            thermoPkt.setThermoPkt2_Hole_Dia(23);
            thermoPkt.setThermoPkt2_Hpos(thermoPkt2Hpos(tank_L, hvb_Ptch));
            thermoPkt.setThermoPkt2_Vpos(thermoPkt2Vpos(tank_W, tank_L));
        }else {
            thermoPkt.setThermoPkt2_Hole_Dia(0);
            thermoPkt.setThermoPkt2_Hpos(0);
            thermoPkt.setThermoPkt2_Vpos(0);
        }
        return thermoPkt;
    }
}
