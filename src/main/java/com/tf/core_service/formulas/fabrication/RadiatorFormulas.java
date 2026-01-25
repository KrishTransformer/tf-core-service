package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Radiator;

import java.util.Objects;

public class RadiatorFormulas {


    private static boolean getRadiatorGNeck(Integer radiator_CC, Integer radiator_Vpos
            , Integer radiator_Min_Bottom_Clearence, Integer tank_H, Integer radiator_Head_Dia) {
        if (radiator_CC + radiator_Vpos + radiator_Min_Bottom_Clearence - (radiator_Head_Dia/2) > tank_H) {
            return true;
        } else {
            return false;
        }
    }

    private static Integer getRadiatorCCOnTank(Integer tankH, Integer radiatorVpos
            , Integer radiatorMinBottomClearence, Integer radiatorCC, boolean radiatorGneck) {
        // Check if radiator_Gneck is true
        if (radiatorGneck) {
            // Calculate radiator_CC_onTank using the formula for Gneck being true
            return tankH - radiatorVpos - radiatorMinBottomClearence;
        } else {
            // If radiator_Gneck is false, use the radiatorCC value
            return radiatorCC;
        }
    }

    // Method to calculate Radiator Offset Distance
    private static Double getRadiatorOffsetDist(Integer radiatorW, Integer radiatorHeadDia) {
        return (double) ((radiatorW / 2) - radiatorHeadDia);
    }


    // Function to calculate radiator_Head_L
    private static Integer calculateRadiatorHeadL(Integer radiator_Fin1_Pos, Integer radiator_Fin_Pitch, Integer radiator_Fin_Nos) {
        return radiator_Fin1_Pos + (radiator_Fin_Pitch * (radiator_Fin_Nos - 1)) + 25; // 25mm is extra projection
    }



    // Method to calculate Radiator Fin Pitch
    private static double getRadiatorFPitch(Double tankL, Double radiatorW, Integer radiatorFlNos, Double radiatorMinGap, String radPitchCalcMode, boolean hvcb) {
        if ("auto".equalsIgnoreCase(radPitchCalcMode) && !hvcb) {
            Double calculatedPitch = (tankL - radiatorW) / (radiatorFlNos - 1);
            return Math.max(calculatedPitch, radiatorMinGap + radiatorW);
        }
        return radiatorMinGap + radiatorW;

    }


    private static double calculateRadiatorVpos(double initialPosition, double offset) {
        // Calculate the vertical position of the radiator
        return initialPosition + offset;
    }

    private static String calculateRadiatorFType(Integer psrValue, boolean radiatorGneck, String tankH, String radiatorVpos, String radiatorMinBottomClearence, String radiatorType, String userSelection) {
        // Get PSR value based on user selection
//        String psrValue = getPSRValue(userSelection);

        // Logic to calculate radiator_F_Type based on radiator_Gneck condition
        if (radiatorGneck) {
            // Convert the strings to doubles for calculation
            double tankHValue = Double.parseDouble(tankH);
            double radiatorVposValue = Double.parseDouble(radiatorVpos);
            double radiatorMinBottomClearenceValue = Double.parseDouble(radiatorMinBottomClearence);

            // Calculate the radiator_F_Type using the formula
            double radiatorFType = tankHValue - radiatorVposValue - radiatorMinBottomClearenceValue;

            // Return the calculated value with PSR logic
            return String.valueOf(radiatorFType) + " (" + psrValue + ")";
        } else {
            // If radiator_Gneck is false, use radiator_Type and append PSR value
            return radiatorType + " (" + psrValue + ")";
        }
    }


    // Method to calculate Radiator B Type
    private static String calculateRadiatorBType(String psrValue, String radiatorType, String userSelection) {

        // Logic to assign radiator_B_Type value to radiator_Type and append the PSR value
        return radiatorType + " (" + psrValue + ")";
    }

    private static double calculateRadiatorPosition(double radiatorMinBottomClearence, double radiatorVpos) {
        // Return the sum of radiator_Min_Bottom_Clearence and radiator_Vpos
        return radiatorMinBottomClearence + radiatorVpos;
    }


