package com.tf.core_service.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rajesh.mylsamy
 * @createdOn 2022-11-06
 */
@JsonIgnoreProperties
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Config implements Serializable {
 private String tenantName;
 private String tenantUrl;
 private String tenantType;
 private String otherName;
 private String email;
 private String phoneNumber;
 private String merchantId;
 private List<ConstantConfig> constants;

}
