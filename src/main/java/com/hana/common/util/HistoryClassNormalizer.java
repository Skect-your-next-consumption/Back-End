package com.hana.common.util;

import com.hana.api.account.dto.response.CategoryInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HistoryClassNormalizer {
    public static CategoryInfo normalize(String info) {
        CategoryInfo categoryInfo = new CategoryInfo();
//        String[] split = info.split(">");
        log.info("CategoryInfo:"+info);
        return categoryInfo;
    }
}