    private static String calculateRadPtchCalcMode(boolean isAutoSelected) {
        // If 'auto' is selected, return 'auto', otherwise return 'manual'
        return isAutoSelected ? "auto" : "manual";
    }

    // Function to calculate radiator_Fin_Pitch
    private static double getRadiatorFinPitch(boolean isISStandard, double defaultPitch) {
        // Return 50 if IS standard applies; otherwise, return the default value
        return isISStandard ? 50.0 : defaultPitch;
    }

    // Function to get the radiator_Fin1_Pos value
    private static int getRadiatorFin1Pos() {
        // Return hardcoded value for radiator_Fin1_Pos
        return 100;
    }

    // Function to get the radiator_Fin_Thick value
    private static int getRadiatorFinThick() {
        // Return hardcoded value for radiator_Fin_Thick
        return 15;
    }

    private static int calculateRadiatorFLNos(int radiatorNos, int radiatorFRNos, boolean hvcb,
                                              int radiatorBLNos, int radiatorBRNos ) {
        // Check if the design is with a cable box ("lvcb")
        if (hvcb) {
            int tempCount1 = radiatorNos - (radiatorBLNos + radiatorBRNos);;
            return tempCount1 - radiatorFRNos;
        } else {
            return radiatorNos - (radiatorBLNos + radiatorBRNos);
        }
    }

    private static int calculateRadiatorFRNos(boolean hvcb, int radiatorNos, int radiatorBLNos, int radiatorBRNos) {
        // If hvcb is true, calculate radiator_FR_Nos
        if (hvcb) {
            int tempCount2 = radiatorNos - (radiatorBLNos + radiatorBRNos);
            return (int) Math.round(tempCount2 / 2.0);
        }
        return 0;
    }

    private static int calculateRadiatorBLNos(boolean lvcb, int radiatorNos, int radiatorBRNos) {
        // Default split (Without Cable Box)
        if (!lvcb) {
            return (int) (Math.round(radiatorNos / 2.0) + 0.5);
        } else {
            // With Cable Box
            int tempCount1 = (int) (Math.round(radiatorNos / 2.0) + 0.5);
            return tempCount1 - radiatorBRNos;
        }
    }

    private static int calculateRadiatorBRNos(int radiatorNos, boolean lvcb) {
        // Check if the design is with a cable box ("lvcb")
        if (lvcb) {
            // Default split (without cable box) - calculate radiators for BR side
            int tempCount1 = (int) ((radiatorNos / 2.0) + 0.5); // Round up for odd radiator count
            int radiatorBRNos = (int) ((tempCount1 / 2.0) + 0.5); // Ensure more radiators go to the LV side if total is odd
            return radiatorBRNos;
        } else {
            return 0;
        }
    }


    // Function to calculate radiator_Left_Nos based on user selection
    private static int getRadiatorLeftNos(int userSelection) {
        // Assume the userSelection represents the number of radiators for the left side
        return userSelection; // Return the value as per user selection
    }

    // Function to calculate radiator_Right_Nos based on user selection
    private static int getRadiatorRightNos(int userSelection) {
        // Assume the userSelection represents the number of radiators for the right side
        return userSelection; // Return the value as per user selection
    }

    // Function to calculate radiator_Min_Gap, default is 150, but user can update
    private static int getRadiatorMinGap(int userInput) {
        // If the user provides a value, use it; otherwise, default to 150
        return (userInput > 0) ? userInput : 150; // Return the user input if valid, otherwise return default (150)
    }

    private static double calculateRadiatorFPitch(String pitchCalcMode, boolean hvcb, double tankLength, double radiator_W, int frontRadiatorNos, double minGap, int radiator_Nos) {
        double radiatorFPitch;

        frontRadiatorNos = radiator_Nos / 2;

        if ("auto".equals(pitchCalcMode) && !hvcb) {
            radiatorFPitch = (tankLength - radiator_W) / (frontRadiatorNos - 1);

            // Validate if there is a minimum gap between radiators
            if (radiatorFPitch < (minGap + radiator_W)) {
                radiatorFPitch = minGap + radiator_W;
            }
        } else {
            radiatorFPitch = minGap + radiator_W;
        }

        return radiatorFPitch;
    }

