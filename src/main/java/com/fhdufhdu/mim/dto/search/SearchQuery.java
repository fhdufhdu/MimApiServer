package com.fhdufhdu.mim.dto.search;

import com.fhdufhdu.mim.entity.SearchType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchQuery {
    String query;
    SearchType type;
}
