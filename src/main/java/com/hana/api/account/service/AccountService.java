package com.hana.api.account.service;

import com.hana.api.account.dto.request.AccountLogRequest;
import com.hana.api.account.dto.response.AccountLogResponse;
import com.hana.api.account.dto.response.AccountResponseDto;
import com.hana.api.account.dto.response.CategoryInfo;
import com.hana.api.account.entity.*;
import com.hana.api.account.repository.AccountAnalysisRepository;
import com.hana.api.account.repository.AccountHistoryRepository;
import com.hana.api.account.repository.AccountRepository;
import com.hana.api.account.repository.CardRepository;
import com.hana.api.user.entity.User;
import com.hana.common.exception.ErrorCode;
import com.hana.common.exception.account.AccountNumDuplicateException;
import com.hana.common.exception.account.PaymentFailedException;
import com.hana.common.response.Response;
import com.hana.common.util.GetPaymentCategory;
import com.hana.common.util.HistoryClassNormalizer;
import com.hana.common.util.UuidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final AccountAnalysisRepository accountAnalysisRepository;
    private final CardRepository cardRepository;
    private final Response response;


    @Autowired
    private GetPaymentCategory getPaymentCategory;

    public ResponseEntity<?> getMyAccount(User user){
        AccountResponseDto accountResponseDto = new AccountResponseDto(user.getAccount(), user.getUserNameEng());
        return response.success(accountResponseDto);
    }

    public ResponseEntity<?> createMyAccountLogs(User user, AccountLogRequest accountLogRequest){

        Account account = user.getAccount();
        try {
            AccountHistory accountHistory = AccountHistory.builder()
                    .historyCode(UuidGenerator.generateUuid())
                    .account(account)
                    .historyAmount(accountLogRequest.getHistoryAmount())
                    .historyOpposit(accountLogRequest.getHistoryOpposit())
                    .historyBeforeBalance(account.getAccountBalance())
                    .historyCategory(accountLogRequest.getHistoryCategory())
                    .historyClass(accountLogRequest.getHistoryClass())
                    .historyAfterBalance(account.updateAccountBalance(accountLogRequest.getHistoryAmount().longValue()))
                    .build();
            accountRepository.save(account);
            accountHistoryRepository.save(accountHistory);
        } catch(Exception e){
            e.printStackTrace();
            throw new PaymentFailedException(ErrorCode.PAYMENT_FAILED);
        }
        if(accountLogRequest.getHistoryAmount()>0) {
            LocalDate thisMounth = LocalDate.now().minusDays(LocalDate.now().getDayOfMonth() - 1);
            AccountAnalysisId accountAnalysisId = AccountAnalysisId.builder()
                    .accountNum(account.getAccountNum())
                    .analysisDate(thisMounth)
                    .build();
            accountAnalysisRepository.findById(accountAnalysisId).ifPresentOrElse(
                    accountAnalysis -> {
                        accountAnalysis.UpdateAnalysis(accountLogRequest.getHistoryAmount(), accountLogRequest.getHistoryClass());
                        accountAnalysisRepository.save(accountAnalysis);
                    },
                    () -> {
                        AccountAnalysis accountAnalysis = AccountAnalysis.builder()
                                .id(accountAnalysisId)
                                .account(account)
                                .build();
                        switch (accountLogRequest.getHistoryClass()) {
                            case "식비":
                                accountAnalysis.setAnalysisFood(accountLogRequest.getHistoryAmount());
                                if(accountLogRequest.getHistoryCategory()=="커피전문점"){

                                }
                                break;
                            case "교통비":
                                accountAnalysis.setTransportation(accountLogRequest.getHistoryAmount());
                                break;
                            case "카페":
                                accountAnalysis.setAnalysisCafe(accountLogRequest.getHistoryAmount());
                                break;
                            case "오락":
                                accountAnalysis.setPleasure(accountLogRequest.getHistoryAmount());
                                break;
                            case "기타":
                                accountAnalysis.setEtc(accountLogRequest.getHistoryAmount());
                                break;
                        }
                        accountAnalysis.setAnalysisTotal(accountLogRequest.getHistoryAmount());
                        log.info(accountAnalysis.toString());
                        accountAnalysisRepository.save(accountAnalysis);
                    });
        }
        return response.success("결제 내역 생성 완료");
    }

    public ResponseEntity<?> getMyAccountLogs(User user, int period){

        List<AccountHistory> accountHistories = accountHistoryRepository.findByAccountAndCreatedDateAfterOrderByCreatedDateDesc(user.getAccount(), LocalDateTime.now().minusMonths(period));
        List<AccountLogResponse> accountLogResponses = new ArrayList<>();

        accountHistories.stream()
                .map(AccountLogResponse::new)
                .forEach(accountLogResponses::add);

        return response.success(accountLogResponses);
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
