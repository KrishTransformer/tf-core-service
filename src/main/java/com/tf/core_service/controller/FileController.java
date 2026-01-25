package com.tf.core_service.controller;

import com.tf.core_service.request.LomRequest;
import com.tf.core_service.service.FileServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/{tenantUrl}/files")
public class FileController {

    @Autowired
    FileServices fileServices;

    @PostMapping(value = "/lom")
    public ResponseEntity<Object> get2Windings(@PathVariable String tenantUrl, @RequestBody LomRequest lomRequest) {
        try {
            return new ResponseEntity<>(fileServices.calculateLom(lomRequest), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred in getting /2windings ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
