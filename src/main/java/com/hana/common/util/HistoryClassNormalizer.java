package com.hana.common.util;

public class HistoryClassNormalizer {
    public static String normalize(String className) {
        if (className == null) {
            return null;
        }
        switch (className) {
            case "음식점":
                return "식비";
            case "카페":
                return "식비";
        }
        return className;
    }
}
