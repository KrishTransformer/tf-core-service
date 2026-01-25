package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Hvcb;

public class HVCBForumulas {
    public static double calculateHvcbFrontFlgThick(double tankThick) {
        return tankThick;
    }

    public static double calculateHvcbFrontCvrThick(double tankThick) {
        return tankThick;
    }

    public static double calculateHvcbFrontCvrBoltDia(double tankFlgBoltDia) {
        return tankFlgBoltDia;
    }

    public static double calculateHvbSupFlgW(double hvbSupFlgOD, double hvbSupID) {
        return (hvbSupFlgOD - hvbSupID) / 2;
    }

    /**
     * Calculates the Flange Thickness (hvb_Sup_Flg_Thick) of the HV Bushing.
     *
     * @param lidThick The thickness of the lid.
     * @return The Flange Thickness (hvb_Sup_Flg_Thick).
     */
    public static double calculateHvbSupFlgThick(double lidThick) {
        return lidThick;
    }


    /**
     * Determines whether the HV Cablebox is selected.
     *
     * @param isHvCableboxChecked Boolean indicating if the checkbox is selected.
     * @return true if selected, false otherwise.
     */
    public static boolean calculateHvcb(boolean isHvCableboxChecked) {
        return isHvCableboxChecked;
    }

    /**
     * Returns the vertical position of the HV Cablebox Flag Box.
     *
     * @return The constant value 100.
     */
    public static double calculateHvcbFlgBoxVpos() {
        return 100;
    }

    /**
     * Returns the front flange width of the HV Cablebox.
     *
     * @return The constant value 35.
     */
    public static double calculateHvcbFrontFlgW() {
        return 35;
    }

    /**
     * Calculates the vertical position of the HV Cablebox Front Flange.
     *
     * @param hvcbFlgBoxVpos The vertical position of the HV Cablebox Flag Box.
     * @return The same value as hvcbFlgBoxVpos.
     */
    public static double calculateHvcbFrontFlgVpos(double hvcbFlgBoxVpos) {
        return hvcbFlgBoxVpos;
    }

    /**
     * Calculates the thickness of the HV Cablebox.
     *
     * @param tankThick The thickness of the tank.
     * @return The same value as tankThick.
     */
    public static double calculateHvcbThick(double tankThick) {
        return tankThick;
    }


    // Calculate hvcb_L1
    public static double calculateHvcbL1(int bushingNos, double ph2ph, double ph2earth) {
        return ((bushingNos - 1) * ph2ph) + (2 * ph2earth);
    }

    // Calculate hvcb_L2
    public static double calculateHvcbL2(double hvcbL1) {
        return hvcbL1 * (2.0 / 3.0);
    }

    // Calculate hvcb_H1
    public static double calculateHvcbH1(double tankH) {
        return tankH * (3.0 / 4.0);
    }

    // Calculate hvcb_H2
    public static double calculateHvcbH2(double ph2earth) {
        return 2 * ph2earth;
    }

    // Calculate hvcb_H3
    public static double calculateHvcbH3(double hvcbH1, double hvcbH2) {
        return hvcbH1 - hvcbH2;
    }

    // Calculate hvcb_W1
    public static double calculateHvcbW1(double bushingL1, double ph2earth) {
        return bushingL1 + ph2earth;
    }

    // Calculate hvcb_W2
    public static double calculateHvcbW2(double hvcbW1) {
        return hvcbW1 * (2.0 / 3.0);
    }

    // Calculate hvcb_W3
    public static double calculateHvcbW3(double hvcbW1, double hvcbW2) {
        return hvcbW1 - hvcbW2;
    }

    // Calculate hvcb_Flg_Box_L
    public static double calculateHvcbFlgBoxL(double hvcbL1) {
        return hvcbL1;
    }

    // Calculate hvcb_Flg_Box_H
    public static double calculateHvcbFlgBoxH(double hvcbH2) {
        return hvcbH2;
    }

    // Calculate hvcb_Flg_Box_W
    public static double calculateHvcbFlgBoxW(double bushingL2) {
        return bushingL2;
    }

    // Calculate hvcb_Flg_Box_Thick
    public static double calculateHvcbFlgBoxThick(double hvcbThick) {
        return hvcbThick;
    }

    // Calculate hvcb_Back_Flg_W
    public static double calculateHvcbBackFlgW() {
        return 35.0; // Constant value
    }

