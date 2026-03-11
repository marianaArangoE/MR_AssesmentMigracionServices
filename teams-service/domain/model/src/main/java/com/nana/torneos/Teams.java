package com.nana.torneos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teams {
    private Integer id;
    private String name;
    private String acronym;
    private Integer creatorId;
}
