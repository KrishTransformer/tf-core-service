package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

import static com.tf.core_service.utils.Constants.NO_FORMULA;

@Data
public class RestOfVariables {

    @JsonProperty
    private Double rating_Plate_L;
    @JsonProperty private Double rating_Plate_H;
    @JsonProperty private Integer rating_Plate_Thick;
    @JsonProperty private Integer tc_Type;

    @JsonProperty private Integer limb_CC;
    @JsonProperty private Double limb_H;
    @JsonProperty private Integer limb_Nos;
    @JsonProperty private Double activePart_L;

    @JsonProperty private boolean yoke_Holes;


    @JsonProperty private Integer stayrod_Dia;
    @JsonProperty private Integer stayrod_L;
    @JsonProperty private Integer tieRod_Dia;
    @JsonProperty private Integer tieRod_L1;
    @JsonProperty private Integer tieRod_L2;
    @JsonProperty private Integer yokeRod_Dia;
    @JsonProperty private Integer yokeRod_L;

    @JsonProperty private Integer stayrod_Nos;
    @JsonProperty private Integer tieRod_L;
    @JsonProperty private Double yoke_Compensation;


    @JsonProperty private Double active_Height;

    @JsonProperty private Integer prv;
    @JsonProperty private String lift_Lid_Type;


    @JsonProperty private Integer transformer_Weight;  //EleDes
    @JsonProperty private int arp_Tank_Hpos;
    @JsonProperty private int arp_Tank_Vpos;
    @JsonProperty private String aircell = NO_FORMULA;
    @JsonProperty private String thrmo_Syphn = NO_FORMULA;
//    @JsonProperty private Integer CT;

    @JsonProperty private String designId;
    @JsonProperty("kVA") private Integer kVA;

}
