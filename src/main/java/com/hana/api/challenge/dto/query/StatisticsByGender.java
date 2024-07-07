package com.hana.api.challenge.dto.query;

import com.hana.common.type.Gender;
import lombok.*;

public interface StatisticsByGender {
    String getChallengeCategory();
    Gender getUserGender();
    int getCount();
}
