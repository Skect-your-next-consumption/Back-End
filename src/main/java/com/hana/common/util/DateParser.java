package com.hana.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null; // 또는 적절한 오류 처리를 수행합니다.
        }
    }
}
