package com.tf.core_service.service.fabrication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tf.core_service.formulas.fabrication.*;
import com.tf.core_service.model.fabrication.*;
import com.tf.core_service.request.FabricationRequest;
import com.tf.core_service.utils.CommonFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FabricationService {

    @Autowired
    ObjectMapper objectMapper;

    public Fabrication calculateForFabrication(FabricationRequest fabricationRequest) throws JsonProcessingException {

        //Process inputs from Electrical and UI
        String designId = fabricationRequest.getDesignId();
        Integer kVA = FabricationInputs.getKVA(fabricationRequest.getKVA());
        Integer tank_L = FabricationInputs.getTank_L(fabricationRequest.getTank_L());
        Integer tank_W = FabricationInputs.getTank_W(fabricationRequest.getTank_W());
        Integer tank_H = FabricationInputs.getTank_H(fabricationRequest.getTank_H());
        Integer tank_Thick = FabricationInputs.getTank_Thick(fabricationRequest.getTank_Thick());
        Integer tank_Bot_Thick = FabricationInputs.getTank_Bot_Thick(fabricationRequest.getTank_Bot_Thick());
        Integer tank_Flg_Thick = FabricationInputs.getTank_Flg_Thick(fabricationRequest.getTank_Flg_Thick());
        Integer radiator_CC = FabricationInputs.getRadiator_CC(fabricationRequest.getRadiator_CC());
        Integer radiator_W = FabricationInputs.getRadiator_W(fabricationRequest.getRadiator_W());
        Integer radiator_Fin_Nos = FabricationInputs.getRadiator_Fin_Nos(fabricationRequest.getRadiator_Fin_Nos());
        Integer radiator_Nos = FabricationInputs.getRadiator_Nos(fabricationRequest.getRadiator_Nos());
        Integer lid_Thick = FabricationInputs.getLid_Thick(fabricationRequest.getLid_Thick());
        Integer hvb_Volt = FabricationInputs.getHvb_Volt(fabricationRequest.getHvb_Volt());
        Integer hvb_Amp = FabricationInputs.getHvb_Amp(fabricationRequest.getHvb_Amp());
        Integer lvb_Volt = FabricationInputs.getLvb_Volt(fabricationRequest.getLvb_Volt());
        Integer lvb_Amp = FabricationInputs.getLvb_Amp(fabricationRequest.getLvb_Amp());
        Integer cons_Vol = FabricationInputs.getCons_Vol(fabricationRequest.getCons_Vol());
        Integer cons_Dia = FabricationInputs.getCons_Dia(fabricationRequest.getCons_Dia());
        Integer cons_L = FabricationInputs.getCons_L(fabricationRequest.getCons_L());
        Integer transformer_Weight = FabricationInputs.getTransformer_Weight(fabricationRequest.getTransformer_Weight());
        Integer core_Dia=FabricationInputs.getCore_Dia(fabricationRequest.getCore_Dia());
        Integer limb_CC=FabricationInputs.getLimb_CC(fabricationRequest.getLimb_CC());
        Integer limb_Nos=FabricationInputs.getLimb_Nos(fabricationRequest.getLimb_Nos());
//        String core_Fixture_Type=FabricationInputs.getCore_Fixture_Type(fabricationRequest.getCore_Fixture_Type());
        Boolean yoke_Holes= CommonFunctions.nullCheckReturnBollean(fabricationRequest.getYoke_Holes());
        Double lv_Cond_W = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getLv_Cond_W());
        Double lv_Cond_H = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getLv_Cond_H());
        Double lv_Cond_Rad = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getLv_Cond_Rad());
        Double lv_Cond_Ax = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getLv_Cond_Ax());
        Double lv_Volts = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getLv_Volts());
        Double lv_ID = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getLv_ID());
        Double lv_OD = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getLv_OD());
        Double lv_Wdg_L = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getLv_Wdg_L());
        Double hv_ID = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getHv_ID());
        Double hv_OD = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getHv_OD());
        Double hv_Wdg_L = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getHv_Wdg_L());
        Double limb_H = CommonFunctions.nullCheckReturnDouble(fabricationRequest.getLimb_H());
        Integer radiator_Left_Nos = CommonFunctions.nullCheckReturnInteger(fabricationRequest.getRadiator_Left_Nos());
        Integer radiator_Right_Nos = CommonFunctions.nullCheckReturnInteger(fabricationRequest.getRadiator_Right_Nos());
        String hvb_Pos=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getHvb_Pos());
        String lvb_Pos=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getLvb_Pos());
        String hvb_Type=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getHvb_Type());
        String lvb_Type=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getLvb_Type());
        Double current=CommonFunctions.nullCheckReturnDouble(fabricationRequest.getCurrent());
        Double hvb_F_Tilt_Ang=CommonFunctions.nullCheckReturnDouble(fabricationRequest.getHvb_F_Tilt_Ang());
        String hvb_Sup_type=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getHvb_Sup_type());
        String lvb_Sup_type=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getLvb_Sup_type());
        String stiffner_Hori_Nos_Calc_Mode=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getStiffner_Hori_Nos_Calc_Mode());
        String stiffner_Vert_Nos_Calc_Mode=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getStiffner_Vert_Nos_Calc_Mode());
        String radiator_Type=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getRadiator_Type());
        String rad_Ptch_Calc_Mode=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getRad_Ptch_Calc_Mode());
        Integer radiator_Min_Gap=CommonFunctions.nullCheckReturnInteger(fabricationRequest.getRadiator_Min_Gap());
        Boolean drain_Vlv=CommonFunctions.nullCheckReturnBooleanTrue(fabricationRequest.getDrain_Vlv());
        Integer drain_Vlv_Nos=CommonFunctions.nullCheckReturnInteger(fabricationRequest.getDrain_Vlv_Nos());
        Boolean smpl_Vlv=CommonFunctions.nullCheckReturnBooleanTrue(fabricationRequest.getSmpl_Vlv());
        Integer smpl_Vlv_Nos=CommonFunctions.nullCheckReturnInteger(fabricationRequest.getSmpl_Vlv_Nos());
        Boolean fill_Vlv=CommonFunctions.nullCheckReturnBooleanTrue(fabricationRequest.getFill_Vlv());
        Boolean tank_LiftLug=CommonFunctions.nullCheckReturnBooleanTrue(fabricationRequest.getTank_LiftLug());
        Boolean hvcb=CommonFunctions.nullCheckReturnBollean(fabricationRequest.getHvcb());
        String hvcb_Pos=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getHvcb_Pos());
        Boolean lvcb=CommonFunctions.nullCheckReturnBollean(fabricationRequest.getLvcb());
        Integer mog_Tlt_Ang=CommonFunctions.nullCheckReturnInteger(fabricationRequest.getMog_Tlt_Ang());
        Boolean roller=CommonFunctions.nullCheckReturnBooleanTrue(fabricationRequest.getRoller());
        String roller_Type=CommonFunctions.nullCheckReturnEmpty(fabricationRequest.getRoller_Type());
        Boolean thermoPkt1 = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getThermoPkt1());
        Boolean thermoPkt2 = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getThermoPkt2());
        Boolean exp_Vent = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getExp_Vent());
        Boolean exp_Vent_With_OI = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getExp_Vent_With_OI());
        Boolean isRoller = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getIsRoller());
        Boolean isOCTC = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getIsOCTC());
        Boolean buchholz_Realy = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getBuchholz_Relay());
        Boolean single_Valve = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getSingle_Valve());
        Boolean valve_Type1 = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getValve_Type1());
        Boolean marshBox = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getMarshBox());
        Boolean mog = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getMog());
        Boolean isRadiatorValve = CommonFunctions.nullCheckReturnBollean(fabricationRequest.getRadiator_Vlv());
        Double tapstepPercent = fabricationRequest.getTapStepPercentage();
        int noOfHvTerminals = fabricationRequest.getEVectorGroup().toString().charAt(0) == 'D' ? 3 : 4;
        int noOfLvTerminals = fabricationRequest.getEVectorGroup().toString().charAt(1) == 'd' ? 3 : 4;


        //Declare
        Fabrication fabrication = new Fabrication();

        Tank tank = TankFormulas.calculateTankFormulas(kVA, tank_L, tank_W, tank_H
                , tank_Thick, tank_Bot_Thick, tank_Flg_Thick);
        fabrication.setTank(tank);

        fabrication.setLid(LidFormulas.calcuateLid(lid_Thick, tank_L, tank_W, tank_Thick
                , tank.getTank_Flg_W(), tank.getTank_Flg_Bolt_Dia()));

        fabrication.setHvb(HvBFormulas.calcuateHvb(
                hvb_Pos,hvb_Volt, hvb_Amp, hvb_Volt, hvcb, hvb_Type, current,
                hvb_F_Tilt_Ang, fabrication.getTank().getTank_L(),
                fabrication.getTank().getTank_Thick(), hvb_Sup_type,
                fabrication.getLid().getLid_Thick(), noOfHvTerminals, tank_W, kVA, tank_H
        ));

        fabrication.setHvcb(HVCBForumulas.calculateHvcb(hvcb, tank_H, tank_L, fabricationRequest.getHvb_Volt(),fabricationRequest.getHvb_Amp()));

        //Previously, instead of hvb_Volt, kVA was used. but, according to formula, the value should be highVoltage.
        // hvb_Volt = highVoltage.

        fabrication.setLvcb(LVCBForumulas.calculateLvcb(lvcb, tank_H, lvb_Volt, lvb_Amp, fabricationRequest.getLvb_Volt(), tank_L));


        fabrication.setStiffner(StiffenerFormulas.calculateStiffner(tank_L, tank_W, tank_H, tank_Flg_Thick, kVA, stiffner_Hori_Nos_Calc_Mode,stiffner_Vert_Nos_Calc_Mode, tank_Thick, tank_Bot_Thick));

        fabrication.setFill_Vlv(FillVlvFormulas.calculateFillerVlv(kVA, fill_Vlv, tank_W, tank_H));
        fabrication.setDrain_Vlv(DrainVlvFormulas.calculateDrainerVlv(kVA,drain_Vlv,tank_W,tank_H));
        fabrication.setSmpl_Vlv(SmplFormulas.calculateSmplVlv(smpl_Vlv,smpl_Vlv_Nos, tank_W, tank_H, isOCTC));
        fabrication.setTank_LiftLug(TankLiftLugFormulas.calculateTankLiftLug(kVA, tank.getTank_Flg_W(),
                tank_Flg_Thick, tank.getTank_Flg_Bolt_H_Pch(), tank_Thick, lid_Thick,tank_LiftLug, isOCTC));
        fabrication.setJackpad(JackapadFormulas.calculateJackpad(kVA, tank_L));


        fabrication.setLvb(LvBFormulas.calcuateLvb(lvcb,lvb_Pos,lvb_Volt, lvb_Amp, lvb_Type,
                fabrication.getTank().getTank_L(),
                lvb_Sup_type,
                fabrication.getTank().getTank_Thick(),
                fabrication.getLid().getLid_Thick(), noOfLvTerminals, tank_W, tank_H, noOfHvTerminals
                ));

        fabrication.setRadiator(RadiatorFormulas.calcualteRadiatorFormulas(kVA, radiator_CC, tank_H, tank_L,tank_Thick,
                tank.getTank_Flg_W(), radiator_W, radiator_Fin_Nos, radiator_Nos, radiator_Type, lvcb, hvcb,
                fabrication.getHvcb().getHvcb_L1(), fabrication.getLvcb().getLvcb_L1(), radiator_Left_Nos,
                radiator_Right_Nos, fabrication.getStiffner().getStiffner_Vert_Data_L(), fabrication.getStiffner().getStiffner_Vert_Data_W(),
                rad_Ptch_Calc_Mode,radiator_Min_Gap, isRadiatorValve, fabrication.getLvb().getLvb_Pos(),
                fabrication.getHvb().getHvb_Pos(), fabrication.getLvb().getLvb_OnTank_Length(), fabrication.getHvb().getHvb_OnTank_Length()));

        fabrication.setMog(MogFormulas.calculateMog(kVA, mog_Tlt_Ang, mog));
        fabrication.setBuch_Rely(BuchRelyFormulas.calculateBuchRely(kVA));
        fabrication.setHv(HvFormulas.calculateHv(kVA, hvcb, hv_ID, hv_OD, hv_Wdg_L,hvb_Amp));
        fabrication.setGorPipe(GorPipeFormulas.calculateGorPipe(buchholz_Realy, single_Valve,valve_Type1, kVA, fabrication.getLid().getLid_L(), tank_L, fabrication.getLvb().getLvb_VPos()));
        fabrication.setCons(ConservatorFormulas.calculateCons(kVA, cons_Vol, cons_Dia, cons_L,
                fabrication.getGorPipe().getGorPipe_Ass_H(), fabrication.getGorPipe().getGorPipe_Ass_L(),
                fabrication.getGorPipe().getGorPipe_Hpos(), fabrication.getLid().getLid_L(), tank_Thick, tank_L,
                fabrication.getLid().getLid_W(), fabrication.getGorPipe().getGorPipe_Vpos(), buchholz_Realy, noOfHvTerminals));
        fabrication.setExp_Vent(ExpVentFormulas.calculateExpVent(exp_Vent, exp_Vent_With_OI, fabrication.getCons().getCons_V_Pos(),
                fabrication.getCons().getCons_Dia(), tank_L, tank_W,fabrication.getCons().getCons_Pos(), fabrication.getHvb().getHvb_Neutral_VPos()));
        fabrication.setPressRelDevice(PressRelDeviceFormulas.calculatePressRelDev(tank_L,tank_W, fabrication.getHvb().getHvb_Ptch()));
        fabrication.setInspect(InspectFormulas.calculateInspect(kVA,
                fabrication.getHvb().getHvb_Pos(),
                fabrication.getLvb().getLvb_Pos(),
                fabrication.getHvb().getHvb_Ptch(),
                fabrication.getLvb().getLvb_Ptch(),
                tank_W,
                fabrication.getLid().getLid_Thick(),
                fabrication.getHvb().getHvb_tank_Oil_Clear(),
                fabrication.getHvb().getHvb_Sup_OD(),
                fabrication.getLvb().getLvb_tank_Oil_Clear(),
                fabrication.getLvb().getLvb_Sup_ID(),
                tank_L, fabrication.getHvb().getHvb_Sup_V_Pos(), hvcb, lvcb,
                fabrication.getLvb().getLvb_Sup_V_Pos(), fabrication.getLvb().getLvb_Sup_OD()
        ));

        fabrication.setInspectCov(InspectCovFormulas.getInspectCov(lvcb, hvcb, tank_W, fabrication.getLvb().getLvb_VPos(),
                fabrication.getHvb().getHvb_VPos(), fabrication.getLvb().getLvb_Sup_OD(), fabrication.getHvb().getHvb_Sup_OD(),
                lvb_Pos, hvb_Pos, fabrication.getCons().getCons_Pos()));

        fabrication.setThermoPkt(ThermoPktFormulas.calculateThermoPkt(thermoPkt1, thermoPkt2, tank_L, tank_W, (int) Math.ceil(fabrication.getHvb().getHvb_Ptch())));

        fabrication.setFabricationCore(FabricationCoreFormulas.calculateFabricationCore(core_Dia,
                limb_CC,
                limb_Nos
        ));

        fabrication.setRoller(RollerFormulas.calculateRoller(kVA, transformer_Weight,
                tank_L, tank_Thick,roller,roller_Type));


        fabrication.setRestOfVariables(RestOfVariablesFormulas.calcuateRestOfVariables(transformer_Weight,
                designId,core_Dia,limb_CC,limb_Nos,yoke_Holes,kVA,
                tank_W,
                limb_H,
                fabrication.getFabricationCore().getCore_Fixture_Type(),
                fabrication.getFabricationCore().getCore_Fixture_H(),
                fabrication.getFabricationCore().getCore_Fixture_Thick(),
                fabrication.getFabricationCore().getCore_Bottom_Clearence(),
                hv_OD, tank_L, fabrication.getInspect().getInspect_Cover(), fabrication.getHvb().getHvb_Ptch()
        ));



        fabrication.setBot_Chnl(BotChnFormulas.calculateBotChnl(kVA, isRoller,tank_L, tank_W, tank_Thick,
                fabrication.getStiffner().getStiffner_Vert_Data_L()));


        fabrication.setLid_LiftLug(LidLIftLugFormulas.calcuateLidLiftLug(kVA,tank_L,tank_Thick,tank_W));


        fabrication.setCoreFoot(CoreFootFormulas.calculateCoreFoot(kVA,
                fabrication.getFabricationCore().getCore_Bottom_Clearence(),
                core_Dia,
                fabrication.getFabricationCore().getCore_Fixture_H(),
                fabrication.getRestOfVariables().getYoke_Compensation(),
                fabrication.getFabricationCore().getCore_Fixture_Thick(),
                fabrication.getFabricationCore().getCore_Fixture_W()
                ));

        fabrication.setLift_Cons(LiftConsFormulas.calculateLiftCons(cons_Dia,cons_Vol));

        fabrication.setCf(CfFormulas.calculateCf(
                fabrication.getRestOfVariables().getStayrod_Dia(),
                fabrication.getRestOfVariables().getStayrod_Nos(),
                fabrication.getFabricationCore().getCore_Fixture_H(),
                fabrication.getRestOfVariables().getTieRod_Dia(),
                core_Dia,
                fabrication.getFabricationCore().getCore_Fixture_Thick(),
                fabrication.getFabricationCore().getCore_Fixture_W(),
                limb_CC,
                lv_Cond_W,
                lv_Cond_Ax,
                hv_OD,
                fabrication.getFabricationCore().getCore_Fixture_L(),
                fabrication.getFabricationCore().getCore_Fixture_Type()
        ));

        int stayRodL = RestOfVariablesFormulas.calculateStayRodL(core_Dia, fabrication.getRestOfVariables().getStayrod_Dia(),
                fabrication.getFabricationCore().getCore_Fixture_Thick(), fabrication.getCf().getCf_StayRod_Plate_Thick());
        fabrication.getRestOfVariables().setStayrod_L(stayRodL);

        fabrication.setLv(LvFormulas.calculateLv(kVA, lv_Cond_W, lv_Cond_H, lv_Cond_Rad, lv_Cond_Ax
        ,lv_ID, lv_OD, lv_Volts, lv_Wdg_L, lvcb,lvb_Amp));
        fabrication.setHvct(HvctFormulas.calculateHvct(kVA));
        fabrication.setLvct(LvctFormulas.calculateLvct(kVA));

        fabrication.setMarshBox(MarshBoxFormulas.calculateMarshBox(kVA, marshBox, tank_H));

        fabrication.setOctcFlange(OctcFlangeFormula.calculateOctcFlange(isOCTC, tank_H));

        fabrication.setPrintOuts(fabricationRequest.getPrintouts());

        fabrication.setTurnsPerTap(TurnsPerTapFormulas.calculateTurnsPerTap(fabricationRequest.getTurnsPerTap()));
        fabrication.setTapVoltages(TapVoltagesFormulas.calculateTapVoltages(fabricationRequest.getTapVoltages()));
        fabrication.setTapCurrent(TapCurrentFormulas.calculateTapCurrent(fabricationRequest.getTapCurrent()));
        fabrication.setTapStepPercentage(tapstepPercent);

        return fabrication;

    }
}
