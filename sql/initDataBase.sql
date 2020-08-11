-- Create table
create table RP_ACCOUNT_BALANCE
(
  balance_id         NUMBER(20) not null,
  key                VARCHAR2(200 CHAR) not null,
  books_id           NUMBER(20) not null,
  company_id         NUMBER(20) not null,
  period_name        VARCHAR2(200 CHAR) not null,
  currency           VARCHAR2(200 CHAR) not null,
  account_id         NUMBER(20) not null,
  opening_dr_amount  NUMBER,
  opening_cr_amount  NUMBER,
  current_dr_amount  NUMBER,
  current_cr_amount  NUMBER,
  closing_dr_amount  NUMBER,
  closing_cr_amount  NUMBER,
  created_by         VARCHAR2(255 CHAR),
  last_modified_by   VARCHAR2(255 CHAR),
  created_date       DATE,
  last_modified_date DATE
);
-- Add comments to the table
comment on table RP_ACCOUNT_BALANCE
is '科目余额表-demo';
-- Add comments to the columns
comment on column RP_ACCOUNT_BALANCE.balance_id
is '余额ID';
comment on column RP_ACCOUNT_BALANCE.key
is '业务主键';
comment on column RP_ACCOUNT_BALANCE.books_id
is '账套ID';
comment on column RP_ACCOUNT_BALANCE.company_id
is '公司ID';
comment on column RP_ACCOUNT_BALANCE.period_name
is '期间';
comment on column RP_ACCOUNT_BALANCE.currency
is '币种';
comment on column RP_ACCOUNT_BALANCE.account_id
is '科目ID';
comment on column RP_ACCOUNT_BALANCE.opening_dr_amount
is '期初借方金额';
comment on column RP_ACCOUNT_BALANCE.opening_cr_amount
is '期初贷方金额';
comment on column RP_ACCOUNT_BALANCE.current_dr_amount
is '本期借方金额';
comment on column RP_ACCOUNT_BALANCE.current_cr_amount
is '本期贷方金额';
comment on column RP_ACCOUNT_BALANCE.closing_dr_amount
is '期末借方金额';
comment on column RP_ACCOUNT_BALANCE.closing_cr_amount
is '期末贷方金额';
-- Create/Recreate primary, unique and foreign key constraints
alter table RP_ACCOUNT_BALANCE
  add constraint ACCOUNT_BALANCE_PK primary key (BALANCE_ID);



-- Create table
create table FST_COLUMN_CONFIG
(
  id              NUMBER(20) not null,
  table_name      VARCHAR2(255),
  column_name     VARCHAR2(255),
  column_type     VARCHAR2(255),
  dict_name       VARCHAR2(255),
  extra           VARCHAR2(255),
  form_show       NUMBER(1),
  form_type       VARCHAR2(255),
  key_type        VARCHAR2(255),
  list_show       NUMBER(1),
  not_null        NUMBER(1),
  query_type      VARCHAR2(255),
  remark          VARCHAR2(255),
  date_annotation VARCHAR2(255),
  lov_code        VARCHAR2(255)
);
-- Create/Recreate primary, unique and foreign key constraints
alter table FST_COLUMN_CONFIG
  add primary key (ID);


create sequence FST_COLUMN_CONFIG_S;



-- Create table
create table FST_GEN_CONFIG
(
  id             NUMBER(19) not null,
  api_path       VARCHAR2(255 CHAR),
  author         VARCHAR2(255 CHAR),
  cover          NUMBER(1),
  module_name    VARCHAR2(255 CHAR),
  pack           VARCHAR2(255 CHAR),
  path           VARCHAR2(255 CHAR),
  prefix         VARCHAR2(255 CHAR),
  table_name     VARCHAR2(255 CHAR),
  api_alias      VARCHAR2(255 CHAR),
  front_template VARCHAR2(255 CHAR),
  section        VARCHAR2(255),
  api_type       VARCHAR2(255) default '',
  api_module     VARCHAR2(255)
);
-- Create/Recreate primary, unique and foreign key constraints
alter table FST_GEN_CONFIG
  add primary key (ID);

create sequence FST_GEN_CONFIG_s;
