
package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Lift_Cons;

public class LiftConsFormulas {

    // Lift_Cons_W Calculation
    public static Integer calculateLift_Cons_W(Integer cons_Dia){
        return (((cons_Dia / 10) / 5)) * 5;
    }

    // Lift_Cons_Thick Calculation
    public static double calculateLift_Cons_Thick(Integer lift_Cons_W){
        return lift_Cons_W/4;
    }

    // Lift_Cons_Hole_Dia Calculation
    public static double calculateLift_Cons_Hole_Dia(Integer lift_Cons_W){
        return lift_Cons_W/2;
    }
    // Lift_Cons_Type Calculation
    public static String calculateLift_Cons_Type(Integer cons_Vol){
        return (cons_Vol <= 100) ? "rod" : "plate";
    }


    public static Lift_Cons calculateLiftCons(Integer cons_Dia, Integer cons_Vol) {
        Lift_Cons liftCons = new Lift_Cons();
        Integer lift_Cons_W=calculateLift_Cons_W(cons_Dia);
        liftCons.setLift_Cons_W(lift_Cons_W);
        liftCons.setLift_Cons_L(lift_Cons_W);
        liftCons.setLift_Cons_Thick(calculateLift_Cons_Thick(lift_Cons_W));
        liftCons.setLift_Cons_Hole_Dia(calculateLift_Cons_Hole_Dia(lift_Cons_W));
        liftCons.setLift_Cons_Type(calculateLift_Cons_Type(cons_Vol));
        return liftCons;
    }
}