    // Calculate hvcb_Back_Flg_Thick
    public static double calculateHvcbBackFlgThick(double tankThick) {
        return tankThick;
    }

    // Calculate hvcb_Back_Flg_Bolt_Dia
    public static double calculateHvcbBackFlgBoltDia(double tankFlgBoltDia) {
        return tankFlgBoltDia;
    }

    // Calculate hvcb_Back_Flg_Bolt_H_Nos
    public static int calculateHvcbBackFlgBoltHNos(double hvcbFlgBoxL, double hvcbBackFlgW, double hvcbBackFlgBoltDia) {
        return (int) ((hvcbFlgBoxL + (hvcbBackFlgW * 2)) / hvcbBackFlgBoltDia / 7) + 2;
    }

    // Calculate hvcb_Back_Flg_Bolt_V_Nos
    public static int calculateHvcbBackFlgBoltVNos(double hvcbFlgBoxH, double hvcbBackFlgW, double hvcbBackFlgBoltDia) {
        return (int) ((hvcbFlgBoxH + (hvcbBackFlgW * 2)) / hvcbBackFlgBoltDia / 7) + 2;
    }

    // Calculate hvcb_Back_Flg_Bolt_H_Pch
    public static int calculateHvcbBackFlgBoltHPch(double hvcbFlgBoxL, double hvcbBackFlgW, int hvcbBackFlgBoltHNos) {
        return (int) ((hvcbFlgBoxL + (hvcbBackFlgW * 2)) / (hvcbBackFlgBoltHNos - 1) + 0.99);
    }

    // Calculate hvcb_Back_Flg_Bolt_V_Pch
    public static int calculateHvcbBackFlgBoltVPch(double hvcbFlgBoxH, double hvcbBackFlgW, int hvcbBackFlgBoltVNos) {
        return (int) ((hvcbFlgBoxH + (hvcbBackFlgW * 2)) / (hvcbBackFlgBoltVNos - 1) + 0.99);
    }

    // Calculate hvcb_Front_Flg_Thick


