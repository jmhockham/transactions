create table merchants(
	id uuid not null constraint merchants_pkey primary key,
	name varchar,
	logo_url varchar(255),
	state varchar(50) not null,
	created_date timestamp not null,
	updated_date timestamp not null
);

create table locations(
	id uuid not null constraint locations_pkey primary key,
	merchant_id UUID NOT NULL,
	name VARCHAR NOT NULL,
	latitude double precision  NOT NULL,
	longitude double precision NOT NULL,
	state VARCHAR(50) NOT NULL,
	created_date TIMESTAMP NOT NULL,
	updated_date TIMESTAMP NOT NULL
);

CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS fuzzystrmatch;
CREATE EXTENSION IF NOT EXISTS postgis_tiger_geocoder;
CREATE EXTENSION IF NOT EXISTS postgis_topology;