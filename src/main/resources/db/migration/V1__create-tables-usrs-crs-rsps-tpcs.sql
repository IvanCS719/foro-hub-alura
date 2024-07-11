CREATE TABLE usuarios(
    id bigint not null auto_increment,
    nombre varchar(4000) not null,
    correoElectronico varchar(100) not null,
    contrasena varchar(300) not null,
    primary key(id)
);

CREATE TABLE topicos(
    id bigint not null auto_increment,
    titulo varchar(255) not null,
    mensaje varchar(4000) not null,
    fechaCreacion timestamp,
    estatus boolean,
    usuario_id bigint not null,
    curso_id bigint not null,
    primary key(id)
);

CREATE TABLE respuestas(
    id bigint not null auto_increment,
    mensaje varchar(4000) not null,
    fechaCreacion timestamp,
    solucion boolean,
    topico_id bigint not null,
    usuario_id bigint not null,
    primary key(id)
);

CREATE TABLE cursos(
    id bigint not null auto_increment,
    nombre varchar(150) not null,
    categoria varchar(100) not null,
    primary key(id)
);

-- Agregar clave foránea a 'topicos' para relacionarla con 'usuarios'
ALTER TABLE topicos
ADD CONSTRAINT fk_topicos_usuarios
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);

-- Agregar clave foránea a 'topicos' para relacionarla con 'cursos'
ALTER TABLE topicos
ADD CONSTRAINT fk_topicos_cursos
FOREIGN KEY (curso_id) REFERENCES cursos(id);

-- Agregar clave foránea a 'respuestas' para relacionarla con 'topicos'
ALTER TABLE respuestas
ADD CONSTRAINT fk_respuestas_topicos
FOREIGN KEY (topico_id) REFERENCES topicos(id);

-- Agregar clave foránea a 'respuestas' para relacionarla con 'usuarios'
ALTER TABLE respuestas
ADD CONSTRAINT fk_respuestas_usuarios
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);