package dao

import java.sql.Timestamp
import java.util.UUID
import javax.inject.Inject

import models.Location
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.{ExecutionContext, Future}

class LocationDao @Inject()
(
  protected val dbConfigProvider: DatabaseConfigProvider,
  protected val merchantDao: MerchantDao
)(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val locations = TableQuery[LocationTable]

  def all(): Future[Seq[Location]] = db.run(locations.result)

  def findLocation(id: UUID): Future[Location] = {
    db.run(locations.filter{ l =>
      l.id === id
    }.result).map(_.head)
  }

  class LocationTable(tag: Tag) extends Table[Location](tag, "locations") {
    def id = column[UUID]("id")
    def merchantId = column[UUID]("merchant_id")
    def merchant = foreignKey("merchants",merchantId, merchantDao.merchants)(_.id)
    def name = column[String]("name")
    def latitude = column[Double]("latitude")
    def longitude = column[Double]("longitude")
    def state = column[String]("state")
    def createdDate = column[Timestamp]("created_date", O.SqlType("timestamp"))
    def updatedDate = column[Timestamp]("updated_date", O.SqlType("timestamp"))

    override def * : ProvenShape[Location] = (id, merchantId, name, latitude, longitude,
      state, createdDate, updatedDate) <> (Location.tupled, Location.unapply)
  }

}
