package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BotChnl {
//    @JsonProperty
//    private Double bot_Chnl_L1;
//    @JsonProperty private Integer bot_Chnl_L2;
//    @JsonProperty private Double bot_Chnl_L3;
//    @JsonProperty private Integer bot_Chnl_Sec_W1;
//    @JsonProperty private Integer bot_Chnl_Sec_W2;
//    @JsonProperty private Double bot_Chnl_Sec_Thick;
//    @JsonProperty private Double bot_Chnl_Pitch1;
//    @JsonProperty private Double bot_Chnl_Pitch2;
//    @JsonProperty private Double bot_Chnl_Pitch3;
//    @JsonProperty private Double bot_Chnl_Pitch4;
//    @JsonProperty private Double bot_Chnl_Pitch5;
//    @JsonProperty private String bot_Chnl_Sec_Data;

    //These are new parameters according to NB Sir.
    @JsonProperty private Boolean isRoller;
    @JsonProperty private Integer bot_Chnl_Type;
    @JsonProperty private Integer bot_Chnl_Roller_Dia;
    @JsonProperty private Integer bot_Chnl_Hole_Cen_Dist;
    @JsonProperty private Integer bot_Chnl_Len;
    @JsonProperty private Double bot_Chnl_Hpos;
    @JsonProperty private Double bot_Chnl_Vpos;
    @JsonProperty private Double bot_Chnl_Pitch;
}
