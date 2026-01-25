package com.tf.core_service.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberFormattingUtils {
    public static double oneDigitDecimal(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(1, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    public static double oneDigitDecimalFloor(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(1, RoundingMode.FLOOR);
        return bd.doubleValue();
    }

    public static double oneDigitDecimalHalfUp(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double twoDigitDecimal(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    public static double twoDigitDecimalfloor(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.FLOOR);
        return bd.doubleValue();
    }

    public static double twoDigitDecimalhalfUp(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



    public static double threeDigitDecimal(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(3, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    public static double fourDigitDecimal(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(4, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    public static double fiveDigitDecimal(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(5, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    public static double sixDigitDecimal(double input) {
        BigDecimal bd = new BigDecimal(input).setScale(6, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    public static Integer next5or0Integer(double number){
        if(number%5 == 0){return (int) Math.ceil(number);}
        else{return (int) ((number - number % 5) + 5);}
    }

    public static Integer next0Integer(double number){
        if(number%10 == 0){return (int) Math.ceil(number);}
        else{return (int) ((number - number % 10) + 10);}
    }

    public static Integer smart0Integer(double number){
        if(number % 10 >= 5){
            return (int) ((number - (number % 10)) + 10);
        }else {
            return  (int) (number - (number % 10));
        }
    }

    public static Integer previous5or0Integer(double number){
        //TODO: The spacer design was using the same formula but we have removed '-5' from the o/p. Check if this still works.
        return (int) ((number - number % 5));
    }

    public static Integer nextInteger(double number){
        return (int) Math.ceil(number);
    }

    public static Integer nextEvenInteger(double number){
        number = Math.ceil(number);
        return (int) (number % 2 == 1 ? number + 1 : number);
    }

    public static Integer previousEvenInteger(double number){
        number = Math.ceil(number);
        return (int) (number % 2 == 1 ? number - 1 : number);
    }

    public static double twoDigitDecimalPart(double number){
        number = NumberFormattingUtils.twoDigitDecimal(number);
        number = number - Math.floor(number);
        return number;
    }

    public static Integer halfUp(double number){
        if(NumberFormattingUtils.twoDigitDecimalPart(number) >= 0.5){
            return (int) Math.ceil(number);
        }else {
            return (int) Math.floor(number);
        }
    }
}
