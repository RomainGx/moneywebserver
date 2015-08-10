# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  bank_name                 varchar(255),
  number                    varchar(255),
  starting_balance          double,
  final_balance             double,
  constraint pk_account primary key (id))
;

create table bank_operation (
  id                        bigint auto_increment not null,
  account_id                bigint,
  bank_note_num             varchar(255),
  operation_date            datetime,
  balance_state             integer,
  third_party_id            bigint,
  charge                    double,
  credit                    double,
  category_id               bigint,
  sub_category_id           bigint,
  notes                     varchar(255),
  constraint ck_bank_operation_balance_state check (balance_state in (0,1,2)),
  constraint pk_bank_operation primary key (id))
;

create table category (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  type                      integer,
  constraint ck_category_type check (type in (0,1)),
  constraint pk_category primary key (id))
;

create table sub_category (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  category_id               bigint,
  constraint pk_sub_category primary key (id))
;

create table third_party (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_third_party primary key (id))
;

alter table bank_operation add constraint fk_bank_operation_account_1 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_bank_operation_account_1 on bank_operation (account_id);
alter table bank_operation add constraint fk_bank_operation_thirdParty_2 foreign key (third_party_id) references third_party (id) on delete restrict on update restrict;
create index ix_bank_operation_thirdParty_2 on bank_operation (third_party_id);
alter table bank_operation add constraint fk_bank_operation_category_3 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_bank_operation_category_3 on bank_operation (category_id);
alter table bank_operation add constraint fk_bank_operation_subCategory_4 foreign key (sub_category_id) references sub_category (id) on delete restrict on update restrict;
create index ix_bank_operation_subCategory_4 on bank_operation (sub_category_id);
alter table sub_category add constraint fk_sub_category_category_5 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_sub_category_category_5 on sub_category (category_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table bank_operation;

drop table category;

drop table sub_category;

drop table third_party;

SET FOREIGN_KEY_CHECKS=1;

