package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Hvb;

import java.util.*;


public class HvBFormulas {

    // Hvb_S_Tilt_Ang Calculation
    public  static double calculateHvb_S_Tilt_Ang(Integer tank_L, Double hvb_Ptch,
                                                  Integer hvb_tank_Oil_Clear, Integer hvb_Sup_H, Integer hvb_L1){
        double hvb_S_Tilt_Ang=0;
        if ((tank_L / 2.0 - hvb_Ptch) < hvb_tank_Oil_Clear) {
            double tilt_angle = Math.atan2(hvb_tank_Oil_Clear ,(hvb_Sup_H + hvb_L1));
            hvb_S_Tilt_Ang = (int) ((tilt_angle * 180.0 / Math.PI) + 2.9);
        }
        return hvb_S_Tilt_Ang;
        //TODO: is there an else condition or default tilt angle?
    }

    // Hvb_Sup_L Calculation
    public static Double calculateHvb_Sup_L(Double hvb_Ptch, int hvb_Nos, Integer hvb_Sup_ID){
        return (hvb_Ptch * (hvb_Nos - 1)) + (2 * hvb_Sup_ID);
    }

    // Hvb_Sup_OD Calculation
    public static Integer calculateHvb_Sup_OD(Integer hvb_Sup_ID, Integer hvb_Sup_Thick){
        return (hvb_Sup_ID + (2 * hvb_Sup_Thick));
    }

    // Hvb_Sup_Flg_W Calculation
    public static Double calculateHvb_Sup_Flg_W(Integer hvb_Sup_Flg_OD, Integer hvb_Sup_ID){
        return (double) ((hvb_Sup_Flg_OD - hvb_Sup_ID) / 2);
    }

    public static String checkHvb_Sup_type(String hvbSuptype) {
        if(hvbSuptype.equals("")){
            return "turret";
        }else{
            return hvbSuptype;
        }
    }

