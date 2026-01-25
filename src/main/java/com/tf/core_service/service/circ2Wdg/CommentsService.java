package com.tf.core_service.service.circ2Wdg;


import com.fasterxml.jackson.core.JacksonException;
import com.tf.core_service.formulas.twoWdg.TwoWindingsFormulas;
import com.tf.core_service.model.twoWindings.Comments;
import com.tf.core_service.model.twoWindings.TwoWindings;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommentsService {
    public Comments getComments(TwoWindings twoWindings) throws JacksonException {

        double minLvCondIns = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getInnerWindings().getIsConductorRound(), twoWindings.getConnection(), twoWindings.getInnerWindings().getIsEnamel(), null, twoWindings.getDryType());
        double minHvCondIns = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getOuterWindings().getIsConductorRound(), twoWindings.getConnection(), twoWindings.getOuterWindings().getIsEnamel(), null, twoWindings.getDryType());
        double minLvInterLayerIns = TwoWindingsFormulas.getInterLayerInsulation(twoWindings.getVoltsPerTurn(), twoWindings.getInnerWindings().getTurnsPerLayer(), twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getInnerWindings().getIsEnamel(), null, twoWindings.getDryType());
        double minHvInterLayerIns = TwoWindingsFormulas.getInterLayerInsulation(twoWindings.getVoltsPerTurn(), twoWindings.getOuterWindings().getTurnsPerLayer(), twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getOuterWindings().getIsEnamel(), null, twoWindings.getDryType());
        double minCoreToLvGap = TwoWindingsFormulas.getCoreLvGap(twoWindings.getKVA(), twoWindings.getLowVoltage(), null, twoWindings.getDryType());
        double minLvToHvGap = TwoWindingsFormulas.getLvHvGap(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getConnection(), null,twoWindings.getDryType());
        double minHvToHvGap = TwoWindingsFormulas.getHvHVGap(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getHighVoltage(), twoWindings.getConnection(), null, twoWindings.getDryType());
        double minLvEndClr = 0;
        if(twoWindings.getLowVoltage() >= 11000 && (Objects.equals(twoWindings.getLvWindingType().toString(), "DISC"))){
            minLvEndClr = TwoWindingsFormulas.getEndClearance(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getConnection(), null, twoWindings.getDryType());
        }else{
            minLvEndClr = TwoWindingsFormulas.getEndClearance(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getConnection(), null, twoWindings.getDryType());
        }
        double minHvEndClr = TwoWindingsFormulas.getEndClearance(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getConnection(), null, twoWindings.getDryType());

        Comments comments = new Comments();
        comments.setTapStepComment("The recommended tap-step% for " + twoWindings.getKVA() + " is 2.5%");
        comments.setWattPerKgComment("Specific Loss of " + twoWindings.getCore().getCoreMaterial() + " at " + twoWindings.getFluxDensity() + "T");
        comments.setLvCondInsComment("Min Conductor Insulation for Lv is " + minLvCondIns + "mm.");
        comments.setHvCondInsComment("Min Conductor Insulation for Hv is " + minHvCondIns + "mm.");
        comments.setLvEndClrComment("Min Lv end clearance required is " + minLvEndClr + "mm.");
        comments.setHvEndClrComment("Min Hv end clearance required is " + minHvEndClr + "mm.");
        comments.setLvInterLayerInsComment("Min Inter-layer Insulation for Lv is " + minLvInterLayerIns + "mm.");
        comments.setHvInterLayerInsComment("Min Inter-layer Insulation for Hv is " + minHvInterLayerIns + "mm.");
        comments.setCoreToLvClrComment("Min Core to Lv gap required is " + minCoreToLvGap + "mm. 10% of " + minCoreToLvGap + "mm can be reduced by the user.");
        comments.setLvToHvClrComment("Min gap required between Lv and Hv is " + minLvToHvGap + "mm. 10% of " + minLvToHvGap + "mm can be reduced by the user.");
        comments.setHvToHvClrComment("Min gap required between Hv-Hv is " + minHvToHvGap + "mm at 75kV Impulse voltage. 10% of " + minHvToHvGap + "mm can be reduced by the user.");
        return comments;
    }
}
