package com.hana.api.user.repository;

import com.hana.api.user.dto.response.StatisticsResponseDto;
import com.hana.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);

    @Query(value = "SELECT DISTINCT user_eas from users WHERE user_eas IS NOT NULL", nativeQuery = true)
    List<String>  findAllDistinctUserEas();
    Optional<User>findByUserPhone(String userPhone);
    boolean existsByUserId(String userId);
}
