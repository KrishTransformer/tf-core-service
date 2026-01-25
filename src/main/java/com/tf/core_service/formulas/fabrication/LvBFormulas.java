package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Lvb;

import java.util.Objects;

public class LvBFormulas {

    // Lvb_S_Tilt_Ang Calculation
    public static double calculateLvb_S_Tilt_Ang(Integer tank_L, Double lvb_Ptch,
                                                 Integer lvb_tank_Oil_Clear, Integer lvb_Sup_H,
                                                 Integer lvb_L1) {
        double lvb_S_Tilt_Ang = 0;
        if ((tank_L / 2.0 - lvb_Ptch) < lvb_tank_Oil_Clear) {
            double tilt_angle = Math.atan(lvb_tank_Oil_Clear / (lvb_Sup_H + lvb_L1));
            lvb_S_Tilt_Ang = (int) ((tilt_angle * 180.0 / 3.15149) + 2.9);
        }
        return Math.toDegrees(lvb_S_Tilt_Ang);
    }

    // Lvb_Sup_L Calculation
    public static Double calculateLvb_Sup_L(Double lvb_Ptch, Integer lvb_Nos, Integer lvb_Sup_ID) {
        return (lvb_Ptch * (lvb_Nos - 1)) + (2 * lvb_Sup_ID);
    }

    // Lvb_Sup_OD Calculation
    public static Integer calculateLvb_Sup_OD(Integer lvb_Sup_ID, Integer lvb_Sup_Thick) {
        return (lvb_Sup_ID + (2 * lvb_Sup_Thick));
    }

    // Lvb_Sup_Flg_W Calculation
    public static Double calculateLvb_Sup_Flg_W(Integer lvb_Sup_Flg_OD, Integer lvb_Sup_ID) {
        return ((lvb_Sup_Flg_OD - lvb_Sup_ID) / 2.0);
    }

    public static String checkLvb_Sup_type(String lvbSuptype) {
        if (lvbSuptype.equals("")) {
            return "turret";
        } else {
            return lvbSuptype;
        }
    }

