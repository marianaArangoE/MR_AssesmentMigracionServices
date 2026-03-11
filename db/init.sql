-- DROP SCHEMA public;

--CREATE SCHEMA public AUTHORIZATION pg_database_owner;

COMMENT ON SCHEMA public IS 'standard public schema';
-- public.categorias definition

-- Drop table

-- DROP TABLE public.categorias;

CREATE TABLE public.categorias (
	id_categoria int4 NOT NULL,
	nombre bpchar(20) NOT NULL,
	CONSTRAINT categorias_pk PRIMARY KEY (id_categoria)
);

-- Permissions

-- ALTER TABLE public.categorias OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.categorias TO neondb_owner;


-- public.comisiones definition

-- Drop table

-- DROP TABLE public.comisiones;

CREATE TABLE public.comisiones (
	id int4 NOT NULL,
	descripcion varchar NOT NULL,
	valor float4 NOT NULL,
	CONSTRAINT comisiones_pk PRIMARY KEY (id)
);

-- Permissions

-- ALTER TABLE public.comisiones OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.comisiones TO neondb_owner;


-- public.plataformas definition

-- Drop table

-- DROP TABLE public.plataformas;

CREATE TABLE public.plataformas (
	id_plataforma int4 NOT NULL,
	nombre bpchar(30) NOT NULL,
	CONSTRAINT plataformas_pk PRIMARY KEY (id_plataforma)
);

-- Permissions

-- ALTER TABLE public.plataformas OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.plataformas TO neondb_owner;


-- public.precios definition

-- Drop table

-- DROP TABLE public.precios;

CREATE TABLE public.precios (
	id int4 NOT NULL,
	monto float8 NOT NULL,
	descripcion bpchar(200) NOT NULL,
	CONSTRAINT precios_pk PRIMARY KEY (id)
);

-- Permissions

-- ALTER TABLE public.precios OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.precios TO neondb_owner;


-- public.tipos_inscripciones definition

-- Drop table

-- DROP TABLE public.tipos_inscripciones;

CREATE TABLE public.tipos_inscripciones (
	id int4 NOT NULL,
	nombre varchar NOT NULL,
	CONSTRAINT tipos_inscripciones_pk PRIMARY KEY (id)
);

-- Permissions

-- ALTER TABLE public.tipos_inscripciones OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.tipos_inscripciones TO neondb_owner;


-- public.video_juegos definition

-- Drop table

-- DROP TABLE public.video_juegos;

CREATE TABLE public.video_juegos (
	id int4 NOT NULL,
	nombre bpchar(40) NOT NULL,
	CONSTRAINT video_juegos_pk PRIMARY KEY (id)
);

-- Permissions

-- ALTER TABLE public.video_juegos OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.video_juegos TO neondb_owner;


-- public.donaciones definition

-- Drop table

-- DROP TABLE public.donaciones;

CREATE TABLE public.donaciones (
	id int4 NOT NULL,
	cantidad float8 NOT NULL,
	fecha_donacion date NOT NULL,
	torneos_id_torneo int4 NOT NULL,
	CONSTRAINT donaciones_pk PRIMARY KEY (id)
);

-- Permissions

-- ALTER TABLE public.donaciones OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.donaciones TO neondb_owner;


-- public.equipos definition

-- Drop table

-- DROP TABLE public.equipos;

CREATE TABLE public.equipos (
	id_equipo int4 NOT NULL,
	nombre bpchar(100) NOT NULL,
	siglas bpchar(6) NOT NULL,
	id_creador int4 NULL,
	CONSTRAINT equipos_pk PRIMARY KEY (id_equipo)
);

-- Permissions

-- ALTER TABLE public.equipos OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.equipos TO neondb_owner;


-- public.inscripciones definition

-- Drop table

-- DROP TABLE public.inscripciones;

CREATE TABLE public.inscripciones (
	id_inscripciones varchar NOT NULL,
	tipo_inscripcion int4 NOT NULL,
	torneos_id_torneo int4 NOT NULL,
	monto float8 NOT NULL,
	codigo text NOT NULL,
	equipos_id_equipo int4 NULL,
	usuarios_id_usuario int4 NULL,
	precios_id int4 NOT NULL,
	CONSTRAINT inscripciones_pk PRIMARY KEY (id_inscripciones)
);

-- Permissions

-- ALTER TABLE public.inscripciones OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.inscripciones TO neondb_owner;


-- public.torneos definition

-- Drop table

-- DROP TABLE public.torneos;

