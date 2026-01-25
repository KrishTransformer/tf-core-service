
//
//        // Function to check if tank lift lug is enabled
//        public static boolean isTankLiftLugEnabled(boolean userSelection) {
//            return userSelection; // True by default but can be modified by user input
//        }
//
//        // Function to get tank lift lug type based on KVA
//        public static String getTankLiftLugType(double KVA) {
//            if (KVA <= 500) {
//                return "type-1";
//            } else if (KVA > 500 && KVA <= 2000) {
//                return "type-2";
//            } else if (KVA > 2000 && KVA <= 5000) {
//                return "type-3";
//            } else {
//                return "type-3";
//            }
//        }
//    }
//
//
//        // Function to determine hvb_Pos
//        public static String determineHVBPosition(boolean isLidSelected, boolean isHVCableBoxSelected) {
//            if (isHVCableBoxSelected) {
//                return "tank";
//            }
//            return isLidSelected ? "lid" : "tank";
//        }
//
//        // Function to determine lvb_Pos
//        public static String determineLVBPosition(boolean isLidSelected, boolean isLVCableBoxSelected) {
//            if (isLVCableBoxSelected) {
//                return "tank";
//            }
//            return isLidSelected ? "lid" : "tank";
//        }
//

//
//    public static double calculateHvbPitch(double tankLength, double hvbTankOilClear, double bushingPitchP2P) {
//        double hvbPitch = bushingPitchP2P; // Default from Bushing_Data.txt
//        if ((tankLength / 2) - hvbPitch < hvbTankOilClear) {
//            hvbPitch = (tankLength / 2) - hvbTankOilClear;
//        }
//        return hvbPitch;
//    }
//
//    // Function to calculate hvb_F_Tilt_Ang
//    public static double getHvbFTiltAngle(double userSelectedAngle) {
//        return userSelectedAngle; // User-selected value
//    }
//
//    // Function to calculate hvb_S_Tilt_Ang
//    public static double calculateHvbSTiltAngle(double tankLength, double hvbPitch, double hvbTankOilClear, double hvbSupH, double hvbL1) {
//        double tiltAngle = 0;
//        if ((tankLength / 2) - hvbPitch < hvbTankOilClear) {
//            tiltAngle = Math.atan(hvbTankOilClear / (hvbSupH + hvbL1)); // In radians
//            return Math.toDegrees(tiltAngle) + 2.9; // Convert to degrees and add offset
//        }
//        return 0; // Default value
//    }
//
//    // Function to determine hvb_Sup_Type
//    public static String determineHvbSupportType(boolean isPlateSelected) {
//        return isPlateSelected ? "Plate" : "turret"; // Default is turret
//    }
//
//    // Function to calculate hvb_Sup_L
//    public static double calculateHvbSupportLength(double hvbPitch, int hvbNos, double hvbSupID, boolean isPlateType) {
//        if (isPlateType) {
//            return (hvbPitch * (hvbNos - 1)) + (2 * hvbSupID);
//        }
//        return 0; // Not applicable if not Plate type
//    }
//
//    // Function to calculate hvb_Sup_W
//    public static double calculateHvbSupportWidth(double hvbSupID, boolean isPlateType) {
//        if (isPlateType) {
//            return 2 * hvbSupID;
//        }
//        return 0; // Not applicable if not Plate type
//    }
//
//    // Function to get hvb_Sup_Thick
//    public static double getHvbSupportThickness(double tankThickness) {
//        return tankThickness; // Same as tank thickness
//    }
//
//    public class HVBushingCalculator {
//
//        /**
//         * Calculates the Outer Diameter (OD) of the HV Bushing.
//         *
//         * @param hvbSupID    The Inner Diameter (ID) of the HV Bushing.
//         * @param hvbSupThick The thickness of the HV Bushing.
//         * @return The Outer Diameter (OD) of the HV Bushing.
//         */
//        public static double calculateHvbSupOD(double hvbSupID, double hvbSupThick) {
//            return hvbSupID + (2 * hvbSupThick);
//        }
//
//        /**
//         * Calculates the support height (Hvb_Sup_H) of the HV Bushing.
//         *
//         * @param hvbL2 The L2 dimension of the HV Bushing.
//         * @return The support height (Hvb_Sup_H) of the HV Bushing.
//         */
//        public static double calculateHvbSupH(double hvbL2) {
//            return hvbL2; // Replace with actual logic if needed
//        }
//    }
//
//    /**
//     * Calculates the Flange Width (hvb_Sup_Flg_W) of the HV Bushing.
//     *
//     * @param hvbSupFlgOD The Outer Diameter (OD) of the HV Bushing Flange.
//     * @param hvbSupID    The Inner Diameter (ID) of the HV Bushing.
//     * @return The Flange Width (hvb_Sup_Flg_W).
//     */
//    public static double calculateHvbSupFlgW(double hvbSupFlgOD, double hvbSupID) {
//        return (hvbSupFlgOD - hvbSupID) / 2;
//    }
//
//    /**
//     * Calculates the Flange Thickness (hvb_Sup_Flg_Thick) of the HV Bushing.
//     *
//     * @param lidThick The thickness of the lid.
//     * @return The Flange Thickness (hvb_Sup_Flg_Thick).
//     */
//    public static double calculateHvbSupFlgThick(double lidThick) {
//        return lidThick;
//    }
//
//    public class HVbushingCalculator {
//
//        /**
//         * Calculates the Outer Diameter (OD) of the HV Bushing.
//         *
//         * @param hvbSupID    The Inner Diameter (ID) of the HV Bushing.
//         * @param hvbSupThick The thickness of the HV Bushing.
//         * @return The Outer Diameter (OD) of the HV Bushing.
//         */
//        public static double calculateHvbSupOD(double hvbSupID, double hvbSupThick) {
//            return hvbSupID + (2 * hvbSupThick);
//        }
//
//        /**
//         * Returns the value of hvb_L2 as hvb_Sup_H.
//         *
//         * @param hvbL2 The L2 value of the HV Bushing.
//         * @return The same value as hvb_L2.
//         */
//        public static double calculateHvbSupH(double hvbL2) {
//            return hvbL2;
//        }
//
//        /**
//         * Calculates the width of the HV Bushing Support Flange.
//         *
//         * @param hvbSupFlgOD The Outer Diameter of the HV Bushing Support Flange.
//         * @param hvbSupID    The Inner Diameter of the HV Bushing Support.
//         * @return The width of the support flange.
//         */
//        public static double calculateHvbSupFlgW(double hvbSupFlgOD, double hvbSupID) {
//            return (hvbSupFlgOD - hvbSupID) / 2;
//        }
//
//        /**
//         * Calculates the thickness of the HV Bushing Support Flange.
//         *
//         * @param lidThick The thickness of the lid.
//         * @return The thickness of the support flange.
//         */
//        public static double calculateHvbSupFlgThick(double lidThick) {
//            return lidThick;
//        }
//
//        /**
//         * Determines whether the HV Cablebox is selected.
//         *
//         * @param isHvCableboxChecked Boolean indicating if the checkbox is selected.
//         * @return true if selected, false otherwise.
//         */
//        public static boolean calculateHvcb(boolean isHvCableboxChecked) {
//            return isHvCableboxChecked;
//        }
//
//        /**
//         * Returns the vertical position of the HV Cablebox Flag Box.
//         *
//         * @return The constant value 100.
//         */
//        public static double calculateHvcbFlgBoxVpos() {
//            return 100;
//        }
//
//        /**
//         * Returns the front flange width of the HV Cablebox.
//         *
//         * @return The constant value 35.
//         */
//        public static double calculateHvcbFrontFlgW() {
//            return 35;
//        }
//
//        /**
//         * Calculates the vertical position of the HV Cablebox Front Flange.
//         *
//         * @param hvcbFlgBoxVpos The vertical position of the HV Cablebox Flag Box.
//         * @return The same value as hvcbFlgBoxVpos.
//         */
//        public static double calculateHvcbFrontFlgVpos(double hvcbFlgBoxVpos) {
//            return hvcbFlgBoxVpos;
//        }
//
//        /**
//         * Calculates the thickness of the HV Cablebox.
//         *
//         * @param tankThick The thickness of the tank.
//         * @return The same value as tankThick.
//         */
//        public static double calculateHvcbThick(double tankThick) {
//            return tankThick;
//        }
//    }
//
//    public class HVCBCalculator {
//
//        // Calculate hvcb_L1
//        public static double calculateHvcbL1(int bushingNos, double ph2ph, double ph2earth) {
//            return ((bushingNos - 1) * ph2ph) + (2 * ph2earth);
//        }
//
//        // Calculate hvcb_L2
//        public static double calculateHvcbL2(double hvcbL1) {
//            return hvcbL1 * (2.0 / 3.0);
//        }
//
//        // Calculate hvcb_H1
//        public static double calculateHvcbH1(double tankH) {
//            return tankH * (3.0 / 4.0);
//        }
//
//        // Calculate hvcb_H2
//        public static double calculateHvcbH2(double ph2earth) {
//            return 2 * ph2earth;
//        }
//
//        // Calculate hvcb_H3
//        public static double calculateHvcbH3(double hvcbH1, double hvcbH2) {
//            return hvcbH1 - hvcbH2;
//        }
//
//        // Calculate hvcb_W1
//        public static double calculateHvcbW1(double bushingL1, double ph2earth) {
//            return bushingL1 + ph2earth;
//        }
//
//        // Calculate hvcb_W2
//        public static double calculateHvcbW2(double hvcbW1) {
//            return hvcbW1 * (2.0 / 3.0);
//        }
//
//        // Calculate hvcb_W3
//        public static double calculateHvcbW3(double hvcbW1, double hvcbW2) {
//            return hvcbW1 - hvcbW2;
//        }
//
//        // Calculate hvcb_Flg_Box_L
//        public static double calculateHvcbFlgBoxL(double hvcbL1) {
//            return hvcbL1;
//        }
//
//        // Calculate hvcb_Flg_Box_H
//        public static double calculateHvcbFlgBoxH(double hvcbH2) {
//            return hvcbH2;
//        }
//
//        // Calculate hvcb_Flg_Box_W
//        public static double calculateHvcbFlgBoxW(double bushingL2) {
//            return bushingL2;
//        }
//
//        // Calculate hvcb_Flg_Box_Thick
//        public static double calculateHvcbFlgBoxThick(double hvcbThick) {
//            return hvcbThick;
//        }
//
//        // Calculate hvcb_Back_Flg_W
//        public static double calculateHvcbBackFlgW() {
//            return 35.0; // Constant value
//        }
//
//        // Calculate hvcb_Back_Flg_Thick
//        public static double calculateHvcbBackFlgThick(double tankThick) {
//            return tankThick;
//        }
//
//        // Calculate hvcb_Back_Flg_Bolt_Dia
//        public static double calculateHvcbBackFlgBoltDia(double tankFlgBoltDia) {
//            return tankFlgBoltDia;
//        }
//
//        // Calculate hvcb_Back_Flg_Bolt_H_Nos
//        public static int calculateHvcbBackFlgBoltHNos(double hvcbFlgBoxL, double hvcbBackFlgW, double hvcbBackFlgBoltDia) {
//            return (int) ((hvcbFlgBoxL + (hvcbBackFlgW * 2)) / hvcbBackFlgBoltDia / 7) + 2;
//        }
//
//        // Calculate hvcb_Back_Flg_Bolt_V_Nos
//        public static int calculateHvcbBackFlgBoltVNos(double hvcbFlgBoxH, double hvcbBackFlgW, double hvcbBackFlgBoltDia) {
//            return (int) ((hvcbFlgBoxH + (hvcbBackFlgW * 2)) / hvcbBackFlgBoltDia / 7) + 2;
//        }
//
//        // Calculate hvcb_Back_Flg_Bolt_H_Pch
//        public static int calculateHvcbBackFlgBoltHPch(double hvcbFlgBoxL, double hvcbBackFlgW, int hvcbBackFlgBoltHNos) {
//            return (int) ((hvcbFlgBoxL + (hvcbBackFlgW * 2)) / (hvcbBackFlgBoltHNos - 1) + 0.99);
//        }
//
//        // Calculate hvcb_Back_Flg_Bolt_V_Pch
//        public static int calculateHvcbBackFlgBoltVPch(double hvcbFlgBoxH, double hvcbBackFlgW, int hvcbBackFlgBoltVNos) {
//            return (int) ((hvcbFlgBoxH + (hvcbBackFlgW * 2)) / (hvcbBackFlgBoltVNos - 1) + 0.99);
//        }
//
//                }// Calculate hvcb_Front_Flg_Thick
//
//    public class conservatorCalculator {
//
//        // Constants (These should be set or passed as parameters in actual usage)
//        private static double cons_Olg_W1 = 62.0;   // Old conservator width 1
//        private static double cons_Olg_W2 = 46.0;   // Old conservator width 2
//        private static double cons_Olg_Thick = 15.0; // Old conservator thickness
//        private static double cons_Olg_Stud_Dia = 6.0; // Old conservator stud diameter
//        private static double cons_Olg_Stud_L = 20.0; // Old conservator stud length
//
//        // Function to calculate the difference in conservator width (W1 - W2)
//        public static double calculateConsOlgWidthDiff() {
//            double cons_Olg_Width_Diff = cons_Olg_W1 - cons_Olg_W2;
//            return cons_Olg_Width_Diff;
//        }
//
//        // Function to calculate the total thickness of the old conservator
//        public static double calculateConsOlgTotalThickness() {
//            double cons_Olg_Total_Thick = cons_Olg_Thick * 2; // Assuming the thickness is doubled for some reason
//            return cons_Olg_Total_Thick;
//        }
//
//        // Function to calculate the conservator width as an average of W1 and W2
//        public static double calculateConsOlgWidthAverage() {
//            double cons_Olg_Width_Avg = (cons_Olg_W1 + cons_Olg_W2) / 2;
//            return cons_Olg_Width_Avg;
//        }
//
//        // Function to calculate the conservator external diameter (assuming W1 and W2 impact diameter)
//        public static double calculateConsOlgExternalDiameter() {
//            // This is just an example formula, you can adjust based on actual requirements
//            double cons_Olg_External_Diameter = cons_Olg_W1 + cons_Olg_W2 + (cons_Olg_Thick * 2);
//            return cons_Olg_External_Diameter;
//        }
//
//        // Function to calculate the area of the stud hole (circular area)
//        public static double calculateConsOlgStudHoleArea() {
//            double stud_Hole_Area = Math.PI * Math.pow(cons_Olg_Stud_Dia / 2, 2); // Area = pi * (diameter/2)^2
//            return stud_Hole_Area;
//        }
//
//        // Function to calculate the volume of the stud (cylinder volume)
//        public static double calculateConsOlgStudVolume() {
//            double stud_Volume = Math.PI * Math.pow(cons_Olg_Stud_Dia / 2, 2) * cons_Olg_Stud_L; // Volume = pi * (diameter/2)^2 * length
//            return stud_Volume;
//        }
//    }
//
//    public class COnservatorCalculator {
//
//        // Declare variables with initialization or make them parameters
//        private static double cons_Olg_W1 = 62.0;   // Old conservator width 1
//        private static double cons_Olg_Sup_Thick = 3.15;  // Thickness from the manual
//        private static double cons_Olg_Sup_Pipe_ID = 15.0; // Pipe ID from the manual
//        private static double cons_Olg_Sup_Pipe_OD = 19.0; // Pipe OD from the manual
//        private static double cons_Olg_Sup_Pipe_L = 15.0;  // Pipe Length from the manual
//        private static double cons_Olg_L1 = 50.0;   // Old conservator length 1 (example value)
//        private static boolean cons_Breath = true;  // Breathing valve is TRUE
//
//        // Function to calculate the supplied conservator width (same as the old conservator width)
//        public static double calculateConsOlgSupWidth() {
//            double cons_Olg_Sup_W = cons_Olg_W1;  // Supplied conservator width is the same as the old width
//            return cons_Olg_Sup_W;
//        }
//
//        // Function to calculate the supplied conservator pipe pitch (clearance calculation)
//        public static double calculateConsOlgSupPipePitch() {
//            double cons_Olg_Sup_Pipe_Pitch = cons_Olg_L1 - (21.5 * 2); // Top and bottom clearance (21.5mm)
//            return cons_Olg_Sup_Pipe_Pitch;
//        }
//
//        // Function to calculate the supplied conservator pipe internal diameter (ID)
//        public static double calculateConsOlgSupPipeID() {
//            double cons_Olg_Sup_Pipe_ID = 15.0; // Ensure the variable is initialized
//            return cons_Olg_Sup_Pipe_ID;
//        }
//
//        // Function to calculate the supplied conservator pipe outer diameter (OD)
//        public static double calculateConsOlgSupPipeOD() {
//            double cons_Olg_Sup_Pipe_OD = 19.0; // Ensure the variable is initialized
//            return cons_Olg_Sup_Pipe_OD;
//        }
//
//        // Function to calculate the supplied conservator pipe length
//        public static double calculateConsOlgSupPipeLength() {
//            double cons_Olg_Sup_Pipe_L = 15.0; // Ensure the variable is initialized
//            return cons_Olg_Sup_Pipe_L;
//        }
//
//        // Function to calculate the supplied conservator length (L1)
//        public static double calculateConsOlgSupLength() {
//            double cons_Olg_Sup_L = cons_Olg_L1; // Supplied conservator length is same as old conservator length
//            return cons_Olg_Sup_L;
//        }
//
//        // Function to check if the conservator has a breathing valve (cons_Breath)
//        public static boolean hasBreathingValve() {
//            return cons_Breath; // Returns true if the conservator has a breathing valve
//        }
//
//        public class BreathPipeCalculations {
//
//            // Function to calculate cons_Breath_Pipe_H1

