package models

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._

trait StringService {
  def calculate(s: String) : Future[Int] = Future { s.length  }
}