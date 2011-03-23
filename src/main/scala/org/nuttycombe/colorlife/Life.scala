/*
 * Game.scala
 */

package org.nuttycombe.colorlife

import java.awt.Color
import scala.annotation.tailrec

trait Life {
  type Entity
  type Location
  type Population = Map[Location, Entity]
  type Parentage = Iterable[Entity]

  def generation(pop: Population): Population = {
    def neighborhood(loc: Location) = neighbors(loc) + loc

    pop.keySet.flatMap(neighborhood).foldLeft[Population](Map()) { (nextGen, loc) =>
      val liveNeighbors = pop.filterKeys(neighbors(loc)).values
      if (willLive(pop.contains(loc), liveNeighbors.size)) nextGen.updated(loc, pop.getOrElse(loc, genesis(liveNeighbors))) else nextGen
    }
  }

  def neighbors(loc: Location): Set[Location]

  def willLive(now: Boolean, n: Int): Boolean
  def genesis(parents: Parentage): Entity

  @tailrec final def run(pop: Population): Unit = {
    if (!pop.isEmpty) run(generation(pop))
  }
}

trait Life2D extends Life {
  type Location = (Int, Int)

  val dxy = (for (dx <- -1 to 1; dy <- -1 to 1 if !(dx == 0 && dy == 0)) yield (dx, dy)).toSet

  override def willLive(now: Boolean, n: Int) = ((now && n == 2) || n == 3)
}

trait Life2DInfinite extends Life2D {
  override def neighbors(loc: Location) = loc match {
    case (x, y) => dxy.map { case (dx, dy) => (x + dx, y + dy) }
  }
}

trait Life2DToroidal extends Life2D {
  val xsize: Int
  val ysize: Int

  override def neighbors(loc: Location) = loc match {
    case (x, y) => dxy.map { case (dx, dy) => ((x + dx) % xsize , (y + dy) % ysize) }
  }
}

object ColorLife {
  def blend(colors : Iterable[java.awt.Color]) = {
    colors.foldLeft((0, 0, 0)) {
      case ((r, g, b), c) => (r + c.getRed(), g + c.getGreen(), b + c.getBlue())
    } match {
      case (r, g, b) => 
        new java.awt.Color(r / colors.size, g / colors.size, b / colors.size)
    }
  }
}

trait ColorLife extends Life {
  type Entity = Color

  override def genesis(parents: Parentage) = ColorLife.blend(parents)
}