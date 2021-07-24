-- public.tb_users definition
CREATE TABLE public.tb_users (
	id bigserial NOT NULL,
	active bool NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	uuid varchar(255) NULL,
	"password" varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT tb_users_pkey PRIMARY KEY (id)
);

-- public.tb_persons definition

CREATE TABLE public.tb_persons (
	id bigserial NOT NULL,
	active bool NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	city varchar(255) NULL,
	complement varchar(255) NULL,
	country varchar(255) NULL,
	neighborhood varchar(255) NULL,
	"number" varchar(255) NULL,
	postal_code varchar(255) NULL,
	street varchar(255) NULL,
	birthday date NULL,
	doc varchar(255) NULL,
	"name" varchar(255) NULL,
	id_user int8 NULL,
	CONSTRAINT tb_persons_pkey PRIMARY KEY (id),
	CONSTRAINT tb_persons_id_user_fk FOREIGN KEY (id_user) REFERENCES public.tb_users(id)
);

-- public.tb_pauta definition

CREATE TABLE public.tb_pauta (
	id bigserial NOT NULL,
	active bool NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	uuid varchar(255) NULL,
	code varchar(255) NULL,
	description varchar(255) NULL,
	"open" bool NOT NULL,
	theme varchar(255) NULL,
	CONSTRAINT tb_pauta_pkey PRIMARY KEY (id)
);

-- public.tb_sessao definition

CREATE TABLE public.tb_sessao (
	id bigserial NOT NULL,
	active bool NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	uuid varchar(255) NULL,
	duration_minutes int4 NOT NULL,
	end_time timestamp NULL,
	info varchar(255) NULL,
	"name" varchar(255) NULL,
	start_time timestamp NULL,
	id_pauta int8 NULL,
	CONSTRAINT tb_sessao_pkey PRIMARY KEY (id),
	CONSTRAINT tb_sessao_id_pauta_fk FOREIGN KEY (id_pauta) REFERENCES public.tb_pauta(id)
);


-- public.tb_sessao_votos definition

CREATE TABLE public.tb_sessao_votos (
	id bigserial NOT NULL,
	active bool NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	uuid varchar(255) NULL,
	vote varchar(255) NULL,
	id_sessao int8 NULL,
	id_user int8 NULL,
	CONSTRAINT tb_sessao_votos_pkey PRIMARY KEY (id),
	CONSTRAINT tb_sessao_voto_id_user_fk FOREIGN KEY (id_user) REFERENCES public.tb_users(id),
	CONSTRAINT tb_sessao_voto_id_sessao_fk FOREIGN KEY (id_sessao) REFERENCES public.tb_sessao(id)
);