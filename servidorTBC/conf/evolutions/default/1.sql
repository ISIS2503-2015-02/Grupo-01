# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table conductor (
  numero_identificacion     varchar(255) not null,
  edad                      integer,
  nombre                    varchar(255),
  tipo_id                   varchar(255),
  telefono                  varchar(255),
  licencia_de_conduccion    varchar(255),
  fecha_vencimiento_licencia varchar(255),
  estado                    varchar(255),
  constraint pk_conductor primary key (numero_identificacion))
;

create table estacion (
  id                        bigint auto_increment not null,
  capacidad                 integer,
  ubicacion                 varchar(255),
  llena                     boolean,
  ocupacion                 double,
  constraint pk_estacion primary key (id))
;

create table posicion (
  id                        bigint auto_increment not null,
  latitud                   double,
  longitud                  double,
  fecha                     timestamp,
  vehiculo_ident            bigint,
  vehiculo_id               bigint,
  constraint pk_posicion primary key (id))
;

create table reserva (
  id                        bigint auto_increment not null,
  usuario_numero_identificacion varchar(255) not null,
  estado                    varchar(255),
  fecha                     timestamp,
  costo                     double,
  turno                     integer,
  constraint pk_reserva primary key (id))
;

create table revision (
  id                        bigint auto_increment not null,
  fecha_anterior            timestamp,
  fecha                     timestamp,
  kilometraje               double,
  tranvia_id                bigint,
  mobibus_id                bigint,
  constraint pk_revision primary key (id))
;

create table ruta (
  id                        bigint auto_increment not null,
  ubicaicon_origen          varchar(255),
  ubicacion_destino         varchar(255),
  tipo                      varchar(255),
  tiempo_trayecto           double,
  terminado                 varchar(255),
  tipo_accidente            varchar(255),
  conductor_id              varchar(255),
  constraint pk_ruta primary key (id))
;

create table usuario (
  numero_identificacion     varchar(255) not null,
  edad                      integer,
  nombre                    varchar(255),
  tipo_id                   varchar(255),
  telefono                  varchar(255),
  condicion                 varchar(255),
  constraint pk_usuario primary key (numero_identificacion))
;

create table vehiculo (
  tipo_vehiculo             varchar(31) not null,
  id                        bigint auto_increment not null,
  estado                    varchar(255),
  estacion_id               bigint,
  presion_choque            double,
  temperatura               double,
  panico                    boolean,
  capacidad                 integer,
  placa                     varchar(255),
  constraint pk_vehiculo primary key (id))
;

create sequence conductor_seq;

create sequence usuario_seq;

alter table posicion add constraint fk_posicion_vehiculo_1 foreign key (vehiculo_id) references vehiculo (id) on delete restrict on update restrict;
create index ix_posicion_vehiculo_1 on posicion (vehiculo_id);
alter table reserva add constraint fk_reserva_usuario_2 foreign key (usuario_numero_identificacion) references usuario (numero_identificacion) on delete restrict on update restrict;
create index ix_reserva_usuario_2 on reserva (usuario_numero_identificacion);
alter table revision add constraint fk_revision_tranv_3 foreign key (tranvia_id) references vehiculo (id) on delete restrict on update restrict;
create index ix_revision_tranv_3 on revision (tranvia_id);
alter table revision add constraint fk_revision_mobi_4 foreign key (mobibus_id) references vehiculo (id) on delete restrict on update restrict;
create index ix_revision_mobi_4 on revision (mobibus_id);
alter table ruta add constraint fk_ruta_conductor_5 foreign key (conductor_id) references conductor (numero_identificacion) on delete restrict on update restrict;
create index ix_ruta_conductor_5 on ruta (conductor_id);
alter table vehiculo add constraint fk_vehiculo_estacion_6 foreign key (estacion_id) references estacion (id) on delete restrict on update restrict;
create index ix_vehiculo_estacion_6 on vehiculo (estacion_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists conductor;

drop table if exists estacion;

drop table if exists posicion;

drop table if exists reserva;

drop table if exists revision;

drop table if exists ruta;

drop table if exists usuario;

drop table if exists vehiculo;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists conductor_seq;

drop sequence if exists usuario_seq;

