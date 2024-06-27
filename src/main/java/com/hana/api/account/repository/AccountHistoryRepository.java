package com.hana.api.account.repository;

import com.hana.api.account.entity.Account;
import com.hana.api.account.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, String> {
    List<AccountHistory> findByAccountAndCreatedDateAfterOrderByCreatedDateDesc(Account account, LocalDateTime localDateTime);
}
