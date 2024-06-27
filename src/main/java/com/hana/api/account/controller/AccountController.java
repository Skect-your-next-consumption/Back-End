package com.hana.api.account.controller;

import com.hana.api.account.service.AccountService;
import com.hana.api.user.dto.request.LoginRequest;
import com.hana.api.user.entity.User;
import com.hana.common.type.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/accounts")
@Tag(name = "Account API", description = "계좌 관련 API")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "계좌 정보 조회", description = "계좌 정보 조회를 위한 API 입니다.")
    @GetMapping("")
    public ResponseEntity<?> getMyAccount(@CurrentUser User user) {
        return accountService.getMyAccount(user);
    }

    @Operation(summary = "계좌 내역 조회", description = "계좌 내역 조회를 위한 API 입니다.")
    @GetMapping("/logs")
    public ResponseEntity<?> getMyAccountLogs(@CurrentUser User user) {
        return accountService.getMyAccountLogs(user);
    }
}
