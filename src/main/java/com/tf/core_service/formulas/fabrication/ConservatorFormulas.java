package com.tf.core_service.formulas.fabrication;

import ch.qos.logback.core.model.INamedModel;
import com.tf.core_service.model.fabrication.BuchRely;
import com.tf.core_service.model.fabrication.Cons;

public class ConservatorFormulas {

    public static boolean calculateConsDetach(int KVA) {
        return KVA < 500;
    }

    public static double calculateConsHPos(String consPos, double tankThick, double tankFlgW, double consDia, double consPipeE, double consPipeHPos, double tankL, double hvPhPhInclCap, double hvPhErthClear) {
        double consHPos;
        if ("lv side".equals(consPos)) {
            consHPos = tankThick + tankFlgW + (consDia * 0.25);
        } else {
            consHPos = consPipeE - tankThick - consPipeHPos;
            double tankHVBDist = (tankL / 2) - hvPhPhInclCap;
            if ((consHPos - (consDia / 2)) + tankHVBDist <= hvPhErthClear) {
                consHPos = Math.round((((hvPhErthClear - tankHVBDist) + (consDia / 2)) * 1.2) / 10 + 0.9) * 10;
            }
        }
        return consHPos;
    }

    public static double calculateConsFlagThickness(double tankThick) {
        return tankThick;
    }

    public static double calculateConsEPlateDia(double consDia, double consThick, double consFlgW) {
        return consDia + (2 * consThick) + (2 * consFlgW);
    }

    public static double calculateConsEPlateThick(double tankThick) {
        return tankThick;
    }

    public static int calculateConsEPlateHoleNos(double consDia, double consEPlateHoleDia) {
        return (int) (Math.PI * consDia / (8 * consEPlateHoleDia));
    }

    public static double calculateConsEPlateHolePCD(double consDia, double consThick, double consFlgW) {
        return consDia + (2 * consThick) + consFlgW;
    }

    public static double calculateConsPipeSlope(String consPos, double Phi) {
        return "left".equals(consPos) ? 7 * (Phi / 180) : 0.0;
    }

    public static double calculateConsPipeBendRad(double consPipeID) {
        return 1.5 * consPipeID;
    }

    public static double calculateConsPipeBendCompensate(double consPipeSlope, double consPipeBendRad) {
        return Math.sin(consPipeSlope) * consPipeBendRad;
    }



            public static Integer calculateBreathPipeH1(Integer consBreathPipeH2, Integer consDia, Integer consBreathPipeH2Again) {
                return consBreathPipeH2 + consDia + consBreathPipeH2Again;
            }

            // Function to calculate cons_Breath_Pipe_H2
            public static Integer calculateBreathPipeH2(Integer consFlgW, Integer consBreathPipeID) {
                return consFlgW + (2 * consBreathPipeID);
            }

            // Function to calculate cons_Breath_Pipe_W1
            public static Integer calculateBreathPipeW1(Integer consBreathPipeW2, Integer consBreathPipeID) {
                return consBreathPipeW2 + (7 * consBreathPipeID);
            }

            // Function to calculate cons_Breath_Pipe_W2
            public static Integer calculateBreathPipeW2(Integer consBreathPipeID) {
                return 3 * consBreathPipeID;
            }

            public static Integer calculateConsSupSectL1(Double cons_H_Pos, Integer tank_Flg_W, Integer tank_Thick, Integer cons_Dia) {
                return (int) Math.round(cons_H_Pos) - tank_Flg_W + tank_Thick + (cons_Dia / 2) - 25;
            }
            public static Integer calculateConsSupSectL2(Integer tank_Flg_W, Integer tank_Flg_Bolt_HPch) {
                return (tank_Flg_W / 2) + (tank_Flg_Bolt_HPch * 3) + (tank_Flg_Bolt_HPch / 2);
            }





