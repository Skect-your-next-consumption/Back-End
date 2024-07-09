package com.hana.api.challenge.service;

import com.hana.api.account.dto.request.AccountLogRequest;
import com.hana.api.account.entity.Account;
import com.hana.api.account.repository.AccountRepository;
import com.hana.api.account.service.AccountService;
import com.hana.api.challenge.entity.Challenge;
import com.hana.api.challenge.entity.ChallengeUsers;
import com.hana.api.challenge.repository.ChallengeUsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChallengeUsersService {

    private final ChallengeUsersRepository challengeUsersRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    //코드에 해당하는 정보를 불러오고 각각의 코드의 목표금액과
    // 유저별 목표금액을 비교한다음 목표금액 이하다. 1번로직수행 아니면 2번로직수행

    public void findChallengeUsers(Challenge challenge) {
        List<ChallengeUsers> challengeUsers =challengeUsersRepository.findByChallenge_ChallengeCodeOrderByChallengeUserSpentMoneyDesc(challenge.getChallengeCode());
        int penalty = 0;
        int member = challengeUsers.size();
        boolean found = false;
        for (int i=0;i<challengeUsers.size();i++) {
            ChallengeUsers user =  challengeUsers.get(i);
            int spentMoney = user.getChallengeUserSpentMoney();
            int targetAmount = challenge.getChallengeTargetAmount();
            int reward = challenge.getChallengeCost();
            if (spentMoney > targetAmount){
                double penaltyrate = (spentMoney/targetAmount) - 1 ;
                int refund = (int) Math.floor(reward * (penaltyrate > 1.0 ? 1.0 : penaltyrate));
                penalty += refund;
                // 유저의 계좌에 입금
                if (reward-refund != 0){
                    Account account =  user.getUser().getAccount();
                    account.setAccountBalance(account.getAccountBalance()+(reward-refund));
                    AccountLogRequest accountLogRequest   = new AccountLogRequest();
                    accountLogRequest.setHistoryAmount(refund-reward);
                    accountLogRequest.setHistoryOpposit("챌린지환급");
                    accountLogRequest.setHistoryCategory("입금");
                    accountLogRequest.setHistoryClass("계좌이체");
                    accountService.createMyAccountLogs(user.getUser(),accountLogRequest);
                }
                // result 실패
                user.setChallengeUserResult(false);
                challengeUsersRepository.save(user);
            }else {
                if(found==false){
                    member-=i;
                }
                found=true;
                int refund = (int) Math.floor(reward + ((double) penalty /member));
                Account account =  user.getUser().getAccount();
                account.setAccountBalance(account.getAccountBalance()+(refund));
                AccountLogRequest accountLogRequest   = new AccountLogRequest();
                accountLogRequest.setHistoryAmount(refund);
                accountLogRequest.setHistoryOpposit("챌린지환급");
                accountLogRequest.setHistoryCategory("입금");
                accountLogRequest.setHistoryClass("계좌이체");
                accountService.createMyAccountLogs(user.getUser(),accountLogRequest);
                // result 실패
                user.setChallengeUserResult(true);
                challengeUsersRepository.save(user);
            }
        }
    }


    // 1번 로직
    //챌린지 참가비용을 주계좌로 입금, 만약 실패한사람의
    // 금액이 있으면 그걸 인원수에 맞게 나눈후 그 금액을 주계좌로 입금, 챌린지참가비용 + 실패한사람의 금액/참여자수.

}
