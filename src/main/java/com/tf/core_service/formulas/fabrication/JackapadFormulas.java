package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Jackpad;

public class JackapadFormulas {

    public static Jackpad calculateJackpad(double kva, Integer tank_L) {
        Jackpad jackpad = new Jackpad();

        if (kva >= 1000 && kva <= 1600) {
            jackpad.setJackPad(true);
            jackpad.setJackPad_Type(59);
            jackpad.setJackpad_HPos(tank_L/2 - 95);
            jackpad.setJackpad_VPos(140);
        } else if (kva <= 2500) {
            jackpad.setJackPad(true);
            jackpad.setJackPad_Type(60);
            jackpad.setJackpad_HPos(tank_L/2 - 105);
            jackpad.setJackpad_VPos(140);
        } else {
            jackpad.setJackPad(false);
            jackpad.setJackPad_Type(0);
            jackpad.setJackpad_HPos(0);
            jackpad.setJackpad_VPos(0);
        }

        return jackpad;
    }


}
