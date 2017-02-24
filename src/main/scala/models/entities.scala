package models

import monocle.macros._
// Some product types
// Level 1

case class Level1(singleVal: Level2, listVal: List[Level2], eitherVal: EitherS[Level2], optionVal: Option[Level2])

case object Level1 {
  val singleVal = GenLens[Level1](_.singleVal)
  val listVal = GenLens[Level1](_.listVal)
  val eitherVal = GenLens[Level1](_.eitherVal)
  val optionVal = GenLens[Level1](_.optionVal)
}

// Level 2

case class Level2(singleVal: Level3, listVal: List[Level3], eitherVal: EitherS[Level3], optionVal: Option[Level3])

case object Level2  {
  val singleVal = GenLens[Level2](_.singleVal)
  val listVal = GenLens[Level2](_.listVal)
  val eitherVal = GenLens[Level2](_.eitherVal)
  val optionVal = GenLens[Level1](_.optionVal)

}

// Level 3

case class Level3(singleVal: Level4, listVal: List[Level4], eitherVal: EitherS[Level4], optionVal: Option[Level4])

case object Level3  {
  val singleVal = GenLens[Level3](_.singleVal)
  val listVal = GenLens[Level3](_.listVal)
  val eitherVal = GenLens[Level3](_.eitherVal)
  val optionVal = GenLens[Level3](_.optionVal)

}

// Level 4

case class Level4(stringVal: String, sumVal: Amount)

case object Level4  {
  val stringVal = GenLens[Level4](_.stringVal)
  val sumVal = GenLens[Level4](_.sumVal)
}

// Sum Type

sealed trait Amount {
  def amount: Int
}

case class USD(amount: Int) extends Amount

case class EUR(amount: Int) extends Amount

case object Amount