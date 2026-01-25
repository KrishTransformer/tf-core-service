package com.tf.core_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tf.core_service.formulas.CoreFormulas;
import com.tf.core_service.model.core.CoreStack;
import com.tf.core_service.model.twoWindings.CorePage;
import com.tf.core_service.model.core.ECoreBladeType;
import com.tf.core_service.request.CoreRequest;
import com.tf.core_service.utils.NumberFormattingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CoreServices {

    CorePage corePage = new CorePage();

    public CorePage calculateForCore(CoreRequest coreRequest)throws JsonProcessingException {
        HashMap<String, Object> formula = new HashMap<>();
        Integer[][] bladeStacking = new Integer[20][3];
        int coreDiameter = coreRequest.getCoreDiameter();
        double grossCoreArea = NumberFormattingUtils.nextInteger(Math.PI * Math.pow(coreDiameter,2) / 4);
        log.info("grossCoreArea " + grossCoreArea);

        //Parameters for the Blade calculation
        int noOfSteps = CoreFormulas.getNoOfSteps(coreDiameter, coreRequest.getNumberOfSteps());
        int minStepWidth = CoreFormulas.getMinimumStepWidth(coreDiameter, coreRequest.getMinimumStepWidth());
        int approxNoOfStacks = CoreFormulas.getApproxNoOfStacks(coreDiameter, minStepWidth);
        double stackArea = 0;
        double totalArea = 0;
        int totalStacks = 0;
        double limbHt = coreRequest.limbHt;
        double cenDist = coreRequest.cenDist;
        double coreWeight = 0;
        Integer fixtureStepWidth = CoreFormulas.getFixtureStepWidth(coreDiameter, coreRequest.getFixtureStepWidth());

        int fixtureStart = CoreFormulas.getFixtureStart(noOfSteps);
        int fixtureStep = noOfSteps - fixtureStart + 1;

        ECoreBladeType eCoreBladeType = coreRequest.eCoreBladeType;
        corePage.setECoreBladeType(eCoreBladeType);

        Integer userStack = null;
        int userStep = 0;
        if(!coreRequest.getCoreStackRequestList().isEmpty()){
            userStep = coreRequest.getCoreStackRequestList().get(0).getStepNo();
            userStack = coreRequest.getCoreStackRequestList().get(0).getStack();
        }

        //FirstBlade calculations
        bladeStacking[0][0] = 1;
        if (coreRequest.getCoreStackRequestList() != null
                && !coreRequest.getCoreStackRequestList().isEmpty()
                && coreRequest.getCoreStackRequestList().get(0).getWidth() != null
                && coreRequest.getCoreStackRequestList().get(0).getStepNo() - 1 == 0) {
            bladeStacking[0][1] = coreRequest.getCoreStackRequestList().get(0).getWidth();
            System.out.println("This is the edited stack 1: " + coreRequest.getCoreStackRequestList().get(0).getStack());
        }else {
            bladeStacking[0][1] = CoreFormulas.getFirstStepWidth(coreDiameter);
        }
        bladeStacking[0][2] = CoreFormulas.getStack(coreDiameter, bladeStacking[0][1], 0, userStep == 1 ? userStack : null);
        if (coreRequest.getPrevCoreStackRequestList() != null
                && !coreRequest.getPrevCoreStackRequestList().isEmpty()
                && coreRequest.getPrevCoreStackRequestList().size() > 0
        ) {
            bladeStacking[0][1] = coreRequest.getPrevCoreStackRequestList().get(0).getWidth();
            bladeStacking[0][2] = coreRequest.getPrevCoreStackRequestList().get(0).getStack();
        }
        totalStacks = bladeStacking[0][2];
        stackArea = CoreFormulas.getStepArea(bladeStacking[0][1], bladeStacking[0][2]);
        totalArea = totalArea + stackArea;

        //SecondBlade calculations
        bladeStacking[1][0] = 2;
        if (coreRequest.getCoreStackRequestList() != null && !coreRequest.getCoreStackRequestList().isEmpty()
                && coreRequest.getCoreStackRequestList().get(0).getWidth() != null
                && coreRequest.getCoreStackRequestList().get(0).getStepNo() - 1 == 1) {
            bladeStacking[1][1] = coreRequest.getCoreStackRequestList().get(0).getWidth();
        }else {
            bladeStacking[1][1] = CoreFormulas.getSecondStepWidth(coreDiameter, bladeStacking[0][1]);
        }
        bladeStacking[1][2] = NumberFormattingUtils.previousEvenInteger(CoreFormulas.getStack(coreDiameter, bladeStacking[1][1], totalStacks, userStep == 2 ? userStack : null));
        if (coreRequest.getPrevCoreStackRequestList() != null
                && !coreRequest.getPrevCoreStackRequestList().isEmpty()
                && coreRequest.getPrevCoreStackRequestList().size() > 1
        ) {
            bladeStacking[1][1] = coreRequest.getPrevCoreStackRequestList().get(1).getWidth();
            bladeStacking[1][2] = coreRequest.getPrevCoreStackRequestList().get(1).getStack();
        }
        totalStacks = totalStacks + bladeStacking[1][2];
        stackArea = CoreFormulas.getStepArea(bladeStacking[1][1], bladeStacking[1][2]);
        totalArea = totalArea + stackArea;

        //Jump Calc
        int remainingStep = noOfSteps - 3;
        int balance = bladeStacking[1][1] - minStepWidth;
        double balanceRate = (double) balance / (remainingStep);
        int jump = NumberFormattingUtils.previous5or0Integer(balanceRate - 2);

        log.info(bladeStacking[0][0] + " " + bladeStacking[0][1]+ " " + bladeStacking[0][2]);
        log.info(bladeStacking[1][0] + " " + bladeStacking[1][1]+ " " + bladeStacking[1][2] + " jump " + jump);

        //Here the categorization is for 3rd to Last But One blade calculation.
        if(Objects.equals(eCoreBladeType, ECoreBladeType.CRUSI_3) || Objects.equals(eCoreBladeType, ECoreBladeType.CRUSI_4)){
            //3rd to LastButOne Blade calculations
            while (remainingStep >= 1){
                int currentStep = (noOfSteps - remainingStep) - 1;
                bladeStacking[currentStep][0] = currentStep + 1;
                if (coreRequest.getCoreStackRequestList() != null && !coreRequest.getCoreStackRequestList().isEmpty()
                        && coreRequest.getCoreStackRequestList().get(0).getWidth() != null
                        && coreRequest.getCoreStackRequestList().get(0).getStepNo() - 1 == currentStep) {
                    bladeStacking[currentStep][1] = coreRequest.getCoreStackRequestList().get(0).getWidth();
                }else {
                    bladeStacking[currentStep][1] = bladeStacking[currentStep-1][1] - jump;
                }
                bladeStacking[currentStep][2] = NumberFormattingUtils.previousEvenInteger(CoreFormulas.getStack(coreDiameter, bladeStacking[currentStep][1], totalStacks, userStep == currentStep + 1 ? userStack : null));
                if (coreRequest.getPrevCoreStackRequestList() != null
                        && !coreRequest.getPrevCoreStackRequestList().isEmpty()
                        && coreRequest.getPrevCoreStackRequestList().size() > (currentStep)
                ) {
                    bladeStacking[currentStep][1] = coreRequest.getPrevCoreStackRequestList().get(currentStep).getWidth();
                    bladeStacking[currentStep][2] = coreRequest.getPrevCoreStackRequestList().get(currentStep).getStack();
                }
                stackArea = CoreFormulas.getStepArea(bladeStacking[currentStep][1], bladeStacking[currentStep][2]);
                totalArea = totalArea + stackArea;
                totalStacks = totalStacks + bladeStacking[currentStep][2];
                balance = bladeStacking[currentStep][1] - minStepWidth;
                balanceRate = (double) balance / (remainingStep);
                jump = NumberFormattingUtils.previous5or0Integer(balanceRate - 2);
                remainingStep--;
                log.info(bladeStacking[currentStep][0] + " " + bladeStacking[currentStep][1]+ " " + bladeStacking[currentStep][2] + " jump " + jump);
            }
        }

        else if(Objects.equals(eCoreBladeType, ECoreBladeType.BLADE_4)){
            remainingStep = fixtureStep - 2;
            while (remainingStep > 1){
                int currentStep = (noOfSteps - remainingStep) - (noOfSteps - fixtureStep);
                bladeStacking[currentStep][0] = currentStep + 1;
                if (coreRequest.getCoreStackRequestList() != null && !coreRequest.getCoreStackRequestList().isEmpty()
                        && coreRequest.getCoreStackRequestList().get(0).getWidth() != null
                        && coreRequest.getCoreStackRequestList().get(0).getStepNo() - 1 == currentStep) {
                    bladeStacking[currentStep][1] = coreRequest.getCoreStackRequestList().get(0).getWidth();
                }else {
                    bladeStacking[currentStep][1] = bladeStacking[currentStep-1][1] - jump;
                }
                bladeStacking[currentStep][2] = NumberFormattingUtils.previousEvenInteger(CoreFormulas.getStack(coreDiameter, bladeStacking[currentStep][1], totalStacks, userStep == currentStep+1 ? userStack : null));
                if (coreRequest.getPrevCoreStackRequestList() != null
                        && !coreRequest.getPrevCoreStackRequestList().isEmpty()
                        && coreRequest.getPrevCoreStackRequestList().size() > (currentStep)
                ) {
                    bladeStacking[currentStep][1] = coreRequest.getPrevCoreStackRequestList().get(currentStep).getWidth();
                    bladeStacking[currentStep][2] = coreRequest.getPrevCoreStackRequestList().get(currentStep).getStack();
                }
                stackArea = CoreFormulas.getStepArea(bladeStacking[currentStep][1], bladeStacking[currentStep][2]);
                totalArea = totalArea + stackArea;
                totalStacks = totalStacks + bladeStacking[currentStep][2];
                balance = (int) (bladeStacking[currentStep][1] - fixtureStepWidth);
                balanceRate = (double) (balance + 5) / (remainingStep - 2);
                jump = NumberFormattingUtils.previous5or0Integer(balanceRate - 2);
                remainingStep--;
                log.info(bladeStacking[currentStep][0] + " " + bladeStacking[currentStep][1]+ " " + bladeStacking[currentStep][2] + " jump " + jump);
            }

            remainingStep = noOfSteps - fixtureStep;
            jump = NumberFormattingUtils.previous5or0Integer((bladeStacking[noOfSteps - remainingStep -2][1] - minStepWidth) / (noOfSteps - fixtureStep + 1));
            while (remainingStep >= 1){
                int currentStep = (noOfSteps - remainingStep) - 1;
                bladeStacking[currentStep][0] = currentStep + 1;
                if (coreRequest.getCoreStackRequestList() != null && !coreRequest.getCoreStackRequestList().isEmpty()
                        && coreRequest.getCoreStackRequestList().get(0).getWidth() != null
                        && coreRequest.getCoreStackRequestList().get(0).getStepNo() - 1 == currentStep) {
                    bladeStacking[currentStep][1] = coreRequest.getCoreStackRequestList().get(0).getWidth();
                }else {
                    bladeStacking[currentStep][1] = bladeStacking[currentStep-1][1] - jump;
                }
                bladeStacking[currentStep][2] = NumberFormattingUtils.previousEvenInteger(CoreFormulas.getStack(coreDiameter, bladeStacking[currentStep][1], totalStacks, userStep == currentStep+1 ? userStack : null));
                if (coreRequest.getPrevCoreStackRequestList() != null
                        && !coreRequest.getPrevCoreStackRequestList().isEmpty()
                        && coreRequest.getPrevCoreStackRequestList().size() > (currentStep)
                ) {
                    bladeStacking[currentStep][1] = coreRequest.getPrevCoreStackRequestList().get(currentStep ).getWidth();
                    bladeStacking[currentStep][2] = coreRequest.getPrevCoreStackRequestList().get(currentStep ).getStack();
                }
                stackArea = CoreFormulas.getStepArea(bladeStacking[currentStep][1], bladeStacking[currentStep][2]);
                totalArea = totalArea + stackArea;
                totalStacks = totalStacks + bladeStacking[currentStep][2];
                balance = bladeStacking[currentStep][1] - minStepWidth;
                balanceRate = (double) balance / (remainingStep);
                jump = NumberFormattingUtils.previous5or0Integer(balanceRate - 2);
                remainingStep--;
                log.info(bladeStacking[currentStep][0] + " " + bladeStacking[currentStep][1]+ " " + bladeStacking[currentStep][2] + " jump " + jump);
            }
        }

        //LastBlade calculations
        bladeStacking[noOfSteps-1][0] = noOfSteps;
        if (coreRequest.getCoreStackRequestList() != null && !coreRequest.getCoreStackRequestList().isEmpty()
                && coreRequest.getCoreStackRequestList().get(0).getStepNo() == noOfSteps) {
            bladeStacking[noOfSteps-1][1] = coreRequest.getCoreStackRequestList().get(0).getWidth();
        }else {
            bladeStacking[noOfSteps-1][1] = minStepWidth;
        }
        bladeStacking[noOfSteps-1][2] = NumberFormattingUtils.previousEvenInteger(CoreFormulas.getStack(coreDiameter, bladeStacking[noOfSteps-1][1], totalStacks, userStep == noOfSteps ? userStack : null));
        stackArea = CoreFormulas.getStepArea(bladeStacking[noOfSteps-1][1], bladeStacking[noOfSteps-1][2]);
        totalArea = totalArea + stackArea;
        log.info(bladeStacking[noOfSteps-1][0] + " " + bladeStacking[noOfSteps-1][1]+ " " + bladeStacking[noOfSteps-1][2]);

        //TODO: If the core dia is more than 500, the Duct is to be made of 6mm width.
        formula.put("totalArea", totalArea);
        formula.put("noOfSteps", noOfSteps);
        corePage.setDesignedCoreArea(totalArea);

        corePage.setCoreArea(grossCoreArea);
        corePage.setCoreFormulas(formula);
        List<CoreStack> coreStacks = new ArrayList<>();
        for (Integer[] bld : bladeStacking) {
            CoreStack coreStack = new CoreStack();
            if (bld[0] != null) {
                coreStack.setStepNo(bld[0]);
                coreStack.setWidth(bld[1]);
                coreStack.setStack(bld[2]);
                coreStacks.add(coreStack);
            }
        }
        corePage.setBldStacks(coreStacks);

        int[] difference = new int[20];
        difference[0] = 0;
        for(int i = 1; i < noOfSteps; i++){
            difference[i] = bladeStacking[i-1][1] - bladeStacking[i][1];
            log.info("difference:" + i + " " + difference[i]);
        }

        if(Objects.equals(eCoreBladeType, ECoreBladeType.CRUSI_3)){
            log.info("In Crusi 3");
            log.info("Center Limb Weight");
            Double[][] centerLimbStacking = new Double[20][6];
            // Here, centerLimbStacking has [stepNo, Length A, Length B, Width, Stack, Weight]
            double centerLimbTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                centerLimbStacking[i][0] = (double) (i + 1);
                if(i == 0){
                    centerLimbStacking[0][1] = limbHt + difference[i];
                }else {
                    centerLimbStacking[i][1] = centerLimbStacking[i-1][1] + difference[i];
                }
                centerLimbStacking[i][2] = centerLimbStacking[i][1] + Double.valueOf(bladeStacking[i][1]);
                centerLimbStacking[i][3] = Double.valueOf(bladeStacking[i][1]);
                centerLimbStacking[i][4] = Double.valueOf(bladeStacking[i][2]);
                centerLimbStacking[i][5] = CoreFormulas.getCenterBladeWeight(centerLimbStacking[i][1], centerLimbStacking[i][3], centerLimbStacking[i][4]);
                centerLimbTotalWeight = centerLimbTotalWeight + centerLimbStacking[i][5];
                log.info(centerLimbStacking[i][0] + " " + centerLimbStacking[i][1] + " " + centerLimbStacking[i][2] + " " + centerLimbStacking[i][3] + " " + centerLimbStacking[i][4] + " " + centerLimbStacking[i][5]);
            }
            corePage.setCenterLimbStacking(centerLimbStacking);

            log.info("Side Limb Weight");
            Double[][] sideLimbStacking = new Double[20][6];
            // Here, sideLimbStacking has [stepNo, Length A, Length B, Width, Stack, Weight]
            double sideLimbTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                sideLimbStacking[i][0] = (double) (i + 1);
                if(i == 0){
                    sideLimbStacking[0][1] = limbHt + difference[i];
                }else {
                    sideLimbStacking[i][1] = sideLimbStacking[i-1][1] + difference[i];
                }
                sideLimbStacking[i][2] = sideLimbStacking[i][1] + (Double.valueOf(bladeStacking[i][1]) * 2);
                sideLimbStacking[i][3] = Double.valueOf(bladeStacking[i][1]);
                sideLimbStacking[i][4] = Double.valueOf(bladeStacking[i][2]) * 2;
                sideLimbStacking[i][5] = CoreFormulas.getSideBladeWeight(sideLimbStacking[i][1], sideLimbStacking[i][3], sideLimbStacking[i][4]);
                sideLimbTotalWeight = sideLimbTotalWeight + sideLimbStacking[i][5];
                log.info(sideLimbStacking[i][0] + " " + sideLimbStacking[i][1] + " " + sideLimbStacking[i][2] + " " + sideLimbStacking[i][3] + " " + sideLimbStacking[i][4] + " " + sideLimbStacking[i][5]);
            }
            corePage.setSideLimbStacking(sideLimbStacking);

            log.info("Yoke Weight");
            Double[][] yokeStacking = new Double[20][6];
            // Here, yokeStacking has [stepNo, Length A, Length B, Width, Stack, Weight]
            double yokeTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                yokeStacking[i][0] = (double) (i + 1);
                yokeStacking[i][1] = 2 * cenDist - Double.valueOf(bladeStacking[i][1]);
                yokeStacking[i][2] = yokeStacking[i][1] + (Double.valueOf(bladeStacking[i][1]) * 2);
                yokeStacking[i][3] = Double.valueOf(bladeStacking[i][1]);
                yokeStacking[i][4] = Double.valueOf(bladeStacking[i][2]) * 2;
                yokeStacking[i][5] = CoreFormulas.getYokeWeight(yokeStacking[i][1], yokeStacking[i][3], yokeStacking[i][4]);
                yokeTotalWeight = yokeTotalWeight + yokeStacking[i][5];
                log.info(yokeStacking[i][0] + " " + yokeStacking[i][1] + " " + yokeStacking[i][2] + " " + yokeStacking[i][3] + " " + yokeStacking[i][4] + " " + yokeStacking[i][5]);
            }
            corePage.setYokeStacking(yokeStacking);

            coreWeight = NumberFormattingUtils.nextInteger(centerLimbTotalWeight + sideLimbTotalWeight + yokeTotalWeight);

        }
        else if (Objects.equals(eCoreBladeType, ECoreBladeType.CRUSI_4)) {
            log.info("In Crusi 4");
            log.info("Center Limb Weight");
            Double[][] centerLimbStacking = new Double[20][5];
            // Here, centerLimbStacking has [stepNo, Length, Width, Stack, Weight]
            double centerLimbTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                centerLimbStacking[i][0] = (double) (i + 1);
                if(i == 0){
                    centerLimbStacking[0][1] = limbHt+ Double.valueOf(bladeStacking[i][1]) * 2;
                }else{
                    centerLimbStacking[i][1] = centerLimbStacking[i-1][1] - difference[i];
                }
                centerLimbStacking[i][2] = Double.valueOf(bladeStacking[i][1]);
                centerLimbStacking[i][3] = Double.valueOf(bladeStacking[i][2]);
                centerLimbStacking[i][4] = CoreFormulas.getCenter4BladeWeight(centerLimbStacking[i][1], centerLimbStacking[i][2], centerLimbStacking[i][3]);
                centerLimbTotalWeight = centerLimbTotalWeight + centerLimbStacking[i][4];
                log.info(centerLimbStacking[i][0] + " " + centerLimbStacking[i][1] + " " + centerLimbStacking[i][2] + " " + centerLimbStacking[i][3] + " " + centerLimbStacking[i][4]);
            }
            corePage.setCenterLimbStacking(centerLimbStacking);

            log.info("Side Limb Weight");
            Double[][] sideLimbStacking = new Double[20][5];
            // Here, sideLimbStacking has [stepNo, Length A, Length B, Width, Stack, Weight]
            double sideLimbTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                sideLimbStacking[i][0] = (double) (i + 1);
                if(i == 0){
                    sideLimbStacking[0][1] = (limbHt + Double.valueOf(bladeStacking[i][1]) * 2) - 10;
                }else{
                    sideLimbStacking[i][1] = sideLimbStacking[i-1][1] - difference[i];
                }
                sideLimbStacking[i][2] = Double.valueOf(bladeStacking[i][1]);
                sideLimbStacking[i][3] = Double.valueOf(bladeStacking[i][2]) * 2;
                sideLimbStacking[i][4] = CoreFormulas.getCenter4BladeWeight(sideLimbStacking[i][1], sideLimbStacking[i][2], sideLimbStacking[i][3]);
                sideLimbTotalWeight = sideLimbTotalWeight + sideLimbStacking[i][4];
                log.info(sideLimbStacking[i][0] + " " + sideLimbStacking[i][1] + " " + sideLimbStacking[i][2] + " " + sideLimbStacking[i][3] + " " + sideLimbStacking[i][4]);
            }
            corePage.setSideLimbStacking(sideLimbStacking);

            log.info("Double Notch Blade Weight");
            Double[][] doubleNotchStacking = new Double[20][5];
            // Here, sideLimbStacking has [stepNo, Length A, Length B, Width, Stack, Weight]
            double doubleNotchTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                doubleNotchStacking[i][0] = (double) (i + 1);
                if(i == 0){
                    doubleNotchStacking[0][1] = cenDist + Double.valueOf(bladeStacking[i][1]);
                }else{
                    doubleNotchStacking[i][1] = doubleNotchStacking[i-1][1] -difference[i];
                }
                doubleNotchStacking[i][2] = Double.valueOf(bladeStacking[i][1]);
                doubleNotchStacking[i][3] = Double.valueOf(bladeStacking[i][2]) * 2;
                doubleNotchStacking[i][4] = CoreFormulas.getCenter4BladeWeight(doubleNotchStacking[i][1], doubleNotchStacking[i][2], doubleNotchStacking[i][3]);
                doubleNotchTotalWeight = doubleNotchTotalWeight + doubleNotchStacking[i][4];
                log.info(doubleNotchStacking[i][0] + " " + doubleNotchStacking[i][1] + " " + doubleNotchStacking[i][2] + " " + doubleNotchStacking[i][3] + " " + doubleNotchStacking[i][4]);
            }
            corePage.setDoubleNotchStacking(doubleNotchStacking);

            log.info("Single Notch Blade Weight");
            Double[][] singleNotchStacking = new Double[20][5];
            // Here, sideLimbStacking has [stepNo, Length A, Length B, Width, Stack, Weight]
            double singleNotchTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                singleNotchStacking[i][0] = (double) (i + 1);
                singleNotchStacking[i][1] = cenDist + 10;
                singleNotchStacking[i][2] = Double.valueOf(bladeStacking[i][1]);
                singleNotchStacking[i][3] = Double.valueOf(bladeStacking[i][2]) * 2;
                singleNotchStacking[i][4] = CoreFormulas.getSingleNotchBladeWeight(singleNotchStacking[i][1], singleNotchStacking[i][2], singleNotchStacking[i][3]);
                singleNotchTotalWeight = singleNotchTotalWeight + singleNotchStacking[i][4];
                log.info(singleNotchStacking[i][0] + " " + singleNotchStacking[i][1] + " " + singleNotchStacking[i][2] + " " + singleNotchStacking[i][3] + " " + singleNotchStacking[i][4]);
            }
            corePage.setSingleNotchStacking(singleNotchStacking);

            coreWeight = NumberFormattingUtils.nextInteger(centerLimbTotalWeight + sideLimbTotalWeight + doubleNotchTotalWeight + singleNotchTotalWeight);
        }
        else if (Objects.equals(eCoreBladeType, ECoreBladeType.BLADE_4)) {
            log.info("In Blade 4");
            log.info("Center Limb Weight");
            Double[][] centerLimbStacking = new Double[20][5];
            // Here, centerLimbStacking has [stepNo, Length, Width, Stack, Weight]
            double centerLimbTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                centerLimbStacking[i][0] = (double) (i + 1);
                centerLimbStacking[i][1] = limbHt+ Double.valueOf(bladeStacking[i][1]) * 2;
                if(i > fixtureStep - 2){
                    centerLimbStacking[i][1] = limbHt + Double.valueOf(bladeStacking[fixtureStep - 2][1]) * 2;
                }
                centerLimbStacking[i][2] = Double.valueOf(bladeStacking[i][1]);
                centerLimbStacking[i][3] = Double.valueOf(bladeStacking[i][2]);
                centerLimbStacking[i][4] = CoreFormulas.getCenter4BladeWeight(centerLimbStacking[i][1], centerLimbStacking[i][2], centerLimbStacking[i][3]);
                centerLimbTotalWeight = centerLimbTotalWeight + centerLimbStacking[i][4];
                log.info(centerLimbStacking[i][0] + " " + centerLimbStacking[i][1] + " " + centerLimbStacking[i][2] + " " + centerLimbStacking[i][3] + " " + centerLimbStacking[i][4]);
            }
            corePage.setCenterLimbStacking(centerLimbStacking);

            log.info("Side Limb Weight");
            Double[][] sideLimbStacking = new Double[20][5];
            // Here, sideLimbStacking has [stepNo, Length A, Length B, Width, Stack, Weight]
            double sideLimbTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                sideLimbStacking[i][0] = (double) (i + 1);
                sideLimbStacking[i][1] = (limbHt + Double.valueOf(bladeStacking[i][1]) * 2) - 10;
                if(i > fixtureStep - 2){
                    sideLimbStacking[i][1] = (limbHt + Double.valueOf(bladeStacking[fixtureStep - 2][1]) * 2) - 10;
                }
                sideLimbStacking[i][2] = Double.valueOf(bladeStacking[i][1]);
                sideLimbStacking[i][3] = Double.valueOf(bladeStacking[i][2]) * 2;
                sideLimbStacking[i][4] = CoreFormulas.getCenter4BladeWeight(sideLimbStacking[i][1], sideLimbStacking[i][2], sideLimbStacking[i][3]);
                sideLimbTotalWeight = sideLimbTotalWeight + sideLimbStacking[i][4];
                log.info(sideLimbStacking[i][0] + " " + sideLimbStacking[i][1] + " " + sideLimbStacking[i][2] + " " + sideLimbStacking[i][3] + " " + sideLimbStacking[i][4]);
            }
            corePage.setSideLimbStacking(sideLimbStacking);

            log.info("Double Notch Blade Weight");
            Double[][] doubleNotchStacking = new Double[20][5];
            // Here, sideLimbStacking has [stepNo, Length A, Length B, Width, Stack, Weight]
            double doubleNotchTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                doubleNotchStacking[i][0] = (double) (i + 1);
                doubleNotchStacking[i][1] = cenDist + Double.valueOf(bladeStacking[i][1]);
                doubleNotchStacking[i][2] = Double.valueOf(bladeStacking[i][1]);
                if(i > fixtureStep - 2){
                    doubleNotchStacking[i-1][1] = cenDist + Double.valueOf(bladeStacking[fixtureStep - 2][1]) - (bladeStacking[fixtureStep - 2][1] - bladeStacking [i - 1][1]);
                    doubleNotchStacking[i][2] = Double.valueOf(bladeStacking[fixtureStep - 2][1]);
                }
                doubleNotchStacking[i][3] = Double.valueOf(bladeStacking[i][2]) * 2;
                doubleNotchStacking[i][4] = CoreFormulas.getCenter4BladeWeight(doubleNotchStacking[i][1], doubleNotchStacking[i][2], doubleNotchStacking[i][3]);
                doubleNotchTotalWeight = doubleNotchTotalWeight + doubleNotchStacking[i][4];
                log.info(doubleNotchStacking[i][0] + " " + doubleNotchStacking[i][1] + " " + doubleNotchStacking[i][2] + " " + doubleNotchStacking[i][3] + " " + doubleNotchStacking[i][4]);
            }
            corePage.setDoubleNotchStacking(doubleNotchStacking);

            log.info("Single Notch Blade Weight");
            Double[][] singleNotchStacking = new Double[20][5];
            // Here, sideLimbStacking has [stepNo, Length A, Length B, Width, Stack, Weight]
            double singleNotchTotalWeight = 0;
            for(int i = 0; i < noOfSteps; i++){
                singleNotchStacking[i][0] = (double) (i + 1);
                singleNotchStacking[i][1] = cenDist + 10;
                singleNotchStacking[i][2] = Double.valueOf(bladeStacking[i][1]);
                if(i > fixtureStep - 2){
                    singleNotchStacking[i][2] = Double.valueOf(bladeStacking[fixtureStep - 2][1]);
                }
                singleNotchStacking[i][3] = Double.valueOf(bladeStacking[i][2]) * 2;
                singleNotchStacking[i][4] = CoreFormulas.getSingleNotchBladeWeight(singleNotchStacking[i][1], singleNotchStacking[i][2], singleNotchStacking[i][3]);
                singleNotchTotalWeight = singleNotchTotalWeight + singleNotchStacking[i][4];
                log.info(singleNotchStacking[i][0] + " " + singleNotchStacking[i][1] + " " + singleNotchStacking[i][2] + " " + singleNotchStacking[i][3] + " " + singleNotchStacking[i][4]);
            }
            corePage.setSingleNotchStacking(singleNotchStacking);

            coreWeight = NumberFormattingUtils.nextInteger(centerLimbTotalWeight + sideLimbTotalWeight + doubleNotchTotalWeight + singleNotchTotalWeight);
        }

        corePage.setFixtureStepWidth(fixtureStepWidth);
        corePage.setCoreWeight(coreWeight);
        return corePage;
    }
}
