package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.BotChnl;

public class BotChnFormulas {

    // Bot_Chnl_Pitch1 Calculation
    public static Double calculateBot_Chnl_Pitch1(String roller_Type, Double roller_Guage,
                                               Integer roller_Thick, Integer roller_Flg_Thick)
    {
        if( roller_Type.equals("flange roller")){
            return (roller_Guage + roller_Thick - (2 * roller_Flg_Thick));
        }else {
            return roller_Guage;
        }
    }

    // Bot_Chnl_Pitch2 Calculation
    public static Double calculateBot_Chnl_Pitch2(Integer tank_W){
        return (double) (tank_W/2);
    }

    // Bot_Chnl_Sec_Data Calculation


    // Bot_Chnl_Sec_W1 Calculation
    public static Integer calculateBot_Chnl_Sec_W1(String bot_Chnl_Sec_Data){
        String num = bot_Chnl_Sec_Data.split("[^0-9\\.]+")[0];
        return Integer.parseInt(num);
    }

    // Bot_Chnl_Sec_W2 Calculation


    // Bot_Chnl_Sec_Thick Calculation
    public static Double calculateBot_Chnl_Sec_Thick(String bot_Chnl_Sec_Data){
        String num = bot_Chnl_Sec_Data.split("[^0-9\\.]+")[2];
        return Double.parseDouble(num);
    }

    // Bot_Chnl_Pitch3 Calculation


    // Bot_Chnl_L2 Calculation
    public static Integer calculateBot_Chnl_L2(Integer tank_Bot_L, Integer bot_Chnl_Sec_W1){
        return tank_Bot_L + (bot_Chnl_Sec_W1 * 5);
    }

    // Bot_Chnl_L3 Calculation
    public static Double calculateBot_Chnl_L3(Double bot_Chnl_Pitch3, Integer bot_Chnl_Sec_W2){
        return bot_Chnl_Pitch3 - bot_Chnl_Sec_W2;
    }

    // Bot_Chnl_L1 Calculation
    public static Double calculateBot_Chnl_L1(String foundation_Type, Double bot_Chnl_Pitch5,
                                              Double bot_Chnl_Pitch4, Integer bot_Chnl_Sec_W2,
                                              Integer bot_Chnl_Sec_W1, Double bot_Chnl_Pitch1,
                                              Integer roller_Dia){
        if(foundation_Type.equals("type-4"))
            return bot_Chnl_Pitch5 + bot_Chnl_Pitch4 + bot_Chnl_Sec_W2 + (bot_Chnl_Sec_W1 * 2);
        else return bot_Chnl_Pitch1 + roller_Dia;
    }

//    public static BotChn1 calculateBotChn(Integer kVA, String roller_Type,
//                                          Double roller_Guage, Integer roller_Thick,
//                                          Integer roller_Flg_Thick, Integer tank_W,
//                                          Integer roller_Dia, Integer tank_Bot_L,
//                                          String foundation_Type) {
//        BotChn1 botChn1 = new BotChn1();
//
//        Double bot_Chnl_Pitch1=calculateBot_Chnl_Pitch1(roller_Type,
//                roller_Guage,
//                roller_Thick,
//                roller_Flg_Thick
//        );
//        Double bot_Chnl_Pitch5=bot_Chnl_Pitch1;
//
//        botChn1.setBot_Chnl_Pitch1(bot_Chnl_Pitch1);
//        botChn1.setBot_Chnl_Pitch2(calculateBot_Chnl_Pitch2(tank_W));
//        botChn1.setBot_Chnl_Pitch5(bot_Chnl_Pitch5);
//
//        String bot_Chnl_Sec_Data=FabricationCommonMethods.calculateBot_Chnl_Sec_Data(kVA);
//        botChn1.setBot_Chnl_Sec_Data(bot_Chnl_Sec_Data);
//
//        Integer bot_Chnl_Sec_W1=calculateBot_Chnl_Sec_W1(bot_Chnl_Sec_Data);
//        botChn1.setBot_Chnl_Sec_W1(bot_Chnl_Sec_W1);
//
//        Integer bot_Chnl_Sec_W2=FabricationCommonMethods.calculateBot_Chnl_Sec_W2(bot_Chnl_Sec_Data);
//        botChn1.setBot_Chnl_Sec_W2(bot_Chnl_Sec_W2);
//
//        botChn1.setBot_Chnl_Sec_Thick(calculateBot_Chnl_Sec_Thick(bot_Chnl_Sec_Data));
//
//        Double bot_Chnl_Pitch3=FabricationCommonMethods.calculateBot_Chnl_Pitch3(roller_Dia,bot_Chnl_Sec_W2);
//        botChn1.setBot_Chnl_Pitch3(bot_Chnl_Pitch3);
//
//        Double bot_Chnl_Pitch4=bot_Chnl_Pitch3;
//        botChn1.setBot_Chnl_Pitch4(bot_Chnl_Pitch4);
//
//        botChn1.setBot_Chnl_L2(calculateBot_Chnl_L2(tank_Bot_L,bot_Chnl_Sec_W1));
//        botChn1.setBot_Chnl_L3(calculateBot_Chnl_L3(bot_Chnl_Pitch3,bot_Chnl_Sec_W2));
//        botChn1.setBot_Chnl_L1(calculateBot_Chnl_L1(foundation_Type,
//                bot_Chnl_Pitch5,
//                bot_Chnl_Pitch4,
//                bot_Chnl_Sec_W2,
//                bot_Chnl_Sec_W1,
//                bot_Chnl_Pitch1,
//                roller_Dia
//                ));
//
//        return botChn1;
//    }

