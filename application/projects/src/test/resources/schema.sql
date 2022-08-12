DROP TABLE IF EXISTS idgc_coe_mapping_config;
DROP TABLE IF EXISTS idgc_coe_audit_history_cnfg;
DROP TABLE IF EXISTS idgc_coe_mutation_stage_cnfg;
DROP TABLE IF EXISTS idgc_coe_country_code_cnfg;
DROP TABLE IF EXISTS idgc_coe_city_code_cnfg;
DROP TABLE IF EXISTS idgc_coe_state_code_cnfg;


create table if not exists idgc_coe_mapping_config (
	id serial not null,
	action varchar(20) not null,
	role varchar(50) not null,
	status varchar(20) not null,
	authorized varchar(1) not null,
	submitted varchar(1) not null,
	update_record_version varchar(1) not null,
	inactive_previous_record varchar(1) not null,
	last_configuration_action varchar(20) not null,
	insert_record_into_audit varchar(1) not null,
	insert_record_into_basetable varchar(1) not null,
	mapping_status varchar(20) not null default 'draft'::character varying,
	copy_record_from_base_table varchar(1) NOT NULL DEFAULT 'N'::character varying,
	constraint idgc_coe_mapping_config_uk unique (action, role, status)
);



create table if not exists idgc_coe_audit_history_cnfg (
	id serial not null,
   	task_code varchar(16) not null,
	task_identifier varchar(50) not null,
	payload jsonb not null,
	record_version integer not null,
	status character varying(10) collate pg_catalog."default" not null,
	authorized char(1) ,
	last_configuration_action varchar(20) not null,
	reference_no varchar(18) ,
	trace_info varchar(50) ,
	action varchar(20) ,
	created_by varchar(20) ,
	creation_time timestamp ,
	last_updated_by varchar(20) ,
	last_updated_time timestamp
);

create table if not exists idgc_coe_mutation_stage_cnfg (
   	task_code varchar(50) not null,
	task_identifier varchar(40) not null,
	payload jsonb not null,
	reference_no varchar(18) ,
	trace_info varchar(50) ,
	record_version integer not null,
	status varchar(10) not null,
	authorized bpchar(1) null,
	last_configuration_action varchar(20) not null,
	action varchar(20) ,
	created_by varchar(20) ,
	creation_time timestamp ,
	last_updated_by varchar(20) ,
	last_updated_time timestamp ,
	constraint idg_coe_mutation_stage_cnfg_task_code_task_identifier_status__key unique (task_code, task_identifier, status, authorized, record_version, action)
);

create table if not exists idgc_coe_country_code_cnfg (
   	country_code varchar(2) unique not null,
   	country_name varchar(100) not null,
	country_code_machine integer not null,
	country_code_alternate varchar(3),
	region_code varchar(10),
	currency_limit varchar(3),
	overall_limit varchar(3),
	is_iban char(1),
	is_eu_member char(1),
	is_bic_code char(1),
	is_mt_generate char(1),
	is_clearing_network char(1),
	record_version integer not null,
	status varchar(10) not null,
	authorized varchar(1) not null,
	last_configuration_action varchar(20) not null,
	created_by varchar(20),
	creation_time timestamp,
	last_updated_by varchar(20),
	last_updated_time timestamp,
	constraint "pk_idgc_coe_country_code_cnfg" primary key ("country_code")
);


create table if not exists idgc_coe_city_code_cnfg (
   	city_code varchar(3) unique not null,
   	city_name varchar(100) not null,
	time_zone varchar(100) not null,
	country_code varchar(2) not null,
	state_code varchar(2) not null,
	record_version integer not null,
	status varchar(10) not null,
	authorized varchar(1) not null,
	last_configuration_action varchar(20) not null,
	created_by varchar(20),
	creation_time timestamp,
	last_updated_by varchar(20),
	last_updated_time timestamp,
	constraint "pk_idgc_coe_city_code_cnfg" primary key ("city_code")
);

create table if not exists idgc_coe_state_code_cnfg
(
    state_code character varying(3) COLLATE pg_catalog."default" NOT NULL,
    state_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    country_code character varying(2) COLLATE pg_catalog."default" NOT NULL,

    life_cycle_id varchar(50),
    	reference_no varchar(50),
    	record_version int not null,
    	record_status varchar(10) not null,
    	is_authorized varchar(1) not null,
    	last_configuration_action varchar(20) not null,
    	created_by varchar(20),
    	creation_time timestamp,
    	last_updated_by varchar(20),
    	last_updated_time timestamp,
    CONSTRAINT pk_idgc_coe_state_code_cnfg PRIMARY KEY (state_code)
);


 INSERT INTO public.idgc_coe_mapping_config ("action","role",status,authorized,submitted,update_record_version,inactive_previous_record,last_configuration_action,insert_record_into_audit,insert_record_into_basetable,mapping_status,copy_record_from_base_table) VALUES
     ('add','maker','new','N','N','Y','N','unauthorized','Y','N','new','N'),
     ('close','maker','closed','N','N','Y','N','unauthorized','Y','N','closed','N'),
     ('authorize','checker','closed','Y','N','N','Y','authorized','Y','Y','closed','N'),
     ('reopen','maker','reopened','N','N','Y','N','unauthorized','Y','N','reopened','N'),
     ('authorize','checker','reopened','Y','N','N','Y','authorized','Y','Y','reopened','N'),
     ('modify','maker','updated','N','Y','Y','N','unauthorized','Y','N','updated','N'),
     ('authorize','checker','new','Y','Y','N','N','authorized','Y','Y','active','N'),
     ('reject','checker','rejected','N','Y','N','N','rejected','Y','N','rejected','N'),
     ('authorize','checker','updated','Y','Y','N','Y','authorized','Y','Y','active','N'),
     ('draft','maker','draft','N','N','N','N','draft','N','N','draft','N'),
	 ('draft','maker','updated','N','Y','N','N','draft','N','N','updated','N'),
     ('delete','maker','deleted','O','N','N','N','deleted','Y','N','deleted','Y');


commit;