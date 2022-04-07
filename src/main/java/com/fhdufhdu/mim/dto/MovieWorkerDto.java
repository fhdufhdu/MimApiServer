package com.fhdufhdu.mim.dto;

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
public class MovieWorkerDto {
    private Long id;
    private Long movieId;
    private Long workerId;
}
