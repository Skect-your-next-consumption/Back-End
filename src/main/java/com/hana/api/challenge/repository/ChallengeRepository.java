package com.hana.api.challenge.repository;

import com.hana.api.challenge.dto.query.HotChallengeInterface;
import com.hana.api.challenge.dto.query.StatisticsByGender;
import com.hana.api.challenge.entity.Challenge;
import com.hana.api.user.entity.User;
import com.hana.common.type.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, String> {
    Optional<Challenge> findByChallengeCode(String challengeCode);

    List<Challenge> findAllChallengeByChallengeUsers_UserAndState(User user, State state);

    List<Challenge> findTop3ByChallengeUsers_UserOrderByCreatedDateDesc(User user);

    @Query(value = "SELECT challenge.challenge_category,COUNT(DISTINCT user_code) as challenge_count FROM challenge_users JOIN challenge ON challenge.challenge_code = challenge_users.challenge_code GROUP BY challenge.challenge_category ORDER BY 2 DESC LIMIT 3", nativeQuery = true)
    List<HotChallengeInterface> findTop3ByChallengeCategory();

    @Query(value = "SELECT challenge.challenge_category, users.user_gender,COUNT(challenge_users.user_code)as count FROM challenge JOIN challenge_users ON challenge.challenge_code = challenge_users.challenge_code JOIN users ON challenge_users.user_code = users.user_code GROUP BY users.user_gender,challenge.challenge_category", nativeQuery = true)
    List<StatisticsByGender> getChallengeStatisticsByGender();

}
