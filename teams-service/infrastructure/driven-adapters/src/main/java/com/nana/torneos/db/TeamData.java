package com.nana.torneos.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("equipos")
public class TeamData implements Persistable<Integer> {

    @Id
    @Column("id_equipo")
    private Integer id;

    @Column("nombre")
    private String name;

    @Column("siglas")
    private String acronym;

    @Column("id_creador")
    private Integer creatorId;

    @Transient
    @Builder.Default
    private boolean isNew = false;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public TeamData markNew() {
        this.isNew = true;
        return this;
    }

    public TeamData markNotNew() {
        this.isNew = false;
        return this;
    }
}