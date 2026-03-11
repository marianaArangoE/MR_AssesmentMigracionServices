package com.nana.torneos.tournaments;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Tournament {

    private Integer id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer videoGameId;
    private Boolean isFree;
    private Integer teamLimit;
    private Integer viewLimit;
    private Integer platformId;
    private Integer categoryId;
    private String description;
    private Integer organizerId;
}