    private static double calculateRadiatorBPitch(String pitchCalcMode, boolean lvcb, double tankLength, double radiatorWidth, int backRadiatorNos, double minGap, int radiator_Nos) {
        double radiatorBPitch;
        backRadiatorNos = radiator_Nos/2;

        if ("auto".equals(pitchCalcMode) && !lvcb) {
            radiatorBPitch = (tankLength - radiatorWidth) / (backRadiatorNos - 1);

            // Validate if there is a minimum gap between radiators
            if (radiatorBPitch < (minGap + radiatorWidth)) {
                radiatorBPitch = minGap + radiatorWidth;
            }
        } else {
            radiatorBPitch = minGap + radiatorWidth;
        }

        return radiatorBPitch;
    }

    private static boolean calculateRadiatorValve(double kva, boolean radiatorGneck, boolean isRadiatorValve) {
        // If KVA >= 1000 or radiator_Gneck is true, return true; otherwise, false
        return kva >= 1000 || radiatorGneck || isRadiatorValve;
    }



    private static Integer calculateRadiatorFlgVPos(boolean radiatorGneck, Integer stiffenerVertData, Integer stiffenerWidth) {
        if (radiatorGneck) {
            return 250;
        } else if (stiffenerVertData < 40) {
            return 60;
        } else {
            return stiffenerWidth + 30;
        }
    }




    // Function to calculate radiator_Head_Thick
    private static double calculateRadiatorHeadThick(double KVA) {
        return (KVA < 1000) ? 2 : 2.5;
    }

    // Function to return radiator_Fin_Sht_Thick
    private static double getRadiatorFinShtThick() {
        return 1.5; // As per standards
    }

    // Function to calculate radiator_FL_HPos
    private static double calculateRadiatorFLHPos(boolean hvcb, double radiator_F_Pitch, int radiator_FL_Nos,
                                                 String radiator_F_Type, double hvcb_L1, double radiator_Min_Gap,
                                                 double radiator_W, double radiator_offset_Dist) {
        if (!hvcb) { // Without Cable Box
            return radiator_F_Pitch * ((radiator_FL_Nos - 1) / 2.0);
        } else { // With Cable Box
            if ("psr_offset".equals(radiator_F_Type)) {
                return (hvcb_L1 / 2) + radiator_Min_Gap + (radiator_W / 2) +
                        (radiator_F_Pitch * radiator_FL_Nos / 2) - radiator_offset_Dist;
            } else {
                return (hvcb_L1 / 2) + radiator_Min_Gap + (radiator_W / 2) +
                        (radiator_F_Pitch * (radiator_FL_Nos / 2));
            }
        }
    }

    // Function to calculate radiator_BL_HPos
    private static double calculateRadiatorBLHPos(boolean lvcb, double radiator_B_Pitch, int radiator_BL_Nos,
                                                 String radiator_F_Type, double lvcb_L1, double radiator_Min_Gap,
                                                 double radiator_W, double radiator_offset_Dist) {
        if (!lvcb) { // Without Cable Box
            return radiator_B_Pitch * ((radiator_BL_Nos - 1) / 2.0);
        } else { // With Cable Box
            if ("psr_offset".equals(radiator_F_Type)) {
                return (lvcb_L1 / 2) + radiator_Min_Gap + (radiator_W / 2) +
                        (radiator_B_Pitch * radiator_BL_Nos / 2) - radiator_offset_Dist;
            } else {
                return (lvcb_L1 / 2) + radiator_Min_Gap + (radiator_W / 2) +
                        (radiator_B_Pitch * (radiator_BL_Nos / 2));
            }
        }
    }

    // Function to calculate radiator_FR_HPos
    private static double calculateRadiatorFRHPos(boolean hvcb, String radiator_F_Type, double hvcb_L1,
                                                 double radiator_Min_Gap, double radiator_W,
                                                 double radiator_F_Pitch, int radiator_FR_Nos, double radiator_offset_Dist) {
        if (hvcb) { // With Cable Box
            if ("psr_offset".equals(radiator_F_Type)) {
                return (hvcb_L1 / 2) + radiator_Min_Gap + (radiator_W / 2) +
                        (radiator_F_Pitch * radiator_FR_Nos / 2) - radiator_offset_Dist;
            } else {
                return (hvcb_L1 / 2) + radiator_Min_Gap + (radiator_W / 2) +
                        (radiator_F_Pitch * (radiator_FR_Nos / 2));
            }
        }
        return 0; // Default return when hvcb is false
    }

