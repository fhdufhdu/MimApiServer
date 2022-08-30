package com.fhdufhdu.mim.dto.searchhistory;

import java.util.Date;

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
public class SearchHistoryListElem {
    private String searchText;
    private Date searchTime;
}
