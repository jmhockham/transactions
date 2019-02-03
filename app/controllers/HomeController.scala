package controllers

import javax.inject._

import dao.TransactionDao
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(
  cc: ControllerComponents,
  transactionDao: TransactionDao
) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    val eventualTransactions = transactionDao.all()
    val transactions = Await.result(eventualTransactions, Duration.Inf)
    val t1 = transactions(1)
    val t2 = transactions(2)
    val transSelected = Await.result(transactionDao.findMultipleTransactions(Seq(t1.id, t2.id)), Duration.Inf)
    Ok(views.html.index("Your new application is ready."))
  }

}
