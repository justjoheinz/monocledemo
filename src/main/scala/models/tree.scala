package models

import scalaz._

sealed trait Tree[A]
case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A]
case class Leaf[A](element: A) extends Tree[A]

case object Tree {
    implicit val functorInstance : Functor[Tree] = new Functor[Tree] {
      override def map[A, B](fa: Tree[A])(f: (A) => B): Tree[B] = {
        fa match {
          case Node(left, right) => Node(map(left)(f), map(right)(f))
          case Leaf(elem) => Leaf(f(elem))
        }
      }
    }

  implicit val foldableInstance : Foldable[Tree] = new Foldable[Tree] {
    override def foldMap[A, B](fa: Tree[A])(f: (A) => B)(implicit F: Monoid[B]): B = {
      foldRight(fa, Monoid[B].zero){case (a,b) => Monoid[B].append(f(a), b)}
    }

    override def foldRight[A, B](fa: Tree[A], z: => B)(f: (A, => B) => B): B = {
      fa match {
        case Node(left, right) =>
          val leftFold: B = foldRight(left, foldRight(right, z)(f))(f)
          leftFold
        case Leaf(elem) => f(elem, z)
      }
    }
  }

  implicit val applicativeInstance : Applicative[Tree] = new Applicative[Tree] {
    override def point[A](a: => A): Tree[A] = Leaf(a)

    override def ap[A, B](fa: => Tree[A])(f: => Tree[(A) => B]): Tree[B] = {
      fa match {
        case Node(left, right) => Node(ap(left)(f), ap(right)(f))
        case Leaf(elem) => f match {
          case Node(fleft, fright) => Node(ap(Leaf(elem))(fleft), ap(Leaf(elem))(fright))
          case Leaf(felem) => Leaf(felem(elem))
        }
      }
    }
  }

  implicit val traverseInstance : Traverse[Tree] = new Traverse[Tree] {
    override def traverseImpl[G[_], A, B](fa: Tree[A])(f: (A) => G[B])(implicit ev: Applicative[G]): G[Tree[B]] = {
      fa match {
        case Node(left, right) =>
          val leftG: G[Tree[B]] = traverseImpl(left)(f)
          val rightG: G[Tree[B]] = traverseImpl(right)(f)
          Applicative[G].apply2(leftG, rightG) { case (left, right) => Node(left, right) }
        case Leaf(elem) => Functor[G].map(f(elem))(a => Applicative[Tree].point(a))
      }
    }
  }
}