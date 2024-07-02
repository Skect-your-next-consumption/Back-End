package com.hana.api.diary.repository;

import com.hana.api.diary.entity.PictureDiary;
import com.hana.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureDiaryRepository extends JpaRepository<PictureDiary, String> {
    Integer countAllByUser(User user);
    List<PictureDiary> findAllByUser(User user);
}
