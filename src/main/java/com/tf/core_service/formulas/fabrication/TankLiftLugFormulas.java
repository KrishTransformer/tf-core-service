package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.TankLiftLug;

public class TankLiftLugFormulas {


    public static TankLiftLug calculateTankLiftLug(double KVA, Integer tank_Flg_W
            , Integer tank_Flg_Thick, Integer tank_Flg_Bolt_H_Pch, Integer tank_Thick, Integer lid_Thick, Boolean tank_LiftLug, Boolean isOCTC) {
        TankLiftLug tankLiftLug = new TankLiftLug();
        tankLiftLug.setTank_LiftLug(tank_LiftLug);
        if (KVA <= 500) {
            tankLiftLug.setTank_LiftLug_Type("type-1");
        } else if (KVA > 500 && KVA <= 2000) {
            tankLiftLug.setTank_LiftLug_Type("type-2");
        } else if (KVA > 2000 && KVA <= 5000) {
            tankLiftLug.setTank_LiftLug_Type("type-3");
        } else {
            tankLiftLug.setTank_LiftLug_Type("type-3");
        }

        if ("type-1".equals(tankLiftLug.getTank_LiftLug_Type())) {
            // For Type1 Lifting Lug
            tankLiftLug.setTank_LiftLug_L1((double) (tank_Flg_W * 2));
            tankLiftLug.setTank_LiftLug_H1(tankLiftLug.getTank_LiftLug_L1() * 0.6);
            tankLiftLug.setTank_LiftLug_Thick(tank_Flg_Thick);
            tankLiftLug.setTank_LiftLug_Hole_Dia(tankLiftLug.getTank_LiftLug_H1() / 2);
            tankLiftLug.setTank_LiftLug_Vpos(tank_Flg_Thick + (tankLiftLug.getTank_LiftLug_H1() / 2));
            tankLiftLug.setTank_LiftLug_Hpos((2 * tank_Flg_Bolt_H_Pch) - tank_Thick);
        } else if ("type-2".equals(tankLiftLug.getTank_LiftLug_Type())) {
            // For Type2 Lifting Lug
            tankLiftLug.setTank_LiftLug_L1((double) (tank_Flg_W * 2));
            tankLiftLug.setTank_LiftLug_L2( tankLiftLug.getTank_LiftLug_L1() / 2);
            tankLiftLug.setTank_LiftLug_L3( tankLiftLug.getTank_LiftLug_L1() / 2);
            tankLiftLug.setTank_LiftLug_H1( tankLiftLug.getTank_LiftLug_L1() * 1.5);
            tankLiftLug.setTank_LiftLug_H2( tankLiftLug.getTank_LiftLug_L3());
            tankLiftLug.setTank_LiftLug_H3( tankLiftLug.getTank_LiftLug_H1() -  tankLiftLug.getTank_LiftLug_H2());
            tankLiftLug.setTank_LiftLug_Thick(tank_Flg_Thick);
            tankLiftLug.setTank_LiftLug_Hole_Dia((tankLiftLug.getTank_LiftLug_L3() / 2));
            tankLiftLug.setTank_LiftLug_Chamfer((double) (tankLiftLug.getTank_LiftLug_Thick() * 2));

            tankLiftLug.setTank_LiftLug_Sup_Thick(tank_Flg_Thick);
            tankLiftLug.setTank_LiftLug_Sup_H(tankLiftLug.getTank_LiftLug_H3() + (2 * tankLiftLug.getTank_LiftLug_Sup_Thick()));
            tankLiftLug.setTank_LiftLug_Sup_W(tankLiftLug.getTank_LiftLug_Thick() + (6 * tankLiftLug.getTank_LiftLug_Sup_Thick()));

            tankLiftLug.setTank_LiftLug_Vpos(- lid_Thick + tankLiftLug.getTank_LiftLug_H2() + (tankLiftLug.getTank_LiftLug_H3() / 2));
            tankLiftLug.setTank_LiftLug_Hpos((2 * tank_Flg_Bolt_H_Pch) - tank_Thick);
        } else if ("type-3".equals(tankLiftLug.getTank_LiftLug_Type())) {
            // For Type3 Lifting Lug
            tankLiftLug.setTank_LiftLug_L1(tank_Flg_W * 1.5);
            tankLiftLug.setTank_LiftLug_L2(tankLiftLug.getTank_LiftLug_L1() / 4);
            tankLiftLug.setTank_LiftLug_L3(tankLiftLug.getTank_LiftLug_L1() / 4);
            tankLiftLug.setTank_LiftLug_L4(tankLiftLug.getTank_LiftLug_L1() / 4);
            tankLiftLug.setTank_LiftLug_H1(tankLiftLug.getTank_LiftLug_L1() * 1.5);
            tankLiftLug.setTank_LiftLug_H2(tankLiftLug.getTank_LiftLug_H1() / 2);
            tankLiftLug.setTank_LiftLug_H3(tankLiftLug.getTank_LiftLug_H2() / 2);
            tankLiftLug.setTank_LiftLug_Thick(tank_Flg_Thick);
            tankLiftLug.setTank_LiftLug_Chamfer(tankLiftLug.getTank_LiftLug_L4() / 2);

            tankLiftLug.setTank_LiftLug_Sup_Thick(tank_Flg_Thick);
            tankLiftLug.setTank_LiftLug_Sup_H(tankLiftLug.getTank_LiftLug_H1() + tankLiftLug.getTank_LiftLug_Sup_Thick());
            tankLiftLug.setTank_LiftLug_Sup_W(tankLiftLug.getTank_LiftLug_Thick() + (6 * tankLiftLug.getTank_LiftLug_Sup_Thick()));

            tankLiftLug.setTank_LiftLug_Vpos(tankLiftLug.getTank_LiftLug_Sup_H() / 2);
            tankLiftLug.setTank_LiftLug_Hpos((2 * tank_Flg_Bolt_H_Pch) - tank_Thick);
        }

        if(KVA <=200){tankLiftLug.setTank_Lifting_lug_TypeNo(50);}
        else if (KVA <=630){
            if(isOCTC){
                tankLiftLug.setTank_Lifting_lug_TypeNo(51);}
            else {tankLiftLug.setTank_Lifting_lug_TypeNo(52);}
        }
        else if (KVA <= 800){tankLiftLug.setTank_Lifting_lug_TypeNo(53);}
        else if (KVA <= 1600){tankLiftLug.setTank_Lifting_lug_TypeNo(54);}
        else {tankLiftLug.setTank_Lifting_lug_TypeNo(55);}

        return tankLiftLug;
    }

}
