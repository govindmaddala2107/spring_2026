package com.gomad.ecom.controllers;

import com.gomad.ecom.payload.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/latest/products/{v}")
@RequestMapping("/latest/products")
public class LatestVersionController {

    @GetMapping(version = "1.0")
    public ResponseEntity<CustomApiResponse> defaultPathVersion(){
        return ResponseEntity.ok(new CustomApiResponse("Response from V1", true, HttpStatus.OK));
//        throw new RuntimeException("AOP testing");
    }

    @GetMapping(version = "2.0+")
    public ResponseEntity<CustomApiResponse> defaultPathVersion2(){
        return ResponseEntity.ok(new CustomApiResponse("Response from V2", true, HttpStatus.OK));
    }
}
