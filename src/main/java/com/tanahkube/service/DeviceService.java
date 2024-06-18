package com.tanahkube.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.tanahkube.entity.Device;
import com.tanahkube.model.DeviceRequest;
import com.tanahkube.model.DeviceResponse;
import com.tanahkube.model.PagingResponse;
import com.tanahkube.model.WebResponse;
import com.tanahkube.repository.DeviceRepository;

@Service
public class DeviceService {
    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }

    private DeviceResponse toDeviceResponseNoApiKey(Device data){
        return DeviceResponse.builder()
        .id(data.getId())
        .name(data.getName())
        .location(data.getLocation())
        .latitude(data.getLatitude())
        .longitude(data.getLongitude())
        .build();
    }

    private DeviceResponse toDeviceResponseWithApiKey(Device data){
        return DeviceResponse.builder()
        .id(data.getId())
        .name(data.getName())
        .apiKey(data.getApiKey())
        .location(data.getLocation())
        .latitude(data.getLatitude())
        .longitude(data.getLongitude())
        .build();
    }

    private WebResponse toWebResponse(String status, Object message, Object data, Object paging){
        return WebResponse.builder()
        .status(status)
        .message(message)
        .data(data)
        .paging(paging)
        .build();
    }

    // Create---------
    @Transactional
    public WebResponse create(DeviceRequest request , Errors errors){
        try {
            // validation
            if (errors.hasErrors()) {
                List<Object> error = new ArrayList<>();
                for (ObjectError err : errors.getAllErrors()) {
                    error.add(err.getDefaultMessage());
                }
                return toWebResponse("failed", error, new HashMap<>(), new HashMap<>());
            }
            // end validation

            Device device = new Device();
            device.setName(request.getName());
            device.setApiKey(UUID.randomUUID().toString());
            device.setLocation(request.getLocation());
            device.setLatitude(request.getLatitude());
            device.setLongitude(request.getLongitude());
            device.setCreatedOn(new Date());
            device.setIsDelete(false);

            Device deviceSave = this.deviceRepository.save(device);
            if (device.equals(deviceSave)) {
                DeviceResponse deviceResponse = toDeviceResponseNoApiKey(deviceSave);
                return toWebResponse("success", "success create data", deviceResponse, new HashMap<>());
            }else{
                return toWebResponse("failed", "failed create data", new HashMap<>(), new HashMap<>());
            }

        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }
    
    // Read-----------
    @Transactional(readOnly = true)
    public WebResponse getDataById(Long id){
        try {
            Device device = this.deviceRepository.findById(id).orElse(null);

            if (device != null) {
                DeviceResponse deviceResponse = toDeviceResponseNoApiKey(device);
                return toWebResponse("success", "success fetch data", deviceResponse, new HashMap<>());
            }else{
                return toWebResponse("failed", "id not found", new HashMap<>(), new HashMap<>());
            }

        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }

    @Transactional(readOnly = true)
    public WebResponse getAllData(){
        try {
            List<DeviceResponse> deviceResponses = this.deviceRepository.findAllData().stream().map(this::toDeviceResponseNoApiKey).toList();

            if (!deviceResponses.isEmpty()) {
                return toWebResponse("success", "success fetch data", deviceResponses, new HashMap<>());
            }else{
                return toWebResponse("success", "no data", new HashMap<>(), new HashMap<>());
            }

        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }

    @Transactional(readOnly = true)
    public WebResponse getAllDataPageble(String search, String order, Integer pageSize, Integer pageNumber){
        try {

            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
            if (order.equals("desc")) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").descending());
            }
            Page<Device> dataPage = this.deviceRepository.findAllDataPageble(pageable);
            if (!search.equals("")) {
                dataPage = this.deviceRepository.findAllDataBySearch(pageable, search);
            }

            List<DeviceResponse> deviceResponses = dataPage.getContent().stream().map(this::toDeviceResponseWithApiKey).toList();

            PagingResponse pagingResponse = new PagingResponse();
            pagingResponse.setCurrentPage(dataPage.getNumber());
            pagingResponse.setSize(dataPage.getSize());
            pagingResponse.setTotalPage(dataPage.getTotalPages());
            pagingResponse.setTotalItem(dataPage.getTotalElements());

            if (!deviceResponses.isEmpty()) {
                return toWebResponse("success", "success fetch data", deviceResponses, pagingResponse);
            }else{
                return toWebResponse("success", "no data", new HashMap<>(), new HashMap<>());
            }

        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }
    // Update---------
    @Transactional
    public WebResponse update(DeviceRequest request , Errors errors){
        try {
            // validation
            if (errors.hasErrors()) {
                List<Object> error = new ArrayList<>();
                for (ObjectError err : errors.getAllErrors()) {
                    error.add(err.getDefaultMessage());
                }
                return toWebResponse("failed", error, new HashMap<>(), new HashMap<>());
            }
            // end validation

            Device device = this.deviceRepository.findById(request.getId()).orElse(null);
            if (device != null) {
                device.setName(request.getName());
                // device.setApiKey(UUID.randomUUID().toString());
                device.setLocation(request.getLocation());
                device.setLatitude(request.getLatitude());
                device.setLongitude(request.getLongitude());
                device.setModifiedOn(new Date());
    
                Device deviceSave = this.deviceRepository.save(device);
                if (device.equals(deviceSave)) {
                    DeviceResponse deviceResponse = toDeviceResponseNoApiKey(deviceSave);
                    return toWebResponse("success", "success modified data", deviceResponse, new HashMap<>());
                }else{
                    return toWebResponse("failed", "failed modified data", new HashMap<>(), new HashMap<>());
                } 
            }else{
                return toWebResponse("failed", "id not found", new HashMap<>(), new HashMap<>());
            }

        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }
    // Delete---------
    @Transactional
    public WebResponse delete(Long id){
        try {
            Device device = this.deviceRepository.findById(id).orElse(null);
            if (device != null) {
                device.setIsDelete(true);
                device.setDeletedOn(new Date());
    
                Device deviceSave = this.deviceRepository.save(device);
                if (device.equals(deviceSave)) {
                    DeviceResponse deviceResponse = toDeviceResponseNoApiKey(deviceSave);
                    return toWebResponse("success", "success deleted data", deviceResponse, new HashMap<>());
                }else{
                    return toWebResponse("failed", "failed deleted data", new HashMap<>(), new HashMap<>());
                } 
            }else{
                return toWebResponse("failed", "id not found", new HashMap<>(), new HashMap<>());
            }

        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }
}
