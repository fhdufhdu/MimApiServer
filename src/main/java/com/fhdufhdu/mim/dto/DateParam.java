package com.fhdufhdu.mim.dto;

import lombok.*;

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
}