//            public static void main(String[] args) {
//                // Example usage:
//                double consBreathPipeH2 = 10.0;
//                double consDia = 5.0;
//                double consBreathPipeH2Again = 10.0;
//                double consFlgW = 8.0;
//                double consBreathPipeID = 2.0;
//                double consBreathPipeW2 = calculateBreathPipeW2(consBreathPipeID);
//
//                double breathPipeH1 = calculateBreathPipeH1(consBreathPipeH2, consDia, consBreathPipeH2Again);
//                double breathPipeH2 = calculateBreathPipeH2(consFlgW, consBreathPipeID);
//                double breathPipeW1 = calculateBreathPipeW1(consBreathPipeW2, consBreathPipeID);
//
//
//            }
//        }
//
//
//    }
//
//    public class LiftAndDrainCalculations {
//
//        // Function to calculate lift_Cons_W
//        public static int calculateLiftConsW(double consDia) {
//            return (int) (((consDia / 10) / 5)) * 5;
//        }
//
//        // Function to calculate lift_Cons_Thick
//        public static double calculateLiftConsThick(int liftConsW) {
//            return liftConsW / 4.0;
//        }
//
//        // Function to calculate lift_Cons_Hole_Dia
//        public static double calculateLiftConsHoleDia(int liftConsW) {
//            return liftConsW / 2.0;
//        }
//
//        // Function to calculate lift_Cons_Type
//        public static String calculateLiftConsType(double consVol) {
//            if (consVol <= 100) {
//                return "rod";
//            } else {
//                return "plate";
//            }
//        }
//
//
//
//        public static void main(String[] args) {
//            // Example usage:
//            double consDia = 50.0;
//            double consPipeID = 20.0;
//            double consPipeOD = 25.0;
//            double consVol = 80.0;
//
//            int liftConsW = calculateLiftConsW(consDia);
//            double liftConsThick = calculateLiftConsThick(liftConsW);
//            double liftConsHoleDia = calculateLiftConsHoleDia(liftConsW);
//            String liftConsType = calculateLiftConsType(consVol);
//            double consDrainID = calculateConsDrainID(consPipeID);
//            double consDrainOD = calculateConsDrainOD(consPipeOD);
//            double consDrainL = calculateConsDrainL(consPipeID);
//            double consFillerID = calculateConsFillerID(consPipeID);
//            double consFillerOD = calculateConsFillerOD(consPipeOD);
//            double consFillerL = calculateConsFillerL(consPipeID);
//        }
//    }
//
//    public class SupportSectionCalculator {
//
//        public static String calculateConsSupSect(double KVA) {
//            if (KVA <= 315) {
//                return "40x40x5-L";
//            } else if (KVA <= 500) {
//                return "50x50x6-L";
//            } else if (KVA <= 1600) {
//                return "65x65x8-L";
//            } else if (KVA <= 3150) {
//                return "75x40x4.8-C";
//            } else {
//                return "100x50x5-C";
//            }
//        }
//
//        public static int calculateConsSupSectL1(int cons_H_Pos, int tank_Flg_W, int tank_Thick, int cons_Dia) {
//            return (int) (cons_H_Pos - tank_Flg_W + tank_Thick + (cons_Dia / 2.0) - 25);
//        }
//
//        public static int calculateConsSupSectL2(int tank_Flg_W, int tank_Flg_Bolt_H_Pch) {
//            return (int) ((tank_Flg_W / 2.0) + (tank_Flg_Bolt_H_Pch * 3) + (tank_Flg_Bolt_H_Pch / 2.0));
//        }
//
//        public static int getConsSupPltThick() {
//            return 6; // Fixed value
//        }
//
//        public static int getConsSupBPlateW() {
//            return 40; // Fixed value
//        }
//
//        public static int getConsSupHoleDim(int tank_Flg_Bolt_Dia) {
//            return tank_Flg_Bolt_Dia; // Match with Lid Holes
//        }
//
//        public static int getConsSupHoleNos(int lidHoles) {
//            return lidHoles; // Match with Lid Holes
//        }
//
//        public class supportsectioncalculator {
//
//            // Method to return the thickness of the logo plate
//            public static int getConsLogoPlateThick() {
//                return 2; // Fixed value
//            }
//
//            // Main method for usage demonstration, optional (can be removed if not needed)
//            public static void main(String[] args) {
//                // Example usage (no print statements)
//                int consLogoPlateThick = getConsLogoPlateThick();
//                // The variable consLogoPlateThick is now ready for use elsewhere in your program.
//            }
//        }
//        public class SupportsectionCalculator {
//
//            // Default values
//            private static int mogTltAng = 0; // Default to 0
//            private static boolean expVent = true; // Default to TRUE
//
//            // Getter for mog_Tlt_Ang
//            public static int getMogTltAng() {
//                return mogTltAng;
//            }
//
//            // Setter for mog_Tlt_Ang
//            public static void setMogTltAng(int value) {
//                mogTltAng = value;
//            }
//
//            // Getter for exp_Vent
//            public static boolean isExpVent() {
//                return expVent;
//            }
//
//            // Setter for exp_Vent
//            public static void setExpVent(boolean value) {
//                expVent = value;
//            }
//
//            // Main method for demonstration (optional)
//            public static void main(String[] args) {
//                // Example usage
//                setMogTltAng(10); // Update mog_Tlt_Ang
//                setExpVent(false); // Update exp_Vent
//
//                int currentMogTltAng = getMogTltAng(); // Retrieve updated value
//                boolean currentExpVent = isExpVent(); // Retrieve updated value
//
//                // Use the values elsewhere in the program as needed
//            }
//        }
//
//
//        public class LiftLugCalculator {
//
//            // Input values (initialize these values as needed)
//            private static int KVA = 1200; // Example value for KVA
//            private static int tank_L = 1000; // Example value for tank length
//            private static int tank_W = 800; // Example value for tank width
//            private static int tank_Thick = 10; // Example value for tank thickness
//            private static boolean roller = true; // Example for roller option
//            private static String roller_Type = "plain_roller"; // Example for roller type
//            private static double roller_Thick = 5; // Example thickness of roller flange
//            private static double roller_Flg_Thick = 2; // Example thickness of roller flange
//
//            // lid_LiftLug_L1 calculation
//            public static int calculateLidLiftLugL1() {
//                if (KVA <= 500) {
//                    return 75;
//                } else {
//                    if (tank_L <= 1000) {
//                        return 50;
//                    } else {
//                        return 100;
//                    }
//                }
//            }
//
//            // lid_LiftLug_L2 calculation
//            public static int calculateLidLiftLugL2(int lid_LiftLug_L1) {
//                return lid_LiftLug_L1 * 2 / 3;
//            }
//
//            // lid_LiftLug_H calculation
//            public static int calculateLidLiftLugH(int lid_LiftLug_L1) {
//                return lid_LiftLug_L1 * 2 / 3;
//            }
//
//            // lid_LiftLug_Thick calculation
//            public static int calculateLidLiftLugThick() {
//                return tank_Thick * 2;
//            }
//
//            // lid_LiftLug_Hole_Dia calculation
//            public static int calculateLidLiftLugHoleDia(int lid_LiftLug_L1) {
//                return lid_LiftLug_L1 / 3;
//            }
//
//            // lift_Lid_Type calculation
//            public static String calculateLiftLidType() {
//                if (KVA <= 500) {
//                    return "handle";
//                } else {
//                    return "plate";
//                }
//            }
//
//            // lid_LiftLug_Rod_Dia calculation
//            public static int calculateLidLiftLugRodDia() {
//                return 5; // Fixed value
//            }
//
//            // lid_LiftLug_Fillet_Rad calculation
//            public static int calculateLidLiftLugFilletRad() {
//                return calculateLidLiftLugRodDia(); // Same as rod diameter
//            }
//
//            // lid_LiftLug_Vpos calculation
//            public static int calculateLidLiftLugVpos() {
//                return tank_W / 6;
//            }
//
//            // lid_LiftLug_Hpos calculation
//            public static int calculateLidLiftLugHpos() {
//                return calculateLidLiftLugL1(); // Same as L1
//            }
//
//            // foundation_Type calculation
//            public static String calculateFoundationType() {
//                if (KVA <= 500) {
//                    if (roller) {
//                        return "type-1";
//                    } else {
//                        return "type-2";
//                    }
//                } else if (KVA > 500 && KVA <= 2000) {
//                    return "type-3";
//                } else {
//                    return "type-4";
//                }
//            }
//
//            // bot_Chnl_Sec_Data calculation
//            public static String calculateBotChnlSecData() {
//                if (KVA <= 500) {
//                    return "75x45x5.3-C";
//                } else if (KVA <= 630) {
//                    return "125x65x5.3-C";
//                } else if (KVA <= 6300) {
//                    return "150x75x5.7-C";
//                } else if (KVA <= 12500) {
//                    return "200x75x6.2-C";
//                } else if (KVA <= 31500) {
//                    return "250x80x8.8-C";
//                } else {
//                    return "250x80x8.8-C";
//                }
//            }
//
//            // roller_Guage calculation
//            public static double calculateRollerGuage() {
//                double result;
//                if ((tank_L * (2 / 3.0)) > 1676.4) {
//                    result = 1676.4;
//                } else {
//                    result = Math.floor(((2 * (tank_L / 3.0) + 10) / 10)) * 10;
//                }
//                return result;
//            }
//
//            // roller_Thick calculation
//            public static double getRollerThick() {
//                return roller_Thick; // Return the roller flange thickness
//            }
//
//            // roller_Flg_Thick calculation
//            public static double getRollerFlgThick() {
//                return roller_Flg_Thick; // Return the roller flange thickness
//            }
//
//            // bot_Chnl_L1 calculation
//            public static double calculateBotChnlL1(double bot_Chnl_Pitch5, double bot_Chnl_Pitch4, double bot_Chnl_Sec_W2, double bot_Chnl_Sec_W1) {
//                if ("type-4".equals(calculateFoundationType())) {
//                    return bot_Chnl_Pitch5 + bot_Chnl_Pitch4 + bot_Chnl_Sec_W2 + (bot_Chnl_Sec_W1 * 2);
//                } else {
//                    return calculateBotChnlPitch1() + calculateRollerGuage(); // Assuming bot_Chnl_Pitch1 is calculated somewhere
//                }
//            }
//
//            // bot_Chnl_L2 calculation
//            public static double calculateBotChnlL2(double bot_Chnl_Sec_W1, double tank_Bot_L) {
//                return tank_Bot_L + (bot_Chnl_Sec_W1 * 5); // Length of Cross Channel in Type 3 & Type 4 bottom channel
//            }
//
//            // bot_Chnl_L3 calculation
//            public static double calculateBotChnlL3(double bot_Chnl_Pitch3, double bot_Chnl_Sec_W2) {
//                return bot_Chnl_Pitch3 - bot_Chnl_Sec_W2; // Length of Support Channel for rollers in Type 4 bottom channel
//            }
//
//            // bot_Chnl_Pitch1 calculation
//            public static double calculateBotChnlPitch1() {
//                if ("flange roller".equals(roller_Type)) {
//                    return calculateRollerGuage() + getRollerThick() - (2 * getRollerFlgThick()); // Assuming roller_Thick and roller_Flg_Thick are provided
//                } else {
//                    return calculateRollerGuage();
//                }
//            }
//
//            // bot_Chnl_Pitch2 calculation
//            public static double calculateBotChnlPitch2() {
//                return tank_W / 2;
//            }
//
//            // bot_Chnl_Pitch3 calculation
//            public static double calculateBotChnlPitch3(double roller_Dia, double bot_Chnl_Sec_W2) {
//                return roller_Dia + (roller_Dia / 4) + bot_Chnl_Sec_W2; // (roller_Dia/4) is for clearance
//            }
//
//            // bot_Chnl_Pitch4 calculation
//            public static double calculateBotChnlPitch4(double bot_Chnl_Pitch3) {
//                return bot_Chnl_Pitch3;
//            }
//
//            // bot_Chnl_Pitch5 calculation
//            public static double calculateBotChnlPitch5(double bot_Chnl_Pitch1) {
//                return bot_Chnl_Pitch1;
//            }
//
//            // transformer_Weight calculation (Example value from Electric Design)
//            public static double calculateTransformerWeight() {
//                return 5000; // Example value, this should be derived from electrical design
//            }
//
//            // Main method for usage (optional)
//            public static void main(String[] args) {
//                // Example usage of the methods
//                int lid_LiftLug_L1 = calculateLidLiftLugL1();
//                int lid_LiftLug_L2 = calculateLidLiftLugL2(lid_LiftLug_L1);
//                int lid_LiftLug_H = calculateLidLiftLugH(lid_LiftLug_L1);
//                String lift_Lid_Type = calculateLiftLidType();
//
//                // Values can now be used elsewhere in your project.
//            }
//        }
//
//
//        public class RollerCalculator {
//
//            // Example input values (adjust according to your project context)
//            private static double roller_Shaft_Dia = 100; // Example value for roller shaft diameter
//            private static double roller_Flg_Dia = 50; // Example value for roller flange diameter
//            private static double roller_Hub_L = 200; // Example value for roller hub length
//            private static double tank_Thick = 10; // Example value for tank thickness
//            private static double roller_Sup_Thick = 5; // Example value for roller support thickness
//            private static double bot_Chnl_Pitch3 = 500; // Example value for bottom channel pitch 3
//            private static double bot_Chnl_Sec_W2 = 80; // Example value for bottom channel section width 2
//            private static double roller_Sup_L = 300; // Example value for roller support length
//            private static double roller_Sup_W = 150; // Example value for roller support width
//            private static double roller_Sup_H = 200; // Example value for roller support height
//            private static String foundation_Type = "type-4"; // Example value for foundation type
//            private static String roller_Type = "flange roller"; // Example value for roller type
//
//            // roller_Shaft_L calculation

