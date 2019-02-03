package models

import java.sql.Timestamp

/*
create table merchants(
	id uuid not null constraint merchants_pkey primary key,
	name varchar,
	logo_url varchar(255),
	state varchar(50) not null,
	created_date timestamp not null,
	updated_date timestamp not null
);
 */
case class Merchant
(
  id: String,
  name: String,
  logoUrl: String,
  state: String,
  createdDate: Timestamp,
  updatedDate: Timestamp
){

}
