package com.hana.api.account.service;

import com.hana.api.account.dto.response.AccountResponseDto;
import com.hana.api.account.entity.Account;
import com.hana.api.account.entity.Card;
import com.hana.api.account.repository.AccountRepository;
import com.hana.api.account.repository.CardRepository;
import com.hana.api.user.entity.User;
import com.hana.common.exception.ErrorCode;
import com.hana.common.exception.account.AccountNumDuplicateException;
import com.hana.common.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final Response response;

    public ResponseEntity<?> getMyAccount(User user){
        AccountResponseDto accountResponseDto = new AccountResponseDto(user.getAccount(), user.getUserNameEng());
        return response.success(accountResponseDto);
    }

    public ResponseEntity<?> getMyAccountLogs(User user){
        return response.success();
    }

    public Account createAccount(String accountName, Long accountBalance){

        Account account = Account.builder()
                .accountNum(generateAccountNum())
                .accountName(accountName)
                .accountBalance(accountBalance)
                .card(createCard())
                .build();

        if(accountRepository.findByAccountNum(account.getAccountNum()).isPresent()){
            throw new AccountNumDuplicateException(ErrorCode.ACCOUNT_NUM_DUPLICATION);
        }

        accountRepository.save(account);
        return account;
    }

    public Card createCard(){

        Card card = Card.builder()
                .cardNum(generateCardNum())
                .cardExpiredDate(LocalDate.now().plusYears(4))
                .cardCvc(generateCardCvc())
                .build();

        cardRepository.save(card);
        return card;
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

    public static String generateCardCvc() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            int digit = random.nextInt(10);  // 0부터 9까지의 랜덤 숫자 생성
            sb.append(digit);
        }

        return sb.toString();
    }
}
