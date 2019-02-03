package models

import java.sql.Timestamp
import java.util.UUID

case class Transaction
(
  id: UUID,
  merchantId: Option[UUID],
  locationId: Option[UUID],
  cardScheme: Option[String],
  bin: Option[String],
  lastFour: Option[String],
  provider: String,
  source: String,
  amount: Int,
  transactionDate: Timestamp,
  state: String,
  createdDate: Timestamp,
  updatedDate: Timestamp,
  merchantName: Option[String],
  candidates: String,
  matchedWith: Option[UUID]

)
