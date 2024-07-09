package com.hana.api.challenge.repository;
import com.hana.api.challenge.dto.query.OngoingChallengeInterface;
import com.hana.api.challenge.entity.ChallengeUsers;
import com.hana.api.challenge.entity.ChallengeUsersId;
import com.hana.api.user.entity.User;
import com.hana.common.type.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeUsersRepository extends JpaRepository<ChallengeUsers, ChallengeUsersId> {
    List<OngoingChallengeInterface> findAllChallengeUsersByUserAndChallengeState_State(User user,State state);
//    List<OngoingChallengeInterface> findAllChallengeUsersByUserAndState(User user, State state);
    Integer countByUserAndChallengeState_State(User user, State state);

    List<ChallengeUsers> findByChallenge_ChallengeCodeOrderByChallengeUserSpentMoneyDesc(String challengeCode);
}
