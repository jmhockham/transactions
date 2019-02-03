create table transactions(
	id uuid not null
		constraint transactions_pkey
			primary key,
  merchant_id uuid,
  location_id uuid,
	card_scheme varchar(10),
	bin varchar(6),
	last_four varchar(4),
	provider varchar(10) not null,
	source varchar(20) not null,
	amount integer not null,
	transaction_date timestamp with time zone not null,
	state varchar(50) not null,
	created_date timestamp with time zone not null,
	updated_date timestamp with time zone not null,
	merchant_name text,
  candidates jsonb not null,
  matched_with uuid
);