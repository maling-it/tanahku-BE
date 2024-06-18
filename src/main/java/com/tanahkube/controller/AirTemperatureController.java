package com.tanahkube.controller;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tanahkube.model.DataRequest;
import com.tanahkube.model.WebResponse;
import com.tanahkube.service.AirTemperatureService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/airtemperature/")
public class AirTemperatureController {
    
    private AirTemperatureService airTemperatureService;

    public AirTemperatureController(AirTemperatureService airTemperatureService){
        this.airTemperatureService = airTemperatureService;
    }

    // Create---------
    @PostMapping("create")
    public WebResponse createData(@RequestHeader String apiKey,@Valid @RequestBody DataRequest request, Errors errors){
        return this.airTemperatureService.create(apiKey ,request, errors);
    }
    // Read-----------
    @GetMapping("lastdata")
    public WebResponse readAllData(@RequestParam Long deviceId){
        return this.airTemperatureService.getLastData(deviceId);
    }
    
    @GetMapping("limitdata")
    public WebResponse readLimitData(@RequestParam Long deviceId, @RequestParam Integer limit){
        return this.airTemperatureService.getLimitData(deviceId, limit);
    }

    @GetMapping("avaragedata")
    public WebResponse readAvarageOneHourData(@RequestParam Long deviceId){
        return this.airTemperatureService.getAvarageOneHourData(deviceId);
    }

    @GetMapping("alldata")
    public WebResponse readData(
        @RequestParam Long deviceId,
        @RequestParam(defaultValue = "") String search,
        @RequestParam(defaultValue = "asc") String order,
        @RequestParam(defaultValue = "5") Integer pageSize,
        @RequestParam(defaultValue = "0") Integer pageNumber
        ){
        return this.airTemperatureService.getAllData(deviceId, search, order, pageSize, pageNumber);
    }
    // Update---------
    // Delete---------
}
