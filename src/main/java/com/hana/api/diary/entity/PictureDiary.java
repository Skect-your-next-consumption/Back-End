package com.hana.api.diary.entity;

import com.amazonaws.services.s3.model.JSONType;
import com.hana.api.account.entity.AccountHistory;
import com.hana.api.user.entity.User;
import com.hana.common.entity.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Builder
@DynamicInsert
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

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String,Object> diaryTags = new HashMap<>();


    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String,Object> diaryPayments = new HashMap<>();

    @Column(length = 40)
    private String diaryConcept;

    @ManyToOne
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    private User user;
}
