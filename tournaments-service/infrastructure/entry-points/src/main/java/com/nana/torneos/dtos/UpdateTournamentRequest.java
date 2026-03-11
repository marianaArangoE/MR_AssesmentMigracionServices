package com.nana.torneos.dtos;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTournamentRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean free;
    private Integer teamLimit;
    private Integer viewLimit;
    private Integer platformId;
    private Integer categoryId;
    private String description;
}