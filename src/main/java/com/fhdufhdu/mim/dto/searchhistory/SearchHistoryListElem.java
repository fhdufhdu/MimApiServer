package com.fhdufhdu.mim.dto.searchhistory;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchHistoryListElem {
    private String search_text;
    private Date Date;
}
