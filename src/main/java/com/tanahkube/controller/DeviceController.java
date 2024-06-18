package com.tanahkube.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tanahkube.model.DeviceRequest;
import com.tanahkube.model.WebResponse;
import com.tanahkube.service.DeviceService;

import jakarta.validation.Valid;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/device/")
public class DeviceController {
    private DeviceService deviceService;

    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    // Create---------
    @PostMapping("admin/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public WebResponse createData(@Valid @RequestBody DeviceRequest request, Errors errors){
        return this.deviceService.create(request, errors);
    }
    // Read-----------
    @GetMapping("{id}")
    public WebResponse readDataById(@PathVariable("id") Long id){
        return this.deviceService.getDataById(id);
    }

    @GetMapping("all")
    public WebResponse readAllData(){
        return this.deviceService.getAllData();
    }

    @GetMapping("admin/alldata")
    @PreAuthorize("hasAuthority('ADMIN')")
    public WebResponse readData(
        @RequestParam(defaultValue = "") String search,
        @RequestParam(defaultValue = "asc") String order,
        @RequestParam(defaultValue = "5") Integer pageSize,
        @RequestParam(defaultValue = "0") Integer pageNumber
        ){
        return this.deviceService.getAllDataPageble(search, order, pageSize, pageNumber);
    }

    // Update-----------
    @PutMapping("admin/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public WebResponse updateData(@Valid @RequestBody DeviceRequest request, Errors errors){
        return this.deviceService.update(request, errors);
    }
    // Delete-----------
    @PutMapping("admin/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public WebResponse updateData(@PathVariable("id") Long id){
        return this.deviceService.delete(id);
    }
}
