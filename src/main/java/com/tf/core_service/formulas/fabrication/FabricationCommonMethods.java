package com.tf.core_service.formulas.fabrication;


public class FabricationCommonMethods {

    public static String calculateBot_Chnl_Sec_Data(Integer kVA){
        if (kVA <= 500) {
            return "75x45x5.3-C";
        } else if (kVA <= 630) {
            return "125x65x5.3-C";
        } else if (kVA <= 6300) {
            return "150x75x5.7-C";
        } else if (kVA <= 12500) {
            return "200x75x6.2-C";
        } else if (kVA <= 31500) {
            return "250x80x8.8-C";
        } else {
            return "250x80x8.8-C";
        }
    }

    public static Integer calculateBot_Chnl_Sec_W2(String bot_Chnl_Sec_Data){
        String num = bot_Chnl_Sec_Data.split("[^0-9\\.]+")[1];
        return Integer.parseInt(num);
    }

    public static Double calculateBot_Chnl_Pitch3(Integer roller_Dia, Integer bot_Chnl_Sec_W2){
        return (double) roller_Dia + (roller_Dia / 4) + bot_Chnl_Sec_W2;
    }


}