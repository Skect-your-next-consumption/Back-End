package com.hana.api.challenge.repository;

import com.hana.api.challenge.entity.Challenge;
import com.hana.api.challenge.entity.ChallengeUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeUsersRepository extends JpaRepository<ChallengeUsers, Long> {
}