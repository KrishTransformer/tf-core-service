package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Stiffner;
import com.tf.core_service.utils.NumberFormattingUtils;

public class StiffenerFormulas {



    private static Integer getStiffenerHoriL(Integer tank_L, Integer stiffenerWidth, Integer tank_Thick) {
        return tank_L + (2 * stiffenerWidth) + (2 * tank_Thick);
    }

    private static Integer getStiffenerHoriNos(Integer tank_H) {
        return NumberFormattingUtils.halfUp((double) tank_H / 400 - 1);
    }

    private static Double getStiffenerHoriPitch(Integer tank_H, Integer stiffenerHoriNos) {
        return (double) tank_H / (stiffenerHoriNos + 1);
    }

    private static Integer getStiffenerVertNos(Integer tank_L) {
        return NumberFormattingUtils.halfUp((double) tank_L / 400 - 1);
    }

    private static Double getStiffenerVertPitch(Integer tank_L, Integer stiffenerVertNos) {
        return (double) tank_L / (stiffenerVertNos + 1);
    }

    private static Integer getStiffenerVertSideNos(Integer tank_W) {
        return tank_W > 650 ? Math.max((int) Math.floor((double) tank_W / 650), 1) : 0;
    }

    private static Double getStiffenerVertSidePitch(Integer tank_W, Integer stiffenerVertSideNos) {
        return (double) tank_W / (stiffenerVertSideNos + 1);
    }

    private static String checkstiffner_HoriandVert_Nos_Calc_Mode(String input) {
        if(input.equals("")){
            return "Auto";
        }
        else return input;
    }


