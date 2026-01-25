package com.tf.core_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author rajesh.mylsamy
 * @createdOn 2022-03-25
 */

@Slf4j
@RestController
@RequestMapping("/{tenantUrl}")
public class ManagementController {

 @GetMapping(value = "/health")
 public ResponseEntity<Object> getHealth(@PathVariable String tenantUrl) {
  try {
   return new ResponseEntity<>("Success", HttpStatus.OK);
  } catch (Exception e) {
   log.error("Error occurred in getting /health check", e);
   return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
 }

 @GetMapping(value = "/version")
 public ResponseEntity<Object> getVersion(@PathVariable String tenantUrl) {
  try {
   return new ResponseEntity<>("Success", HttpStatus.OK);
  } catch (Exception e) {
   log.error("Error occurred in getting /health check", e);
   return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
 }

}
