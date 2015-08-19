# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table mobibus (
  id                        bigint auto_increment not null,
  ubicacion_x               double,
  ubicacion_y               double,
  estado                    varchar(255),
  capacidad                 integer,
  placa                     varchar(255),
  constraint pk_mobibus primary key (id))
;

create table reserva (
  estado                    varchar(255),
  fecha                     varchar(255),
  costo                     double,
  turno                     integer)
;

create table revision (
  id                        bigint auto_increment not null,
  fecha_anterior            timestamp,
  fecha                     timestamp,
  kilometraje               double,
  constraint pk_revision primary key (id))
;

create table ruta (
  ubicaicon_origen          varchar(255),
  ubicacion_destino         varchar(255),
  tipo                      varchar(255),
  tiempo_trayecto           double,
  terminado                 varchar(255),
  tipo_accidente            varchar(255))
;

create table tranvia (
  id                        bigint auto_increment not null,
  ubicacion_x               double,
  ubicacion_y               double,
  estado                    varchar(255),
  presion_choque            double,
  temperatura               double,
  panico                    boolean,
  constraint pk_tranvia primary key (id))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists mobibus;

drop table if exists reserva;

drop table if exists revision;

drop table if exists ruta;

drop table if exists tranvia;

SET REFERENTIAL_INTEGRITY TRUE;

