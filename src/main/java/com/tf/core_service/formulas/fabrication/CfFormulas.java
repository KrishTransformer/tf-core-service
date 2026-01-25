
package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Cf;


public class CfFormulas {

    // Cf_StayRod_Hole_Dia Calculation
    public static Integer calculateCf_StayRod_Hole_Dia(Integer stayRod_Dia) {
        return stayRod_Dia+2;
    }

    // Cf_StayRod_Hole_Pitch Calculation
    public static Double calculateCf_StayRod_Hole_Pitch(Integer core_Fixture_H) {
        return (double) (core_Fixture_H/3);
    }

    // Cf_TieRod_Hole_Dia Calculation
    public static Integer calculateCf_TieRod_Hole_Dia(Integer tieRod_Dia) {
        return tieRod_Dia+2;
    }

    // Cf_StayRod_Plate_L Calculation
    public static Integer calculateCf_StayRod_Plate_L(Integer cf_StayRod_Hole_Dia) {
        return cf_StayRod_Hole_Dia*3;
    }

    // Cf_StayRod_Plate_H Calculation
    public static Double calculateCf_StayRod_Plate_H(Integer cf_StayRod_Hole_Dia,
                                                     Double cf_StayRod_Hole_Pitch,
                                                     Integer stayRod_Nos) {
        return (cf_StayRod_Hole_Dia +
                (cf_StayRod_Hole_Pitch * (stayRod_Nos - 1)) +
                cf_StayRod_Hole_Dia +
                (cf_StayRod_Hole_Dia * 2));
    }

    // Cf_StayRod_Plate_Thick Calculation - Yet to complete
    public static Integer calculateCf_StayRod_Plate_Thick(Integer core_Dia, Integer stayRod_Dia, Double core_Fixture_Thick){
        return 5;
        //TODO: correct the above value. We have kept this as a default.
    }

    // Cf_YokeRod_Plate_L Calculation
    public static Integer calculateCf_YokeRod_Plate_L(Integer cf_StayRod_Hole_Dia){
        return cf_StayRod_Hole_Dia*3;
    }

    // Cf_YokeRod_Plate_Thick Calculation
    public static  Double calculateCf_YokeRod_Plate_Thick(Double core_Fixture_Thick){
        return core_Fixture_Thick*2;
    }

    // Cf_Cond_CutOut_W Calculation - Yet to complete
    public static Integer calculateCf_Cond_CutOut_W(Double lv_Cond_W, Double lv_Cond_Ax){
        // (Int((lv_Cond_W * (lv_Cond_Ax) + (lv_Cond_Clear * 2)) / 2))
        return null;
    }

    // Cf_Wind_Sup_F_Rad Calculation
    public static Double calculateCf_Wind_Sup_F_Rad(Double hv_OD){
        return ((hv_OD/2)+5);
    }

    // Cf_Wind_Sup_F_W Calculation
    public static Integer calculateCf_Wind_Sup_F_W(Double cf_Wind_Sup_F_Rad,
                                                   Integer core_Dia,
                                                   Integer core_Fixture_W){
        return  (int)(cf_Wind_Sup_F_Rad - ((core_Dia) / 2.0) - core_Fixture_W);
    }

    // Cf_Wind_Sup_F_Stiff_W Calculation
    public static Integer calculateCf_Wind_Sup_F_Stiff_W(Double cf_Wind_Sup_F_Rad,
                                                         Integer core_Dia){
        return  (int)((cf_Wind_Sup_F_Rad - (core_Dia/2.0)) * 0.75);
    }

    // Cf_Wind_Sup_F_Stiff_Space Calculation

    // Cf_Wind_Sup_S_W Calculation
    public static Double calculateCf_Wind_Sup_S_W(Integer core_Dia){
        return  (double)((core_Dia / 2) - 10);
    }

    // Cf_Wind_Sup_S_L Calculation
    public static Integer calculateCf_Wind_Sup_S_L(Integer core_Fixture_L,
                                                   Integer limb_CC, Integer core_Dia){
        return  (int)((core_Fixture_L / 2) - limb_CC - (core_Dia / 3));
    }

