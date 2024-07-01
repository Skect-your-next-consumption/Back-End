package com.hana.api.diary.repository;

import com.hana.api.diary.entity.PictureDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureDiaryRepository extends JpaRepository<PictureDiary, Long> {
}
