package models

import java.sql.Timestamp
import java.util.UUID

//TODO location modelling (child seq)
case class Merchant
(
  id: UUID,
  name: Option[String],
  logoUrl: Option[String],
  state: String,
  createdDate: Timestamp,
  updatedDate: Timestamp
)
