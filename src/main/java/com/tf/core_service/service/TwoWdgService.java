package com.tf.core_service.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tf.core_service.model.common.Config;
import com.tf.core_service.model.twoWindings.ETransBodyType;
import com.tf.core_service.model.twoWindings.TwoWindings;
import com.tf.core_service.request.TwoWindingRequest;
import com.tf.core_service.service.circ2Wdg.Circ2WdgService;
import com.tf.core_service.service.rect2Wdg.Rect2WdgService;
import com.tf.core_service.utils.Configurations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rajesh.mylsamy
 * @createdOn 2022-06-01
 */

@Slf4j
@Service
public class TwoWdgService {

 @Autowired
 private Circ2WdgService circ2WdgService;

 @Autowired
 private Rect2WdgService rect2WdgService;

 public TwoWindings calculate2WdgService(TwoWindingRequest twoWindingRequest, String coreType) throws JacksonException {
  if (ETransBodyType.RECTANGULAR.toString().equalsIgnoreCase(coreType)) {
   return rect2WdgService.calculate2Wdg(twoWindingRequest);
  } else {
   return circ2WdgService.calculateForTwoWindingsWithChecks(twoWindingRequest);
  }
 }


}
