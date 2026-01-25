package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Lvcb;
import com.tf.core_service.utils.NumberFormattingUtils;

public class LVCBForumulas {

    public static double calculateLvcbL1(double bushingNos, Integer ph2ph, Integer ph2earth) {
        return ((bushingNos - 1) * ph2ph) + (2 * ph2earth);
    }

    public static double calculateLvcbL2(double lvcb_L1) {
        return NumberFormattingUtils.twoDigitDecimal(((double) 2 /3) * lvcb_L1);
    }

    public static double calculateLvcbH1(double tank_H) {
        return tank_H * ((double) 3 / 4);
    }

    public static double calculateLvcbH2(double ph2earth) {
        return 2 * ph2earth;
    }

    public static double calculateLvcbW1(double Bushing_L1, Integer ph2earth) {
        return Bushing_L1 + ph2earth;
    }

    public static double calculateLvcbW2(double lvcbW1) {
        return ((double) 2 /3) * lvcbW1;
    }

    public static int calculateLvcbBackFlgBoltHNos(double lvcbFlgBoxL, double lvcbBackFlgW, double lvcbBackFlgBoltDia) {
        return (int) ((lvcbFlgBoxL + (lvcbBackFlgW * 2)) / lvcbBackFlgBoltDia / 7) + 2;
    }

    // Calculate hvcb_Back_Flg_Bolt_V_Nos
    public static int calculateLvcbBackFlgBoltVNos(double lvcbFlgBoxH, double lvcbBackFlgW, double lvcbBackFlgBoltDia) {
        return (int) ((lvcbFlgBoxH + (lvcbBackFlgW * 2)) / lvcbBackFlgBoltDia / 7) + 2;
    }

    // Calculate hvcb_Back_Flg_Bolt_H_Pch
    public static int calculateLvcbBackFlgBoltHPch(double lvcbFlgBoxL, double lvcbBackFlgW, int lvcbBackFlgBoltHNos) {
        return (int) ((lvcbFlgBoxL + (lvcbBackFlgW * 2)) / (lvcbBackFlgBoltHNos - 1) + 0.99);
    }

    // Calculate hvcb_Back_Flg_Bolt_V_Pch
    public static int calculateLvcbBackFlgBoltVPch(double lvcbFlgBoxH, double lvcbBackFlgW, int lvcbBackFlgBoltVNos) {
        return (int) ((lvcbFlgBoxH + (lvcbBackFlgW * 2)) / (lvcbBackFlgBoltVNos - 1) + 0.99);
    }

    // Calculate hvcb_Front_Flg_Thick
//    public static Lvcb calcualteLvcb(boolean lvcbFromInput, Integer tank_Thick, Integer lowVoltage, Integer tank_H
//
//    , Integer tank_Flg_Bolt_Dia, Integer hvb_L1, Integer hvb_L2,Integer current) {
//
//
//        HvLvCommonVariables hvLvCommonVariables = HvLvCommonMethods.calcuateHvLvCommonVariables(lowVoltage, lvcbFromInput, current);
//
//        Lvcb lvcb = new Lvcb();
//        lvcb.setLvcb(lvcbFromInput);
//        lvcb.setLvcb_Front_Flg_Vpos(100);
//        lvcb.setLvcb_Thick(tank_Thick);
//
//        lvcb.setLvcb_L1(calculateLvcbL1(4, hvLvCommonVariables.getPh2ph(), hvLvCommonVariables.getPh2earth()));
//        lvcb.setLvcb_L2(calculateLvcbL2(lvcb.getLvcb_L1()));
//        lvcb.setLvcb_H1(calculateLvcbH1(tank_H));
//        lvcb.setLvcb_H2(calculateLvcbH2(hvLvCommonVariables.getPh2earth()));
//        lvcb.setLvcb_H3(lvcb.getLvcb_H1() - lvcb.getLvcb_H2());
//        lvcb.setLvcb_W1(calculateLvcbW1(hvb_L1, hvLvCommonVariables.getPh2earth()));
//        lvcb.setLvcb_W2(calculateLvcbW2(lvcb.getLvcb_W1()));
//        lvcb.setLvcb_W3(lvcb.getLvcb_W1() - lvcb.getLvcb_W2());
//
//        lvcb.setLvcb_Flg_Box_L(lvcb.getLvcb_L1());
//        lvcb.setLvcb_Flg_Box_H(lvcb.getLvcb_H2());
//        lvcb.setLvcb_Flg_Box_W(hvb_L2);
//        lvcb.setLvcb_Flg_Box_Thick(lvcb.getLvcb_Thick());
//        lvcb.setLvcb_Back_Flg_W(35);
//        lvcb.setLvcb_Back_Flg_Thick(tank_Thick);
//        lvcb.setLvcb_Back_Flg_Bolt_Dia(tank_Flg_Bolt_Dia);
//        lvcb.setLvcb_Back_Flg_Bolt_H_Nos(calculateLvcbBackFlgBoltHNos(lvcb.getLvcb_Flg_Box_L()
//                , lvcb.getLvcb_Back_Flg_W(), lvcb.getLvcb_Back_Flg_Bolt_Dia()));
//        lvcb.setLvcb_Back_Flg_Bolt_V_Nos(calculateLvcbBackFlgBoltVNos(lvcb.getLvcb_Flg_Box_H()
//                , lvcb.getLvcb_Back_Flg_W(), lvcb.getLvcb_Back_Flg_Bolt_Dia()));
//        lvcb.setLvcb_Back_Flg_Bolt_H_Pch(calculateLvcbBackFlgBoltHPch(lvcb.getLvcb_Flg_Box_L()
//                , lvcb.getLvcb_Back_Flg_W(), lvcb.getLvcb_Back_Flg_Bolt_H_Nos()));
//        lvcb.setLvcb_Back_Flg_Bolt_V_Pch(calculateLvcbBackFlgBoltVPch(lvcb.getLvcb_Flg_Box_L()
//                , lvcb.getLvcb_Back_Flg_W(), lvcb.getLvcb_Back_Flg_Bolt_V_Nos()));
//
//        lvcb.setLvcb_Front_Flg_W(35);
//        lvcb.setLvcb_Front_Flg_Thick(tank_Thick);
//        lvcb.setLvcb_Flg_Box_Vpos(100);
//        lvcb.setLvcb_Front_Cvr_Thick(tank_Thick);
//        lvcb.setLvcb_Front_Cvr_Bolt_Dia(tank_Flg_Bolt_Dia);
//        return lvcb;
//    }

