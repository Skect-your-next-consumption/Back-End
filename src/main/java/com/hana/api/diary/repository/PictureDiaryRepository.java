package com.hana.api.diary.repository;

import com.hana.api.diary.dto.query.HotDiaryInterface;
import com.hana.api.diary.entity.PictureDiary;
import com.hana.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureDiaryRepository extends JpaRepository<PictureDiary, String> {
    Integer countAllByUser(User user);
    List<PictureDiary> findAllByUserOrderByCreatedDateDesc(User user);
    Optional<PictureDiary> findByDiaryCode(String pictureDiaryCode);
    @Query(value = "SELECT diary_concept, count(*) as diary_concept_count FROM picture_diary GROUP BY diary_concept ORDER BY COUNT(*) DESC LIMIT 3", nativeQuery = true)
    List<HotDiaryInterface> findHotDiaryCategory();

    List<PictureDiary> findTop6ByUserOrderByCreatedDateDesc(User user);


}