    //CheckForEmptyHvcb_Pos
    public static String checkForEmptyHvcb_Pos(String hvcb_Pos){
        if(hvcb_Pos.isEmpty()){
            return "Tank";
        }else {
            return hvcb_Pos;
        }
    }


//    public static Hvcb calcualteHvcb(boolean hvcbFromInput, Integer tankThick, Integer voltage, Integer tank_h
//
//            , Integer tank_Flg_Bolt_Dia, Integer hvb_L1, Integer current, String hvcb_Pos) {
//
//        HvLvCommonVariables hvLvCommonVariables = HvLvCommonMethods.calcuateHvLvCommonVariables(voltage, hvcbFromInput,current);
//        Hvcb hvcb = new Hvcb();
//        hvcb.setHvcb(hvcbFromInput);
//        hvcb.setHvcb_Flg_Box_Vpos(100);
//        hvcb.setHvcb_Front_Flg_W(35);
//        hvcb.setHvcb_Front_Flg_Vpos(100);
//        hvcb.setHvcb_Thick(tankThick);
//
//        int ph2ph;
//        int ph2earth;
//        if (voltage <= 500) {
//            ph2ph = 40;
//            ph2earth = 40;
//        } else if (voltage > 500 && voltage <= 1000) {
//            ph2ph = 120;
//            ph2earth = 120;
//        } else if (voltage > 1000 && voltage <= 3600) {
//            ph2ph = 120;
//            ph2earth = 120;
//        } else if (voltage > 3600 && voltage <= 17500) {
//            if (hvcbFromInput) {
//                ph2ph = 105;
//                ph2earth = 105;
//            } else {
//                ph2ph = 280;
//                ph2earth = 140;
//            }
//        } else if (voltage > 17500 && voltage <= 24000) {
//            if (hvcbFromInput) {
//                ph2ph = 170;
//                ph2earth = 170;
//            } else {
//                ph2ph = 330;
//                ph2earth = 230;
//            }
//        } else if (voltage > 24000 && voltage <= 36000) {
//            if (hvcbFromInput) {
//                ph2ph = 200;
//                ph2earth = 200;
//            } else {
//                ph2ph = 350;
//                ph2earth = 220;
//            }
//        } else if (voltage > 36000 && voltage <= 52000) {
//            ph2ph = 530;
//            ph2earth = 480;
//        } else if (voltage > 52000 && voltage <= 725000) {
//            ph2ph = 700;
//            ph2earth = 660;
//        } else if (voltage > 725000 && voltage <= 145000) {
//            ph2ph = 1220;
//            ph2earth = 1220;
//        } else {
//            ph2ph = 1430;
//            ph2earth = 1430;
//        }
//        Double hvcb_L1=calculateHvcbL1(3,ph2ph, ph2earth );
//        hvcb_L1 = 700.0; //In the NB's drawing no. 0012, the L1 is 700mm. We have only this as an option.
//        hvcb.setHvcb_L1(hvcb_L1);
//
//        hvcb.setHvcb_L2(calculateHvcbL2(hvcb.getHvcb_L1()));
//        hvcb.setHvcb_H1(calculateHvcbH1(tank_h));
//
//        Double hvcb_H2=calculateHvcbH2(ph2earth);
//        hvcb.setHvcb_H2(hvcb_H2);
//
//        hvcb.setHvcb_H3(calculateHvcbH3(hvcb.getHvcb_H1(), hvcb.getHvcb_H2()));
//        hvcb.setHvcb_W1(calculateHvcbW1(hvb_L1, ph2earth));
//        hvcb.setHvcb_W2(calculateHvcbW2(hvcb.getHvcb_W1()));
//        hvcb.setHvcb_W3(calculateHvcbW3(hvcb.getHvcb_W1(), hvcb.getHvcb_W2()));
//        hvcb.setHvcb_Flg_Box_L(hvcb_L1);
//        hvcb.setHvcb_Flg_Box_H(hvcb_H2);
//        hvcb.setHvcb_Flg_Box_W(hvLvCommonVariables.getBushing_L2());
//        hvcb.setHvcb_Flg_Box_Thick(hvcb.getHvcb_Thick());
//        hvcb.setHvcb_Back_Flg_W(35);
//        hvcb.setHvcb_Back_Flg_Thick(tankThick);
//        hvcb.setHvcb_Back_Flg_Bolt_Dia(tank_Flg_Bolt_Dia);
//        hvcb.setHvcb_Back_Flg_Bolt_H_Nos(calculateHvcbBackFlgBoltHNos(hvcb.getHvcb_Flg_Box_L()
//            ,hvcb.getHvcb_Back_Flg_W(), hvcb.getHvcb_Back_Flg_Bolt_Dia()));
//        hvcb.setHvcb_Back_Flg_Bolt_V_Nos(calculateHvcbBackFlgBoltVNos(hvcb.getHvcb_Flg_Box_H()
//                ,hvcb.getHvcb_Back_Flg_W(), hvcb.getHvcb_Back_Flg_Bolt_Dia()));
//        hvcb.setHvcb_Back_Flg_Bolt_H_Pch(calculateHvcbBackFlgBoltHPch(hvcb.getHvcb_Flg_Box_L()
//                ,hvcb.getHvcb_Back_Flg_W(), hvcb.getHvcb_Back_Flg_Bolt_H_Nos()));
//        hvcb.setHvcb_Back_Flg_Bolt_V_Pch(calculateHvcbBackFlgBoltVPch(hvcb.getHvcb_Flg_Box_H()
//                ,hvcb.getHvcb_Back_Flg_W(), hvcb.getHvcb_Back_Flg_Bolt_V_Nos()));
//
//
//        hvcb.setHvcb_Front_Flg_Thick(tankThick);
//        hvcb.setHvcb_Front_Cvr_Thick(tankThick);
//        hvcb.setHvcb_Front_Cvr_Bolt_Dia(tank_Flg_Bolt_Dia);
//        hvcb.setHvcb_Pos(checkForEmptyHvcb_Pos(hvcb_Pos));
//
//        return hvcb;
//    }

