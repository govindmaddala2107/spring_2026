package com.gomad.ecom.controllers;

import com.gomad.ecom.payload.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version-controller")
public class LegacyVersionController {

    @GetMapping({"", "/", "/v1"})
    public ResponseEntity<CustomApiResponse> defaultPathVersion(){
        return ResponseEntity.ok(new CustomApiResponse("Response from defaultPathVersion: V1", true, HttpStatus.OK));
    }

    @GetMapping("v2")
    public ResponseEntity<CustomApiResponse> defaultPathVersion2(){
        return ResponseEntity.ok(new CustomApiResponse("Response from defaultPathVersion: V2", true, HttpStatus.OK));
    }

    @GetMapping(params = "version=1")
    public ResponseEntity<CustomApiResponse> defaultParamVersion1(){
        return ResponseEntity.ok(new CustomApiResponse("Response from defaultParamVersion: V1", true, HttpStatus.OK));
    }

    @GetMapping(params = "version=2")
    public ResponseEntity<CustomApiResponse> defaultParamVersion2(){
        return ResponseEntity.ok(new CustomApiResponse("Response from defaultParamVersion: V2", true, HttpStatus.OK));
    }

    @GetMapping(headers = "X-API-Version=1")
    public ResponseEntity<CustomApiResponse> defaultHeaderVersion1(){
        return ResponseEntity.ok(new CustomApiResponse("Response from defaultHeaderVersion: V1", true, HttpStatus.OK));
    }

    @GetMapping(headers = "X-API-Version=2")
    public ResponseEntity<CustomApiResponse> defaultHeaderVersion2(){
        return ResponseEntity.ok(new CustomApiResponse("Response from defaultHeaderVersion: V2", true, HttpStatus.OK));
    }

    @GetMapping(produces = "application/vnd.gomad.v1+json")
    public ResponseEntity<CustomApiResponse> defaultMediaTypeVersion1(){
        return ResponseEntity.ok(new CustomApiResponse("Response from defaultMediaTypeVersion: V1", true, HttpStatus.OK));
    }

    @GetMapping(produces = "application/vnd.gomad.v2+json")
    public ResponseEntity<CustomApiResponse> defaultMediaTypeVersion2(){
        return ResponseEntity.ok(new CustomApiResponse("Response from defaultMediaTypeVersion: V2", true, HttpStatus.OK));
    }
}
