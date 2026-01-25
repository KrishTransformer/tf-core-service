package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;


@Data
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class Fabrication {
    private Tank tank;
    private Stiffner stiffner;
    private Radiator radiator;
    private FillVlv fill_Vlv;
    private DrainVlv drain_Vlv;
    private SmplVlv smpl_Vlv;
    private TankLiftLug tank_LiftLug;
    private Jackpad jackpad;

    private Hvb hvb;
    private Hvcb hvcb;
    private Lvb lvb;
    private Lvcb lvcb;
    private Lid lid;

    private Cons cons;
    private BuchRely buch_Rely;
    private Mog mog;
    private ExpVent exp_Vent;
    private PressRelDevice pressRelDevice;
    private Inspect inspect;
    private InspectCov inspectCov;
    private ThermoPkt thermoPkt;

    private BotChnl bot_Chnl;
    private Roller roller;
    private Cf cf;
    private LidLiftLug lid_LiftLug;
    private RestOfVariables restOfVariables;
    private FabricationCore fabricationCore;
    private CoreFoot coreFoot;
    private Lift_Cons lift_Cons;
    private Hv hv;
    private Lv lv;
    private Hvct hvct;
    private Lvct lvct;
    private GorPipe gorPipe;
    private MarshBox marshBox;
    private OctcFlange octcFlange;
    private Printouts printOuts;
    public TurnsPerTap turnsPerTap;
    public TapVoltages tapVoltages;
    public TapCurrent tapCurrent;
    public Double tapStepPercentage;
}
