package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.PressRelDevice;

public class PressRelDeviceFormulas {
    public static PressRelDevice calculatePressRelDev(Integer tank_L, Integer tank_W, Double hvb_Ptch){
        PressRelDevice pressRelDevice = new PressRelDevice();
        if(tank_L < 800){
            pressRelDevice.setPrd(true);
            pressRelDevice.setPrd_HPos((int) (-(hvb_Ptch/2 + 10)));
            pressRelDevice.setPrd_VPos(-(tank_W/2 - 30));
        }else {
            pressRelDevice.setPrd(false);
            pressRelDevice.setPrd_HPos(0);
            pressRelDevice.setPrd_VPos(0);
        }
        return pressRelDevice;
    }
}
