package controllers

import javax.inject._

import dao.{MerchantDao, TransactionDao}
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
  merchantDao: MerchantDao
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

    val merchants = Await.result(merchantDao.all(), 5 minutes)
    val m1 = merchants.head
    val mSeq: Seq[Merchant] = Await.result(merchantDao.findMultipleMerchants(Seq(m1.id, m1.id)), 5 minutes)
    val m3 = Await.result(merchantDao.findMerchant(m1.id), 5 minutes)
    Ok(views.html.index("Your new application is ready."))
  }

  //TODO routing/requests for actions

}
