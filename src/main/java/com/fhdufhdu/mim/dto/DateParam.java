package com.fhdufhdu.mim.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateParam {
    private int year;
    private int month;
    private int day;
    private int hours;

    public Timestamp toTimestamp() {
        return Timestamp.valueOf(LocalDateTime.of(year, month, day, hours, 0));
    }
}