    public static Hvb calcuateHvb(String hvb_Pos, Integer hvb_Volt, Integer hvb_Amp, Integer voltage, boolean hvcbFromInput,
                                  String hvb_Type, Double current, Double hvb_F_Tilt_Ang,
                                  Integer tank_L, Integer tank_Thick, String hvbSuptype, Integer lid_Thick, int hvb_Nos, Integer tank_W, int KVA,
                                  Integer tank_H) {


        HvLvCommonVariables hvLvCommonVariables = HvLvCommonMethods.calcuateHvLvCommonVariables(voltage, hvcbFromInput,hvb_Amp);


        Hvb hvb = new Hvb();
        hvb.setHvb_Pos(hvb_Pos);
        hvb.setHvb_Volt(hvb_Volt);
        hvb.setHvb_Amp(hvb_Amp);
        hvb.setHvb_Ph_Erth_Clear(40);  //TODO:  Use 'Bushing_L1' from 'Bushing_Data.txt'

        Integer hvb_tank_Oil_Clear= hvLvCommonVariables.getTankClrnce();
        hvb.setHvb_Sup_V_Pos(Double.valueOf(hvb_tank_Oil_Clear));
        hvb.setHvb_tank_Oil_Clear(hvb_tank_Oil_Clear);

        Double hvb_Ptch = (double) hvLvCommonVariables.getCap_Dia() + hvLvCommonVariables.getPh2ph();
        hvb_Ptch = 315.0; // Overriding the previous statement. (The previous st. was by Suveer sir)
        ///The 315.0mm clr is for 17.5kV 250A; given by NB.
        hvb.setHvb_Ptch(hvb_Ptch);
        hvb.setHvb_VPos(tank_W/2 - 75);
        hvb.setHvb_Sup_H(hvb.getHvb_L2());

        hvb.setHvb_Type(hvb_Type);
        hvb.setHvb_Nos(hvb_Nos);

        //L1 is the distance from Lid to top of the bushing. (by SD)
        //L2 is the distance from lid to bottom of bushing. (by SD)
        Integer hvb_L1= hvLvCommonVariables.getBushing_L1();
        Integer hvb_L2= hvLvCommonVariables.getBushing_L2();

        hvb.setHvb_L1(hvb_L1);
        hvb.setHvb_L2(hvb_L2);

        Integer hvb_Sup_H=hvb_L2;
        hvb.setHvb_Sup_H(hvb_Sup_H);

        hvb.setHvb_F_Tilt_Ang(hvb_F_Tilt_Ang);
        hvb.setHvb_S_Tilt_Ang(calculateHvb_S_Tilt_Ang(tank_L,hvb_Ptch,hvb_tank_Oil_Clear,hvb_Sup_H,hvb_L1));
        if(hvb_Nos == 3){
            if(hvb_Volt <= 1100){
                if(hvb_Amp <= 250){
                    hvb.setHvb_Ptch(110.0);
                    hvb.setHvb_VPos(tank_W/2 - 40);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                }
                else if(hvb_Amp <= 630){
                    hvb.setHvb_Ptch(125.0);
                    hvb.setHvb_VPos(tank_W/2 - 50);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                }
                else if(hvb_Amp <= 1000){
                    hvb.setHvb_Ptch(150.0);
                    hvb.setHvb_VPos(tank_W/2 - 65);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                }
                else if(hvb_Amp <= 4000){
                    hvb.setHvb_Ptch(150.0);
                    hvb.setHvb_VPos(tank_W/2 - 65);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);

                }
                else {
                    hvb.setHvb_Ptch(120.0);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                    hvb.setHvb_VPos(tank_W/2 - 50);
                }
            }
            else if(hvb_Volt <= 17500){
                if(hvb_Amp <= 250){
                    if(tank_L <= 590){
                        if(tank_W <= 275){
                            //t45-tf-t45
                            hvb.setHvb1_Bush_Rot_Ang(45);
                            hvb.setHvb1_F_Tilt_Ang(30);
                            hvb.setHvb2_Bush_Rot_Ang(90);
                            hvb.setHvb2_F_Tilt_Ang(30);
                            hvb.setHvb3_Bush_Rot_Ang(45);
                            hvb.setHvb3_F_Tilt_Ang(30);
                            hvb.setHvb_Ptch(180.0);
                            hvb.setHvb_VPos(tank_W/2 - 50);
                        } else{
                            //ta-tf-ta
                            hvb.setHvb1_Bush_Rot_Ang(0);
                            hvb.setHvb1_F_Tilt_Ang(30);
                            hvb.setHvb2_Bush_Rot_Ang(90);
                            hvb.setHvb2_F_Tilt_Ang(30);
                            hvb.setHvb3_Bush_Rot_Ang(0);
                            hvb.setHvb3_F_Tilt_Ang(30);
                            hvb.setHvb_Ptch(180.0);
                            hvb.setHvb_VPos(tank_W/2 - 75);
                        }
                    }
                    else if(tank_L < 705){
                        //ta - erect - ta
                        hvb.setHvb1_Bush_Rot_Ang(0);
                        hvb.setHvb1_F_Tilt_Ang(30);
                        hvb.setHvb2_Bush_Rot_Ang(0);
                        hvb.setHvb2_F_Tilt_Ang(0);
                        hvb.setHvb3_Bush_Rot_Ang(0);
                        hvb.setHvb3_F_Tilt_Ang(30);
                        hvb.setHvb_Ptch(235.0);
                        hvb.setHvb_VPos(tank_W/2 - 50);
                    }
                    else if(tank_L < 800){
                        if(tank_W < 260){
                            //tf-tf-tf
                            hvb.setHvb1_Bush_Rot_Ang(90);
                            hvb.setHvb1_F_Tilt_Ang(30);
                            hvb.setHvb2_Bush_Rot_Ang(90);
                            hvb.setHvb2_F_Tilt_Ang(30);
                            hvb.setHvb3_Bush_Rot_Ang(90);
                            hvb.setHvb3_F_Tilt_Ang(30);
                            hvb.setHvb_Ptch(305.0);
                            hvb.setHvb_VPos(tank_W/2 - 50);
                        }else {
                            //e-e-e
                            hvb.setHvb1_Bush_Rot_Ang(0);
                            hvb.setHvb1_F_Tilt_Ang(0);
                            hvb.setHvb2_Bush_Rot_Ang(0);
                            hvb.setHvb2_F_Tilt_Ang(0);
                            hvb.setHvb3_Bush_Rot_Ang(0);
                            hvb.setHvb3_F_Tilt_Ang(0);
                            hvb.setHvb_Ptch(305.0);
                            hvb.setHvb_VPos(tank_W/2 - 60);
                        }
                    }
                    //Tank_L 800 onwards, the conservator is on the Left side
                    else if(tank_L <= 940){
                        //tf-e-tf
                        hvb.setHvb1_Bush_Rot_Ang(90);
                        hvb.setHvb1_F_Tilt_Ang(30);
                        hvb.setHvb2_Bush_Rot_Ang(0);
                        hvb.setHvb2_F_Tilt_Ang(0);
                        hvb.setHvb3_Bush_Rot_Ang(90);
                        hvb.setHvb3_F_Tilt_Ang(30);
                        hvb.setHvb_Ptch(235.0);
                        hvb.setHvb_VPos(tank_W < 275 ? tank_W/2 -50 : tank_W/2 - 75);
                    }
                    else {
                        //e-e-e
                        hvb.setHvb1_Bush_Rot_Ang(0);
                        hvb.setHvb1_F_Tilt_Ang(0);
                        hvb.setHvb2_Bush_Rot_Ang(0);
                        hvb.setHvb2_F_Tilt_Ang(0);
                        hvb.setHvb3_Bush_Rot_Ang(0);
                        hvb.setHvb3_F_Tilt_Ang(0);
                        hvb.setHvb_Ptch(315.0);
                        hvb.setHvb_VPos(tank_W < 275 ? tank_W/2 -50 : tank_W/2 - 75);
                    }
                }
                else {
                    //e-e-e
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                    hvb.setHvb_Ptch(330.0);
                    hvb.setHvb_VPos(tank_W/2 - 75);
                }
            }
            else if (hvb_Volt <= 36000){
                if(hvb_Amp <= 250){hvb.setHvb_Ptch(410.0);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                    hvb.setHvb_VPos(tank_W/2 - 70);
                } else {
                    hvb.setHvb_Ptch(410.0);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                    hvb.setHvb_VPos(tank_W/2 - 75);

                }
            }
        }
        else if(hvb_Nos == 4){
            hvb.setHvb_Neutral_Bushing(true);
            if(hvb_Volt <= 1100){
                if(hvb_Amp <= 250){
                    hvb.setHvb_Ptch(110.0);
                    hvb.setHvb_VPos(tank_W/2 - 40);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                }
                else if(hvb_Amp <= 630){
                    hvb.setHvb_Ptch(125.0);
                    hvb.setHvb_VPos(tank_W/2 - 50);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                }
                else if(hvb_Amp <= 1000){
                    hvb.setHvb_Ptch(150.0);
                    hvb.setHvb_VPos(tank_W/2 - 65);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                }
                else if(hvb_Amp <= 4000){
                    hvb.setHvb_Ptch(150.0);
                    hvb.setHvb_VPos(tank_W/2 - 65);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);

                }
                else {
                    hvb.setHvb_Ptch(120.0);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                    hvb.setHvb_VPos(tank_W/2 - 50);
                }
                hvb.setHvb_Ofst(0);
                hvb.setHvb_Neutral_HPos((int) (hvb.getHvb_Ptch() * 1.5));
                hvb.setHvb_Neutral_VPos(hvb.getHvb_VPos());
            }
            else if(hvb_Volt <= 17500){
                if(hvb_Amp <= 250){
                    if(tank_L >= 1260){
                        hvb.setHvb1_Bush_Rot_Ang(0);
                        hvb.setHvb1_F_Tilt_Ang(0);
                        hvb.setHvb2_Bush_Rot_Ang(0);
                        hvb.setHvb2_F_Tilt_Ang(0);
                        hvb.setHvb3_Bush_Rot_Ang(0);
                        hvb.setHvb3_F_Tilt_Ang(0);
                        hvb.setHvb_Ptch(315.0);
                        hvb.setHvb_VPos(tank_W/2 - 75);
                        hvb.setHvb_Neutral_HPos((int) (hvb.getHvb_Ptch() * 1.5));
                        hvb.setHvb_Neutral_VPos(hvb.getHvb_VPos());
                        hvb.setHvb_Neutral_F_Tilt_Ang(0);
                        hvb.setHvb_neutral_Bush_Rot_Ang(0);
                    } else if (tank_L >= 1090) {
                        hvb.setHvb1_Bush_Rot_Ang(0);
                        hvb.setHvb1_F_Tilt_Ang(0);
                        hvb.setHvb2_Bush_Rot_Ang(0);
                        hvb.setHvb2_F_Tilt_Ang(0);
                        hvb.setHvb3_Bush_Rot_Ang(0);
                        hvb.setHvb3_F_Tilt_Ang(0);
                        hvb.setHvb_Ptch(315.0);
                        hvb.setHvb_VPos(tank_W/2 - 75);
                        hvb.setHvb_Neutral_HPos((int) (hvb.getHvb_Ptch() * 1.5));
                        hvb.setHvb_Neutral_VPos(hvb.getHvb_VPos());
                        hvb.setHvb_Neutral_F_Tilt_Ang(0);
                        hvb.setHvb_neutral_Bush_Rot_Ang(0);
                    } else if (tank_L >= 950) {
                        hvb.setHvb1_Bush_Rot_Ang(0);
                        hvb.setHvb1_F_Tilt_Ang(0);
                        hvb.setHvb2_Bush_Rot_Ang(0);
                        hvb.setHvb2_F_Tilt_Ang(0);
                        hvb.setHvb3_Bush_Rot_Ang(0);
                        hvb.setHvb3_F_Tilt_Ang(0);
                        hvb.setHvb_Ptch(315.0);
                        hvb.setHvb_VPos(tank_W/2 - 75);
                        hvb.setHvb_Neutral_HPos(-(tank_L/2 - 75));
                        hvb.setHvb_Neutral_VPos(-hvb.getHvb_VPos());
                        hvb.setHvb_Neutral_F_Tilt_Ang(30);
                        hvb.setHvb_neutral_Bush_Rot_Ang(45);
                    } else if (tank_L >= 830) {
                        hvb.setHvb1_Bush_Rot_Ang(0);
                        hvb.setHvb1_F_Tilt_Ang(0);
                        hvb.setHvb2_Bush_Rot_Ang(0);
                        hvb.setHvb2_F_Tilt_Ang(0);
                        hvb.setHvb3_Bush_Rot_Ang(0);
                        hvb.setHvb3_F_Tilt_Ang(0);
                        hvb.setHvb_Ptch(315.0);
                        hvb.setHvb_Neutral_HPos(-(tank_L/2 - 75));
                        hvb.setHvb_Neutral_VPos(-hvb.getHvb_VPos());
                        hvb.setHvb_Neutral_F_Tilt_Ang(30);
                        hvb.setHvb_neutral_Bush_Rot_Ang(45);

                    } else {
                        hvb.setHvb1_Bush_Rot_Ang(0);
                        hvb.setHvb1_F_Tilt_Ang(30);
                        hvb.setHvb2_Bush_Rot_Ang(0);
                        hvb.setHvb2_F_Tilt_Ang(0);
                        hvb.setHvb3_Bush_Rot_Ang(0);
                        hvb.setHvb3_F_Tilt_Ang(30);
                        hvb.setHvb_Ptch(235.0);
                        hvb.setHvb_Neutral_HPos(-(tank_L/2 - 75));
                        hvb.setHvb_Neutral_VPos(-hvb.getHvb_VPos());
                        hvb.setHvb_Neutral_F_Tilt_Ang(30);
                        hvb.setHvb_neutral_Bush_Rot_Ang(45);
                    }


                }
                else {
                    //e-e-e
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                    hvb.setHvb_Ptch(330.0);
                    hvb.setHvb_VPos(tank_W/2 - 75);
                }

            }
            else if (hvb_Volt <= 36000){
                if(hvb_Amp <= 250){hvb.setHvb_Ptch(410.0);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                    hvb.setHvb_VPos(tank_W/2 - 70);
                }
                else {
                    hvb.setHvb_Ptch(410.0);
                    hvb.setHvb1_Bush_Rot_Ang(0);
                    hvb.setHvb1_F_Tilt_Ang(0);
                    hvb.setHvb2_Bush_Rot_Ang(0);
                    hvb.setHvb2_F_Tilt_Ang(0);
                    hvb.setHvb3_Bush_Rot_Ang(0);
                    hvb.setHvb3_F_Tilt_Ang(0);
                    hvb.setHvb_VPos(tank_W/2 - 75);

                }
                hvb.setHvb_Ofst(0);
                hvb.setHvb_Neutral_HPos((int) (hvb.getHvb_Ptch() * 1.5));
                hvb.setHvb_Neutral_VPos(hvb.getHvb_VPos());
            }
        }
        // condition: if lv has bushing or CB on tank, the following condition will follow.

        Integer hvb_Sup_ID= hvLvCommonVariables.getBoredia();
        hvb.setHvb_Sup_ID(hvb_Sup_ID);

        Integer hvb_Sup_Thick=tank_Thick;
        hvb.setHvb_Sup_Thick(hvb_Sup_Thick);

        String hvb_Sup_type=checkHvb_Sup_type(hvbSuptype);
        hvb.setHvb_Sup_type(hvb_Sup_type);

        hvb.setHvb_Sup_L(calculateHvb_Sup_L(hvb_Ptch,hvb_Nos,hvb_Sup_ID));
        hvb.setHvb_Sup_W(hvb_Sup_ID*2);
        hvb.setHvb_Sup_OD(calculateHvb_Sup_OD(hvb_Sup_ID,hvb_Sup_Thick));
        hvb.setHvb_Sup_Flg_Thick(lid_Thick);
        hvb.setHvb_Sup_Flg_Stud_Dia(hvLvCommonVariables.getStud_Dia());
        hvb.setHvb_Sup_Flg_Stud_L(hvLvCommonVariables.getStud_L());
        hvb.setHvb_Sup_Flg_Stud_Nos(hvLvCommonVariables.getStud_Nos());
        hvb.setHvb_Sup_Flg_ID(hvLvCommonVariables.getBoredia());

        Integer hvb_Sup_Flg_OD= hvLvCommonVariables.getSupFlangeDia();
        hvb.setHvb_Sup_Flg_OD(hvb_Sup_Flg_OD);

        hvb.setHvb_Sup_Flg_W(calculateHvb_Sup_Flg_W(hvb_Sup_Flg_OD,hvb_Sup_ID));
        hvb.setHvb_Sup_Flg_PCD(hvLvCommonVariables.getSupFlangePCD());
        hvb.setHvb_Tank_On_Wall_DesNo(null);

        hvb.setHvb_OnTank_Length(null);
        if(Objects.equals(hvb_Pos, "tank")){
            if(tank_L > 660 && tank_L <= 830){
                hvb.setHvb_Tank_On_Wall_DesNo(166001);
                hvb.setHvb_OnTank_Length(700);
                hvb.setHvb_OnTank_cutout_L(610);
                hvb.setHvb_OnTank_cutout_W(240);
            } else if (tank_L > 830) {
                hvb.setHvb_Tank_On_Wall_DesNo(166002);
                hvb.setHvb_OnTank_Length(760);
                hvb.setHvb_OnTank_cutout_L(780);
                hvb.setHvb_OnTank_cutout_W(240);
            } else {
                hvb.setHvb_Tank_On_Wall_DesNo(0);
                hvb.setHvb_OnTank_Length(0);
                hvb.setHvb_OnTank_cutout_L(0);
                hvb.setHvb_OnTank_cutout_W(0);
            }
            hvb.setHvb_OnTank_cutout_Hpos(0);
            hvb.setHvb_OnTank_cutout_Vpos(tank_H/2 - 60 - hvb.getHvb_OnTank_cutout_W()/2);
        }



        return hvb;
    }


}
