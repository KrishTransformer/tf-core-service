package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.InspectCov;

import java.util.Objects;

public class InspectCovFormulas {
    public static Integer getRemainingSpace(boolean isLvCb, boolean isHvCb, int tank_W, int lvb_Vpos, int hvb_Vpos,
                                            int lvbOD, int hvbOD, String lvb_Pos, String hvb_Pos, String cons_Pos){
        if(Objects.equals(lvb_Pos, "tank")){
            isLvCb = true;
        }
        if(Objects.equals(hvb_Pos, "tank")){
            isHvCb = true;
        }

        int remainingSpace;
        if(isLvCb && isHvCb){
            if(Objects.equals(cons_Pos,"left")){
                remainingSpace = tank_W - 60;
            }else {
                remainingSpace = tank_W - 60 - (tank_W / 2 - lvb_Vpos);
            }
        }
        else if (isLvCb && !isHvCb) {
            if(Objects.equals(cons_Pos,"left")){
                remainingSpace = tank_W - lvb_Vpos - (lvbOD/2) - 60;
            } else {
                remainingSpace = tank_W - (tank_W/2 - lvb_Vpos) - (lvbOD/2) - (tank_W/2 - hvb_Vpos) - (hvbOD/2) - 60;
            }

        } else if (!isLvCb && isHvCb) {
            remainingSpace = tank_W - hvb_Vpos - (hvbOD/2) - 60;
        }
        else{
            remainingSpace = tank_W - (tank_W/2 - lvb_Vpos) - (lvbOD/2) - (tank_W/2 - hvb_Vpos) - (hvbOD/2) - 60;
        }
        return remainingSpace;
    }

    public static InspectCov getInspectCov(boolean isLvCb, boolean isHvCb, int tank_W, int lvb_Vpos, int hvb_Vpos,
                                        int lvbOD, int hvbOD, String lvb_Pos, String hvb_Pos, String cons_Pos){

        int remainingSpace = InspectCovFormulas.getRemainingSpace(isLvCb,isHvCb,tank_W,lvb_Vpos,hvb_Vpos,lvbOD,hvbOD, lvb_Pos, hvb_Pos,cons_Pos);

        InspectCov inspectCov = new InspectCov();

        inspectCov.setInspectCov_Rem_Space(remainingSpace);

        if (remainingSpace < 120){
            inspectCov.setInspectCov(false);
            inspectCov.setInspectCov_Dia(0);
            inspectCov.setInspectCov_W(0);
            inspectCov.setInspectCov_L(0);
            inspectCov.setInspectCov_Des_No(0);
        }
        else{
            if(remainingSpace <= 200){
                inspectCov.setInspectCov(true);
                inspectCov.setInspectCov_Dia(0);
                inspectCov.setInspectCov_L(250);
                inspectCov.setInspectCov_W(120);
                inspectCov.setInspectCov_Des_No(0);
            }
            else if (remainingSpace <= 300){
                inspectCov.setInspectCov(true);
                inspectCov.setInspectCov_Dia(0);
                inspectCov.setInspectCov_L(465);
                inspectCov.setInspectCov_W(190);
                inspectCov.setInspectCov_Des_No(36);
            }
            else{
                inspectCov.setInspectCov(true);
                inspectCov.setInspectCov_Dia(0);
                inspectCov.setInspectCov_L(380);
                inspectCov.setInspectCov_W(280);
                inspectCov.setInspectCov_Des_No(35);
            }
        }

        int vPos;

        if(Objects.equals(lvb_Pos, "tank")){
            isLvCb = true;
        }
        if (Objects.equals(hvb_Pos, "tank")){
            isHvCb = true;
        }
        //TODO: this is where we can change the offset of inspect cov: currently +ve value offset towards LV and -ve value offset towards HV
        if(isLvCb && isHvCb){
            vPos = 0;
        } else if (!isLvCb && isHvCb) {
            vPos = -(tank_W/2 - lvb_Vpos);
        } else if (isLvCb && !isHvCb) {
            vPos = (tank_W/2 - lvb_Vpos);
        }else {
            vPos = Math.abs(hvb_Vpos - lvb_Vpos);
        }

        inspectCov.setInspectCov_Hpos(0);
        inspectCov.setInspectCov_Vpos(vPos);
        inspectCov.setInspectCov_Type(inspectCov.getInspectCov_Dia() == 0 ?
                inspectCov.getInspectCov_L() + "L X " + inspectCov.getInspectCov_W() + "W" :
                inspectCov.getInspectCov_Dia() + " D");

        return inspectCov;
    }
}
