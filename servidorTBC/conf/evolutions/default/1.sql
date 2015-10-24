# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table conductor (
  numero_identificacion     bigint not null,
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
  auth_token                varchar(255),
  capacidad                 integer,
  ubicacion                 varchar(255),
  longitud                  double,
  latitud                   double,
  llena                     boolean,
  ocupacion                 double,
  constraint pk_estacion primary key (id))
;

create table mobibus (
  id                        bigint auto_increment not null,
  auth_token                varchar(255),
  estado                    varchar(255),
  capacidad                 integer,
  placa                     varchar(255),
  ruta_id                   bigint,
  constraint pk_mobibus primary key (id))
;

create table posicion (
  id                        bigint auto_increment not null,
  latitud                   double,
  longitud                  double,
  fecha                     timestamp,
  tranvia_id                bigint,
  vcub_id                   bigint,
  mobibus_id                bigint,
  constraint pk_posicion primary key (id))
;

create table reserva (
  id                        bigint auto_increment not null,
  estado                    varchar(255),
  fecha                     timestamp,
  costo                     double,
  turno                     integer,
  ruta_id                   bigint,
  usuario_numero_identificacion bigint,
  constraint uq_reserva_ruta_id unique (ruta_id),
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
  mobibus_id                bigint,
  tranvia_id                bigint,
  conductor_numero_identificacion bigint,
  reserva_id                bigint,
  constraint uq_ruta_mobibus_id unique (mobibus_id),
  constraint uq_ruta_tranvia_id unique (tranvia_id),
  constraint uq_ruta_reserva_id unique (reserva_id),
  constraint pk_ruta primary key (id))
;

create table tranvia (
  id                        bigint auto_increment not null,
  auth_token                varchar(255),
  estado                    varchar(255),
  presion_choque            double,
  temperatura               double,
  panico                    boolean,
  ruta_id                   bigint,
  constraint pk_tranvia primary key (id))
;

create table usuario (
  numero_identificacion     bigint not null,
  edad                      integer,
  nombre                    varchar(255),
  tipo_id                   varchar(255),
  telefono                  varchar(255),
  auth_token                varchar(255),
  rol                       varchar(255),
  condicion                 varchar(255),
  usuario                   varchar(255),
  password                  varchar(255),
  constraint uq_usuario_usuario unique (usuario),
  constraint pk_usuario primary key (numero_identificacion))
;

create table vcub (
  id                        bigint auto_increment not null,
  auth_token                varchar(255),
  estado                    varchar(255),
  usuario_numero_identificacion bigint,
  estacion_id               bigint,
  constraint uq_vcub_usuario_numero_identific unique (usuario_numero_identificacion),
  constraint pk_vcub primary key (id))
;

create sequence conductor_seq;

create sequence usuario_seq;

alter table mobibus add constraint fk_mobibus_ruta_1 foreign key (ruta_id) references ruta (id) on delete restrict on update restrict;
create index ix_mobibus_ruta_1 on mobibus (ruta_id);
alter table posicion add constraint fk_posicion_tranvia_2 foreign key (tranvia_id) references tranvia (id) on delete restrict on update restrict;
create index ix_posicion_tranvia_2 on posicion (tranvia_id);
alter table posicion add constraint fk_posicion_vcub_3 foreign key (vcub_id) references vcub (id) on delete restrict on update restrict;
create index ix_posicion_vcub_3 on posicion (vcub_id);
alter table posicion add constraint fk_posicion_mobibus_4 foreign key (mobibus_id) references mobibus (id) on delete restrict on update restrict;
create index ix_posicion_mobibus_4 on posicion (mobibus_id);
alter table reserva add constraint fk_reserva_ruta_5 foreign key (ruta_id) references ruta (id) on delete restrict on update restrict;
create index ix_reserva_ruta_5 on reserva (ruta_id);
alter table reserva add constraint fk_reserva_usuario_6 foreign key (usuario_numero_identificacion) references usuario (numero_identificacion) on delete restrict on update restrict;
create index ix_reserva_usuario_6 on reserva (usuario_numero_identificacion);
alter table revision add constraint fk_revision_tranv_7 foreign key (tranvia_id) references tranvia (id) on delete restrict on update restrict;
create index ix_revision_tranv_7 on revision (tranvia_id);
alter table revision add constraint fk_revision_mobi_8 foreign key (mobibus_id) references mobibus (id) on delete restrict on update restrict;
create index ix_revision_mobi_8 on revision (mobibus_id);
alter table ruta add constraint fk_ruta_bus_9 foreign key (mobibus_id) references mobibus (id) on delete restrict on update restrict;
create index ix_ruta_bus_9 on ruta (mobibus_id);
alter table ruta add constraint fk_ruta_tranvia_10 foreign key (tranvia_id) references tranvia (id) on delete restrict on update restrict;
create index ix_ruta_tranvia_10 on ruta (tranvia_id);
alter table ruta add constraint fk_ruta_conductor_11 foreign key (conductor_numero_identificacion) references conductor (numero_identificacion) on delete restrict on update restrict;
create index ix_ruta_conductor_11 on ruta (conductor_numero_identificacion);
alter table ruta add constraint fk_ruta_reserva_12 foreign key (reserva_id) references reserva (id) on delete restrict on update restrict;
create index ix_ruta_reserva_12 on ruta (reserva_id);
alter table tranvia add constraint fk_tranvia_ruta_13 foreign key (ruta_id) references ruta (id) on delete restrict on update restrict;
create index ix_tranvia_ruta_13 on tranvia (ruta_id);
alter table vcub add constraint fk_vcub_usuario_14 foreign key (usuario_numero_identificacion) references usuario (numero_identificacion) on delete restrict on update restrict;
create index ix_vcub_usuario_14 on vcub (usuario_numero_identificacion);
alter table vcub add constraint fk_vcub_estacion_15 foreign key (estacion_id) references estacion (id) on delete restrict on update restrict;
create index ix_vcub_estacion_15 on vcub (estacion_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists conductor;

drop table if exists estacion;

drop table if exists mobibus;

drop table if exists posicion;

drop table if exists reserva;

drop table if exists revision;

drop table if exists ruta;

drop table if exists tranvia;

drop table if exists usuario;

drop table if exists vcub;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists conductor_seq;

drop sequence if exists usuario_seq;