    public static Lvcb calculateLvcb(Boolean lvcbBoolean, Integer tank_H, Integer lvb_Volt, Integer lvb_Amp, Integer lowVoltage, Integer tank_L){
        Lvcb lvcb = new Lvcb();
        lvcb.setLvcb_HPos(0);
        lvcb.setLvcb(lvcbBoolean);

        if(lvcbBoolean){
            if(lowVoltage <= 1100){
                if(lvb_Amp <= 250){
                    lvcb.setLvcb_L1(530.0);
                    lvcb.setLvcb_W1(230.0);
                    lvcb.setLvcb_Pkt_Type(2);
                    lvcb.setLvcb_Type(13);
                    lvcb.setLvcb_CutOutL(450);
                    lvcb.setLvcb_CutOutW(150);
                }
                else  if(lvb_Amp <= 630){
                    lvcb.setLvcb_L1(670.0);
                    lvcb.setLvcb_W1(330.0);
                    lvcb.setLvcb_Pkt_Type(3);
                    lvcb.setLvcb_Type(14);
                    lvcb.setLvcb_CutOutL(580);
                    lvcb.setLvcb_CutOutW(240);
                }
                else  if(lvb_Amp <= 1000){
                    lvcb.setLvcb_L1(670.0);
                    lvcb.setLvcb_W1(330.0);
                    lvcb.setLvcb_Pkt_Type(4);
                    lvcb.setLvcb_Type(15);
                    lvcb.setLvcb_CutOutL(580);
                    lvcb.setLvcb_CutOutW(240);
                }
                else  if(lvb_Amp <= 2000){
                    lvcb.setLvcb_L1(810.0);
                    lvcb.setLvcb_W1(380.0);
                    lvcb.setLvcb_Pkt_Type(5);
                    lvcb.setLvcb_Type(16);
                    lvcb.setLvcb_CutOutL(720);
                    lvcb.setLvcb_CutOutW(290);
                }
                else  if(lvb_Amp <= 3150){
                    lvcb.setLvcb_L1(980.0);
                    lvcb.setLvcb_W1(430.0);
                    lvcb.setLvcb_Pkt_Type(6);
                    lvcb.setLvcb_Type(17);
                    lvcb.setLvcb_CutOutL(890);
                    lvcb.setLvcb_CutOutW(340);
                } else{
                    lvcb.setLvcb_L1(840.0);
                    lvcb.setLvcb_W1(380.0);
                    lvcb.setLvcb_Pkt_Type(11);
                    lvcb.setLvcb_Type(18);
                    lvcb.setLvcb_CutOutL(900);
                    lvcb.setLvcb_CutOutW(280);
                }

            }
            else if(lowVoltage <= 17500){
                if(lvb_Amp <= 250){
                    if(tank_L < 700){
                        lvcb.setLvcb_CutOutL(450);
                        lvcb.setLvcb_CutOutW(250);
                        lvcb.setLvcb_Pkt_Type(156);
                        lvcb.setLvcb_Type(159);

                    } else{
                        lvcb.setLvcb_CutOutL(610);
                        lvcb.setLvcb_CutOutW(240);
                        lvcb.setLvcb_Pkt_Type(1);
                        lvcb.setLvcb_Type(12);
                    }
                    lvcb.setLvcb_L1(700.0);
                    lvcb.setLvcb_W1(380.0);
                }else {
                    lvcb.setLvcb_CutOutL(610);
                    lvcb.setLvcb_CutOutW(240);
                    lvcb.setLvcb_Pkt_Type(1);
                    lvcb.setLvcb_Type(12);
                    lvcb.setLvcb_L1(700.0);
                    lvcb.setLvcb_W1(380.0);

                }
            } else if(lowVoltage <= 36000){
                if(lvb_Amp <= 250){
                    if(tank_L <= 1100){
                        lvcb.setLvcb_L1(1420.0);
                        lvcb.setLvcb_W1(596.0);
                        lvcb.setLvcb_Pkt_Type(141);
                        lvcb.setLvcb_Type(139);
                        lvcb.setLvcb_CutOutL(750);
                        lvcb.setLvcb_CutOutW(150);

                    }else{
                        lvcb.setLvcb_L1(1420.0);
                        lvcb.setLvcb_W1(596.0);
                        lvcb.setLvcb_Pkt_Type(140);
                        lvcb.setLvcb_Type(138);
                        lvcb.setLvcb_CutOutL(1070);
                        lvcb.setLvcb_CutOutW(250);

                    }
                }
            }
           lvcb.setLvcb_VPos(tank_H/2 - 60 - lvcb.getLvcb_CutOutW()/2);
        }
        else{
            lvcb.setLvcb_L1(0.0);
            lvcb.setLvcb_W1(0.0);
            lvcb.setLvcb_Pkt_Type(0);
            lvcb.setLvcb_Type(0);
            lvcb.setLvcb_CutOutL(0);
            lvcb.setLvcb_CutOutW(0);
            lvcb.setLvcb_VPos(0);
        }

        return lvcb;
    }

}

