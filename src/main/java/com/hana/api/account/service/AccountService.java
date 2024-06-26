package com.hana.api.account.service;

import com.hana.api.account.entity.Account;
import com.hana.api.account.repository.AccountRepository;
import com.hana.common.exception.ErrorCode;
import com.hana.common.exception.account.AccountNumDuplicateException;
import com.hana.common.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final Response response;

    public Account createAccount(String accountName, Long accountBalance){

        Account account = Account.builder()
                .accountNum(generateAccountNum())
                .accoundCard(generateCardNum())
                .accountName(accountName)
                .accountBalance(accountBalance)
                .build();

        if(accountRepository.findByAccountNum(account.getAccountNum()).isPresent()){
            throw new AccountNumDuplicateException(ErrorCode.ACCOUNT_NUM_DUPLICATION);
        }

        accountRepository.save(account);
        return account;
    }

    public static String generateAccountNum() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("110");

        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);  // 0부터 9까지의 랜덤 숫자 생성

            if(i == 0 | i == 3){
                sb.append('-');
            }
            sb.append(digit);
        }

        return sb.toString();
    }

    public static String generateCardNum() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10);  // 0부터 9까지의 랜덤 숫자 생성

            if(i != 0 && (i % 4) == 0){
                sb.append('-');
            }
            sb.append(digit);
        }

        return sb.toString();
    }
}
