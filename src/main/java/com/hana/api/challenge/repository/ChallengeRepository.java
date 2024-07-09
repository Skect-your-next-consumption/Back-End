package com.hana.api.challenge.repository;

import com.hana.api.challenge.dto.query.HotChallengeInterface;
import com.hana.api.challenge.dto.query.MinimumBalanceUser;
import com.hana.api.challenge.dto.query.StatisticsByGender;
import com.hana.api.challenge.dto.query.StatisticsByRatio;
import com.hana.api.challenge.entity.Challenge;
import com.hana.api.user.entity.User;
import com.hana.common.type.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, String> {
    Optional<Challenge> findByChallengeCode(String challengeCode);

    List<Challenge> findAllChallengeByChallengeUsers_UserAndStateOrderByCreatedDateDesc(User user, State state);

    List<Challenge> findTop3ByChallengeUsers_UserOrderByCreatedDateDesc(User user);

    @Query(value = "SELECT challenge.challenge_category,COUNT(DISTINCT user_code) as challenge_count FROM challenge_users JOIN challenge ON challenge.challenge_code = challenge_users.challenge_code GROUP BY challenge.challenge_category ORDER BY 2 DESC LIMIT 3", nativeQuery = true)
    List<HotChallengeInterface> findTop3ByChallengeCategory();

    @Query(value = "SELECT challenge.challenge_category, users.user_gender,COUNT(challenge_users.user_code)as count FROM challenge JOIN challenge_users ON challenge.challenge_code = challenge_users.challenge_code JOIN users ON challenge_users.user_code = users.user_code GROUP BY users.user_gender,challenge.challenge_category", nativeQuery = true)
    List<StatisticsByGender> getChallengeStatisticsByGender();

    @Query(value = "SELECT challenge_category, COUNT(case when challenge_users.challenge_user_result=TRUE then 1 END)/COUNT(*)*100 AS ratio FROM challenge JOIN challenge_users ON challenge.challenge_code = challenge_users.challenge_code WHERE challenge.state='Finish' GROUP BY challenge_category", nativeQuery = true)
    List<StatisticsByRatio> getChallengeSuccessRatioByCategory();

    @Query(value = "SELECT account_balance, user_name FROM users JOIN accounts ON users.account_num = accounts.account_num WHERE users.user_code IN (:userCodes) order by account_balance limit 1 ", nativeQuery = true)
    MinimumBalanceUser getMaxAmount(@Param("userCodes") List<String> userCodes);

    @Query(value = "SELECT c FROM Challenge c WHERE c.state = 'ACTIVE' AND DATE_ADD(c.createdDate, c.challengePeriod, 'DAY') = CURRENT_DATE", nativeQuery = true)
    List<Challenge> findActiveChallengesEndingToday();

}
