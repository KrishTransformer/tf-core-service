
package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Roller;

public class RollerFormulas {
    // Roller_Guage Calculation
    public static Double calculationRoller_Guage(Integer tank_L){
        if ((tank_L * (2.0/ 3.0))> 1676.4) {
            return 1676.4;
        }

        else return (double) ((((2 * (tank_L / 3) + 10) / 10)) * 10);
    }

    public static Double calculateRollerShaftL(String foundation_Type, Double bot_Chnl_Pitch3,
                            Integer bot_Chnl_Sec_W2, Integer roller_Shaft_Dia, Integer roller_Sup_L) {
        Double roller_Shaft_L;
        if ("type-4".equals(foundation_Type)) {
            roller_Shaft_L = Math.floor(bot_Chnl_Pitch3 - bot_Chnl_Sec_W2 + (roller_Shaft_Dia / 3));
        } else {
            roller_Shaft_L = Math.floor(roller_Sup_L + (roller_Shaft_Dia / 3));
        }
        return roller_Shaft_L;
    }

    // roller_Shaft_Vpos calculation
    public static double calculateRollerShaftVpos(String roller_Type, String foundation_Type,
                                                  Integer roller_Sup_Thick, Integer roller_Flg_Dia, Integer bot_Chnl_Sec_W2, Integer roller_Shaft_Dia) {
        double roller_Shaft_Vpos;
        if ("flanged_roller".equals(roller_Type)) {
            if ("type-4".equals(foundation_Type)) {
                roller_Shaft_Vpos = bot_Chnl_Sec_W2 / 2;
            } else {
                roller_Shaft_Vpos = (roller_Sup_Thick * 3) + (roller_Flg_Dia / 2);
            }
        } else {
            if ("type-4".equals(foundation_Type)) {
                roller_Shaft_Vpos = bot_Chnl_Sec_W2 / 2;
            } else {
                roller_Shaft_Vpos = (roller_Sup_Thick * 3) + (roller_Shaft_Dia / 2);
            }
        }
        return roller_Shaft_Vpos;
    }

    // roller_Sup_L calculation
    public static Integer calculateRollerSupL(Integer roller_Hub_L, Integer roller_Sup_Thick) {
        return roller_Hub_L + (4 * roller_Sup_Thick);
    }

    // roller_Sup_W calculation
    public static Integer calculateRollerSupW(Integer roller_Shaft_Dia) {
        return roller_Shaft_Dia * 2;
    }

    // roller_Sup_H calculation
    public static Double calculateRollerSupH(Double rollerShaftVpos, Integer roller_Shaft_Dia) {
        return rollerShaftVpos + roller_Shaft_Dia;
    }

    public static String calculateFoundation_Type(Integer kVA, Boolean roller){
        if(kVA<=500){
            if(roller) return "type-1";
            else return "type-2";
        }
        else if(kVA<=2000) return "type-3";
        else return "type-4";
    }

    public static String checkRoller_Type(String rollerType) {
        if(rollerType.isEmpty()){
            return "plain_roller";
        }else {
            return rollerType;
        }
    }




    public static Roller calculateRoller(Integer kVA, Integer transformerWeight, Integer tank_L, Integer tank_Thick, Boolean rollerfrominput, String rollerType) {
        Roller roller = new Roller();

        Integer rollerDia, rollerThick, rollerShaftDia, rollerHubDia, rollerHubL, rollerFlgDia,
                rollerFlgThick;


        if (transformerWeight <= 800) {
            rollerDia = 100;
            rollerThick = 40;
            rollerShaftDia = 21;
            rollerHubDia = 45;
            rollerHubL = 50;
            rollerFlgDia = 130;
            rollerFlgThick = 10;
        } else if (transformerWeight <= 1500) {
            rollerDia = 150;
            rollerThick = 50;
            rollerShaftDia = 32;
            rollerHubDia = 65;
            rollerHubL = 60;
            rollerFlgDia = 180;
            rollerFlgThick = 10;
        } else if (transformerWeight <= 4000) {
            rollerDia = 200;
            rollerThick = 80;
            rollerShaftDia = 50;
            rollerHubDia = 95;
            rollerHubL = 90;
            rollerFlgDia = 230;
            rollerFlgThick = 15;
        } else if (transformerWeight <= 6000) {
            rollerDia = 250;
            rollerThick = 80;
            rollerShaftDia = 75;
            rollerHubDia = 125;
            rollerHubL = 120;
            rollerFlgDia = 280;
            rollerFlgThick = 15;
        } else if (transformerWeight <= 8000) {
            rollerDia = 250;
            rollerThick = 80;
            rollerShaftDia = 75;
            rollerHubDia = 125;
            rollerHubL = 120;
            rollerFlgDia = 280;
            rollerFlgThick = 15;
        } else if (transformerWeight <= 15000) {
            rollerDia = 300;
            rollerThick = 80;
            rollerShaftDia = 75;
            rollerHubDia = 155;
            rollerHubL = 150;
            rollerFlgDia = 330;
            rollerFlgThick = 15;
        } else {
            rollerDia = 300;
            rollerThick = 80;
            rollerShaftDia = 75;
            rollerHubDia = 155;
            rollerHubL = 150;
            rollerFlgDia = 330;
            rollerFlgThick = 15;
        }
        roller.setRoller(rollerfrominput);
        roller.setFoundation_Type(calculateFoundation_Type(kVA, roller.isRoller()));

        String roller_Type=checkRoller_Type(rollerType);
        roller.setRoller_Type(roller_Type);

        roller.setRoller_Dia(rollerDia);
        roller.setRoller_Thick(rollerThick);
        roller.setRoller_Shaft_Dia(rollerShaftDia);
        roller.setRoller_Hub_Dia(rollerHubDia);
        roller.setRoller_Hub_L(rollerHubL);
        roller.setRoller_Flg_Dia(rollerFlgDia);
        roller.setRoller_Flg_Thick(rollerFlgThick);
        roller.setRoller_Sup_Thick(tank_Thick);
        roller.setRoller_Sup_L(calculateRollerSupL(rollerHubL, roller.getRoller_Sup_Thick()));


        String bot_Chnl_Sec_Data=FabricationCommonMethods.calculateBot_Chnl_Sec_Data(kVA);
        Integer bot_Chnl_Sec_W2=FabricationCommonMethods.calculateBot_Chnl_Sec_W2(bot_Chnl_Sec_Data);
        Double bot_Chnl_Pitch3=FabricationCommonMethods.calculateBot_Chnl_Pitch3(rollerDia,bot_Chnl_Sec_W2);


        roller.setRoller_Shaft_L(calculateRollerShaftL(roller.getFoundation_Type(),
                bot_Chnl_Pitch3, bot_Chnl_Sec_W2,
                rollerShaftDia, roller.getRoller_Sup_L()
                ));
        roller.setRoller_Shaft_Vpos(calculateRollerShaftVpos(roller.getRoller_Type(), roller.getFoundation_Type()
        , roller.getRoller_Sup_Thick(), rollerFlgDia, bot_Chnl_Sec_W2, rollerShaftDia));
        roller.setRoller_Sup_W(calculateRollerSupW(rollerShaftDia));
        roller.setRoller_Sup_H(calculateRollerSupH(roller.getRoller_Shaft_Vpos(), rollerShaftDia));

        roller.setRoller_Guage(calculationRoller_Guage(tank_L));
        return roller;
    }


}
