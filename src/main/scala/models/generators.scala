package models

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen._

trait Generators {

  private def levelGenerator[T2, T1](gen : Gen[T2], f : (T2, List[T2], EitherS[T2], Option[T2]) => T1) : Gen[T1] = for {
    l4 <- gen
    l <- listOfN(3, gen)
    e <- genEitherS(gen)
    o <- option(gen)
  } yield f(l4, l, e, o)

  def genLevel1: Gen[Level1] = levelGenerator(genLevel2, Level1.apply)

  def genLevel2: Gen[Level2] = levelGenerator(genLevel3, Level2.apply)

  def genLevel3: Gen[Level3] = levelGenerator(genLevel4, Level3.apply)

  def genLevel4: Gen[Level4] = for {
    sv <- arbitrary[String]
    sum <- genAmount
  } yield {
    Level4(sv, sum)
  }

  def genUSD: Gen[USD] = for {
    amount <- arbitrary[Int]
  } yield USD(amount)

  def genEUR: Gen[USD] = for {
    amount <- arbitrary[Int]
  } yield USD(amount)

  def genAmount: Gen[USD] = oneOf(genUSD, genEUR)

  def genEitherS[A](genA: Gen[A]): Gen[EitherS[A]] = Gen.oneOf(arbitrary[String].map(Left(_)), genA.map(Right(_)))

  implicit val arbLevel1 : Arbitrary[Level1] = Arbitrary(genLevel1)
  implicit val arbLevel2 : Arbitrary[Level2] = Arbitrary(genLevel2)
  implicit val arbLevel3 : Arbitrary[Level3] = Arbitrary(genLevel3)
  implicit val arbLevel4 : Arbitrary[Level4] = Arbitrary(genLevel4)
}