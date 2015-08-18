# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table revision (
  id                        bigint auto_increment not null,
  fecha_anterior            timestamp,
  fecha                     timestamp,
  kilometraje               double,
  tranvia_id                bigint,
  constraint pk_revision primary key (id))
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

alter table revision add constraint fk_revision_tranv_1 foreign key (tranvia_id) references tranvia (id) on delete restrict on update restrict;
create index ix_revision_tranv_1 on revision (tranvia_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists revision;

drop table if exists tranvia;

SET REFERENTIAL_INTEGRITY TRUE;

