
-- Database: queue

-- DROP DATABASE queue;

-- CREATE DATABASE queue
--   WITH OWNER = postgres
--        ENCODING = 'UTF8'
--        TABLESPACE = pg_default
--        LC_COLLATE = 'Portuguese_Brazil.1252'
--        LC_CTYPE = 'Portuguese_Brazil.1252'
--        CONNECTION LIMIT = -1;

-- CREATE SCHEMA estatisticas
  -- AUTHORIZATION postgres;

DROP TABLE estatisticas.estatisticas;

CREATE TABLE estatisticas.estatisticas
(
  "ID" SERIAL NOT NULL,
  "FLUXO_ID" text NOT NULL,
  "REQUISICAO_ID" integer NOT NULL,
  "TIPO" text,
  "CHEGADA" double precision,
  "TEMPO_ESPERA" double precision,
  "TEMPO_SERVICO" double precision,
  "SAIDA" double precision,
  "SUB_FLUXO" text,
  "PASSO" text,
  "SERVIDOR" text,
  CONSTRAINT "PKEY" PRIMARY KEY ("ID")
)
WITH (
OIDS=FALSE
);
ALTER TABLE estatisticas.estatisticas
OWNER TO postgres;
