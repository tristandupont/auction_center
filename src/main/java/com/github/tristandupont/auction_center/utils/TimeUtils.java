package com.github.tristandupont.auction_center.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public enum TimeUtils {

    ;

    public static LocalDateTime nowUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

}
