package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Lid;

public class LidFormulas {
    private static double getLidThickness(double electricalDesignValue) {
        return electricalDesignValue;
    }

    private static Integer calculateLidExtraProjection(Integer lidThickness) {
        return lidThickness;
    }

    private static Integer calculateLidLength(Integer tankLength, Integer tankThickness, Integer tankFlangeWidth, Integer lidExtraProjection) {
        return tankLength + (2 * tankThickness) + (2 * tankFlangeWidth) + 10 + 2; // previously, lidProjection * 2 instead of 10+2
    }

    private static Integer calculateLidWidth(Integer tankWidth, Integer tankThickness, Integer tankFlangeWidth, Integer lidExtraProjection) {
        return tankWidth + (2 * tankThickness) + (2 * tankFlangeWidth) + 10 + 2; // previously, lidProjection * 2 instead of 10+2
    }

    private static double getLidBoltHoleDiameter(double tankFlangeBoltDiameter) {
        return tankFlangeBoltDiameter;
    }

    private static int getLidSlopeAngle() {
        return 0;
    }

    private static double calculateHvbPitch(double tankLength, double hvbTankOilClear, double bushingPitchP2P) {
        double hvbPitch = bushingPitchP2P;
        if ((tankLength / 2) - hvbPitch < hvbTankOilClear) {
            hvbPitch = (tankLength / 2) - hvbTankOilClear;
        }
        return hvbPitch;
    }

    private static double getHvbFTiltAngle(double userSelectedAngle) {
        return userSelectedAngle;
    }

    private static double calculateHvbSTiltAngle(double tankLength, double hvbPitch, double hvbTankOilClear, double hvbSupH, double hvbL1) {
        double tiltAngle = 0;
        if ((tankLength / 2) - hvbPitch < hvbTankOilClear) {
            tiltAngle = Math.atan(hvbTankOilClear / (hvbSupH + hvbL1));
            return Math.toDegrees(tiltAngle) + 2.9;
        }
        return 0;
    }

    public static Lid calcuateLid(Integer lid_Thick, Integer tank_L, Integer tank_W, Integer tank_Thick,
                                  Integer tank_Flg_W, Integer tank_Flg_Bolt_Dia) {
        Lid lid = new Lid();
        lid.setLid_Thick(lid_Thick);
        lid.setLid_Extra_Projection(calculateLidExtraProjection(lid_Thick));
        lid.setLid_L(calculateLidLength(tank_L, tank_Thick, tank_Flg_W, lid.getLid_Extra_Projection()));
        lid.setLid_W(calculateLidWidth(tank_W, tank_Thick, tank_Flg_W, lid.getLid_Extra_Projection()));
        lid.setLid_Bolt_Hole_Dia(tank_Flg_Bolt_Dia);
        lid.setLid_Slope_Ang(0);

        return lid;
    }



}
