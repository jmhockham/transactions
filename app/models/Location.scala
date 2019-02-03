package models

import java.sql.Timestamp
import java.util.UUID

case class Location
(
  id: UUID,
  merchant_id: UUID,
  name: String,
  latitude: Double,
  longitude: Double,
  state: String,
  createdDate: Timestamp,
  updatedDate: Timestamp
)