    public static  Hvcb calculateHvcb(Boolean hvcbBool, Integer tank_H, Integer tank_L, Integer highVoltage, Integer hvb_Amp){
        Hvcb hvcb = new Hvcb();
        hvcb.setHvcb(hvcbBool);

        if(hvcbBool){
            hvcb.setHvcb_HPos(0.0);
            if(highVoltage <= 1100){
                if(hvb_Amp <= 250){
                    hvcb.setHvcb_L1(530.0);
                    hvcb.setHvcb_W1(230.0);
                    hvcb.setHvcb_Pkt_Type(2);
                    hvcb.setHvcb_Type(13);
                    hvcb.setHvcb_CutOutL(450);
                    hvcb.setHvcb_CutOutW(150);
                }
                else  if(hvb_Amp <= 630){
                    hvcb.setHvcb_L1(670.0);
                    hvcb.setHvcb_W1(330.0);
                    hvcb.setHvcb_Pkt_Type(3);
                    hvcb.setHvcb_Type(14);
                    hvcb.setHvcb_CutOutL(580);
                    hvcb.setHvcb_CutOutW(240);
                }
                else  if(hvb_Amp <= 1000){
                    hvcb.setHvcb_L1(670.0);
                    hvcb.setHvcb_W1(330.0);
                    hvcb.setHvcb_Pkt_Type(4);
                    hvcb.setHvcb_Type(15);
                    hvcb.setHvcb_CutOutL(580);
                    hvcb.setHvcb_CutOutW(240);
                }
                else  if(hvb_Amp <= 2000){
                    hvcb.setHvcb_L1(810.0);
                    hvcb.setHvcb_W1(380.0);
                    hvcb.setHvcb_Pkt_Type(5);
                    hvcb.setHvcb_Type(16);
                    hvcb.setHvcb_CutOutL(720);
                    hvcb.setHvcb_CutOutW(290);
                }
                else  if(hvb_Amp <= 3150){
                    hvcb.setHvcb_L1(980.0);
                    hvcb.setHvcb_W1(430.0);
                    hvcb.setHvcb_Pkt_Type(6);
                    hvcb.setHvcb_Type(17);
                    hvcb.setHvcb_CutOutL(890);
                    hvcb.setHvcb_CutOutW(340);
                } else{
                    hvcb.setHvcb_L1(840.0);
                    hvcb.setHvcb_W1(380.0);
                    hvcb.setHvcb_Pkt_Type(11);
                    hvcb.setHvcb_Type(18);
                    hvcb.setHvcb_CutOutL(900);
                    hvcb.setHvcb_CutOutW(280);
                }
            }
            else if(highVoltage <= 17500){
                if(hvb_Amp <= 250){
                    if(tank_L < 700){
                        hvcb.setHvcb_CutOutL(450);
                        hvcb.setHvcb_CutOutW(250);
                        hvcb.setHvcb_Pkt_Type(156);
                        hvcb.setHvcb_Type(159);

                    } else{
                        hvcb.setHvcb_CutOutL(610);
                        hvcb.setHvcb_CutOutW(240);
                        hvcb.setHvcb_Pkt_Type(1);
                        hvcb.setHvcb_Type(12);
                    }
                    hvcb.setHvcb_L1(700.0);
                    hvcb.setHvcb_W1(380.0);
                }
            } else if(highVoltage <= 36000){
                if(hvb_Amp <= 250){
                    if(tank_L <= 1100){
                        hvcb.setHvcb_L1(1420.0);
                        hvcb.setHvcb_W1(596.0);
                        hvcb.setHvcb_Pkt_Type(141);
                        hvcb.setHvcb_Type(139);
                        hvcb.setHvcb_CutOutL(750);
                        hvcb.setHvcb_CutOutW(150);

                    }else{
                        hvcb.setHvcb_L1(1420.0);
                        hvcb.setHvcb_W1(596.0);
                        hvcb.setHvcb_Pkt_Type(140);
                        hvcb.setHvcb_Type(138);
                        hvcb.setHvcb_CutOutL(1070);
                        hvcb.setHvcb_CutOutW(250);

                    }
                }
            }
            hvcb.setHvcb_VPos(((double) tank_H /2 - 60 - (hvcb.getHvcb_CutOutW()/2)));
        } else{
            hvcb.setHvcb_L1(0.0);
            hvcb.setHvcb_W1(0.0);
            hvcb.setHvcb_Pkt_Type(0);
            hvcb.setHvcb_Type(0);
            hvcb.setHvcb_CutOutL(0);
            hvcb.setHvcb_CutOutW(0);
            hvcb.setHvcb_HPos(0.0);
            hvcb.setHvcb_VPos(0.0);

        }

        return hvcb;
    }
}

