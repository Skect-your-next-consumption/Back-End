package com.hana.api.account.repository;

import com.hana.api.account.entity.AccountAnalysis;
import com.hana.api.account.entity.AccountAnalysisId;
import com.hana.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AccountAnalysisRepository extends JpaRepository<AccountAnalysis, String> {
    Optional<AccountAnalysis> findById(AccountAnalysisId id);
}
