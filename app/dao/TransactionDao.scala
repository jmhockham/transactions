package dao

import java.sql.Timestamp
import java.util.UUID
import javax.inject.Inject

import models.Transaction
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

class TransactionDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  private val transactions = TableQuery[TransactionTable]

  def all(): Future[Seq[Transaction]] = db.run(transactions.result)

  def findTransaction(id: UUID): Future[Transaction] = {
    db.run(transactions.filter{ t =>
      t.id === id
    }.result).map(_.head)
  }

  def findMultipleTransactions(ids: Seq[UUID]): Future[Seq[Transaction]] = {
    db.run(transactions.filter{ t =>
      t.id inSetBind ids
    }.result)
  }

  def matchTransactions(firstId:UUID, secondId:UUID): Boolean = {
    val firstResult = Await.result(updateMatching(firstId, secondId), 5 minutes)
    val secondResult = Await.result(updateMatching(secondId, firstId), 5 minutes)
    firstResult && secondResult
  }

  def unmatchTransactions(firstId:UUID, secondId:UUID): Boolean = {
    val firstResult = Await.result(updateMatchingReset(firstId), 5 minutes)
    val secondResult = Await.result(updateMatchingReset(secondId), 5 minutes)
    firstResult && secondResult
  }

  def updateMatching(id:UUID, matchingId: UUID): Future[Boolean] = db.run {
    transactions.filter(_.id === id)
      .map( t => (t.state, t.matchedWith) )
      .update(("MATCHED",Some(matchingId)))
      .map{
        case 0 => false
        case _ => true
      }
  }

  def updateMatchingReset(id:UUID): Future[Boolean] = db.run {
    transactions.filter(_.id === id)
      .map( t => (t.state, t.matchedWith) )
      .update(("UNMATCHED",None))
      .map{
        case 0 => false
        case _ => true
      }
  }

  private class TransactionTable(tag:Tag) extends Table[Transaction](tag,"transactions"){
    def id = column[UUID]("id")
    def merchantId = column[Option[UUID]]("merchant_id")
    def locationId = column[Option[UUID]]("location_id")
    def cardScheme = column[Option[String]]("card_scheme")
    def bin = column[Option[String]]("bin")
    def lastFour = column[Option[String]]("last_four")
    def provider = column[String]("provider")
    def source = column[String]("source")
    def amount = column[Int]("amount")
    def transactionDate = column[Timestamp]("transaction_date", O.SqlType("timestamp with time zone"))
    def state = column[String]("state")
    def createdDate = column[Timestamp]("created_date", O.SqlType("timestamp with time zone"))
    def updatedDate = column[Timestamp]("updated_date", O.SqlType("timestamp with time zone"))
    def merchantName = column[Option[String]]("merchant_name")
    def candidates = column[String]("candidates")
    def matchedWith = column[Option[UUID]]("matched_with")

    override def * : ProvenShape[Transaction] = (id, merchantId, locationId, cardScheme, bin,
      lastFour, provider, source, amount, transactionDate, state, createdDate, updatedDate, merchantName,
      candidates, matchedWith) <> (Transaction.tupled, Transaction.unapply)

  }

}
