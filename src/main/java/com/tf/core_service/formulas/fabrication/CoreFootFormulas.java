
package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.CoreFoot;

public class CoreFootFormulas {

    // CoreFoot_H Calculation
    public static Double calculateCoreFoot_H(Integer coreFoot_Thick1, Integer core_Bottom_Clearence,
                                             Integer core_Dia, Integer core_Fixture_H,
                                             Double yoke_Compensation){
        return coreFoot_Thick1 + core_Bottom_Clearence + (core_Dia / 2) - (core_Fixture_H / 2) + yoke_Compensation;
    }

    // coreFoot_W Calculation
    public static Integer calculateCoreFoot_W(Integer core_Dia, Integer core_Fixture_W){
        return (core_Dia) + (core_Fixture_W * 2);
    }

    public static CoreFoot calculateCoreFoot(Integer kVA, Integer core_Bottom_Clearence,
                                             Integer core_Dia, Integer core_Fixture_H,
                                             Double yoke_Compensation, Double core_Fixture_Thick,
                                             Integer core_Fixture_W) {
        CoreFoot coreFoot = new CoreFoot();

        if (kVA <= 100) {
            coreFoot.setCoreFoot_Thick1(8);
            coreFoot.setCoreFoot_L(50);
            coreFoot.setCoreFoot_Stud_Hole_Dia(14); // M12
            coreFoot.setCoreFoot_Stud_Hole_Nos(2);  // Per Plate
            coreFoot.setCoreFoot_Nos(2);
        }
        else if (kVA <= 400) {
            coreFoot.setCoreFoot_Thick1(10);
            coreFoot.setCoreFoot_L(50);
            coreFoot.setCoreFoot_Stud_Hole_Dia(14); // M12
            coreFoot.setCoreFoot_Stud_Hole_Nos(2);  // Per Plate
            coreFoot.setCoreFoot_Nos(2);
        }
        else if (kVA <= 800) {
            coreFoot.setCoreFoot_Thick1(12);
            coreFoot.setCoreFoot_L(65);
            coreFoot.setCoreFoot_Stud_Hole_Dia(14); // M12
            coreFoot.setCoreFoot_Stud_Hole_Nos(4);  // Per Plate
            coreFoot.setCoreFoot_Nos(2);
        }
        else if (kVA <= 1250) {
            coreFoot.setCoreFoot_Thick1(16);
            coreFoot.setCoreFoot_L(75);
            coreFoot.setCoreFoot_Stud_Hole_Dia(14); // M12
            coreFoot.setCoreFoot_Stud_Hole_Nos(4);  // Per Plate
            coreFoot.setCoreFoot_Nos(2);
        }
        else if (kVA <= 2000) {
            coreFoot.setCoreFoot_Thick1(20);
            coreFoot.setCoreFoot_L(90);
            coreFoot.setCoreFoot_Stud_Hole_Dia(14); // M12
            coreFoot.setCoreFoot_Stud_Hole_Nos(4);  // Per Plate
            coreFoot.setCoreFoot_Nos(3);
        }
        else if (kVA <= 2500) {
            coreFoot.setCoreFoot_Thick1(20);
            coreFoot.setCoreFoot_L(100);
            coreFoot.setCoreFoot_Stud_Hole_Dia(18); // M16
            coreFoot.setCoreFoot_Stud_Hole_Nos(4);  // Per Plate
            coreFoot.setCoreFoot_Nos(3);
        }
        else if (kVA <= 5000) {
            coreFoot.setCoreFoot_Thick1(16);
            coreFoot.setCoreFoot_L(150);
            coreFoot.setCoreFoot_Stud_Hole_Dia(18); // M16
            coreFoot.setCoreFoot_Stud_Hole_Nos(4);  // Per Plate
            coreFoot.setCoreFoot_Nos(3);
        }
        else if (kVA <= 8000) {
            coreFoot.setCoreFoot_Thick1(20);
            coreFoot.setCoreFoot_L(150);
            coreFoot.setCoreFoot_Stud_Hole_Dia(18); // M16
            coreFoot.setCoreFoot_Stud_Hole_Nos(4);  // Per Plate
            coreFoot.setCoreFoot_Nos(3);
        }
        else {
            coreFoot.setCoreFoot_Thick1(20);
            coreFoot.setCoreFoot_L(150);
            coreFoot.setCoreFoot_Stud_Hole_Dia(18); // M16
            coreFoot.setCoreFoot_Stud_Hole_Nos(4);  // Per Plate
            coreFoot.setCoreFoot_Nos(3);
        }
        coreFoot.setCoreFoot_H(calculateCoreFoot_H(coreFoot.getCoreFoot_Thick1(),
                core_Bottom_Clearence,
                core_Dia,
                core_Fixture_H,
                yoke_Compensation
        ));
        coreFoot.setCoreFoot_Thick2(core_Fixture_Thick);
        coreFoot.setCoreFoot_W(calculateCoreFoot_W(core_Dia,core_Fixture_W));
        return coreFoot;
    }
}
