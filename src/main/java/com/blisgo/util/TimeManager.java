package com.blisgo.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

enum TIMEDEF {
    SEC(60), MIN(60), HOUR(24), DAY(30), MONTH(12);

    final long num;

    TIMEDEF(long num) {
        this.num = num;
    }

    public long num() {
        return num;
    }
}

/**
 * 시간차이를 계산해주는 기능
 *
 * @author okjae
 * <a href="https://jinsiri.tistory.com/554">참고 자료: Siri's Programming</>
 */
public class TimeManager {

    public static String calcTimeDiff(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();

        long diffTime = localDateTime.until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -

        if (diffTime < TIMEDEF.SEC.num()) {
            return diffTime + "초 전";
        } else if ((diffTime /= TIMEDEF.SEC.num()) < TIMEDEF.MIN.num()) {
            return diffTime + "분 전";
        } else if ((diffTime /= TIMEDEF.MIN.num()) < TIMEDEF.HOUR.num()) {
            return diffTime + "시간 전";
        } else if ((diffTime /= TIMEDEF.HOUR.num()) < TIMEDEF.DAY.num()) {
            return diffTime + "일 전";
        } else if ((diffTime /= TIMEDEF.DAY.num()) < TIMEDEF.MONTH.num()) {
            return diffTime + "개월 전";
        } else {
            diffTime /= TIMEDEF.MONTH.num();
            return diffTime + "년 전";
        }
    }
}