    // Function to calculate radiator_BR_HPos
    private static double calculateRadiatorBRHPos(boolean lvcb, String radiator_F_Type, double lvcb_L1,
                                                 double radiator_Min_Gap, double radiator_W,
                                                 double radiator_B_Pitch, int radiator_BR_Nos, double radiator_offset_Dist) {
        if (lvcb) { // With Cable Box
            if ("psr_offset".equals(radiator_F_Type)) {
                return (lvcb_L1 / 2) + radiator_Min_Gap + (radiator_W / 2) +
                        (radiator_B_Pitch * radiator_BR_Nos / 2) - radiator_offset_Dist;
            } else {
                return (lvcb_L1 / 2) + radiator_Min_Gap + (radiator_W / 2) +
                        (radiator_B_Pitch * (radiator_BR_Nos / 2));
            }
        }
        return 0; // Default return when lvcb is false
    }



    // Function to calculate radiator_Gneck_dim_A
    private static double calculateRadiatorGneckDimA(double tank_Flg_W, double radiator_Head_Dia) {
        return tank_Flg_W + (radiator_Head_Dia / 2);
    }

    private static double calculateRadiatorGneckDimC(double radiator_Flg_VPos, double radiator_Gneck_dim_A, double radiator_Gneck_dim_B) {
        return radiator_Flg_VPos - radiator_Gneck_dim_A - radiator_Gneck_dim_B;
    }



    // Function to calculate radiator_Ext_FL_L
    private static double calculateRadiatorExtFLL(double radiator_FL_Hpos, double radiator_Head_Dia, double tank_L, double tank_Thick, boolean hvcb) {
        double radiatorExtFLL = radiator_FL_Hpos + (radiator_Head_Dia / 2) + 17.5 - ((tank_L / 2) + tank_Thick);
        if (!hvcb) { // Without Cable Box
            if ((radiator_FL_Hpos + (radiator_Head_Dia / 2)) > (tank_L / 2)) {
                if(radiatorExtFLL > 0){
                    radiatorExtFLL = radiatorExtFLL > 110 ? radiatorExtFLL : 110;
                }
                return radiatorExtFLL;
            }
        }
        // With Cable Box
        if(radiatorExtFLL > 0){
            radiatorExtFLL = radiatorExtFLL > 110 ? radiatorExtFLL : 110;
        }
        return radiatorExtFLL;
    }

    // Function to calculate radiator_Ext_BL_L
    private static double calculateRadiatorExtBLL(double radiator_BL_Hpos, double radiator_Head_Dia, double tank_L, double tank_Thick, boolean lvcb) {
        double radiatorExtBLL = radiator_BL_Hpos + (radiator_Head_Dia / 2) + 17.5 - ((tank_L / 2) + tank_Thick);
        if (!lvcb) { // Without Cable Box
            if ((radiator_BL_Hpos + (radiator_Head_Dia / 2)) > (tank_L / 2)) {
                if(radiatorExtBLL > 0){
                    radiatorExtBLL = radiatorExtBLL > 110 ? radiatorExtBLL : 110;
                }
                return radiatorExtBLL;
            }
        }
        // With Cable Box
        if(radiatorExtBLL > 0){
            radiatorExtBLL = radiatorExtBLL > 110 ? radiatorExtBLL : 110;
        }
        return radiatorExtBLL;
    }

    // Function to calculate radiator_Ext_FR_L
    private static double calculateRadiatorExtFRL(double radiator_FL_Hpos, double radiator_FR_Hpos, double radiator_Head_Dia, double tank_L, double tank_Thick, boolean hvcb) {
        if (!hvcb) { // Without Cable Box
            if ((radiator_FL_Hpos + (radiator_Head_Dia / 2)) > (tank_L / 2)) {
                double radiatorExtFRL =  calculateRadiatorExtFLL(radiator_FL_Hpos, radiator_Head_Dia, tank_L, tank_Thick, hvcb);
                if(radiatorExtFRL > 0){
                    radiatorExtFRL = radiatorExtFRL > 110? radiatorExtFRL : 110;
                }
                return radiatorExtFRL;
            }
        }
        // With Cable Box
        double radiatorExtFRL =  radiator_FR_Hpos + (radiator_Head_Dia / 2) + 17.5 - ((tank_L / 2) + tank_Thick);
        if(radiatorExtFRL > 0){
            radiatorExtFRL = radiatorExtFRL > 110? radiatorExtFRL : 110;
        }
        return radiatorExtFRL;
    }