    ///Formulas for NB's begin from here

    public static BotChnl calculateBotChnl(Integer KVA, Boolean isRoller, Integer tank_L, Integer tank_W, Integer tank_Thk, Integer ver_Stiff_Wdt){
        BotChnl botChnl = new BotChnl();

        //Finding the typeNo
        int typeNo = 0;
        if(isRoller){
            if(KVA <= 500){
                typeNo =  86;
            } else if (KVA <= 1600) {
                typeNo =  87;
            } else if (KVA <= 5000) {
                typeNo =  88;
            }
            else typeNo =  88;
        }
        else {
            if(KVA <= 315){
                typeNo =  89;
            } else if (KVA <= 500) {
                typeNo =  90;
            } else if (KVA <= 2000) {
                typeNo =  91;
            } else if (KVA <= 5000) {
                typeNo =  92;
            }
            else typeNo =  92;
        }


        //Finding Hole center Distance

        int clearance = 0;
        if(KVA <= 500){clearance = 50;}
        else if (KVA <= 1600){clearance = 110;}
        else if (KVA <= 5000){clearance = 130;}
        else {clearance = 130;}

        int holeCentDist = 0;

        if(typeNo == 86 || typeNo == 87 || typeNo == 88){
            holeCentDist = tank_W + (tank_Thk * 2) + (2 * ver_Stiff_Wdt) + clearance;
        }else if (typeNo == 89 || typeNo == 90) {
            holeCentDist = 415;
        }else if (typeNo == 91 || typeNo == 92) {
            holeCentDist = tank_W;
        }else holeCentDist = tank_W;

        int channelLen = 0;
        if(typeNo == 86){channelLen = holeCentDist + 200;}
        else if(typeNo == 87){channelLen = holeCentDist + 270;}
        else if(typeNo == 88){channelLen = holeCentDist + 280;}
        else if(typeNo == 89){channelLen = tank_W + (tank_Thk * 2) + (2 * ver_Stiff_Wdt) + 150;}
        else if(typeNo == 90){channelLen = tank_W + (tank_Thk * 2) + (2 * ver_Stiff_Wdt) + 200;}
        else if(typeNo == 91){channelLen = tank_W + (tank_Thk * 2) + (2 * ver_Stiff_Wdt) + 250;}
        else if(typeNo == 92){channelLen = tank_W + (tank_Thk * 2) + (2 * ver_Stiff_Wdt) + 300;}
        else{channelLen = tank_W + (tank_Thk * 2) + (2 * ver_Stiff_Wdt) + 300;}

        double pitch = (double) tank_L /2;

        double hPos = (double) tank_L /4;

        double vPos = (double) channelLen/2;

        Integer rollerDia = 0;
        if(typeNo == 86){rollerDia = 100;}
        else if(typeNo == 87){rollerDia = 150;}
        else if(typeNo == 88){rollerDia = 200;}
        else rollerDia = 200;

        botChnl.setIsRoller(isRoller);
        botChnl.setBot_Chnl_Type(typeNo);
        botChnl.setBot_Chnl_Hole_Cen_Dist(holeCentDist);
        botChnl.setBot_Chnl_Len(channelLen);
        botChnl.setBot_Chnl_Pitch(pitch);
        botChnl.setBot_Chnl_Hpos(hPos);
        botChnl.setBot_Chnl_Vpos(vPos);
        botChnl.setBot_Chnl_Roller_Dia(rollerDia);

        return botChnl;
    }
}
