package com.nana.torneos.adapters.db;

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
@Table("usuarios")
public class UserData implements Persistable<Integer> {

    @Id
    @Column("id_usuario")
    private Integer id;

    @Column("nombre")
    private String name;

    @Column("correo")
    private String email;

    @Column("contrasena")
    private String password;

    @Column("apodo")
    private String nickname;

    @Column("equipos_id_equipo")
    private Integer teamId;

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

    public UserData markNew() {
        this.isNew = true;
        return this;
    }

    public UserData markNotNew() {
        this.isNew = false;
        return this;
    }
}