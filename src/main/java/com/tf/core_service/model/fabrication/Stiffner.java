package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Stiffner {
    @JsonProperty private String stiffner_Hori_Nos_Calc_Mode;
    @JsonProperty private Integer stiffner_Hori_L;
    @JsonProperty private String stiffner_Hori_Data;
    @JsonProperty private Integer stiffner_Hori_Nos;
    @JsonProperty private Double stiffner_Hori_Pitch;
    @JsonProperty private Integer stiffner_Hori_Side_L;
    @JsonProperty private String  stiffner_Vert_Nos_Calc_Mode;
    @JsonProperty private Integer stiffner_Vert_L;
    @JsonProperty private String stiffner_Vert_Data;
    @JsonProperty private Integer stiffner_Vert_Nos;
    @JsonProperty private Double stiffner_Vert_Pitch;
    @JsonProperty private Integer stiffner_Vert_Side_Nos;
    @JsonProperty private Double stiffner_Vert_Side_Pitch;
    @JsonProperty private Integer stiffner_Vert_Data_L;
    @JsonProperty private Integer stiffner_Vert_Data_W;
    @JsonProperty private String stiffner_Vert_Data_Type;
    @JsonProperty private Integer stiffner_Hori_Data_L;
    @JsonProperty private Integer stiffner_Hori_Data_W;
    @JsonProperty private String stiffner_Hori_Data_Type;

}