    // Cf_Wind_Sup_S_Rad Calculation
    public static Double calculateCf_Wind_Sup_S_Rad(Integer core_Dia){
        return  (double)((core_Dia / 2) + 10);
    }

    // Cf_Wind_Sup_S_Stiff_W Calculation
    public static Integer calculateCf_Wind_Sup_S_Stiff_W(Double cf_Wind_Sup_S_W){
        return  (int)(cf_Wind_Sup_S_W * 0.75);
    }

    // Cf_Wind_Sup_S_Stiff_H Calculation
    public static Integer calculateCf_Wind_Sup_S_Stiff_H(Integer core_Fixture_H){
        return  (int)(core_Fixture_H * 0.75);
    }

    // Cf_Wind_Sup_S_Stiff_Cham Calculation
    public static Double calculateCf_Wind_Sup_S_Stiff_Cham(Double core_Fixture_Thick){
        return  core_Fixture_Thick*2;
    }

    // Cf_Lift_Lug_L1 Calculation
    public static Integer calculateCf_Lift_Lug_L1(Integer core_Fixture_W){
        return (((int)((core_Fixture_W * 1.3) / 5)) * 5) ;
    }

    // Cf_Lift_Lug_L2 Calculation
    public static Double calculateCf_Lift_Lug_L2(Integer cf_Lift_Lug_L1){
        return (double)(cf_Lift_Lug_L1 * 3 / 4) ;
    }

    // Cf_Lift_Lug_Thick Calculation
    public static Double calculateCf_Lift_Lug_Thick(String core_Fixture_Type,
                                                    Double core_Fixture_Thick){
        if(core_Fixture_Type.equals("c-channel")){
            return core_Fixture_Thick*2;
        }
        else return core_Fixture_Thick;
    }

    // Cf_Lift_Lug_Hole_Dia Calculation
    public static Double calculateCf_Lift_Lug_Hole_Dia(Integer cf_Lift_Lug_L1){
        return (double)(cf_Lift_Lug_L1 / 3) ;
    }


