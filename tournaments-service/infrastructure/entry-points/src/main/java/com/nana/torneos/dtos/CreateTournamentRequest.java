package com.nana.torneos.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTournamentRequest {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer videoGameId;
    private Boolean free;
    private Integer teamLimit;
    private Integer viewLimit;
    private Integer platformId;
    private Integer categoryId;
    private String description;
}