    public static Stiffner calculateStiffner(Integer tank_L, Integer tank_W, Integer tank_H,
                                             Integer tank_Flg_Thick, Integer kVA, String stiffnerHoriNosCalcMode, String stiffnerVertNosCalcMode, Integer tank_Thick, Integer tank_Bot_Thick) {
        Stiffner stiffner = new Stiffner();

        Integer stiffnerWidth = 5;

        String stiffner_Hori_Data = null;
        Integer stiffner_Hori_Data_L = null;
        Integer stiffner_Hori_Data_W = null;
        String stiffner_Hori_Data_Type = null;

        String stiffner_Vert_Data = null;
        Integer stiffner_Vert_Data_L = null;
        Integer stiffner_Vert_Data_W = null;
        String stiffner_Vert_Data_Type = null;

        if (kVA <= 25) {
            stiffner_Hori_Data_L = 40;
            stiffner_Hori_Data_W = 6;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "40 X 6 - Flat";
        } else if (kVA <= 63) {
            stiffner_Hori_Data_L = 40;
            stiffner_Hori_Data_W = 6;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "40 X 6 - Flat";
        } else if (kVA <= 100) {
            stiffner_Hori_Data_L = 40;
            stiffner_Hori_Data_W = 8;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "40 X 8 - Flat";
        } else if (kVA <= 160) {
            stiffner_Hori_Data_L = 50;
            stiffner_Hori_Data_W = 8;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "50 X 8 - Flat";
        } else if (kVA <= 200) {
            stiffner_Hori_Data_L = 50;
            stiffner_Hori_Data_W = 8;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "50 X 8 - Flat";
        } else if (kVA <= 250) {
            stiffner_Hori_Data_L = 50;
            stiffner_Hori_Data_W = 10;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "50 X 10 - Flat";
        } else if (kVA <= 315) {
            stiffner_Hori_Data_L = 50;
            stiffner_Hori_Data_W = 10;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "50 X 10 - Flat";
        } else if (kVA <= 400) {
            stiffner_Hori_Data_L = 50;
            stiffner_Hori_Data_W = 10;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "50 X 10 - Flat";
        } else if (kVA <= 500) {
            stiffner_Hori_Data_L = 60;
            stiffner_Hori_Data_W = 10;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "60 X 10 - Flat";
        } else if (kVA <= 630) {
            stiffner_Hori_Data_L = 60;
            stiffner_Hori_Data_W = 10;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "60 X 10 - Flat";
        } else if (kVA <= 800) {
            stiffner_Hori_Data_L = 65;
            stiffner_Hori_Data_W = 12;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "65 X 12 - Flat";
        } else if (kVA <= 1000) {
            stiffner_Hori_Data_L = 65;
            stiffner_Hori_Data_W = 12;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "65 X 12 - Flat";
        } else if (kVA <= 1250) {
            stiffner_Hori_Data_L = 65;
            stiffner_Hori_Data_W = 12;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "65 X 12 - Flat";
        } else if (kVA <= 1600) {
            stiffner_Hori_Data_L = 65;
            stiffner_Hori_Data_W = 12;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "65 X 12 - Flat";
        } else if (kVA <= 2000) {
            stiffner_Hori_Data_L = 65;
            stiffner_Hori_Data_W = 12;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "65 X 12 - Flat";
        } else if (kVA <= 2500) {
            stiffner_Hori_Data_L = 65;
            stiffner_Hori_Data_W = 12;
            stiffner_Hori_Data_Type = "Flat";
            stiffner_Hori_Data = "65 X 12 - Flat";
        } else if (kVA <= 6300) {
            stiffner_Hori_Data = "75 x 12 – Flat";
            stiffner_Hori_Data_L = 75;
            stiffner_Hori_Data_W = 12;
            stiffner_Hori_Data_Type = "Flat";
        } else {
            stiffner_Hori_Data = "85 x 12 – Flat";
            stiffner_Hori_Data_L = 85;
            stiffner_Hori_Data_W = 12;
            stiffner_Hori_Data_Type = "Flat";
        }

        //For Vertical Stiffeners
        if (kVA <= 25) {
            stiffner_Vert_Data_L = 40;
            stiffner_Vert_Data_W = 6;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "40 X 6 - Flat";
        } else if (kVA <= 63) {
            stiffner_Vert_Data_L = 40;
            stiffner_Vert_Data_W = 6;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "40 X 6 - Flat";
        } else if (kVA <= 100) {
            stiffner_Vert_Data_L = 40;
            stiffner_Vert_Data_W = 8;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "40 X 8 - Flat";
        } else if (kVA <= 160) {
            stiffner_Vert_Data_L = 50;
            stiffner_Vert_Data_W = 8;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "50 X 8 - Flat";
        } else if (kVA <= 200) {
            stiffner_Vert_Data_L = 50;
            stiffner_Vert_Data_W = 8;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "50 X 8 - Flat";
        } else if (kVA <= 250) {
            stiffner_Vert_Data_L = 50;
            stiffner_Vert_Data_W = 10;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "50 X 10 - Flat";
        } else if (kVA <= 315) {
            stiffner_Vert_Data_L = 50;
            stiffner_Vert_Data_W = 10;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "50 X 10 - Flat";
        } else if (kVA <= 400) {
            stiffner_Vert_Data_L = 50;
            stiffner_Vert_Data_W = 10;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "50 X 10 - Flat";
        } else if (kVA <= 500) {
            stiffner_Vert_Data_L = 60;
            stiffner_Vert_Data_W = 10;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "60 X 10 - Flat";
        } else if (kVA <= 630) {
            stiffner_Vert_Data_L = 60;
            stiffner_Vert_Data_W = 10;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "60 X 10 - Flat";
        } else if (kVA <= 800) {
            stiffner_Vert_Data_L = 65;
            stiffner_Vert_Data_W = 12;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "65 X 12 - Flat";
        } else if (kVA <= 1000) {
            stiffner_Vert_Data_L = 65;
            stiffner_Vert_Data_W = 12;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "65 X 12 - Flat";
        } else if (kVA <= 1250) {
            stiffner_Vert_Data_L = 65;
            stiffner_Vert_Data_W = 12;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "65 X 12 - Flat";
        } else if (kVA <= 1600) {
            stiffner_Vert_Data_L = 65;
            stiffner_Vert_Data_W = 12;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "65 X 12 - Flat";
        } else if (kVA <= 2000) {
            stiffner_Vert_Data_L = 65;
            stiffner_Vert_Data_W = 12;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "65 X 12 - Flat";
        } else if (kVA <= 2500) {
            stiffner_Vert_Data_L = 65;
            stiffner_Vert_Data_W = 12;
            stiffner_Vert_Data_Type = "Flat";
            stiffner_Vert_Data = "65 X 12 - Flat";
        } else if (kVA <= 6300) {
            stiffner_Vert_Data =  "75 x 12 – Flat";
            stiffner_Vert_Data_L = 75;
            stiffner_Vert_Data_W = 12;
            stiffner_Vert_Data_Type = "Flat";
        } else {
            stiffner_Vert_Data =  "85 x 12 – Flat";
            stiffner_Vert_Data_L = 85;
            stiffner_Vert_Data_W = 12;
            stiffner_Vert_Data_Type = "Flat";
        }

        //stiffner
        Integer stiffner_Hori_L = StiffenerFormulas.getStiffenerHoriL(tank_L,stiffnerWidth, tank_Thick);

        Integer stiffner_Hori_Nos = StiffenerFormulas.getStiffenerHoriNos(tank_H);
        Double stiffner_Hori_Pitch = StiffenerFormulas.getStiffenerHoriPitch(tank_H, stiffner_Hori_Nos);
        Integer stiffner_Vert_L = tank_H - tank_Flg_Thick;

        Integer stiffner_Vert_Nos = StiffenerFormulas.getStiffenerVertNos(tank_L);
        Double stiffner_Vert_Pitch = StiffenerFormulas.getStiffenerVertPitch(tank_L, stiffner_Vert_Nos);
        Integer stiffner_Vert_Side_Nos = StiffenerFormulas.getStiffenerVertSideNos(tank_W);
        Double stiffner_Vert_Side_Pitch = StiffenerFormulas.getStiffenerVertSidePitch(tank_W, stiffner_Vert_Side_Nos);

        String stiffner_Hori_Nos_Calc_Mode=checkstiffner_HoriandVert_Nos_Calc_Mode(stiffnerHoriNosCalcMode);
        stiffner.setStiffner_Hori_Nos_Calc_Mode(stiffner_Hori_Nos_Calc_Mode);
        stiffner.setStiffner_Hori_L(stiffner_Hori_L);
        stiffner.setStiffner_Hori_Data(stiffner_Hori_Data);
        stiffner.setStiffner_Hori_Nos(stiffner_Hori_Nos);
        stiffner.setStiffner_Hori_Pitch(stiffner_Hori_Pitch);
        stiffner.setStiffner_Hori_Side_L(tank_W + tank_Thick);
        String stiffner_Vert_Nos_Calc_Mode=checkstiffner_HoriandVert_Nos_Calc_Mode(stiffnerVertNosCalcMode);
        stiffner.setStiffner_Vert_Nos_Calc_Mode(stiffner_Vert_Nos_Calc_Mode);
        stiffner.setStiffner_Vert_L(stiffner_Vert_L + tank_Bot_Thick);
        stiffner.setStiffner_Vert_Data(stiffner_Vert_Data);
        stiffner.setStiffner_Vert_Nos(stiffner_Vert_Nos);
        stiffner.setStiffner_Vert_Pitch(stiffner_Vert_Pitch);
        stiffner.setStiffner_Vert_Side_Nos(stiffner_Vert_Side_Nos);
        stiffner.setStiffner_Vert_Side_Pitch(stiffner_Vert_Side_Pitch);

        stiffner.setStiffner_Hori_Data_L(stiffner_Hori_Data_L);
        stiffner.setStiffner_Hori_Data_W(stiffner_Hori_Data_W);
        stiffner.setStiffner_Hori_Data_Type(stiffner_Hori_Data_Type);
        stiffner.setStiffner_Vert_Data_L(stiffner_Vert_Data_L);
        stiffner.setStiffner_Vert_Data_W(stiffner_Vert_Data_W);
        stiffner.setStiffner_Vert_Data_Type(stiffner_Vert_Data_Type);
        return stiffner;
    }
}
