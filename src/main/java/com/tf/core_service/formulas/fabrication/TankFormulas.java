package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Tank;

public class TankFormulas {

    private static String getTankBuildType(Integer kVA) {
        if (kVA > 500) {
            return "single";
        } else {
            return "l-joint";
        }
    }

    private static Integer getTankBotL(Integer tank_L, Integer tank_Thick, Integer tank_Bot_Thick) {
        Integer output = tank_L + (2 * tank_Thick) + 20; // was previously (2 * tank_Bot_Thick) instead of 20
        return output;
    }

    private static Integer getTankBotW(Integer tank_W, Integer tank_Thick, Integer tank_Bot_Thick) {
        Integer output = tank_W + (2 * tank_Thick) + 20; //was previously (2 * tank_Bot_Thick) instead of 20
        return output;
    }

    private static Integer getTankFlgW(Integer kVA) {
        if (kVA <= 100) {
            return 40;
        } else if (kVA <= 400) {
            return 50;
        } else if (kVA <= 630) {
            return 60;
        } else if (kVA <= 2500) {
            return 65;
        } else if (kVA <= 6300) {
            return 75;
        } else if (kVA <= 8000) {
            return 80;
        } else {
            return 100;
        }
    }

    private static Integer getTankFlgL1(Integer tank_L, Integer tank_Thick, Integer tank_Flg_W) {
        return tank_L + (2 * tank_Thick) + (2 * tank_Flg_W) + 2; //2mm extra for attachment (NB)
    }

    private static Integer getTankFlgL2(Integer tank_W, Integer tank_Thick, Integer tank_Flg_W) {
        return tank_W + (2 * tank_Thick) + (2 * tank_Flg_W) + 2; //2mm extra for attachment (NB)
    }

    private static Integer getTankFlgBoltDia(Integer kVA) {
        if (kVA <= 5000) {
            return 12;
        } else if (kVA > 5000 && kVA <= 7000) {
            return 14;
        } else {
            return 18;
        }
    }


    private static Integer getTankFlgBoltHNos(Integer tank_Flg_L1, Integer tank_Flg_Bolt_Dia, Integer tank_Flg_W) {
        return (int) (((tank_Flg_L1 - tank_Flg_W)/ (Math.sqrt(tank_Flg_Bolt_Dia) * 25)) + 1) + 1; //NB sir's formula (C80))
    }

    private static Integer getTankFlgBoltVNos(Integer tank_Flg_L2, Integer tank_Flg_Bolt_Dia, Integer tank_Flg_W) {
        return (int) (((tank_Flg_L2 - tank_Flg_W)/ (Math.sqrt(tank_Flg_Bolt_Dia) * 25)) + 1) + 1; //NB sir's formula (C80))
    }

    private static Integer getTankFlgBoltHPch(Integer tank_Flg_L1, Integer tank_Flg_Bolt_H_Nos) {
        return (int) (((double) tank_Flg_L1 / (tank_Flg_Bolt_H_Nos - 1)) + 0.99);
    }

    private static Integer getTankFlgBoltVPch(Integer tank_Flg_L2, Integer tank_Flg_Bolt_V_Nos) {
        return (int) (((double) tank_Flg_L2 / (tank_Flg_Bolt_V_Nos - 1))+ 0.99);
    }

    public static Tank calculateTankFormulas(Integer kVA, Integer tank_L, Integer tank_W, Integer tank_H
            , Integer tank_Thick
            , Integer tank_Bot_Thick, Integer tank_Flg_Thick) {
        Tank tank = new Tank();
        tank.setTank_L(tank_L);
        tank.setTank_W(tank_W);
        tank.setTank_H(tank_H);
        tank.setTank_Thick(tank_Thick);
        tank.setTank_Build_Type(getTankBuildType(kVA));
        tank.setTank_Bot_Thick(tank_Bot_Thick);
        tank.setTank_Bot_L (getTankBotL(tank_L,tank_Thick,tank_Bot_Thick));
        tank.setTank_Bot_W (getTankBotW(tank_W,tank_Thick,tank_Bot_Thick));
        tank.setTank_Flg_Thick(tank_Flg_Thick);
        tank.setTank_Flg_W(getTankFlgW(kVA));
        tank.setTank_Flg_L1(getTankFlgL1(tank_L, tank_Thick,tank.getTank_Flg_W()));
        tank.setTank_Flg_L2(getTankFlgL2(tank_W,tank_Thick,tank.getTank_Flg_W()));
        tank.setTank_Flg_Bolt_Dia(getTankFlgBoltDia(kVA));
        tank.setTank_Flg_Bolt_H_Nos(getTankFlgBoltHNos(tank.getTank_Flg_L1(),tank.getTank_Flg_Bolt_Dia(), getTankFlgW(kVA)));
        tank.setTank_Flg_Bolt_V_Nos(getTankFlgBoltVNos(tank.getTank_Flg_L2(),tank.getTank_Flg_Bolt_Dia(), getTankFlgW(kVA)));
        tank.setTank_Flg_Bolt_H_Pch(getTankFlgBoltHPch(tank.getTank_Flg_L1(),tank.getTank_Flg_Bolt_H_Nos()));
        tank.setTank_Flg_Bolt_V_Pch(getTankFlgBoltVPch(tank.getTank_Flg_L2(),tank.getTank_Flg_Bolt_V_Nos()));
        return tank;
    }


}



