    // Function to calculate radiator_Ext_BR_L
    private static double calculateRadiatorExtBRL(double radiator_BL_Hpos, double radiator_BR_Hpos, double radiator_Head_Dia, double tank_L, double tank_Thick, boolean lvcb) {
        if (!lvcb) { // Without Cable Box
            if ((radiator_BL_Hpos + (radiator_Head_Dia / 2)) > (tank_L / 2)) {
                double radiatorExtBRL =  calculateRadiatorExtBLL(radiator_BL_Hpos, radiator_Head_Dia, tank_L, tank_Thick, lvcb);
                if(radiatorExtBRL > 0){
                    radiatorExtBRL = radiatorExtBRL > 110 ? radiatorExtBRL : 110;
                }
                return radiatorExtBRL;
            }
        }
        // With Cable Box
        double radiatorExtBRL = radiator_BR_Hpos + (radiator_Head_Dia / 2) + 17.5 - ((tank_L / 2) + tank_Thick);
        if(radiatorExtBRL > 0){
            radiatorExtBRL = radiatorExtBRL > 110 ? radiatorExtBRL : 110;
        }
        return radiatorExtBRL;
    }

    private static String checkRadiator_Type(String radiatorType) {
        if (radiatorType.isEmpty()){
            return "psr";
        }else {
            return radiatorType;
        }
    }

    private static String checkRad_Ptch_Calc_Mode(String rad_Ptch_Calc_Mode) {
        if(rad_Ptch_Calc_Mode.isEmpty()){
            return "auto";
        }else {
            return rad_Ptch_Calc_Mode;
        }
    }

