package models

import java.sql.Timestamp

/*
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
 */
case class Location
(
  id: String,
  merchant_id: String,
  name: String,
  latitude: Double,
  longitude: Double,
  state: String,
  createdDate: Timestamp,
  updatedDate: Timestamp
) {

}