    public static Cf calculateCf(Integer stayRod_Dia, Integer stayRod_Nos,
                                 Integer core_Fixture_H, Integer tieRod_Dia,
                                 Integer core_Dia, Double core_Fixture_Thick,
                                 Integer core_Fixture_W, Integer limb_CC,
                                 Double lv_Cond_W, Double lv_Cond_Ax,
                                 Double hv_OD, Integer core_Fixture_L,
                                 String core_Fixture_Type) {
        Cf cf = new Cf();

        Integer cf_StayRod_Hole_Dia=calculateCf_StayRod_Hole_Dia(stayRod_Dia);
        cf.setCf_StayRod_Hole_Dia(cf_StayRod_Hole_Dia);

        cf.setCf_StayRod_Hole_Nos(stayRod_Nos);

        Double cf_StayRod_Hole_Pitch=calculateCf_StayRod_Hole_Pitch(core_Fixture_H);
        cf.setCf_StayRod_Hole_Pitch(cf_StayRod_Hole_Pitch);

        cf.setCf_TieRod_Hole_Dia(calculateCf_TieRod_Hole_Dia(tieRod_Dia));
        cf.setCf_StayRod_Plate_L(calculateCf_StayRod_Plate_L(cf_StayRod_Hole_Dia));

        cf.setCf_StayRod_Plate_H(calculateCf_StayRod_Plate_H(
                cf_StayRod_Hole_Dia,
                cf_StayRod_Hole_Pitch,
                stayRod_Nos
                ));

        //** some mistake in the formula for this **//

        cf.setCf_StayRod_Plate_Thick(calculateCf_StayRod_Plate_Thick(
                core_Dia,
                stayRod_Dia,
                core_Fixture_Thick
        ));

        cf.setCf_Yoke_Hole_Dia(cf_StayRod_Hole_Dia);

        Integer cf_YokeRod_Plate_L=calculateCf_YokeRod_Plate_L(cf_StayRod_Hole_Dia);
        cf.setCf_YokeRod_Plate_L(cf_YokeRod_Plate_L);
        cf.setCf_YokeRod_Plate_H(cf_YokeRod_Plate_L);
        cf.setCf_YokeRod_Plate_Thick(calculateCf_YokeRod_Plate_Thick(core_Fixture_Thick));

        // this formula have some missing variable - (yet to complete)
        cf.setCf_Cond_CutOut_W(calculateCf_Cond_CutOut_W(lv_Cond_W,lv_Cond_Ax));

        cf.setCf_Stiff_Thick(core_Fixture_Thick);
        cf.setCf_tie_Stiff_Space(core_Fixture_W);
        cf.setCf_tie_Stiff_Pitch(limb_CC);

        Double cf_Wind_Sup_F_Rad=calculateCf_Wind_Sup_F_Rad(hv_OD);
        cf.setCf_Wind_Sup_F_Rad(cf_Wind_Sup_F_Rad);

        cf.setCf_Wind_Sup_F_W(calculateCf_Wind_Sup_F_W(
                cf_Wind_Sup_F_Rad,
                core_Dia,
                core_Fixture_W
        ));

        cf.setCf_Wind_Sup_F_Thick(core_Fixture_Thick);

        cf.setCf_Wind_Sup_F_Stiff_W(calculateCf_Wind_Sup_F_Stiff_W(cf_Wind_Sup_F_Rad,
                core_Dia));

        cf.setCf_Wind_Sup_F_Stiff_Thick(core_Fixture_Thick);
        cf.setCf_Wind_Sup_F_Stiff_Hole_Dia(10);
        cf.setCf_Wind_Sup_F_Stiff_Hole_Nos(4);
        cf.setCf_Wind_Sup_F_Stiff_Nos(2);

        // for this we need cf_Cond_CutOut_W(which is need to calculated)
//        cf.setCf_Wind_Sup_F_Stiff_Space();

        cf.setCf_Wind_Sup_F_Stiff_Pitch(limb_CC);

        Double cf_Wind_Sup_S_W=calculateCf_Wind_Sup_S_W(core_Dia);
        cf.setCf_Wind_Sup_S_W(cf_Wind_Sup_S_W);

        cf.setCf_Wind_Sup_S_L(calculateCf_Wind_Sup_S_L(
                core_Fixture_L,
                limb_CC,
                core_Dia
        ));
        cf.setCf_Wind_Sup_S_Rad(calculateCf_Wind_Sup_S_Rad(core_Dia));
        cf.setCf_Wind_Sup_S_Thick(core_Fixture_Thick);
        cf.setCf_Wind_Sup_S_Stiff_W(calculateCf_Wind_Sup_S_Stiff_W(cf_Wind_Sup_S_W));
        cf.setCf_Wind_Sup_S_Stiff_H(calculateCf_Wind_Sup_S_Stiff_H(core_Fixture_H));
        cf.setCf_Wind_Sup_S_Stiff_Thick(core_Fixture_Thick);
        cf.setCf_Wind_Sup_S_Stiff_Cham(calculateCf_Wind_Sup_S_Stiff_Cham(core_Fixture_Thick));

        Integer cf_Lift_Lug_L1=calculateCf_Lift_Lug_L1(core_Fixture_W);
        cf.setCf_Lift_Lug_L1(cf_Lift_Lug_L1);

        cf.setCf_Lift_Lug_L2(calculateCf_Lift_Lug_L2(cf_Lift_Lug_L1));
        cf.setCf_Lift_Lug_H(cf_Lift_Lug_L1);
        cf.setCf_Lift_Lug_Thick(calculateCf_Lift_Lug_Thick(core_Fixture_Type,
                core_Fixture_Thick));
        cf.setCf_Lift_Lug_Hole_Dia(calculateCf_Lift_Lug_Hole_Dia(cf_Lift_Lug_L1));
        cf.setCf_StayRod_Hole_Clear(10);

        return cf;
    }
}
