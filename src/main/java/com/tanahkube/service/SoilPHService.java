package com.tanahkube.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.tanahkube.entity.Device;
import com.tanahkube.entity.SoilPH;
import com.tanahkube.model.DataRequest;
import com.tanahkube.model.DataResponse;
import com.tanahkube.model.DeviceResponse;
import com.tanahkube.model.PagingResponse;
import com.tanahkube.model.WebResponse;
import com.tanahkube.repository.DeviceRepository;
import com.tanahkube.repository.SoilPHRepository;

@Service
public class SoilPHService {
    private SoilPHRepository soilPHRepository;
    private DeviceRepository deviceRepository;

    public SoilPHService(SoilPHRepository soilPHRepository, DeviceRepository deviceRepository){
        this.soilPHRepository = soilPHRepository;
        this.deviceRepository = deviceRepository;
    }

    private DeviceResponse toDeviceResponse(Device data){
        return DeviceResponse.builder()
        .id(data.getId())
        .name(data.getName())
        .location(data.getLocation())
        .latitude(data.getLatitude())
        .longitude(data.getLongitude())
        .build();
    }

    private DataResponse toDataResponse(SoilPH data){
        return DataResponse.builder()
        .id(data.getId())
        .value(data.getValue())
        .deviceId(data.getDeviceId())
        .createdOn(data.getCreatedOn())
        .device(toDeviceResponse(data.getDevice()))
        .build();
    }

    private WebResponse toWebResponse(String status, Object message, Object data, Object paging) {
        return WebResponse.builder()
                .status(status)
                .message(message)
                .data(data)
                .paging(paging)
                .build();
    }

    private boolean cekApiKey(Long deviceId, String apiKey){
        boolean isAuth = false;
        try {
            Device device = this.deviceRepository.findById(deviceId).orElse(null);
            if (device != null && apiKey.equals(device.getApiKey())) {
                isAuth = true;
            }
            return isAuth;
        } catch (Exception e) {
            return isAuth;
        }
    }
    
    // Create---------
    @Transactional
    public WebResponse create(String apiKey ,DataRequest request, Errors errors){
        try {
            // Validation
            if (!cekApiKey(request.getDeviceId(), apiKey)) {
                return toWebResponse("failed", "key does not match", new HashMap<>(), new HashMap<>());
            }

            if (errors.hasErrors()) {
                List<Object> error = new ArrayList<>();
                for (ObjectError objectError: errors.getAllErrors()) {
                    error.add(objectError.getDefaultMessage());
                }
                return toWebResponse("failed", error, new HashMap<>(), new HashMap<>());
            }
            // EndValidation

            SoilPH soilPH = new SoilPH();
            soilPH.setValue(request.getValue());
            soilPH.setDeviceId(request.getDeviceId());
            soilPH.setCreatedOn(new Date());
            soilPH.setIsDelete(false);
            soilPH = this.soilPHRepository.save(soilPH);

            Device device = this.deviceRepository.findById(soilPH.getDeviceId()).orElse(null);
            if (device != null && apiKey.equals(device.getApiKey())) {
                soilPH.setDevice(device);
            }

            DataResponse dataResponse = toDataResponse(soilPH);

            return toWebResponse("success", "success create data", dataResponse, new HashMap<>());
        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }

    // Read-----------
    @Transactional(readOnly = true)
    public WebResponse getLastData(Long deviceId){
        try {
            SoilPH soilPH = this.soilPHRepository.findLastData(deviceId).orElse(null);
            if (soilPH != null) {
                DataResponse dataResponse = toDataResponse(soilPH);
                return toWebResponse("success", "success fetch data", dataResponse, new HashMap<>());
            }else{
                return toWebResponse("success", "no data", new HashMap<>(), new HashMap<>());
            }
        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }

    @Transactional(readOnly = true)
    public WebResponse getLimitData(Long deviceId, Integer limit){
        try {
            List<SoilPH> soilPH = this.soilPHRepository.findLimitData(deviceId, limit);
            if (!soilPH.isEmpty()) {
                List<DataResponse> dataResponse = soilPH.stream().map(this::toDataResponse).toList();
                return toWebResponse("success", "success fetch data", dataResponse, new HashMap<>());
            }else{
                return toWebResponse("success", "no data", new HashMap<>(), new HashMap<>());
            }
        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }

    @Transactional(readOnly = true)
    public WebResponse getAllData(Long deviceId, String search, String order, Integer pageSize, Integer pageNumber){
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
            if (order.equals("desc")) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
            }
            Page<SoilPH> dataPage = this.soilPHRepository.findAllData(pageable, deviceId);
            if (!search.trim().equals("")) {
                dataPage = this.soilPHRepository.findAllBySearch(pageable, deviceId, search);
            }
            List<DataResponse> dataResponse = dataPage.getContent().stream().map(this::toDataResponse).toList();

            PagingResponse pagingResponse = new PagingResponse();
            pagingResponse.setCurrentPage(dataPage.getNumber());
            pagingResponse.setSize(dataPage.getSize());
            pagingResponse.setTotalPage(dataPage.getTotalPages());
            pagingResponse.setTotalItem(dataPage.getTotalElements());

            if (!dataResponse.isEmpty()) {
                return toWebResponse("success", "success fetch data", dataResponse, pagingResponse);
            }else{
                return toWebResponse("success", "no data", new HashMap<>(), new HashMap<>());
            }
        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }
    @Transactional(readOnly = true)
    public WebResponse getAvarageOneHourData(Long deviceId){
        try {
            List<SoilPH> soilPHs = this.soilPHRepository.findAvarageOneHourData(deviceId);
            if (!soilPHs.isEmpty()) {
                Double sum = 0.0;
                Double avarage = 0.0;
                Integer countNull = 0;
                for (SoilPH data : soilPHs) {
                    if (data.getValue() != null) {
                        sum += data.getValue();
                    }else{
                        countNull++;
                    }
                }
                avarage = sum / (soilPHs.size()- countNull);
                return toWebResponse("success", "success fetch data", avarage, new HashMap<>());
            }else{
                return toWebResponse("success", "no data", new HashMap<>(), new HashMap<>());
            }
        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), new HashMap<>(), new HashMap<>());
        }
    }
    // Update---------
    // Delete---------
}
