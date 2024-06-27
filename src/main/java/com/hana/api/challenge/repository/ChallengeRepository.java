package com.hana.api.challenge.repository;

import com.hana.api.challenge.dto.query.HotChallengeInterface;
import com.hana.api.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Optional<Challenge> findByChallengeCode(String challengeCode);

    @Query(value = "SELECT COUNT(*) as challengeCount, CHALLENGE_CATEGORY FROM challenge GROUP BY CHALLENGE_CATEGORY ORDER BY COUNT(*) DESC LIMIT 3", nativeQuery = true)
    List<HotChallengeInterface> findTop3ByChallengeCategory();
}
