package com.hana.api.admin.controller;

import com.hana.api.admin.dto.response.AccumulatedAmountResponseDto;
import com.hana.api.admin.service.AdminService;
import com.hana.api.user.entity.User;
import com.hana.common.response.Response;
import com.hana.common.type.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admins")
@Tag(name = "Admin API", description = "관리자 페이지 API")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "씽크 계좌 내역 조회", description = "사업자 등록 계좌 정보 조회")
    @GetMapping("/accumulated/{period}")
    public ResponseEntity<?> getAccumulatedAmounts(@CurrentUser User user,
                                                   @PathVariable("period") int period) {
        return adminService.getAccumulatedAmounts(period);
    }
}
