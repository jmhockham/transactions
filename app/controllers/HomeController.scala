package controllers

import java.util.UUID
import javax.inject._

import dao.{LocationDao, MerchantDao, TransactionDao}
import models.{Merchant, Transaction}
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(
  cc: ControllerComponents,
  transactionDao: TransactionDao,
  merchantDao: MerchantDao,
  locationDao: LocationDao
) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    *
    * We're using this to quickly sanity check the dao stuff
    */
  def index = Action {
    val transactions = Await.result(transactionDao.all(), 5 minutes)
    val t1 = transactions.head
    val t2 = transactions(1)
    val tSeq: Seq[Transaction] = Await.result(transactionDao.findMultipleTransactions(Seq(t1.id, t2.id)), 5 minutes)
    val t3 = Await.result(transactionDao.findTransaction(t1.id), 5 minutes)
    t1.matchedWith = Some(t2.id)
    val updateResult = transactionDao.matchTransactions(t1.id, t2.id)
    if(updateResult){
      transactionDao.unmatchTransactions(t1.id, t2.id)
    }

    val merchants = Await.result(merchantDao.all(), 5 minutes)
    val m1 = merchants.head
    val mSeq: Seq[Merchant] = Await.result(merchantDao.findMultipleMerchants(Seq(m1.id, m1.id)), 5 minutes)
    val m3 = Await.result(merchantDao.findMerchant(m1.id), 5 minutes)

    val locations = Await.result(locationDao.all(), 5 minutes)

    Ok(views.html.index("Your new application is ready."))
  }

  //TODO routing/requests for actions

  def getTransaction(id: UUID) = Action {
    val transaction = Await.result(transactionDao.findTransaction(id), 5 minutes)
    Ok(transaction.toString)
  }

  def getMerchant(id: UUID ) = Action {
    val merchant = Await.result(merchantDao.findMerchant(id), 5 minutes)
    Ok(merchant.toString)
  }

  def getTransactionStates(state: String = "") = Action {
    val transactions = Await.result(transactionDao.all(), 5 minutes)
    val stateCount = collection.mutable.Map[String, Int]()
    transactions.map { t =>
      val count = stateCount.getOrElse(t.state.toUpperCase, 0)
      stateCount.put(t.state.toUpperCase, count+1)
    }
    if (state != null && state.nonEmpty) {
      Ok(s"$state: ${stateCount.getOrElse(state.toUpperCase, 0)}")
    }
    else{
      Ok(stateCount.toString())
    }
  }

}
