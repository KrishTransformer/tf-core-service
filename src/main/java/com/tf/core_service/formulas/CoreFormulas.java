package com.tf.core_service.formulas;

import com.fasterxml.jackson.databind.node.DoubleNode;
import com.tf.core_service.utils.NumberFormattingUtils;
import io.swagger.v3.oas.models.security.SecurityScheme;

public class CoreFormulas {

    //By TVR (3/12/2024)
    //For Energy Efficient Transformers, we will go for 80 grade material, step-lap core.
    //For Energy Efficient, the flux density = 1.55. Build Factor = 1.25 up to 100KVA
    //>100 up-to <= 2500 Build Factor = 1.2. And Build Factor = 1.15 for beyond

    public static Integer getFixtureStart(int noOfSteps){
        if (noOfSteps > 7){
            return 4;
        } else if (noOfSteps > 11) {
            return 5;
        } else {
            return 3;
        }
    }

    public static Integer getNoOfSteps(double coreDia, Integer numberOfSteps){
        if(numberOfSteps != null && numberOfSteps != 0 && numberOfSteps <= 20 && numberOfSteps >= 5){
            return numberOfSteps;
        }
        if(coreDia <= 100){
            return 7;
        } else if (coreDia > 100 && coreDia <= 170) {
            return 9;
        } else if (coreDia > 170 && coreDia <= 235) {
            return 10;
        } else if (coreDia > 235 && coreDia <= 275) {
            return 13;
        } else if (coreDia > 275 && coreDia <= 500) {
            return 15;
        } else if (coreDia > 500) {
            return 17;
        }
        return 17;
    }

    public static Integer getFirstStepWidth(double coreDia){
        return NumberFormattingUtils.previous5or0Integer(coreDia) - 5;
    }

    public static Integer getSecondStepWidth(double coreDia, int firstStepWidth){
        int reduction = 0;
        if(coreDia <= 100){
            reduction = 5;
        } else if (coreDia > 100 && coreDia <= 300) {
            reduction = 10;
        } else if (coreDia > 300 && coreDia <= 400) {
            reduction = 15;
        } else if (coreDia > 400) {
            reduction = 20;
        }
        return firstStepWidth - reduction;
    }

    public static Integer getStack(double coreDia, int stepWidth, int stack, Integer userStack){
        double coreDiaSqr = Math.pow(coreDia, 2);
        double stpSqr = Math.pow(stepWidth, 2);
        //By-default the answer will be a previous integer. Can be odd or even
        if (userStack == null){
            return (int) Math.floor(Math.sqrt(coreDiaSqr - stpSqr) - stack);
        }else {
            return Math.min((int) Math.floor(Math.sqrt(coreDiaSqr - stpSqr) - stack), userStack);
        }
    }

    public static Integer getStepArea(double stepWidth, Integer stack){
        return (int) Math.floor(stepWidth * stack * 0.97);
    }

    public static Integer getFixtureStepWidth(double coreDia, Integer fixtureStepWidth){
        double factor = 0.9;
        if(coreDia <= 200){
            factor = 1;
        }
        if (fixtureStepWidth != null && fixtureStepWidth != 0){
            return fixtureStepWidth;
        } else {
           return (int) ((coreDia / 2) * factor);
        }
    }

    public static Integer getNextStepWidth(int previousStepWidth, int stepNumber, int noOfSteps, int minStepWidth){
        double percentSteps = ((double) stepNumber /noOfSteps) * 100;
        int nextStepWidth = 0;
        int[] array = {10, 15, 20, 25, 25, 25, 30, 30, 30, 30};
        if(percentSteps <= 10){
            nextStepWidth = previousStepWidth;
        } else if (percentSteps > 10 && percentSteps <= 20) {
            nextStepWidth = previousStepWidth - 10;
        }else if (percentSteps > 20 && percentSteps <= 30) {
            nextStepWidth = previousStepWidth - 15;
        }else if (percentSteps > 30 && percentSteps <= 40) {
            nextStepWidth = previousStepWidth - 20;
        }else if (percentSteps > 40 && percentSteps <= 50) {
            nextStepWidth = previousStepWidth - 25;
        }else if (percentSteps > 50 && percentSteps <= 60) {
            nextStepWidth = previousStepWidth - 25;
        }else if (percentSteps > 60 && percentSteps <= 70) {
            nextStepWidth = previousStepWidth - 25;
        }else if (percentSteps > 70 && percentSteps <= 80) {
            nextStepWidth = previousStepWidth - 30;
        }else if (percentSteps > 80 && percentSteps <= 90) {
            nextStepWidth = previousStepWidth - 30;
        }else if (percentSteps > 90 && percentSteps <= 95) {
            nextStepWidth = previousStepWidth - 30;
        }else if (percentSteps > 95 && percentSteps <= 100) {
            nextStepWidth = minStepWidth;
        }
        return nextStepWidth;
    }

    public static Integer getMinimumStepWidth(double coreDia, Integer minimumStepWidth){
        if(minimumStepWidth != null && minimumStepWidth != 0){
            return minimumStepWidth;
        }else {
            if(coreDia <= 65){
                return 25;
            } else if (coreDia > 65 && coreDia <= 100) {
                return 30;
            } else if (coreDia > 100 && coreDia <= 235) {
                return 40;
            } else if (coreDia > 235 && coreDia <= 275) {
                return 45;
            } else if (coreDia > 275) {
                return 80;
            }
            return 80;
        }
    }

    public static Integer getApproxNoOfStacks(int coreDia, int minStepWidth){
        return (int) Math.floor(Math.sqrt(Math.pow(coreDia, 2) - Math.pow(minStepWidth, 2)));
    }

    public static Double getCenterBladeWeight(double length, double width, double stack){
        double area = (length * width) + 0.5 * Math.pow(width,2);
        return NumberFormattingUtils.twoDigitDecimal(area * 0.97 * stack * 7.65 * Math.pow(10, -6));
    }

    public static Double getCenter4BladeWeight(double length, double width, double stack){
        double area = (length * width) - (0.5 * Math.pow(width,2) * 2);
        return NumberFormattingUtils.twoDigitDecimal(area * 0.97 * stack * 7.65 * Math.pow(10, -6));
    }

    public static Double getSingleNotchBladeWeight(double length, double width, double stack){
        double area = (length * width) - (0.5 * Math.pow(width,2));
        return NumberFormattingUtils.twoDigitDecimal(area * 0.97 * stack * 7.65 * Math.pow(10, -6));
    }

    public static Double getSideBladeWeight(double length, double width, double stack){
        double area = (length * width) + Math.pow(width,2);
        return NumberFormattingUtils.twoDigitDecimal(area * 0.97 * stack * 7.65 * Math.pow(10, -6));
    }

    public static Double getYokeWeight(double length, double width, double stack){
        double area = (length * width) + (Math.pow(width,2) * 1.5 * 0.5);
        return NumberFormattingUtils.twoDigitDecimal(area * 0.97 * stack * 7.65 * Math.pow(10, -6));
    }
}
