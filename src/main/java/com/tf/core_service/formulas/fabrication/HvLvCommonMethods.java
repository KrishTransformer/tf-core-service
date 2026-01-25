package com.tf.core_service.formulas.fabrication;


public class HvLvCommonMethods {

    //TODO: verify the following values and check if they are according to the IS.
    public static HvLvCommonVariables calcuateHvLvCommonVariables(Integer voltage, boolean cableBox, Integer current) {
        int ph2ph = 0;
        int ph2earth = 0;
        int tankClrnce = 0;
        int blength = 0;
        int noofpeticoat = 0;
        int peticoat = 0;
        int boltdia = 0;
        int boredia = 0;
        int bushing_L1 = 0;
        int bushing_L2 = 0;
        int Cap_Dia = 0;
        int supFlangePCD;
        int supFlangeDia = 0;
        int stud_L = 0;
        int stud_Dia = 0;
        int stud_Nos = 0;

        if (voltage <= 500) {
            if (cableBox) {
                ph2ph = 40;
                ph2earth = 40;
            } else {
                ph2ph = 40;
                ph2earth = 40;
            }

            tankClrnce = 30;
            blength = 50;
            noofpeticoat = 1;

            // Get other Dimensions w.r.t Current Rating
            if (current <= 250) {
                peticoat = 50;
                if (cableBox) {
                    boltdia = 12;
                    boredia = 30;
                    bushing_L1 = 45;
                    bushing_L2 = 25;
                    Cap_Dia = 28;
                    supFlangePCD = 0;
                } else {
                    boltdia = 12;
                    boredia = 30;
                    bushing_L1 = 45;
                    bushing_L2 = 25;
                    Cap_Dia = 28;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else if (current <= 630) {
                peticoat = 70;
                if (cableBox) {
                    boltdia = 20;
                    boredia = 45;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 40;
                    supFlangePCD = 0;
                } else {
                    boltdia = 20;
                    boredia = 45;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 40;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else if (current <= 1000) {
                peticoat = 90;
                if (cableBox) {
                    boltdia = 30;
                    boredia = 56;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 56;
                    supFlangePCD = 0;
                } else {
                    boltdia = 30;
                    boredia = 56;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 56;
                    supFlangeDia = 90;
                    supFlangePCD = 0;
                }
            } else if (current <= 2000) {
                peticoat = 104;
                if (cableBox) {
                    boltdia = 42;
                    boredia = 70;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 70;
                    supFlangePCD = 0;
                } else {
                    boltdia = 42;
                    boredia = 70;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 70;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else { // current > 2000
                peticoat = 125;
                if (cableBox) {
                    boltdia = 48;
                    boredia = 90;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 80;
                    supFlangePCD = 0;
                } else {
                    boltdia = 42;
                    boredia = 70;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 70;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            }
        }

        else if (voltage <= 1000) {
            if (cableBox) {
                ph2ph = 120;
                ph2earth = 120;
            } else {
                ph2ph = 120;
                ph2earth = 120;
            }

            tankClrnce = 30;
            blength = 55;

            // Get other Dimensions w.r.t Current Rating
            if (current <= 250) {
                peticoat = 50;
                if (cableBox) {
                    boltdia = 12;
                    boredia = 30;
                    bushing_L1 = 45;
                    bushing_L2 = 25;
                    Cap_Dia = 28;
                    supFlangePCD = 0;
                } else {
                    boltdia = 12;
                    boredia = 30;
                    bushing_L1 = 45;
                    bushing_L2 = 25;
                    Cap_Dia = 28;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else if (current <= 630) {
                peticoat = 70;
                if (cableBox) {
                    boltdia = 20;
                    boredia = 45;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 40;
                    supFlangePCD = 0;
                } else {
                    boltdia = 20;
                    boredia = 45;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 40;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else if (current <= 1000) {
                peticoat = 90;
                if (cableBox) {
                    boltdia = 30;
                    boredia = 56;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 56;
                    supFlangePCD = 0;
                } else {
                    boltdia = 30;
                    boredia = 56;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 56;
                    supFlangeDia = 90;
                    supFlangePCD = 0;
                }
            } else if (current <= 2000) {
                peticoat = 104;
                if (cableBox) {
                    boltdia = 42;
                    boredia = 70;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 70;
                    supFlangePCD = 0;
                } else {
                    boltdia = 42;
                    boredia = 70;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 70;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else { // current > 2000
                peticoat = 125;
                if (cableBox) {
                    boltdia = 48;
                    boredia = 90;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 80;
                    supFlangePCD = 0;
                } else {
                    boltdia = 42;
                    boredia = 70;
                    bushing_L1 = 55;
                    bushing_L2 = 25;
                    Cap_Dia = 70;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            }
        }

        else if (voltage <= 3600) {
            if (cableBox) {
                ph2ph = 120;
                ph2earth = 120;
            } else {
                ph2ph = 120;
                ph2earth = 120;

            }
            tankClrnce = 60;
            blength = 85;
            noofpeticoat = 1;

            // Get other Dimensions w.r.t Current Rating
            if (current <= 250) {
                peticoat = 75;
                if (cableBox) {
                    boltdia = 12;
                    boredia = 40;
                    Cap_Dia = 32;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    supFlangePCD = 0;
                } else {
                    boltdia = 12;
                    boredia = 40;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    Cap_Dia = 32;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else if (current <= 630) {
                peticoat = 90;
                if (cableBox) {
                    boltdia = 20;
                    boredia = 45;
                    Cap_Dia = 47;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    supFlangePCD = 0;
                } else {
                    boltdia = 20;
                    boredia = 45;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    Cap_Dia = 47;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else if (current <= 1000) {
                peticoat = 110;
                if (cableBox) {
                    boltdia = 30;
                    boredia = 55;
                    Cap_Dia = 65;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    supFlangePCD = 0;
                } else {
                    boltdia = 30;
                    boredia = 55;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    Cap_Dia = 65;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else if (current <= 2000) {
                peticoat = 125;
                if (cableBox) {
                    boltdia = 42;
                    boredia = 70;
                    Cap_Dia = 80;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    supFlangePCD = 0;
                } else {
                    boltdia = 42;
                    boredia = 70;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    Cap_Dia = 80;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            } else { // current > 2000
                peticoat = 145;
                if (cableBox) {
                    boltdia = 48;
                    boredia = 90;
                    Cap_Dia = 100;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    supFlangePCD = 0;
                } else {
                    boltdia = 48;
                    boredia = 90;
                    bushing_L1 = 85;
                    bushing_L2 = 50;
                    Cap_Dia = 100;
                    supFlangeDia = 0;
                    supFlangePCD = 0;
                }
            }
        }

        else if (voltage <= 17500) {

            if (cableBox) {
                ph2ph = 105;
                ph2earth = 105;
            } else {
                ph2ph = 280;
                ph2earth = 140;
            }
            tankClrnce = 100;
            blength = 235;
            noofpeticoat = 2;

            // Get other Dimensions w.r.t Current Rating
            if (current <= 250) {
                peticoat = 140;
                if (cableBox) {
                    boltdia = 12;
                    boredia = 75;
                    bushing_L1 = 230;
                    bushing_L2 = 65;
                    Cap_Dia = 50;
                    supFlangePCD = 95;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 4;
                } else {
                    boltdia = 12;
                    boredia = 75;
                    bushing_L1 = 230;
                    bushing_L2 = 65;
                    Cap_Dia = 50;
                    supFlangePCD = 123;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            } else if (current <= 630) {
                peticoat = 150;
                if (cableBox) {
                    boltdia = 20;
                    boredia = 90;
                    bushing_L1 = 230;
                    bushing_L2 = 65;
                    Cap_Dia = 60;
                    supFlangePCD = 140;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 20;
                    boredia = 90;
                    bushing_L1 = 230;
                    bushing_L2 = 65;
                    Cap_Dia = 60;
                    supFlangePCD = 140;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            } else if (current <= 1000) {
                peticoat = 170;
                if (cableBox) {
                    boltdia = 30;
                    boredia = 110;
                    bushing_L1 = 235;
                    bushing_L2 = 90;
                    Cap_Dia = 100;
                    supFlangePCD = 180;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 30;
                    boredia = 110;
                    bushing_L1 = 235;
                    bushing_L2 = 90;
                    Cap_Dia = 100;
                    supFlangePCD = 180;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            } else if (current <= 2000) {
                peticoat = 190;
                if (cableBox) {
                    boltdia = 42;
                    boredia = 134;
                    bushing_L1 = 235;
                    bushing_L2 = 90;
                    Cap_Dia = 120;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 12;
                    boredia = 134;
                    bushing_L1 = 235;
                    bushing_L2 = 90;
                    Cap_Dia = 120;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            } else { // current > 2000
                peticoat = 190;
                if (cableBox) {
                    boltdia = 48;
                    boredia = 134;
                    bushing_L1 = 235;
                    bushing_L2 = 90;
                    Cap_Dia = 120;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 48;
                    boredia = 134;
                    bushing_L1 = 235;
                    bushing_L2 = 90;
                    Cap_Dia = 120;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            }
        }

        else if (voltage <= 24000) {

            if (cableBox) {
                ph2ph = 170;
                ph2earth = 170;
            } else {
                ph2ph = 330;
                ph2earth = 230;
            }
            tankClrnce = 125;
            blength = 310;
            noofpeticoat = 3;

            // Get other Dimensions w.r.t Current Rating
            if (current <= 250) {
                peticoat = 155;
                if (cableBox) {
                    boltdia = 12;
                    boredia = 75;
                    bushing_L1 = 305;
                    bushing_L2 = 80;
                    Cap_Dia = 50;
                    supFlangePCD = 123;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 4;
                } else {
                    boltdia = 12;
                    boredia = 75;
                    bushing_L1 = 305;
                    bushing_L2 = 80;
                    Cap_Dia = 50;
                    supFlangePCD = 123;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 4;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            } else if (current <= 630) {
                peticoat = 165;
                if (cableBox) {
                    boltdia = 20;
                    boredia = 90;
                    bushing_L1 = 305;
                    bushing_L2 = 80;
                    Cap_Dia = 100;
                    supFlangePCD = 140;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 6;
                } else {
                    boltdia = 20;
                    boredia = 90;
                    bushing_L1 = 305;
                    bushing_L2 = 80;
                    Cap_Dia = 100;
                    supFlangePCD = 140;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            } else if (current <= 1000) {
                peticoat = 185;
                if (cableBox) {
                    boltdia = 30;
                    boredia = 110;
                    bushing_L1 = 310;
                    bushing_L2 = 100;
                    Cap_Dia = 120;
                    supFlangePCD = 180;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 30;
                    boredia = 110;
                    bushing_L1 = 310;
                    bushing_L2 = 100;
                    Cap_Dia = 120;
                    supFlangePCD = 180;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            } else if (current <= 2000) {
                peticoat = 210;
                if (cableBox) {
                    boltdia = 42;
                    boredia = 134;
                    bushing_L1 = 310;
                    bushing_L2 = 100;
                    Cap_Dia = 120;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 42;
                    boredia = 134;
                    bushing_L1 = 310;
                    bushing_L2 = 100;
                    Cap_Dia = 120;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            } else { // current > 2000
                peticoat = 210;
                if (cableBox) {
                    boltdia = 48;
                    boredia = 134;
                    bushing_L1 = 310;
                    bushing_L2 = 100;
                    Cap_Dia = 120;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 48;
                    boredia = 134;
                    bushing_L1 = 310;
                    bushing_L2 = 100;
                    Cap_Dia = 120;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD+ (2 * stud_Dia);
                }
            }
        }

        else if (voltage <= 36000) {

            if (cableBox) {
                ph2ph = 200;
                ph2earth = 200;
            } else {
                ph2ph = 350;
                ph2earth = 320;
            }
            tankClrnce = 150;
            blength = 410;
            noofpeticoat = 4;

            // Get other Dimensions w.r.t Current Rating
            if (current <= 250) {
                peticoat = 200;
                if (cableBox) {
                    boltdia = 12;
                    boredia = 75;
                    bushing_L1 = 405;
                    bushing_L2 = 80;
                    Cap_Dia = 45;
                    supFlangePCD = 123;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 4;
                } else {
                    boltdia = 12;
                    boredia = 75;
                    bushing_L1 = 405;
                    bushing_L2 = 80;
                    Cap_Dia = 45;
                    supFlangePCD = 123;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 4;
                    supFlangeDia = supFlangePCD + (2 * stud_Dia);
                }
            } else if (current <= 630) {
                peticoat = 208;
                if (cableBox) {
                    boltdia = 20;
                    boredia = 90;
                    bushing_L1 = 410;
                    bushing_L2 = 100;
                    Cap_Dia = 53;
                    supFlangePCD = 140;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 6;
                } else {
                    boltdia = 20;
                    boredia = 90;
                    bushing_L1 = 410;
                    bushing_L2 = 100;
                    Cap_Dia = 53;
                    supFlangePCD = 140;
                    stud_L = 55;
                    stud_Dia = 10;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD + (2 * stud_Dia);
                }
            } else if (current <= 1000) {
                peticoat = 230;
                if (cableBox) {
                    boltdia = 30;
                    boredia = 110;
                    bushing_L1 = 415;
                    bushing_L2 = 125;
                    Cap_Dia = 80;
                    supFlangePCD = 180;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 30;
                    boredia = 110;
                    bushing_L1 = 415;
                    bushing_L2 = 125;
                    Cap_Dia = 80;
                    supFlangePCD = 180;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD + (2 * stud_Dia);
                }
            } else if (current <= 2000) {
                peticoat = 260;
                if (cableBox) {
                    boltdia = 42;
                    boredia = 134;
                    bushing_L1 = 415;
                    bushing_L2 = 125;
                    Cap_Dia = 100;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 42;
                    boredia = 134;
                    bushing_L1 = 415;
                    bushing_L2 = 125;
                    Cap_Dia = 100;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD + (2 * stud_Dia);
                }
            } else { // current > 2000
                peticoat = 260;
                if (cableBox) {
                    boltdia = 48;
                    boredia = 134;
                    bushing_L1 = 415;
                    bushing_L2 = 125;
                    Cap_Dia = 100;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                } else {
                    boltdia = 48;
                    boredia = 134;
                    bushing_L1 = 415;
                    bushing_L2 = 125;
                    Cap_Dia = 100;
                    supFlangePCD = 200;
                    stud_L = 65;
                    stud_Dia = 12;
                    stud_Nos = 6;
                    supFlangeDia = supFlangePCD + (2 * stud_Dia);
                }
            }
        }

        else if (voltage <= 52000) {

            ph2ph = 530;
            ph2earth = 530; // 480
            tankClrnce = 175;
            blength = 460;
            noofpeticoat = 11;

            // Higher voltage 630A taken restOfVariables temporarily
            if (current <= 1000) {
                peticoat = 200;
                boltdia = 30;
                boredia = 140;
                bushing_L1 = 525;
                bushing_L2 = 280;
                Cap_Dia = 148;
                supFlangePCD = 210;
                stud_L = 65;
                stud_Dia = 15;
                stud_Nos = 6;
                supFlangeDia = supFlangePCD + (2 * stud_Dia);
            } else if (current <= 1600) {
                peticoat = 208;
                boltdia = 60;
                boredia = 135;
                bushing_L1 = 525;
                bushing_L2 = 280;
                Cap_Dia = 148;
                supFlangePCD = 210;
                stud_L = 65;
                stud_Dia = 15;
                stud_Nos = 8;
                supFlangeDia = supFlangePCD + (2 * stud_Dia);
            } else if (current <= 2000) {
                peticoat = 208;
                boltdia = 60;
                boredia = 135;
                bushing_L1 = 525;
                bushing_L2 = 280;
                Cap_Dia = 148;
                supFlangePCD = 210;
                stud_L = 65;
                stud_Dia = 15;
                stud_Nos = 8;
                supFlangeDia = supFlangePCD + (2 * stud_Dia);
            } else { // current > 2000
                peticoat = 208;
                boltdia = 60;
                boredia = 135;
                bushing_L1 = 525;
                bushing_L2 = 280;
                Cap_Dia = 148;
                supFlangePCD = 210;
                stud_L = 65;
                stud_Dia = 15;
                stud_Nos = 8;
                supFlangeDia = supFlangePCD + (2 * stud_Dia);
            }
        }

        else if (voltage <= 72500) { // 72500 instead of 725000 which seems like a typo in original

            ph2ph = 700;
            ph2earth = 700; // 660
            tankClrnce = 175;
            blength = 610;
            noofpeticoat = 11;

            peticoat = 208;
            boltdia = 60;
            boredia = 120;
            bushing_L1 = 760;
            bushing_L2 = 495;
            Cap_Dia = 200;
            supFlangePCD = 185;
            stud_L = 65;
            stud_Dia = 15;
            stud_Nos = 8;
            supFlangeDia = supFlangePCD + (2 * stud_Dia);
        }

        else if (voltage <= 145000) {

            ph2ph = 1220;
            ph2earth = 1220;
            tankClrnce = 220;
            blength = 1060;
            noofpeticoat = 11;

            boltdia = 60;
            boredia = 120;
            bushing_L1 = 760;
            bushing_L2 = 495;
            Cap_Dia = 200;
            supFlangePCD = 185;
            stud_L = 65;
            stud_Dia = 15;
            stud_Nos = 8;
            supFlangeDia = supFlangePCD + (2 * stud_Dia);
        }
        else { // voltage > 145000
            ph2ph = 1430;
            ph2earth = 1430;
            tankClrnce = 220;
            blength = 1060;
            noofpeticoat = 11;

            boltdia = 60;
            boredia = 120;
            bushing_L1 = 760;
            bushing_L2 = 495;
            Cap_Dia = 200;
            supFlangePCD = 185;
            stud_L = 65;
            stud_Dia = 15;
            stud_Nos = 8;
            supFlangeDia = supFlangePCD + (2 * stud_Dia);
        }

        HvLvCommonVariables hvLvCommonVariables = new HvLvCommonVariables();
        hvLvCommonVariables.setPh2ph(ph2ph);
        hvLvCommonVariables.setPh2earth(ph2earth);
        hvLvCommonVariables.setTankClrnce(tankClrnce);
        hvLvCommonVariables.setBoredia(boredia);
        hvLvCommonVariables.setBushing_L1(bushing_L1);
        hvLvCommonVariables.setBushing_L2(bushing_L2);
        hvLvCommonVariables.setStud_Dia(stud_Dia);
        hvLvCommonVariables.setStud_L(stud_L);
        hvLvCommonVariables.setStud_Nos(stud_Nos);
        hvLvCommonVariables.setSupFlangePCD(supFlangePCD);
        hvLvCommonVariables.setSupFlangeDia(supFlangeDia);
        hvLvCommonVariables.setBlength(blength);
        hvLvCommonVariables.setCap_Dia(Cap_Dia);
        hvLvCommonVariables.setPeticoat(peticoat);
        hvLvCommonVariables.setNoofpeticoat(noofpeticoat);
        hvLvCommonVariables.setBoltdia(boltdia);
        return hvLvCommonVariables;
    }

}