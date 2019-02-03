package models

import java.sql.Timestamp
import java.util.UUID

case class Merchant
(
  id: UUID,
  name: Option[String],
  logoUrl: Option[String],
  state: String,
  createdDate: Timestamp,
  updatedDate: Timestamp
)
