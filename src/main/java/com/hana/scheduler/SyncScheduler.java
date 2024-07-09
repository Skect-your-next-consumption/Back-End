package com.hana.scheduler;

import com.hana.api.challenge.entity.Challenge;
import com.hana.api.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SyncScheduler {

    private final ChallengeService challengeService;
    @Scheduled(cron = "0 0 12 * * ?") // 매일 낮 12시에 실행
    public void reward() { challengeService.rewardChallenge(); }
}
