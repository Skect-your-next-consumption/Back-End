package com.hana.api.challenge.repository;
import com.hana.api.challenge.entity.ChallengeUsers;
import com.hana.api.challenge.entity.ChallengeUsersId;
import com.hana.api.user.entity.User;
import com.hana.common.type.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeUsersRepository extends JpaRepository<ChallengeUsers, ChallengeUsersId> {
    Integer countByUserAndState(User user, State state);
}