//        }
//        public class TransformerValveCalculator {
//
//            public static class ValveData {
//                public int pipeID;
//                public double pipeOD;
//                public int flangeID;
//                public int flangeOD;
//                public int flangePCD;
//                public int flangeThick;
//                public int boltDia;
//                public int boltNos;
//
//                // Constructor to initialize ValveData
//                public ValveData(int pipeID, double pipeOD, int flangeID, int flangeOD, int flangePCD,
//                                 int flangeThick, int boltDia, int boltNos) {
//                    this.pipeID = pipeID;
//                    this.pipeOD = pipeOD;
//                    this.flangeID = flangeID;
//                    this.flangeOD = flangeOD;
//                    this.flangePCD = flangePCD;
//                    this.flangeThick = flangeThick;
//                    this.boltDia = boltDia;
//                    this.boltNos = boltNos;
//                }
//            }
//
//            public static ValveData getValveData(double transformerRange, boolean isFillerValve) {
//                if (transformerRange <= 315) {
//                    return new ValveData(20, 26.5, 20, 100, 75, 10, 14, 4);
//                } else if (transformerRange > 315 && transformerRange <= 630) {
//                    int flangeID = isFillerValve ? 25 : 5;
//                    return new ValveData(25, 33.1, flangeID, 115, 85, 10, 14, 4);
//                } else if (transformerRange > 630 && transformerRange <= 2000) {
//                    return new ValveData(32, 40.1, 32, 120, 90, 12, 14, 4);
//                } else if (transformerRange > 10000) {
//                    return new ValveData(50, 59, 50, 150, 115, 12, 18, 4);
//                } else {
//                    return null; // Optional: Handle invalid ranges
//                }
//            }
//        }
//        public class TankCalculation {
//
//            public static class LiftingLugData {
//                public double L1, L2, L3, L4;
//                public double H1, H2, H3;
//                public double Thick, HoleDia, RodDia, Chamfer;
//                public double SupH, SupW, SupThick;
//                public int Nos;
//                public double Vpos, Hpos;
//            }
//
//            public static class JackpadData {
//                public double Length, Width, Thickness;
//            }
//
//            public static LiftingLugData calculateLiftingLug(String type, double tankFlgW, double tankFlgThick,
//                                                             double tankFlgBoltHPch, double tankThick, double lidThick) {
//                LiftingLugData data = new LiftingLugData();
//
//                switch (type.toLowerCase()) {
//                    case "type-1":
//                        data.L1 = tankFlgW * 2;
//                        data.H1 = data.L1 * 0.6;
//                        data.Thick = tankFlgThick;
//                        data.HoleDia = data.H1 / 2;
//                        data.Vpos = tankFlgThick + (data.H1 / 2);
//                        data.Hpos = (2 * tankFlgBoltHPch) - tankThick;
//                        break;
//
//                    case "type-2":
//                        data.L1 = tankFlgW * 2;
//                        data.L2 = data.L1 / 2;
//                        data.L3 = data.L1 / 2;
//                        data.H1 = data.L1 * 1.5;
//                        data.H2 = data.L3;
//                        data.H3 = data.H1 - data.H2;
//                        data.Thick = tankFlgThick;
//                        data.HoleDia = data.L3 / 2;
//                        data.Chamfer = data.Thick * 2;
//
//                        data.SupThick = tankFlgThick;
//                        data.SupH = data.H3 + (2 * data.SupThick);
//                        data.SupW = data.Thick + (6 * data.SupThick);
//                        data.Vpos = -lidThick + data.H2 + (data.H3 / 2);
//                        data.Hpos = (2 * tankFlgBoltHPch) - tankThick;
//                        break;
//
//                    case "type-3":
//                        data.L1 = tankFlgW * 1.5;
//                        data.L2 = data.L1 / 4;
//                        data.L3 = data.L1 / 4;
//                        data.L4 = data.L1 / 4;
//                        data.H1 = data.L1 * 1.5;
//                        data.H2 = data.H1 / 2;
//                        data.H3 = data.H2 / 2;
//                        data.Thick = tankFlgThick;
//                        data.Chamfer = data.L4 / 2;
//
//                        data.SupThick = tankFlgThick;
//                        data.SupH = data.H1 + data.SupThick;
//                        data.SupW = data.Thick + (6 * data.SupThick);
//                        data.Vpos = data.SupH / 2;
//                        data.Hpos = (2 * tankFlgBoltHPch) - tankThick;
//                        break;
//
//                    default:
//                        throw new IllegalArgumentException("Invalid lifting lug type.");
//                }
//
//                return data;
//            }
//
//            public static JackpadData calculateJackpad(double kva, double tankFlgThick) {
//                JackpadData data = new JackpadData();
//
//                if (kva <= 1000) {
//                    data.Length = 150;
//                    data.Width = 100;
//                } else if (kva <= 5000) {
//                    data.Length = 200;
//                    data.Width = 150;
//                } else if (kva <= 10000) {
//                    data.Length = 250;
//                    data.Width = 200;
//                } else if (kva <= 20000) {
//                    data.Length = 300;
//                    data.Width = 250;
//                } else if (kva <= 30000) {
//                    data.Length = 300;
//                    data.Width = 250;
//                } else if (kva <= 40000) {
//                    data.Length = 350;
//                    data.Width = 300;
//                } else {
//                    data.Length = 350;
//                    data.Width = 300;
//                }
//
//                data.Thickness = tankFlgThick;
//                return data;
//            }
//        }
//
//        public class BushingCalculation {
//
//            public static class BushingData {
//                public double hv_Ph_Ph;
//                public double hv_Ph_Erth;
//                public double Hv_Ph_Ph_Incl_cap;
//                public double hvb_Ph_Erth_Clear;
//                public double hvb_tank_Oil_Clear;
//                public double hvb_L1;
//                public double hvb_L2;
//                public double hvb_Sup_ID;
//                public double hvb_Sup_Flg_OD;
//                public double hvb_Sup_Flg_ID;
//                public double hvb_Sup_Flg_Stud_Dia;
//                public double hvb_Sup_Flg_Stud_L;
//                public int hvb_Sup_Flg_Stud_Nos;
//
//                public double lv_Ph_Ph;
//                public double lv_Ph_Erth;
//                public double lvb_tank_Oil_Clear;
//                public double lvb_L1;
//                public double lvb_L2;
//                public double lvb_Ptch;
//                public double lvb_Sup_ID;
//                public double lvb_Sup_Flg_PCD;
//                public double lvb_Sup_Flg_OD;
//                public double lvb_Sup_Flg_ID;
//                public double lvb_Sup_Flg_Stud_Dia;
//                public double lvb_Sup_Flg_Stud_L;
//                public int lvb_Sup_Flg_Stud_Nos;
//            }
//
//            public static BushingData calculateBushingData(Map<String, Double> tankData, Map<String, Double> bushingData) {
//                // Ensure the maps are not null
//                if (tankData == null || bushingData == null) {
//                    throw new IllegalArgumentException("Input maps cannot be null");
//                }
//
//                // Ensure all required keys exist in the input maps
//                validateMapKeys(tankData, "tank_L");
//                validateMapKeys(bushingData, "ph2ph", "ph2earth", "TankClrnce", "Bushing_L1", "Bushing_L2", "boredia", "SupFlangeDia", "SupFlangePCD", "bushing_Pitch_p2p", "Stud_Dia", "Stud_L", "Stud_Nos");
//
//                BushingData result = new BushingData();
//
//                // HV Bushing Calculations
//                result.hv_Ph_Ph = bushingData.get("ph2ph");
//                result.hv_Ph_Erth = bushingData.get("ph2earth");
//                result.Hv_Ph_Ph_Incl_cap = result.hv_Ph_Ph * 1.1; // Example adjustment, change as needed
//                result.hvb_Ph_Erth_Clear = result.hv_Ph_Erth * 0.8; // Example adjustment
//                result.hvb_tank_Oil_Clear = bushingData.get("TankClrnce");
//                result.hvb_L1 = bushingData.get("Bushing_L1");
//                result.hvb_L2 = bushingData.get("Bushing_L2");
//                result.hvb_Sup_ID = bushingData.get("boredia");
//                result.hvb_Sup_Flg_OD = bushingData.get("SupFlangeDia");
//                result.hvb_Sup_Flg_ID = bushingData.get("boredia");
//                result.hvb_Sup_Flg_Stud_Dia = bushingData.get("Stud_Dia");
//                result.hvb_Sup_Flg_Stud_L = bushingData.get("Stud_L");
//                result.hvb_Sup_Flg_Stud_Nos = bushingData.get("Stud_Nos").intValue();
//
//                // LV Bushing Calculations
//                result.lv_Ph_Ph = bushingData.get("ph2ph");
//                result.lv_Ph_Erth = bushingData.get("ph2earth");
//                result.lvb_tank_Oil_Clear = bushingData.get("TankClrnce");
//                result.lvb_L1 = bushingData.get("Bushing_L1");
//                result.lvb_L2 = bushingData.get("Bushing_L2");
//
//                double tankL = tankData.get("tank_L");
//                double lvb_Ptch = bushingData.get("bushing_Pitch_p2p");
//                double lvb_tank_Oil_Clear = result.lvb_tank_Oil_Clear;
//
//                if ((tankL / 2) - lvb_Ptch < lvb_tank_Oil_Clear) {
//                    lvb_Ptch = (tankL / 2) - lvb_tank_Oil_Clear;
//                }
//                result.lvb_Ptch = lvb_Ptch;
//
//                result.lvb_Sup_ID = bushingData.get("boredia");
//                result.lvb_Sup_Flg_PCD = bushingData.get("SupFlangePCD");
//                result.lvb_Sup_Flg_OD = bushingData.get("SupFlangeDia");
//                result.lvb_Sup_Flg_ID = bushingData.get("boredia");
//                result.lvb_Sup_Flg_Stud_Dia = bushingData.get("Stud_Dia");
//                result.lvb_Sup_Flg_Stud_L = bushingData.get("Stud_L");
//                result.lvb_Sup_Flg_Stud_Nos = bushingData.get("Stud_Nos").intValue();
//
//                return result;
//            }
//
//            private static void validateMapKeys(Map<String, Double> map, String... keys) {
//                for (String key : keys) {
//                    if (!map.containsKey(key)) {
//                        throw new IllegalArgumentException("Key '" + key + "' is missing in the input map");
//                    }
//                }
//            }
//        }




//
//
//

