package com.hana.api.account.repository;

import com.hana.api.account.entity.Account;
import com.hana.api.account.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, String> {
}
