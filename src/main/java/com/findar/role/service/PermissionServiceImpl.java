package com.findar.role.service;


import com.findar.role.repository.PermissionRepository;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
//    @Cacheable(cacheNames = "permisions")
//    public MedusaMerchantResponse<?> viewAll() {
//        log.info("viewAll permission from db ");
//        return new MedusaMerchantResponse<>(Response.SUCCESS_CODE, Response.RECORDS_FOUND, new HashSet<>(permissionRepository.findAll()), HttpStatus.OK);
//    }




}
