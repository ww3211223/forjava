package com.nonono.test.datetimeTest;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 时间范围
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeRange {

    /**
     * 获取或设置开始时间
     */
    private LocalDateTime Start;

    /**
     * 获取或设置结束时间
     */
    private LocalDateTime End;

    /**
     * 获取时间获围相交的分钟数
     *
     * @param left
     * @param right
     * @return
     */
    public static int getIntersectMinutes(DateTimeRange left, DateTimeRange right) {
        if (left == null || right == null) {
            return 0;
        }
        if ((left.getStart().isAfter(left.getEnd()) || left.getStart().equals(left.getEnd()))
                || (right.getStart().isAfter(right.getEnd()) || right.getStart().equals(right.getEnd()))) {
            return 0;
        }
        if ((left.getEnd().isBefore(right.getStart()) || left.getEnd().equals(right.getStart()))
                || (right.getEnd().isBefore(left.getStart()) || right.getEnd().equals(left.getStart()))) {
            return 0;
        }

        LocalDateTime dtStart = left.getStart().isAfter(right.getStart()) ? left.getStart() : right.getStart();
        LocalDateTime dtEnd = left.getEnd().isBefore(right.getEnd()) || left.getEnd().equals(right.getEnd()) ? left.getEnd() : right.getEnd();

        return (int) Duration.between(dtEnd, dtStart).toMinutes();
    }

}
