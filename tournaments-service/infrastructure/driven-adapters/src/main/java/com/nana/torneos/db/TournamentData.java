package com.nana.torneos.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("torneos")
public class TournamentData implements Persistable<Integer> {

    @Id
    @Column("id_torneo")
    private Integer id;

    @Column("video_juegos_id")
    private Integer videoGameId;

    @Column("nombre")
    private String name;

    @Column("fecha_inicio")
    private LocalDate startDate;

    @Column("fecha_fin")
    private LocalDate endDate;

    @Column("its_free")
    private String itsFree;

    @Column("limite_equipos")
    private Integer teamLimit;

    @Column("limite_views")
    private Integer viewLimit;

    @Column("plataformas_id_plataforma")
    private Integer platformId;

    @Column("organizador")
    private Integer organizerId;

    @Column("categorias_id_categoria")
    private Integer categoryId;

    @Column("descripcion")
    private String description;

    // ✅ esto NO se guarda en DB, solo decide INSERT vs UPDATE
    @Transient
    @Builder.Default
    private boolean isNew = false;

    @Override
    public boolean isNew() {
        return isNew;
    }

    // helpers opcionales
    public TournamentData markNew() {
        this.isNew = true;
        return this;
    }

    public TournamentData markNotNew() {
        this.isNew = false;
        return this;
    }

    @Override
    public Integer getId() {
        return id;
    }
}