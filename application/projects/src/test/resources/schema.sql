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
	update_record_version varchar(1) not null,
	inactive_previous_record varchar(1) not null,
	last_configuration_action varchar(20) not null,
	insert_record_into_audit varchar(1) not null,
	insert_record_into_basetable varchar(1) not null,
	mapping_status varchar(20) not null default 'draft'::character varying,
	constraint idgc_coe_mapping_config_action_role_status__key unique (action, role, status)
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
    record_version integer NOT NULL,
    status character varying(10) COLLATE pg_catalog."default" NOT NULL,
    authorized character varying(1) COLLATE pg_catalog."default" NOT NULL,
    last_configuration_action character varying(20) COLLATE pg_catalog."default" NOT NULL,
    created_by character varying(20) COLLATE pg_catalog."default",
    creation_time timestamp without time zone,
    last_updated_by character varying(20) COLLATE pg_catalog."default",
    last_updated_time timestamp without time zone,
    CONSTRAINT pk_idgc_coe_state_code_cnfg PRIMARY KEY (state_code)
);


INSERT INTO idgc_coe_mapping_config (id,action,role,status,authorized,update_record_version,inactive_previous_record,last_configuration_action,insert_record_into_audit,insert_record_into_basetable,mapping_status) VALUES
	 (1,'add','maker','new','N','Y','N','unauthorized','Y','N','new'),
	 (2,'close','maker','closed','N','Y','N','unauthorized','Y','N','closed'),
	 (3,'authorize','checker','closed','Y','N','Y','authorized','Y','Y','closed'),
	 (4,'reopen','maker','reopened','N','Y','N','unauthorized','Y','N','reopened'),
	 (5,'authorize','checker','reopened','Y','N','Y','authorized','Y','Y','reopened'),
	 (6,'delete','maker','deleted','O','N','N','deleted','Y','N','deleted'),
	 (7,'modify','maker','updated','N','Y','N','unauthorized','Y','N','updated'),
	 (8,'draft','maker','draft','N','N','N','draft','Y','N','draft'),
	 (9,'authorize','checker','new','Y','N','N','authorized','Y','Y','active'),
	 (10,'draft','maker','updated','N','N','N','draft','Y','N','updated'),
     (11,'reject','checker','rejected','N','N','N','rejected','Y','N','rejected'),
	 (12,'authorize','checker','updated','Y','N','Y','authorized','Y','Y','active');
	 
commit;