package dao

import java.sql.Timestamp
import java.util.UUID
import javax.inject.Inject

import models.Merchant
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.{ExecutionContext, Future}

class MerchantDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  private val merchants = TableQuery[MerchantTable]

  def all(): Future[Seq[Merchant]] = db.run(merchants.result)

  private class MerchantTable(tag: Tag) extends Table[Merchant](tag, "merchant") {
    def id = column[UUID]("id")
    def name = column[Option[String]]("name")
    def logoUrl = column[Option[String]]("logo_url")
    def state = column[String]("state")
    def createdDate = column[Timestamp]("created_date", O.SqlType("timestamp"))
    def updatedDate = column[Timestamp]("updated_date", O.SqlType("timestamp"))

    override def * : ProvenShape[Merchant] = (id, name, logoUrl, state, createdDate, updatedDate) <> (Merchant.tupled, Merchant.unapply)
  }

}
