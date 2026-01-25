package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Inspect;

public class InspectFormulas {
    public static Inspect calculateInspect(Integer kVA, String hvb_Pos,
                                           String lvb_Pos, Double hvb_Ptch,
                                           Double lvb_Ptch, Integer tank_W,
                                           Integer lid_Thick, Integer hvb_tank_Oil_Clear,
                                           Integer hvb_Sup_OD, Integer lvb_tank_Oil_Clear,
                                           Integer lvb_Sup_ID, Integer tank_L, Double hvb_Sup_V_Pos,
                                           Boolean hvcb, Boolean lvcb, Double lvb_Sup_V_Pos, Integer lvb_Sup_OD) {

        Inspect inspect = new Inspect();
        Boolean inspect_Cover = null;
        Integer inspect_Cover_Nos = 0;
        Integer inspect_Cover_W = 0;
        Integer inspect_Cover_L = 0;
        Double inspect_Cover_1_V_Pos = 0.0;
        Double inspect_Cover_1_H_Pos = 0.0;
        Double inspect_Cover_2_V_Pos = 0.0;
        Double inspect_Cover_2_H_Pos = 0.0;
        String inspect_Cover_Type="";
        Integer inspect_Cover_Dia=0;
        Integer inspect_Cover_Thick=0;
        Integer inspect_Flg_Dia=0;
        Integer inspect_Flg_Thick=0;
        Integer inspect_Cutout_Dia=0;
        Integer inspect_Flg_PCD=0;
        Integer inspect_Bolt_Nos=0;
        Integer inspect_Bolt_Dia=0;
        Integer inspect_Flg_L=0;
        Integer inspect_Flg_W=0;
        Integer inspect_Cutout_L=0;
        Integer inspect_Cutout_W=0;
        Integer inspect_Bolt_H_Nos=0;
        Integer inspect_Bolt_V_Nos=0;
        Double inspect_Bolt_H_Ptch=0.0;
        Double inspect_Bolt_V_Ptch=0.0;
        Integer inspect_Cover_TypeNo=0;

        boolean condition = (hvb_Pos.equalsIgnoreCase("tank") && lvb_Pos.equalsIgnoreCase("tank")) || (hvcb && lvcb);
        if(kVA <= 250){
            if(condition){
                inspect.setInspect_Cover(false);
            }else{
                inspect_Cover=true;
                inspect_Cover_W = 150;
                inspect_Cover_L = (int) (hvb_Ptch * 2);
                inspect_Cover_1_V_Pos = (double) 0;
                inspect_Cover_1_H_Pos = (double) 0;
                inspect_Cover_2_V_Pos = (double) (inspect_Cover_W / 2); //From LV Side tank wall
                inspect_Cover_2_H_Pos = (double) 0;
                if(tank_W >= 350){
                    inspect_Cover_TypeNo = 35;
                } else if (tank_W >= 250){
                    inspect_Cover_TypeNo = 36;
                } else {inspect_Cover_TypeNo = 0;}
            }
        }
        else {
            if(condition){
                inspect_Cover=true;
                inspect_Cover_W = 150;
                inspect_Cover_L = (int) (hvb_Ptch * 2);
                inspect_Cover_1_V_Pos = (double) 0;
                inspect_Cover_1_H_Pos = (double) 0;
                inspect_Cover_2_V_Pos = (double) (inspect_Cover_W / 2); //From LV Side tank wall
                inspect_Cover_2_H_Pos = (double) 0;
                if(tank_W >= 350){
                    inspect_Cover_TypeNo = 35;
                } else if (tank_W >= 250){
                    inspect_Cover_TypeNo = 36;
                } else {inspect_Cover_TypeNo = 0;}
                System.out.println("1 inspect_Cover_W : " + inspect_Cover_W);
            }
            else if((hvb_Pos.equalsIgnoreCase("lid") && lvb_Pos.equalsIgnoreCase("tank")) ||
                    (!hvcb && lvcb)){
                inspect_Cover = true;
                inspect_Cover_W = 150;
                inspect_Cover_L = (int) (lvb_Ptch * 2);
                inspect_Cover_1_V_Pos = (double) (inspect_Cover_W / 2); //From LV Side tank wall
                inspect_Cover_1_H_Pos = (double) 0;
                System.out.println("2 inspect_Cover_W : " + inspect_Cover_W);
            }
            else if((hvb_Pos.equalsIgnoreCase("tank") && lvb_Pos.equalsIgnoreCase("lid")) ||
                    (hvcb && !lvcb)){
                inspect_Cover = true;
                inspect_Cover_W = 150;
                inspect_Cover_L = (int) (hvb_Ptch * 2);
                inspect_Cover_1_V_Pos = (double) -(inspect_Cover_W / 2); //From HV Side tank wall
                inspect_Cover_1_H_Pos = (double) 0;
                System.out.println("3 inspect_Cover_W : " + inspect_Cover_W);
            }
            else {
                // Inspection Cover Dimensions
                if ((tank_W - ((lvb_Sup_V_Pos + (lvb_Sup_OD/ 2)) + (hvb_Sup_V_Pos + (hvb_Sup_OD/ 2))) - 30)  >= 200){
                    inspect_Cover = true;
                    inspect_Cover_W = 200;
                    inspect_Cover_L = ((int)((hvb_Ptch - 40) / 10) * 10) - 40;
                    inspect_Cover_1_V_Pos = 30.0;
                    inspect_Cover_1_H_Pos = 0.0;
                    System.out.println("4 inspect_Cover_W : " + inspect_Cover_W);
                }
                else if ((tank_W - ((lvb_Sup_V_Pos + (lvb_Sup_OD/ 2)) + (hvb_Sup_V_Pos + (hvb_Sup_OD/ 2))) - 30) >= 100){
                    inspect_Cover = true;
                    inspect_Cover_W = 100;
                    inspect_Cover_L = ((int)((hvb_Ptch - 40) / 10) * 10) - 40;
                    inspect_Cover_1_V_Pos = 20.0;
                    inspect_Cover_1_H_Pos = 0.0;
                    System.out.println("5 inspect_Cover_W : " + inspect_Cover_W);
                }
                else{
                    inspect_Cover = false;
                }
                inspect_Cover_L = 300;
            }
        }

        inspect_Cover_Nos = 1;
        inspect.setInspect_Cover(inspect_Cover);
        inspect.setInspect_Cover_W(inspect_Cover_W);
        inspect.setInspect_Cover_L(inspect_Cover_L);
        inspect.setInspect_Cover_Nos(inspect_Cover_Nos);
        inspect.setInspect_Cover_1_V_Pos(inspect_Cover_1_V_Pos);
        inspect.setInspect_Cover_1_H_Pos(inspect_Cover_1_H_Pos);
        inspect.setInspect_Cover_2_V_Pos(inspect_Cover_2_V_Pos);
        inspect.setInspect_Cover_2_H_Pos(inspect_Cover_2_H_Pos);

        if(kVA<=500){
            inspect_Cover_Type="round";
            inspect_Cover_Dia = inspect_Cover_W;
            inspect_Cover_Thick = lid_Thick;
            inspect_Flg_Dia = inspect_Cover_Dia;
            inspect_Flg_Thick = lid_Thick;
            inspect_Cutout_Dia = inspect_Flg_Dia - (20 * 2); //20mm = Flange Width
            inspect_Flg_PCD = inspect_Flg_Dia - (10 * 2);    //10mm = Flange width / 2
            inspect_Bolt_Nos = 6;
            inspect_Bolt_Dia = 10;
        }
        else {
            inspect_Cover_Type = "rectangle";
            inspect_Cover_Thick = lid_Thick;
            inspect_Flg_L = inspect_Cover_L;
            inspect_Flg_W = inspect_Cover_W;
            inspect_Flg_Thick = lid_Thick;

            inspect_Cutout_L = inspect_Flg_L - (20 * 2); //20mm = Flange Width
            inspect_Cutout_W = inspect_Flg_W - (20 * 2); //20mm = Flange Width

            inspect_Bolt_Dia = 10;
            inspect_Bolt_H_Nos = 4;
            inspect_Bolt_V_Nos = 3;
            inspect_Bolt_H_Ptch = (double) ((inspect_Flg_L - 20) / (inspect_Bolt_H_Nos - 1));
            inspect_Bolt_V_Ptch = (double) ((inspect_Flg_W - 20) / (inspect_Bolt_V_Nos - 1));
        }

        if((tank_L < 900 || tank_W < 300) && !hvcb && !lvcb){
            inspect.setInspect_Cover(false);
            inspect.setInspect_Cover_Nos(0);
        }

        inspect.setInspect_Cover_Type(inspect_Cover_Type);
        inspect.setInspect_Cover_Dia(inspect_Cover_Dia);
        inspect.setInspect_Cover_Thick(inspect_Cover_Thick);
        inspect.setInspect_Flg_L(inspect_Flg_L);
        inspect.setInspect_Flg_W(inspect_Flg_W);
        inspect.setInspect_Flg_Dia(inspect_Flg_Dia);
        inspect.setInspect_Flg_Thick(inspect_Flg_Thick);
        inspect.setInspect_Cutout_Dia(inspect_Cutout_Dia);
        inspect.setInspect_Flg_PCD(inspect_Flg_PCD);
        inspect.setInspect_Bolt_H_Nos(inspect_Bolt_H_Nos);
        inspect.setInspect_Bolt_V_Nos(inspect_Bolt_V_Nos);
        inspect.setInspect_Bolt_Nos(inspect_Bolt_Nos);
        inspect.setInspect_Bolt_Dia(inspect_Bolt_Dia);
        inspect.setInspect_Cutout_L(inspect_Cutout_L);
        inspect.setInspect_Cutout_W(inspect_Cutout_W);
        inspect.setInspect_Bolt_H_Ptch(inspect_Bolt_H_Ptch);
        inspect.setInspect_Bolt_V_Ptch(inspect_Bolt_V_Ptch);
        return inspect;
    }
}