CREATE TABLE public.torneos (
	id_torneo int4 NOT NULL,
	video_juegos_id int4 NULL,
	nombre bpchar(100) NOT NULL,
	fecha_inicio date NOT NULL,
	fecha_fin date NOT NULL,
	its_free bpchar(1) NOT NULL,
	limite_equipos int4 NOT NULL,
	limite_views int4 NOT NULL,
	plataformas_id_plataforma int4 NOT NULL,
	organizador int4 NOT NULL,
	categorias_id_categoria int4 NOT NULL,
	descripcion bpchar(200) NOT NULL,
	CONSTRAINT torneos_pk PRIMARY KEY (id_torneo)
);

-- Permissions

-- ALTER TABLE public.torneos OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.torneos TO neondb_owner;


-- public.usuarios definition

-- Drop table

-- DROP TABLE public.usuarios;

CREATE TABLE public.usuarios (
	id_usuario int4 NOT NULL,
	nombre bpchar(40) NOT NULL,
	correo bpchar(60) NOT NULL,
	contrasena bpchar(100) NOT NULL,
	apodo bpchar(40) NULL,
	equipos_id_equipo int4 NULL,
	CONSTRAINT usuarios_pk PRIMARY KEY (id_usuario)
);

-- Permissions

-- ALTER TABLE public.usuarios OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.usuarios TO neondb_owner;


-- public.donaciones foreign keys

ALTER TABLE public.donaciones ADD CONSTRAINT donaciones_torneos_fk FOREIGN KEY (torneos_id_torneo) REFERENCES public.torneos(id_torneo);


-- public.equipos foreign keys

ALTER TABLE public.equipos ADD CONSTRAINT fk_equipo_creador FOREIGN KEY (id_creador) REFERENCES public.usuarios(id_usuario);


-- public.inscripciones foreign keys

ALTER TABLE public.inscripciones ADD CONSTRAINT inscripciones_equipos_fk FOREIGN KEY (equipos_id_equipo) REFERENCES public.equipos(id_equipo);
ALTER TABLE public.inscripciones ADD CONSTRAINT inscripciones_precios_fk FOREIGN KEY (precios_id) REFERENCES public.precios(id);
ALTER TABLE public.inscripciones ADD CONSTRAINT inscripciones_tipos_fk FOREIGN KEY (tipo_inscripcion) REFERENCES public.tipos_inscripciones(id);
ALTER TABLE public.inscripciones ADD CONSTRAINT inscripciones_torneos_fk FOREIGN KEY (torneos_id_torneo) REFERENCES public.torneos(id_torneo);
ALTER TABLE public.inscripciones ADD CONSTRAINT inscripciones_usuarios_fk FOREIGN KEY (usuarios_id_usuario) REFERENCES public.usuarios(id_usuario);


-- public.torneos foreign keys

ALTER TABLE public.torneos ADD CONSTRAINT torneos_categorias_fk FOREIGN KEY (categorias_id_categoria) REFERENCES public.categorias(id_categoria);
ALTER TABLE public.torneos ADD CONSTRAINT torneos_plataformas_fk FOREIGN KEY (plataformas_id_plataforma) REFERENCES public.plataformas(id_plataforma);
ALTER TABLE public.torneos ADD CONSTRAINT torneos_usuarios_fk FOREIGN KEY (organizador) REFERENCES public.usuarios(id_usuario);
ALTER TABLE public.torneos ADD CONSTRAINT torneos_video_juegos_fk FOREIGN KEY (video_juegos_id) REFERENCES public.video_juegos(id);


-- public.usuarios foreign keys

ALTER TABLE public.usuarios ADD CONSTRAINT usuarios_equipos_fk FOREIGN KEY (equipos_id_equipo) REFERENCES public.equipos(id_equipo);


-- public.vista_torneos_csgo source

CREATE OR REPLACE VIEW public.vista_torneos_csgo
AS SELECT t.id_torneo,
    t.nombre AS torneo_nombre,
    t.fecha_inicio,
    t.fecha_fin,
    vj.nombre AS juego
   FROM torneos t
     JOIN video_juegos vj ON vj.id = t.video_juegos_id
  WHERE vj.nombre ~~ '%CSGO%'::text;

-- Permissions

-- ALTER TABLE public.vista_torneos_csgo OWNER TO neondb_owner;
--GRANT ALL ON TABLE public.vista_torneos_csgo TO neondb_owner;




-- Permissions

GRANT ALL ON SCHEMA public TO pg_database_owner;
GRANT USAGE ON SCHEMA public TO public;
-- ALTER DEFAULT PRIVILEGES FOR ROLE cloud_admin IN SCHEMA public GRANT SELECT, TRUNCATE, DELETE, TRIGGER, UNKNOWN, INSERT, REFERENCES, UPDATE ON TABLES TO neon_superuser WITH GRANT OPTION;
-- ALTER DEFAULT PRIVILEGES FOR ROLE cloud_admin IN SCHEMA public GRANT SELECT, USAGE, UPDATE ON SEQUENCES TO neon_superuser WITH GRANT OPTION;