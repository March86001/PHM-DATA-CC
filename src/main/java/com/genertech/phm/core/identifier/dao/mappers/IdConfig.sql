-- Create table
create table ID_CONFIG
(
  config_code       VARCHAR2(20) not null,
  config_name       VARCHAR2(50),
  expression        VARCHAR2(400),
  suffix            VARCHAR2(400),
  separator         VARCHAR2(400),
  seq_current_value NUMBER(32),
  seq_begin_value   NUMBER(32),
  exp_current_value VARCHAR2(100),
  suffix_rebegin    VARCHAR2(1),
  enable_cache      VARCHAR2(1),
  remark            VARCHAR2(400)
);
-- Add comments to the columns 
comment on column ID_CONFIG.config_code is '编码';
comment on column ID_CONFIG.config_name is '名称';
comment on column ID_CONFIG.expression is '主表达式';
comment on column ID_CONFIG.suffix is '后缀（表达式）';
comment on column ID_CONFIG.separator is '分隔符（表达式）';
comment on column ID_CONFIG.seq_current_value is '序列当前值';
comment on column ID_CONFIG.seq_begin_value is '序列开始值';
comment on column ID_CONFIG.exp_current_value is '主表达式当前值';
comment on column ID_CONFIG.suffix_rebegin is '是否从头计算';
comment on column ID_CONFIG.enable_cache is '启用缓存';
comment on column ID_CONFIG.remark is '描述';
-- Create/Recreate primary, unique and foreign key constraints 
alter table ID_CONFIG add primary key (CONFIG_CODE);

insert into ID_CONFIG (REMARK, ENABLE_CACHE, CONFIG_CODE, CONFIG_NAME, EXPRESSION, SUFFIX, SEPARATOR, SEQ_CURRENT_VALUE, SEQ_BEGIN_VALUE, EXP_CURRENT_VALUE, SUFFIX_REBEGIN)
values (null, '0', '00001', '出库单主键', 'md${sysdateStr(''yyyyMMddHHmmssSSS'')}', '${leftPad(seqNextValue, 5, 0)}', null, 1, 1, '', '0');
