package com.tf.core_service.controller;

import com.tf.core_service.request.CoreRequest;
import com.tf.core_service.request.FabricationRequest;
import com.tf.core_service.request.TwoWindingRequest;
import com.tf.core_service.service.CoreServices;
import com.tf.core_service.service.fabrication.FabricationService;
import com.tf.core_service.service.TwoWdgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/{tenantUrl}/calculate")
public class CalculatorController {

    @Autowired
    TwoWdgService twoWdgService;

    @Autowired
    FabricationService fabricationService;

    @Autowired
    CoreServices coreServices;

    @PostMapping(value = "/2windings/{coreType}")
    public ResponseEntity<Object> get2Windings(@PathVariable String tenantUrl, @PathVariable String coreType, @RequestBody TwoWindingRequest twoWindingRequest) {
        try {
            return new ResponseEntity<>(twoWdgService.calculate2WdgService(twoWindingRequest, coreType), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred in getting /2windings ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping(value = "/3windings")
//    public ResponseEntity<Object> get3Windings(@PathVariable String tenantUrl, @RequestBody TwoWindingRequest twoWindingRequest) {
//        try {
//            return new ResponseEntity<>(circ2WdgService.calculateForTwoWindingsWithChecks(twoWindingRequest), HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Error occurred in getting /2windings ", e);
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    @PostMapping(value = "/4windings")
//    public ResponseEntity<Object> get4Windings(@PathVariable String tenantUrl, @RequestBody TwoWindingRequest twoWindingRequest) {
//        try {
//            return new ResponseEntity<>(circ2WdgService.calculateForTwoWindingsWithChecks(twoWindingRequest), HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Error occurred in getting /2windings ", e);
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping(value = "/5windings")
//    public ResponseEntity<Object> get5Windings(@PathVariable String tenantUrl, @RequestBody TwoWindingRequest twoWindingRequest) {
//        try {
//            return new ResponseEntity<>(circ2WdgService.calculateForTwoWindingsWithChecks(twoWindingRequest), HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Error occurred in getting /2windings ", e);
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @PostMapping(value = "/fabrication")
    public ResponseEntity<Object> getFabrication(@PathVariable String tenantUrl, @RequestBody FabricationRequest fabricationRequest) {
        try {
            return new ResponseEntity<>(fabricationService.calculateForFabrication(fabricationRequest), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred in getting /fabrication ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/core")
    public ResponseEntity<Object> getCore(@PathVariable String tenantUrl, @RequestBody CoreRequest coreRequest) {
        try {
            return new ResponseEntity<>(coreServices.calculateForCore(coreRequest), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred in getting /core ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