    public static Lvb calcuateLvb(Boolean lvcbFromInput, String lvb_Pos, Integer lvb_Volt, Integer lvb_Amp,

                                  String lvb_Type, Integer tank_L, String lvb_Sup_type, Integer tank_Thick, Integer lid_Thick, Integer noOfLvTerminals, Integer tank_W,
                                  Integer tank_H, Integer noOfHvTerminals) {

        HvLvCommonVariables hvLvCommonVariables = HvLvCommonMethods.calcuateHvLvCommonVariables(lvb_Volt, lvcbFromInput, lvb_Amp);

        Lvb lvb = new Lvb();
        lvb.setLvb_Pos(lvb_Pos);
        lvb.setLvb_Volt(lvb_Volt);
        lvb.setLvb_Amp(lvb_Amp);
        lvb.setLvb_Type(lvb_Type);

        Integer lvb_Nos = noOfLvTerminals;
        lvb.setLvb_Nos(lvb_Nos);
        lvb.setLvb_offset(0);

        Double lvb_Ptch = 0.0;

        if (tank_L <= 590) {
            lvb.setLvb_Ptch(0.0);
            lvb.setLvb_Side_Pitch(150);
            lvb.setLvb_Center_Pitch(110);
            lvb.setLvb_VPos(tank_W / 2 - 40);
        } else if (tank_L < 800) {
            lvb.setLvb_Ptch(0.0);
            lvb.setLvb_Side_Pitch(170);
            lvb.setLvb_Center_Pitch(170);
            lvb.setLvb_VPos(tank_W / 2 - 40);
        }
        //Conservator will be on the Left side if the tank L is beyond 900
        else {
            if (lvb_Amp <= 250) {
                lvb_Ptch = 110.0;
                lvb.setLvb_VPos(tank_W / 2 - 40);
            } else if (lvb_Amp <= 630) {
                lvb_Ptch = 125.0;
                lvb.setLvb_VPos(tank_W / 2 - 50);
            } else if (lvb_Amp <= 1000) {
                lvb_Ptch = 150.0;
                lvb.setLvb_VPos(tank_W / 2 - 65);
            } else if (lvb_Amp <= 4000) {
                lvb_Ptch = 150.0;
                lvb.setLvb_VPos(tank_W / 2 - 65);
            } else {
                lvb_Ptch = (double) hvLvCommonVariables.getCap_Dia() + hvLvCommonVariables.getPh2ph();
            }
            lvb.setLvb_Ptch(lvb_Ptch);
            lvb.setLvb_Side_Pitch(0);
            lvb.setLvb_Center_Pitch(0);
        }
        if (lvb_Volt > 1100) {
            lvb.setLvb_Ptch(315.0);
            lvb.setLvb_Side_Pitch(315);
            lvb.setLvb_Center_Pitch(315);
        }


        Integer lvb_tank_Oil_Clear = hvLvCommonVariables.getTankClrnce();
        lvb.setLvb_tank_Oil_Clear(lvb_tank_Oil_Clear);

        Integer lvb_L1 = hvLvCommonVariables.getBushing_L1();
        lvb.setLvb_L1(lvb_L1);

        Integer lvb_L2 = hvLvCommonVariables.getBushing_L2();
        lvb.setLvb_L2(lvb_L2);

        Integer lvb_Sup_H = lvb_L2;
        lvb.setLvb_Sup_H(lvb_Sup_H);

        lvb.setLvb_S_Tilt_Ang(calculateLvb_S_Tilt_Ang(tank_L, lvb_Ptch, lvb_tank_Oil_Clear, lvb_Sup_H, lvb_L1));
        lvb.setLvb_Sup_type(checkLvb_Sup_type(lvb_Sup_type));

        Integer lvb_Sup_ID = hvLvCommonVariables.getBoredia();
        lvb.setLvb_Sup_ID(lvb_Sup_ID);
        lvb.setLvb_Sup_V_Pos((double) (lvb_tank_Oil_Clear + (lvb_Sup_ID / 2)));

        lvb.setLvb_Sup_L(calculateLvb_Sup_L(lvb_Ptch, lvb_Nos, lvb_Sup_ID));
        lvb.setLvb_Sup_W(lvb_Sup_ID * 2);

        Integer lvb_Sup_Thick = tank_Thick;
        lvb.setLvb_Sup_Thick(lvb_Sup_Thick);

        lvb.setLvb_Sup_OD(calculateLvb_Sup_OD(lvb_Sup_ID, lvb_Sup_Thick));
        lvb.setLvb_Sup_H(lvb_L2);

        lvb.setLvb_Sup_Flg_PCD(hvLvCommonVariables.getSupFlangePCD());

        Integer lvb_Sup_Flg_OD = hvLvCommonVariables.getSupFlangeDia();
        lvb.setLvb_Sup_Flg_OD(lvb_Sup_Flg_OD);
        lvb.setLvb_Sup_Flg_W(calculateLvb_Sup_Flg_W(lvb_Sup_Flg_OD, lvb_Sup_ID));
        lvb.setLvb_Sup_Flg_ID(hvLvCommonVariables.getBoredia());
        lvb.setLvb_Sup_Flg_Thick(lid_Thick);
        lvb.setLvb_Sup_Flg_Stud_Dia(hvLvCommonVariables.getStud_Dia());
        lvb.setLvb_Sup_Flg_Stud_L(hvLvCommonVariables.getStud_L());
        lvb.setLvb_Sup_Flg_Stud_Nos(hvLvCommonVariables.getStud_Nos());
        lvb.setLvb_Ph_Erth_Clear("NO_FORMULA_GIVEN");
        lvb.setLvb_Bolt_Dia("NO_FORMULA_GIVEN");
        lvb.setLvb_Cap_Dia("NO_FORMULA_GIVEN");
        lvb.setLvb_F_Tilt_Ang("NO_FORMULA_GIVEN");

        lvb.setLvb_OnTank_Length(null);
        if (Objects.equals(lvb_Pos, "tank")) {
            if (lvb_Amp <= 250) {
                lvb.setLvb_Tank_On_Wall_DesNo(7);
                lvb.setLvb_OnTank_Length(456);
                lvb.setLvb_OnTank_cutout_L(456);
                lvb.setLvb_OnTank_cutout_W(156);
            } else if (lvb_Amp <= 630) {
                lvb.setLvb_Tank_On_Wall_DesNo(8);
                lvb.setLvb_OnTank_Length(590);
                lvb.setLvb_OnTank_cutout_L(590);
                lvb.setLvb_OnTank_cutout_W(250);
            } else if (lvb_Amp <= 1000) {
                lvb.setLvb_Tank_On_Wall_DesNo(167);
                lvb.setLvb_OnTank_Length(620);
                lvb.setLvb_OnTank_cutout_L(610);
                lvb.setLvb_OnTank_cutout_W(240);
            } else if (lvb_Amp <= 2000) {
                lvb.setLvb_Tank_On_Wall_DesNo(0);
                lvb.setLvb_OnTank_Length(0);
                lvb.setLvb_OnTank_cutout_L(0);
                lvb.setLvb_OnTank_cutout_W(0);
            } else if (lvb_Amp <= 3150) {
                lvb.setLvb_Tank_On_Wall_DesNo(0);
                lvb.setLvb_OnTank_Length(0);
                lvb.setLvb_OnTank_cutout_L(0);
                lvb.setLvb_OnTank_cutout_W(0);
            } else {
                lvb.setLvb_Tank_On_Wall_DesNo(0);
                lvb.setLvb_OnTank_Length(0);
                lvb.setLvb_OnTank_cutout_L(0);
                lvb.setLvb_OnTank_cutout_W(0);
            }
            lvb.setLvb_OnTank_cutout_Hpos(0);
            lvb.setLvb_OnTank_cutout_Vpos(tank_H / 2 - 60 - lvb.getLvb_OnTank_cutout_W() / 2);

        }
        if (noOfHvTerminals == 4) {
            if (tank_L >= 830 && tank_L < 950) {
                lvb.setLvb_offset(50);
                lvb.setLvb_Ptch(0.0);
                lvb.setLvb_Side_Pitch(170);
                lvb.setLvb_Center_Pitch(170);
                lvb.setLvb_VPos(tank_W / 2 - 50);
            } else if (tank_L >= 950 && tank_L < 1090) {
                lvb.setLvb_offset(0);
                lvb.setLvb_Ptch(120.0);
                lvb.setLvb_Side_Pitch(0);
                lvb.setLvb_Center_Pitch(0);
                lvb.setLvb_VPos(tank_W / 2 - 50);
            } else if (tank_L >= 1090) {
                lvb.setLvb_offset(0);
                lvb.setLvb_Ptch(0.0);
                lvb.setLvb_Side_Pitch(170);
                lvb.setLvb_Center_Pitch(170);
                lvb.setLvb_VPos(tank_W / 2 - 50);
            }


        }

        return lvb;
    }
}