    public static Cons calculateCons(Integer kVA, Integer cons_Vol, Integer cons_Dia
            , Integer cons_L, Double gorPipe_Ass_H, Double gorPipe_Ass_L, Integer gorPipe_HPos, Integer cover_L, Integer tank_Thick,
                                     Integer tank_L, Integer cover_W, Integer gorPipe_VPos, boolean buchholz_Relay, int noOfHvTerminals
    ) {

        Cons cons = new Cons();
        cons.setConseravator(true);
        cons.setCons_Detach(calculateConsDetach(kVA));
        cons.setCons_Pos(tank_L < 800 ? "lv side" : "left");
        if(noOfHvTerminals == 4){
            if(tank_L >= 1260){cons.setCons_Pos("left");}
            else if (tank_L >= 1090) {cons.setCons_Pos("lv side");}
            else if (tank_L >= 950) {cons.setCons_Pos("right");}
            else {cons.setCons_Pos("lv side");}
        }
        cons.setCons_Mounting("lid mounted");

        cons.setCons_Vol(cons_Vol);
        cons.setCons_Dia(cons_Dia);
        cons.setCons_L(cons_L);
        cons.setCons_Thick(2);
        cons.setCons_Flg_Thick(cons.getCons_Thick());
        cons.setCons_Flg_W(35);

        cons.setCons_EPlate_Dia(cons_Dia + (2 * cons.getCons_Thick()) + (2 * cons.getCons_Flg_W()));
        cons.setCons_EPlate_Thick(tank_Thick);

        cons.setCons_EPlate_Hole_PCD(cons_Dia + (2 * cons.getCons_Thick()) + cons.getCons_Flg_W());

//        if (kVA <= 750) {
//            cons.setCons_EPlate_Hole_Dia(10);
//            cons.setCons_Pipe_ID(25);
//            cons.setCons_Pipe_OD(33.8);
//            cons.setCons_Pipe_Flg_OD(115);
//            cons.setCons_Pipe_Flg_Thick(10);
//            cons.setCons_Pipe_Flg_PCD(85);
//            cons.setCons_Pipe_Flg_Bolt_Dia(8);
//            cons.setCons_Pipe_Flg_Bolt_Nos(4);
//        } else if (kVA > 750 && kVA <= 1000) {
//            cons.setCons_EPlate_Hole_Dia(10);
//            cons.setCons_Pipe_ID(25);
//            cons.setCons_Pipe_OD(33.8);
//            cons.setCons_Pipe_Flg_OD(115);
//            cons.setCons_Pipe_Flg_Thick(10);
//            cons.setCons_Pipe_Flg_PCD(85);
//            cons.setCons_Pipe_Flg_Bolt_Dia(8);
//            cons.setCons_Pipe_Flg_Bolt_Nos(4);
//
//        } else if (kVA > 1000 && kVA <= 10000) {
//            cons.setCons_EPlate_Hole_Dia(12);
//            cons.setCons_Pipe_ID(50);
//            cons.setCons_Pipe_OD(60.3);
//            cons.setCons_Pipe_Flg_OD(150);
//            cons.setCons_Pipe_Flg_Thick(16);
//            cons.setCons_Pipe_Flg_PCD(115);
//            cons.setCons_Pipe_Flg_Bolt_Dia(18);
//            cons.setCons_Pipe_Flg_Bolt_Nos(4);
//
//        } else {
//            cons.setCons_EPlate_Hole_Dia(12);
//            cons.setCons_Pipe_ID(80);
//            cons.setCons_Pipe_OD(88.9);
//            cons.setCons_Pipe_Flg_OD(175);
//            cons.setCons_Pipe_Flg_Thick(16);
//            cons.setCons_Pipe_Flg_PCD(115);
//            cons.setCons_Pipe_Flg_Bolt_Dia(14);
//            cons.setCons_Pipe_Flg_Bolt_Nos(4);
//        }
//
//        cons.setCons_EPlate_Hole_Nos(calculateConsEPlateHoleNos(cons_Dia, cons.getCons_EPlate_Hole_Dia()));
//
//        cons.setCons_Pipe_H_Pos(cons.getCons_Pipe_ID() * 2);
//
//        Double cons_Pipe_Slope = null;  // Conservator Pipe Slope
//        Double cons_Pipe_A = null;  // Conservator Pipe A
//        Double cons_Pipe_B = null;  // Conservator Pipe B
//        Double cons_Pipe_C = null;  // Conservator Pipe C
//        Double cons_Pipe_D = null;  // Conservator Pipe D
//        Double cons_Pipe_E = null;  // Conservator Pipe E
//
//
//        if (cons.getCons_Pos().equals("left")) {
//            double Phi = 1.618;
//            cons_Pipe_Slope = 7 * (Math.toRadians(Phi));  // 7 degrees converted to radians
//            double cons_Pipe_Bend_Rad = 1.5 * cons.getCons_Pipe_ID();
//            double cons_Pipe_Bend_BFactor = 0.334595;   // For 7-degree pipe slope
//            double cons_Pipe_Bend_Compensate = Math.sin(cons_Pipe_Slope) * cons_Pipe_Bend_Rad;
//
//            cons.setCons_Pipe_Slope(cons_Pipe_Slope);
//            cons.setCons_Pipe_Bend_Rad(cons_Pipe_Bend_Rad);
//            cons.setCons_Pipe_Bend_BFactor(cons_Pipe_Bend_BFactor);
//            cons.setCons_Pipe_Bend_Compensate(cons_Pipe_Bend_Compensate);
//
//            // Bucholz relay logic
//            if (buchRely.getBuch_Rely()) {
//                if (buchRely.getBuch_Rely_Vlv_Nos() == 0) {
//                    cons_Pipe_A = (double) (2 * cons.getCons_Pipe_ID()) + (2 * cons.getCons_Pipe_ID());
//                    cons_Pipe_B = (double) (4 * cons.getCons_Pipe_ID()) + buchRely.getBuch_Rely_L() + (4 * cons.getCons_Pipe_ID());
//                }
//                if (buchRely.getBuch_Rely_Vlv_Nos() == 1) {
//                    cons_Pipe_A = (double) (2 * cons.getCons_Pipe_ID()) + buchRely.getBuch_Rely_Vlv_L() + (3 * cons.getCons_Pipe_ID());
//                    cons_Pipe_B = (double) (4 * cons.getCons_Pipe_ID()) + buchRely.getBuch_Rely_L() + (4 * cons.getCons_Pipe_ID());
//                }
//                if (buchRely.getBuch_Rely_Vlv_Nos() == 2) {
//                    cons_Pipe_A = (double) (2 * cons.getCons_Pipe_ID()) + buchRely.getBuch_Rely_Vlv_L() + (3 * cons.getCons_Pipe_ID());
//                    cons_Pipe_B = (4 * cons.getCons_Pipe_ID()) + buchRely.getBuch_Rely_L() + (2.5 * cons.getCons_Pipe_ID()) + buchRely.getBuch_Rely_Vlv_Nos() + (4 * cons.getCons_Pipe_ID());
//                }
//            } else {
//                cons_Pipe_A = (double) (3 * cons.getCons_Pipe_ID()) + (2 * cons.getCons_Pipe_ID());
//                cons_Pipe_B = (double) (4 * cons.getCons_Pipe_ID()) + (4 * cons.getCons_Pipe_ID());
//            }
//
//            cons_Pipe_C = (double) (3 * cons.getCons_Pipe_ID()) + lid_Thick;
//            cons_Pipe_D = Math.sin(cons_Pipe_Slope) * cons_Pipe_B;
//            cons_Pipe_E = Math.sqrt(Math.pow(cons_Pipe_B, 2) - Math.pow(cons_Pipe_D, 2));
//
//        }
//        cons.setCons_Pipe_A(cons_Pipe_A);
//        cons.setCons_Pipe_B(cons_Pipe_B);
//        cons.setCons_Pipe_C(cons_Pipe_C);
//        cons.setCons_Pipe_D(cons_Pipe_D);
//        cons.setCons_Pipe_E(cons_Pipe_E);
//
//
//        Double cons_Pipe_L = 0.0;  // Conservator Pipe Length
//        Double cons_V_Pos = null;  // Conservator Pipe Length
//        Double cons_H_Pos = null;  // Conservator Pipe Length
//
//        if (cons.getCons_Pos().equals("lv side")) {
//            cons_V_Pos = (double) hvb_Sup_H + hvb_L1;
//            cons_H_Pos = tank_Thick + tank_Flg_W + (cons_Dia * 0.25);
//            cons_Pipe_L = Math.sqrt(Math.pow((cons.getCons_Pipe_H_Pos() + tank_Thick + cons_H_Pos - tank_Thick), 2) + Math.pow(cons_V_Pos - (cons_Dia / 2), 2)) + cons.getCons_Pipe_ID();
//        } else {
//            cons_V_Pos = lid_Thick + (cons_Pipe_A + cons_Pipe_C + cons_Pipe_D) + (cons_Dia / 2) + cons.getCons_Thick();
//            cons_H_Pos = cons_Pipe_E - tank_Thick - cons.getCons_Pipe_H_Pos();
//
//            // Validate the V.Position of Conservator to match the Height of the bushing
//            if (cons_V_Pos < (hvb_Sup_H + hvb_L1)) {
//                cons_V_Pos = (double) hvb_Sup_H + hvb_L1;
//            }
//
//            // Validate the H.Position of conservator to match the HV Phase-Earth distance from 1st HV Bushing
//            double tank_HVB_Dist = (tank_L / 2) - Hv_Ph_Ph_Incl_cap;
//            if ((cons_H_Pos - (cons_Dia / 2)) + tank_HVB_Dist <= hvb_Ph_Erth_Clear) {
//                cons_H_Pos = Math.floor((((hvb_Ph_Erth_Clear - tank_HVB_Dist) + (cons_Dia / 2)) * 1.2) / 10 + 0.9) * 10;
//
//                // Update the value of 'cons_Pipe_E', 'cons_Pipe_B', 'cons_Pipe_D' w.r.t new Conservator Horizontal position
//                cons_Pipe_E = cons_H_Pos + tank_Thick + cons.getCons_Pipe_H_Pos();
//                cons_Pipe_B = Math.sqrt(Math.pow(cons_Pipe_E, 2) + Math.pow(Math.sin(cons_Pipe_Slope), 2));
//                cons_Pipe_D = Math.sin(cons_Pipe_Slope) * cons_Pipe_B;
//                cons.setCons_Pipe_B(cons_Pipe_B);
//                cons.setCons_Pipe_D(cons_Pipe_D);
//                cons.setCons_Pipe_E(cons_Pipe_E);
//            }
//        }
//        cons.setCons_H_Pos(cons_H_Pos);
//        cons.setCons_V_Pos(cons_V_Pos);
//        cons.setCons_Pipe_L(cons_Pipe_L);

        cons.setCons_V_Pos(gorPipe_Ass_H + cons_Dia/2 + cons.getCons_Thick());
        cons.setCons_Sup_Bracket_W((int) Math.ceil(cons_Dia * 0.6));
        if(tank_L < 800){
            cons.setCons_Sup_L((int) (gorPipe_Ass_L + ((double) cons.getCons_Sup_Bracket_W() /2) + 120 + gorPipe_VPos - ((double) cover_W /2)));
        } else {
            cons.setCons_Sup_L((int) (gorPipe_Ass_L + ((double) cons.getCons_Sup_Bracket_W() /2) + 120 + gorPipe_HPos - ((double) cover_L /2)));
            if(buchholz_Relay){
                cons.setCons_Sup_L((int) ( gorPipe_Ass_L + ((double) cons.getCons_Sup_Bracket_W() /2) + 120 + gorPipe_HPos - ((double) cover_L /2)));
            }
        }


        int Ref_OLGLgh = (int) (Math.floor((cons_Dia * 0.9) / 5) * 3);

        int cons_Olg_L1,cons_Olg_Nos,cons_Olg_Stud_Row_Nos,cons_Olg_L2;
        // Select logic based on Ref_OLGLgh value
        if (Ref_OLGLgh <= 135) {
            cons_Olg_L1 = 132;
            cons_Olg_Nos = 1;
            cons_Olg_Stud_Row_Nos = 2;
            cons_Olg_L2 = 116;
        } else if (Ref_OLGLgh <= 200) {
            cons_Olg_L1 = 196;
            cons_Olg_Nos = 1;
            cons_Olg_Stud_Row_Nos = 3;
            cons_Olg_L2 = 90;
        } else if (Ref_OLGLgh <= 300) {
            cons_Olg_L1 = 256;
            cons_Olg_Nos = 1;
            cons_Olg_Stud_Row_Nos = 4;
            cons_Olg_L2 = 80;
        } else if (Ref_OLGLgh <= 400) {
            cons_Olg_L1 = 196;
            cons_Olg_Nos = 2;
            cons_Olg_Stud_Row_Nos = 3;
            cons_Olg_L2 = 90;
        } else {
            cons_Olg_L1 = 256;
            cons_Olg_Nos = 2;
            cons_Olg_Stud_Row_Nos = 4;
            cons_Olg_L2 = 80;
        }

        cons.setCons_Olg_Nos(cons_Olg_Nos);
        cons.setCons_Olg_L1(cons_Olg_L1);
        cons.setCons_Olg_W1(62);
        cons.setCons_Olg_W2(46);
        cons.setCons_Olg_Thick(15);
        cons.setCons_Olg_L2(cons_Olg_L2);
        cons.setCons_Olg_Stud_Dia(6);
        cons.setCons_Olg_Stud_L(20);
        cons.setCons_Olg_Stud_Row_Nos(cons_Olg_Stud_Row_Nos);
        cons.setCons_Olg_Sup_W(cons.getCons_Olg_W1());
        cons.setCons_Olg_Sup_Thick(3.15);
        cons.setCons_Olg_Sup_Pipe_ID(15);
        cons.setCons_Olg_Sup_Pipe_OD(19);
        cons.setCons_Olg_Sup_Pipe_L(15);
        cons.setCons_Olg_Sup_L(cons_Olg_L1);
        cons.setCons_Olg_Sup_Pipe_Pitch(cons_Olg_L1 - (21.5*2));


        int consBreathH = 0;
        int consBreathDia = 0;
        double consBreatherWt = 0;

        if (kVA <= 63) {
            consBreathH = 115;
            consBreathDia = 90;
            consBreatherWt = 0.125;
        } else if (kVA <= 100) {
            consBreathH = 150;
            consBreathDia = 90;
            consBreatherWt = 0.25;
        } else if (kVA <= 250) {
            consBreathH = 230;
            consBreathDia = 90;
            consBreatherWt = 0.5;
        } else if (kVA <= 500) {
            consBreathH = 250;
            consBreathDia = 120;
            consBreatherWt = 1;
        } else if (kVA <= 1000) {
            consBreathH = 340;
            consBreathDia = 120;
            consBreatherWt = 1.5;
        } else if (kVA <= 2000) {
            consBreathH = 420;
            consBreathDia = 120;
            consBreatherWt = 2;
        } else if (kVA <= 3000) {
            consBreathH = 420;
            consBreathDia = 140;
            consBreatherWt = 3;
        } else if (kVA <= 5000) {
            consBreathH = 320;
            consBreathDia = 200;
            consBreatherWt = 4;
        } else if (kVA <= 8000) {
            consBreathH = 370;
            consBreathDia = 200;
            consBreatherWt = 5;
        } else if (kVA <= 15000) {
            consBreathH = 420;
            consBreathDia = 200;
            consBreatherWt = 6;
        } else if (kVA <= 20000) {
            consBreathH = 520;
            consBreathDia = 200;
            consBreatherWt = 8;
        } else {
            System.out.println("KVA not found in table");
        }

        cons.setCons_Breath(true);
        cons.setCons_Breath_Dia(consBreathDia);
        cons.setCons_Breath_H(consBreathH);
        cons.setCons_Breath_Wt(consBreatherWt);

//        cons.setCons_Drain_ID(cons.getCons_Pipe_ID());
//        cons.setCons_Drain_OD(cons.getCons_Pipe_OD());
//        cons.setCons_Drain_L((double) cons.getCons_Pipe_ID() / 2);
//        cons.setCons_Filler_ID(cons.getCons_Pipe_ID());
//        cons.setCons_Filler_OD(cons.getCons_Pipe_OD());
//        cons.setCons_Filler_L((double) cons.getCons_Pipe_ID() / 2);


//        String cons_Sup_Sect = null;
//        if (kVA <= 315) {
//            cons_Sup_Sect = "40x40x5-L";
//        } else if (kVA <= 500) {
//            cons_Sup_Sect = "50x50x6-L";
//        } else if (kVA <= 1600) {
//            cons_Sup_Sect = "65x65x8-L";
//        } else if (kVA <= 3150) {
//            cons_Sup_Sect = "75x40x4.8-C";
//        } else {
//            cons_Sup_Sect = "100x50x5-C";
//        }


//        cons.setCons_Sup_Sect(cons_Sup_Sect);
//        cons.setCons_Sup_Sect_L1(calculateConsSupSectL1(cons_H_Pos, tank_Flg_W, tank_Thick, cons_Dia) );
//        cons.setCons_Sup_Sect_L2(calculateConsSupSectL2(tank_Flg_W, tank_Flg_Bolt_HPch));
//        cons.setCons_Sup_Plt_Thick(6);
//        cons.setCons_Sup_BPlate_W(40);
//        cons.setCons_Sup_Hole_Dim(tank_Flg_Bolt_Dia);
//
//        cons.setCons_Sup_Hole_Pitch(tank_Flg_Bolt_HPch);


        Integer cons_Logo_Plate_L = null;
        Double cons_Logo_Plate_W = null;
        Integer cons_Logo_Plate_H = null;
        Double logo_Aspect_Ratio = 0.25; // This was 1.0 previously

        if (cons_Dia <= 250) {
            cons_Logo_Plate_L = 225;
            cons_Logo_Plate_W = cons_Logo_Plate_L * logo_Aspect_Ratio;
            cons_Logo_Plate_H = 25;
        } else if (cons_Dia > 250 && cons_Dia <= 450) {
            cons_Logo_Plate_L = 300;
            cons_Logo_Plate_W = Math.floor(cons_Logo_Plate_L * logo_Aspect_Ratio);
            cons_Logo_Plate_H = 30;
        } else if (cons_Dia > 450 && cons_Dia <= 550) {
            cons_Logo_Plate_L = 375;
            cons_Logo_Plate_W = cons_Logo_Plate_L * logo_Aspect_Ratio;
            cons_Logo_Plate_H = 35;
        } else {
            cons_Logo_Plate_L = 450;
            cons_Logo_Plate_W = cons_Logo_Plate_L * logo_Aspect_Ratio;
            cons_Logo_Plate_H = 50;
        }

        cons.setCons_Logo_Plate_L(cons_Logo_Plate_L);
        cons.setCons_Logo_Plate_W(cons_Logo_Plate_W);
        cons.setCons_Logo_Plate_H(cons_Logo_Plate_H);
        cons.setCons_Logo_Plate_Thick(2);


        return cons;

    }

}
