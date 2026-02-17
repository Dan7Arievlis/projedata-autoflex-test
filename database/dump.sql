--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3 (Debian 16.3-1.pgdg120+1)
-- Dumped by pg_dump version 16.3 (Debian 16.3-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: input_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.input_product (
    amount numeric(15,3) NOT NULL,
    input_id uuid NOT NULL,
    product_id uuid NOT NULL
);


ALTER TABLE public.input_product OWNER TO postgres;

--
-- Name: lgpd_audit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lgpd_audit (
    processed_at timestamp(6) without time zone,
    entity_id uuid,
    id uuid NOT NULL,
    entity_name character varying(255),
    operation character varying(255),
    reason character varying(255)
);


ALTER TABLE public.lgpd_audit OWNER TO postgres;

--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    flg_active boolean,
    value numeric(18,2) NOT NULL,
    created_at timestamp(6) without time zone,
    deleted_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    id uuid NOT NULL,
    last_updated_by uuid,
    name character varying(100) NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Name: tb_input; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_input (
    amount double precision NOT NULL,
    flg_active boolean,
    created_at timestamp(6) without time zone,
    deleted_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    id uuid NOT NULL,
    last_updated_by uuid,
    name character varying(100) NOT NULL
);


ALTER TABLE public.tb_input OWNER TO postgres;

--
-- Name: tb_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_user (
    flg_active boolean,
    created_at timestamp(6) without time zone,
    deleted_at timestamp(6) without time zone,
    registration_date timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    id uuid NOT NULL,
    last_updated_by uuid,
    login character varying(20) NOT NULL,
    email character varying(150) NOT NULL,
    password character varying(300) NOT NULL,
    roles character varying[]
);


ALTER TABLE public.tb_user OWNER TO postgres;

--
-- Data for Name: input_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.input_product (amount, input_id, product_id) FROM stdin;
600.000	7119be29-b79b-4771-abca-6ec6e4fafc2b	48e0c6d2-1469-4e95-ad66-af97498e66f6
4000.000	7119be29-b79b-4771-abca-6ec6e4fafc2b	052d20d6-1b79-4a37-9064-05a395f21383
50.000	36117d2f-8737-438a-9f74-76493b412d41	75d84251-41f8-4bd6-8f6a-9d1f7dae5115
120.000	23314045-c26b-4546-a665-a8f9df513eb3	f0cb338f-f082-4551-a129-fcd85f342653
20.000	293b2a56-ff69-41bc-b7c2-1d06d9b847b8	052d20d6-1b79-4a37-9064-05a395f21383
30.000	261db4a1-08c6-4d10-874b-7be18b3335ea	13f726a2-c318-4dab-b0a6-b2a95bca3a84
60.000	29f3b0b8-af3d-4179-b9bd-e6767b45963c	13f726a2-c318-4dab-b0a6-b2a95bca3a84
30.000	293b2a56-ff69-41bc-b7c2-1d06d9b847b8	75d84251-41f8-4bd6-8f6a-9d1f7dae5115
\.


--
-- Data for Name: lgpd_audit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lgpd_audit (processed_at, entity_id, id, entity_name, operation, reason) FROM stdin;
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (flg_active, value, created_at, deleted_at, updated_at, id, last_updated_by, name) FROM stdin;
t	30.00	2026-02-13 17:28:29.466837	\N	2026-02-13 17:28:29.466837	f0cb338f-f082-4551-a129-fcd85f342653	\N	Camisa
t	35.00	2026-02-15 15:08:24.681642	\N	2026-02-15 15:08:24.681642	13f726a2-c318-4dab-b0a6-b2a95bca3a84	\N	Sushi
t	45.60	2026-02-13 16:38:40.264728	\N	2026-02-17 13:53:08.404389	48e0c6d2-1469-4e95-ad66-af97498e66f6	\N	Vidro
t	223.50	2026-02-13 16:42:45.388308	\N	2026-02-17 13:53:31.2211	052d20d6-1b79-4a37-9064-05a395f21383	\N	Cimento
t	12.00	2026-02-13 16:53:12.69088	\N	2026-02-17 13:54:02.255936	75d84251-41f8-4bd6-8f6a-9d1f7dae5115	\N	Chocolate
\.


--
-- Data for Name: tb_input; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_input (amount, flg_active, created_at, deleted_at, updated_at, id, last_updated_by, name) FROM stdin;
50000	t	2026-02-13 16:28:56.931116	\N	2026-02-13 16:28:56.931116	7119be29-b79b-4771-abca-6ec6e4fafc2b	\N	Areia
700	t	2026-02-13 16:29:40.581691	\N	2026-02-13 16:29:40.581691	36117d2f-8737-438a-9f74-76493b412d41	\N	Cacau
300000	t	2026-02-13 16:30:12.008656	\N	2026-02-13 16:30:12.008656	cc249787-6899-420a-a2c2-b17f237fb515	\N	Terra
12000	t	2026-02-13 16:33:43.84139	\N	2026-02-13 16:33:43.84139	293b2a56-ff69-41bc-b7c2-1d06d9b847b8	\N	Açúcar
2000	t	2026-02-15 15:08:45.561872	\N	2026-02-15 15:08:45.561872	29f3b0b8-af3d-4179-b9bd-e6767b45963c	\N	Salmão
1000	t	2026-02-15 15:07:54.956607	\N	2026-02-15 15:08:52.635564	261db4a1-08c6-4d10-874b-7be18b3335ea	\N	Arroz
12000	t	2026-02-13 16:30:28.805608	\N	2026-02-17 13:52:23.940822	23314045-c26b-4546-a665-a8f9df513eb3	\N	Algodão
\.


--
-- Data for Name: tb_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_user (flg_active, created_at, deleted_at, registration_date, updated_at, id, last_updated_by, login, email, password, roles) FROM stdin;
\N	2026-02-09 16:35:04.59667	\N	2026-02-09 16:35:04.59667	2026-02-09 16:35:04.59667	7d9bc004-1f25-4eef-896a-a2a28ba6e2d5	\N	admin	admin@admin.com	$2a$10$3PU3CuwOkAdbBNuHqCfGNutg52Vj9esc1RWFpz.7nw3VRRUHWnZhu	{ADMIN}
\N	2026-02-09 19:33:04.641881	\N	2026-02-09 19:33:04.641881	2026-02-09 19:33:04.641881	da9d0c15-cf8a-4481-8171-5fca7173c308	7d9bc004-1f25-4eef-896a-a2a28ba6e2d5	user	user@user.com	$2a$10$hwlXyxHe3WQAAsrfq.lEz.c0pkID9q/9/EDhMKK4t6pFTqTng6Jpq	{USER}
\N	2026-02-15 15:20:27.082869	\N	2026-02-15 15:20:27.082869	2026-02-15 15:20:27.082869	bcaa784a-b038-4d6a-aaf6-3ae806115f32	\N	test	test@test.com	$2a$10$qtEpU78X9EXvx6Z2LEiGLO4mm0H4QocR6YUez3Grl.vL3o095RaoK	{USER}
\.


--
-- Name: input_product input_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.input_product
    ADD CONSTRAINT input_product_pkey PRIMARY KEY (input_id, product_id);


--
-- Name: lgpd_audit lgpd_audit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lgpd_audit
    ADD CONSTRAINT lgpd_audit_pkey PRIMARY KEY (id);


--
-- Name: product product_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_name_key UNIQUE (name);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: tb_input tb_input_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_input
    ADD CONSTRAINT tb_input_name_key UNIQUE (name);


--
-- Name: tb_input tb_input_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_input
    ADD CONSTRAINT tb_input_pkey PRIMARY KEY (id);


--
-- Name: tb_user tb_user_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_user
    ADD CONSTRAINT tb_user_login_key UNIQUE (login);


--
-- Name: tb_user tb_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_user
    ADD CONSTRAINT tb_user_pkey PRIMARY KEY (id);


--
-- Name: input_product fk2taqacdnpwid8j4y9j3xr5xn2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.input_product
    ADD CONSTRAINT fk2taqacdnpwid8j4y9j3xr5xn2 FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: input_product fka9v9lyxvxdd71oyeo92sy7n5m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.input_product
    ADD CONSTRAINT fka9v9lyxvxdd71oyeo92sy7n5m FOREIGN KEY (input_id) REFERENCES public.tb_input(id);


--
-- Name: product fkerjv0r5two0dpbjw29mevgd75; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fkerjv0r5two0dpbjw29mevgd75 FOREIGN KEY (last_updated_by) REFERENCES public.tb_user(id);


--
-- Name: tb_input fkl1sh98cwgr66u7gm0jpnl45dr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_input
    ADD CONSTRAINT fkl1sh98cwgr66u7gm0jpnl45dr FOREIGN KEY (last_updated_by) REFERENCES public.tb_user(id);


--
-- Name: tb_user fksauw2ih9kadnsf2e8p2yf4fr3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_user
    ADD CONSTRAINT fksauw2ih9kadnsf2e8p2yf4fr3 FOREIGN KEY (last_updated_by) REFERENCES public.tb_user(id);


--
-- PostgreSQL database dump complete
--

