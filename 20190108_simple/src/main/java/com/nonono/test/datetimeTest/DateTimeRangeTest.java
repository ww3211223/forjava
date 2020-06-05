package com.nonono.test.datetimeTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期范围测试
 */
public class DateTimeRangeTest {

    public void test() {
        String beginTimeStr = "1800";
        String endTimeStr = "1000+";

        DateTimeRange dateTimeRange = resolveRange(beginTimeStr, endTimeStr, LocalDate.now());
        int intervalSecond = 25 * 60 * 60;
        LocalDateTime now = LocalDateTime.now();


        System.out.println("---核心工作时间: " + beginTimeStr + " - " + endTimeStr);
        System.out.println("延期时间（分钟）: " + intervalSecond / 60);
        System.out.println("延期时间（小时）: " + Double.valueOf(intervalSecond) / 3600);
        System.out.println("------当前时间: " + now);
        System.out.println("------计算结果: " + calcDateTime(dateTimeRange, intervalSecond, now));
    }

    private DateTimeRange resolveRange(String beginTimeStr, String endTimeStr, LocalDate date) {
        if (beginTimeStr.endsWith("+")) {
            beginTimeStr = beginTimeStr + "1";
        }
        if (endTimeStr.endsWith("+")) {
            endTimeStr = endTimeStr + "1";
        }

        String str = "^(?<time>(([01]\\d)|(2[0-3]))[0-5]\\d)(\\+(?<cnt>[1-9]\\d*?))?$";
        Pattern regex = Pattern.compile(str);

        Matcher matchStart = regex.matcher(beginTimeStr);
        Matcher matchEnd = regex.matcher(endTimeStr);

        // 不符合格式
        if (!matchStart.find() || !matchEnd.find()) {
            return null;
        }

        int nStartDays = Objects.nonNull(matchStart.group("cnt")) ? Integer.parseInt(matchStart.group("cnt")) : 0;
        int nEndDays = Objects.nonNull(matchEnd.group("cnt")) ? Integer.parseInt(matchEnd.group("cnt")) : 0;
        if (nStartDays > 0) {
            nEndDays -= nStartDays;
            nStartDays = 0;

            if (nEndDays < 0) {
                return null;
            }
        }

        String strTimeStart = matchStart.group("time");
        String strTimeEnd = matchEnd.group("time");

        LocalDateTime tempStartTime = this.HHmmToDateTime(strTimeStart, date).plusDays(nStartDays);
        LocalDateTime tempEndTime = this.HHmmToDateTime(strTimeEnd, date).plusDays(nEndDays);

        return DateTimeRange.builder().Start(tempStartTime).End(tempEndTime).build();
    }

    private LocalDateTime calcDateTime(DateTimeRange range, int intervalSecond, LocalDateTime time) {
        if (range.getEnd().isBefore(time)) {
            return calcDateTime(DateTimeRange.builder().Start(range.getStart().plusDays(1)).End(range.getEnd().plusDays(1)).build(), intervalSecond, time);
        }

        LocalDateTime start = range.getStart().isAfter(time) ? range.getStart() : time;
        Duration duration = Duration.between(start, range.getEnd());
        long seconds = duration.getSeconds();
        if (intervalSecond < seconds) {
            return start.plusSeconds(intervalSecond);
        }

        intervalSecond -= seconds;
        time = range.getEnd();
        DateTimeRange newRange = DateTimeRange.builder()
                .Start(range.getStart().plusDays(1))
                .End(range.getEnd().plusDays(1))
                .build();

        return calcDateTime(newRange, intervalSecond, time);
    }

    /**
     * 将HHmm转换为时间
     * 内部使用，不做参数校验
     *
     * @param HHmm
     * @param dt
     * @return
     */
    private LocalDateTime HHmmToDateTime(String HHmm, LocalDate dt) {
        return LocalDateTime.of(dt, LocalTime.of(Integer.parseInt(HHmm.substring(0, 2)),
                Integer.parseInt(HHmm.substring(2, 4)), 0));
    }

}