    private static Integer checkRadiator_Min_Gap(Integer radiatorMinGap) {
        //Note that the previous gap was 150mm. now made 100mm. (NB)
        if(radiatorMinGap<100){
            return 100;
        }else {
            return  radiatorMinGap;
        }
    }
    
    
    public static Radiator calcualteRadiatorFormulas(Integer kVA, Integer radiator_CC, Integer tank_H, Integer tank_L,
                                                     Integer tank_Thick, Integer tank_Flg_W, Integer radiator_W
    , Integer radiator_Fin_Nos, Integer radiator_Nos, String radiatorType,
       Boolean lvcb, Boolean hvcb, Double hvcb_L1, Double lvcb_L1,  Integer radiator_Left_Nos, Integer radiator_Right_Nos
            ,Integer stiffner_Vert_Data_L,Integer stiffner_Vert_Data_W,String radPtchCalcMode, Integer radiatorMinGap,
                boolean isRadiatorValve, String lvb_Pos, String hvb_Pos, Integer lvb_OnTank_Length ,Integer hvb_OnTank_Length) {
        Radiator radiator = new Radiator();

        //This is to assume that the hvb or lvb when on tank it is just like the CB on the tank.
        if(Objects.equals(lvb_Pos, "tank")){
            lvcb = true;
            lvcb_L1 = Double.valueOf(lvb_OnTank_Length);
        }
        if(Objects.equals(hvb_Pos, "tank")){
            hvcb = true;
            hvcb_L1 = Double.valueOf(hvb_OnTank_Length);
        }

        Integer radiator_Vpos = isRadiatorValve ? 110 : 90; //Previously it was 150mm (NB said 110)
        int radiator_Head_Dia = 90;
        Integer radiator_Min_Bottom_Clearence = radiator_Vpos;
        boolean radiator_Gneck = RadiatorFormulas.getRadiatorGNeck(radiator_CC, radiator_Vpos, radiator_Min_Bottom_Clearence, tank_H, radiator_Head_Dia);
        Integer radiator_CC_onTank = RadiatorFormulas.getRadiatorCCOnTank(tank_H, radiator_Vpos, radiator_Min_Bottom_Clearence
                ,radiator_CC, radiator_Gneck);
        Double radiator_offset_Dist = RadiatorFormulas.getRadiatorOffsetDist(radiator_W, radiator_Head_Dia);
        Integer radiator_F_Vpos = radiator_Vpos;
        Integer radiator_B_Vpos = radiator_Vpos;

        Integer radiator_Fin_Pitch = 50;
        Integer radiator_Fin1_Pos = 100;
        Integer radiator_Fin_Thick = 10; //It was previously 15 (Changed-NB)

        Integer radiator_BR_Nos = calculateRadiatorBRNos(radiator_Nos, lvcb);
        Integer radiator_BL_Nos = calculateRadiatorBLNos(lvcb,radiator_Nos, radiator_BR_Nos);
        Integer radiator_FR_Nos = calculateRadiatorFRNos(hvcb,radiator_Nos,radiator_BL_Nos, radiator_BR_Nos);
        Integer radiator_FL_Nos = calculateRadiatorFLNos(radiator_Nos, radiator_FR_Nos, hvcb , radiator_BL_Nos,radiator_BR_Nos);

        Integer radiator_Head_L = RadiatorFormulas.calculateRadiatorHeadL(radiator_Fin1_Pos
                , radiator_Fin_Pitch, radiator_Fin_Nos);

        //radiator
        String radiator_Type=checkRadiator_Type(radiatorType);
        radiator.setRadiator_Type(radiator_Type);
        radiator.setRadiator_F_Type(radiator_Type);
        radiator.setRadiator_B_Type(radiator_Type);
        radiator.setRadiator_CC(radiator_CC);
        radiator.setRadiator_Min_Bottom_Clearence(radiator_Min_Bottom_Clearence);
        radiator.setRadiator_CC_onTank(radiator_CC_onTank);
        radiator.setRadiator_W(radiator_W);
        radiator.setRadiator_offset_Dist(radiator_offset_Dist);
        radiator.setRadiator_Fin_Nos(radiator_Fin_Nos);

        String rad_Ptch_Calc_Mode=checkRad_Ptch_Calc_Mode(radPtchCalcMode);
        radiator.setRad_Ptch_Calc_Mode(rad_Ptch_Calc_Mode);

        radiator.setRadiator_Fin_Pitch(radiator_Fin_Pitch);
        radiator.setRadiator_Fin1_Pos(radiator_Fin1_Pos);
        radiator.setRadiator_Nos(radiator_Nos);
        radiator.setRadiator_Fin_Thick(radiator_Fin_Thick);

        radiator.setRadiator_FL_Nos(radiator_FL_Nos);
        radiator.setRadiator_FR_Nos(radiator_FR_Nos);
        radiator.setRadiator_BL_Nos(radiator_BL_Nos);
        radiator.setRadiator_BR_Nos(radiator_BR_Nos);

        radiator.setRadiator_Left_Nos(radiator_Left_Nos);
        radiator.setRadiator_Right_Nos(radiator_Right_Nos);
        Integer radiator_Min_Gap=checkRadiator_Min_Gap(radiatorMinGap);
        radiator.setRadiator_Min_Gap(radiator_Min_Gap);
        radiator.setRadiator_F_Pitch(calculateRadiatorFPitch(rad_Ptch_Calc_Mode, hvcb, tank_L, radiator_W, radiator.getRadiator_FR_Nos(), radiator.getRadiator_Min_Gap(), radiator.getRadiator_Nos()));
        radiator.setRadiator_B_Pitch(calculateRadiatorBPitch(rad_Ptch_Calc_Mode, lvcb, tank_L, radiator_W, radiator.getRadiator_BR_Nos(), radiator.getRadiator_Min_Gap(), radiator.getRadiator_Nos()));
        radiator.setRadiator_Vlv(calculateRadiatorValve(kVA, radiator_Gneck, isRadiatorValve));

        radiator.setRadiator_Flg_W(150);
        radiator.setRadiator_Flg_H(150);
        radiator.setRadiator_Flg_Thick(16);
        radiator.setRadiator_Flg_Hole_Dia(20);
        radiator.setRadiator_Flg_Hole_Nos(4);
        radiator.setRadiator_Flg_Hole_PCD(160);
        radiator.setRadiator_Flg_VPos(calculateRadiatorFlgVPos(radiator_Gneck, stiffner_Vert_Data_L, stiffner_Vert_Data_W ));
        radiator.setRadiator_Vlv_Thick(40);
        radiator.setRadiator_Head_Dia(radiator_Head_Dia);
        radiator.setRadiator_Head_L(radiator_Head_L);
        radiator.setRadiator_Head_Thick(calculateRadiatorHeadThick(kVA));
        radiator.setRadiator_Fin_Sht_Thick(1.25); // Previously it was 1.5 (Acc. IEEMA's Std, 1.25)

        radiator.setRadiator_FL_HPos(calculateRadiatorFLHPos(hvcb, radiator.getRadiator_F_Pitch(), radiator_FL_Nos
        ,radiator.getRadiator_F_Type(), hvcb_L1,radiator.getRadiator_Min_Gap(), radiator_W, radiator_offset_Dist ));
        radiator.setRadiator_BL_Hpos(calculateRadiatorBLHPos(lvcb, radiator.getRadiator_B_Pitch(), radiator_BL_Nos
                ,radiator.getRadiator_F_Type(), lvcb_L1,radiator.getRadiator_Min_Gap(), radiator_W, radiator_offset_Dist ));
        radiator.setRadiator_FR_Hpos(calculateRadiatorFRHPos(hvcb,radiator.getRadiator_F_Type(), hvcb_L1,radiator
                        .getRadiator_Min_Gap(), radiator_W, radiator.getRadiator_F_Pitch(), radiator_FR_Nos
                , radiator_offset_Dist ));
        radiator.setRadiator_BR_Hpos(calculateRadiatorBRHPos(lvcb,radiator.getRadiator_F_Type(), lvcb_L1,radiator
                        .getRadiator_Min_Gap(), radiator_W, radiator.getRadiator_B_Pitch(), radiator_BR_Nos
                , radiator_offset_Dist ));

        radiator.setRadiator_Vpos(radiator_Vpos);
        radiator.setRadiator_F_Vpos(radiator_F_Vpos);
        radiator.setRadiator_B_Vpos(radiator_B_Vpos);

        radiator.setRadiator_Gneck(radiator_Gneck);
        radiator.setRadiator_Gneck_dim_A(calculateRadiatorGneckDimA(tank_Flg_W, radiator_Head_Dia));
        radiator.setRadiator_Gneck_dim_B(80);
        radiator.setRadiator_Gneck_dim_C(calculateRadiatorGneckDimC(radiator.getRadiator_Flg_VPos(), radiator.getRadiator_Gneck_dim_A(), radiator.getRadiator_Gneck_dim_B()));
        radiator.setRadiator_Gneck_dim_D(radiator_CC - radiator_CC_onTank);

        radiator.setRadiator_Ext_H(radiator_Head_Dia + 35);
        radiator.setRadiator_Ext_W(65);
        radiator.setRadiator_Ext_Thick(5);
        radiator.setRadiator_Ext_FL_L(calculateRadiatorExtFLL(radiator.getRadiator_FL_HPos(), radiator_Head_Dia, tank_L, tank_Thick, hvcb));
        radiator.setRadiator_Ext_BL_L(calculateRadiatorExtBLL(radiator.getRadiator_BL_Hpos(), radiator_Head_Dia, tank_L, tank_Thick, lvcb));
        radiator.setRadiator_Ext_FR_L(calculateRadiatorExtFRL(radiator.getRadiator_FL_HPos(), radiator.getRadiator_FR_Hpos(), radiator_Head_Dia, tank_L, tank_Thick, hvcb));
        radiator.setRadiator_Ext_BR_L(calculateRadiatorExtBRL(radiator.getRadiator_BL_Hpos(), radiator.getRadiator_BR_Hpos(), radiator_Head_Dia, tank_L, tank_Thick, lvcb));

        radiator.setRadiator_Lift_Lug_L(45);
        radiator.setRadiator_Lift_Lug_H(45);
        radiator.setRadiator_Lift_Lug_Thick(10);
        radiator.setRadiator_Lift_Lug_Hole_Dia((double) radiator.getRadiator_Lift_Lug_L() / 2);

        return radiator;
    }




}
