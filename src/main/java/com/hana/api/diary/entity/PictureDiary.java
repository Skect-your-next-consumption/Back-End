package com.hana.api.diary.entity;

import com.hana.api.user.entity.User;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PictureDiary extends BaseEntity {

    @Id
    @Column(length = 40)
    private String diaryCode;

    @Column(length = 100)
    private String diaryTitle;

    @Column(length = 200)
    private String diaryImage;

    @Column(columnDefinition = "json")
    private String diaryTags;

    @Column(columnDefinition = "json")
    private String diaryPayments;

    @Column(length = 40)
    private String diaryConcept;

    @ManyToOne
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    private User user;
}
