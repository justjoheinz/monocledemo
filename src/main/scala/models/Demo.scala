package models

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties


object Demo extends Properties("Linsensuppe") with Generators with StringService {

  val level1 = genLevel1

  property("simple getter") = forAll {l: Level4 =>
    l.stringVal == l.stringVal
  }

}
