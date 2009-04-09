/*
 * Game.scala
 */

package org.nuttycombe.colorlife

import java.awt.Color
import scala.collection._
import scala.collection.immutable.Map

abstract class Game[+T <: Game[T]](val xsize:Int, val ysize:Int) {
    type ControllerType <: Controller[T]

    case class Cell(x:Int, y:Int) {
        lazy val neighbors : Seq[Cell] = for (i <- Math.max(0, x-1) until Math.min(x+1, xsize-1);
                                              j <- Math.max(0, y-1) until Math.min(y+1, ysize-1);
                                              if (!(i == x && j == y))) yield cells(i)(j)

        private var c : Color = Color.BLACK
        private var nextColor : Color = Color.BLACK

        def color = c
        def color_=(c:Color) = {
            if (c != this.c) {
                cellsToEvaluate ++= neighbors
                cellsToEvaluate += this
                this.c = c
            }
        }

        def evaluate = {
            val liveNeighbors = neighbors.filter(_.c != Color.BLACK)
            this.nextColor = if (liveNeighbors.size < 2 || liveNeighbors.size > 3) {
                Color.BLACK
            } else if (liveNeighbors.size == 3 && c == Color.BLACK) {
                val colorCounts = liveNeighbors.foldLeft(Map.empty[Color,Int]) {(counts, n) =>
                    counts + ((n.c, counts.getOrElse(n.c, 0) + 1))
                }

                if (colorCounts.size < 3) {
                    //find the color with the maximum count
                    colorCounts.reduceLeft((a, b) => if (b._2 > a._2) b else a)._1
                } else {
                    Colors.blend(liveNeighbors.map(_.c))
                }
            } else {
                c
            }
        }

        def evolve = {
            color = nextColor
        }
    }

    val cells : Array[Array[Cell]] = Array.fromFunction(buildInitialCell(_,_))(xsize, ysize)
    private var cellsToEvaluate = Set.empty[Cell]

    case class CellUpdateEvent(cells: Set[Cell]) extends GameEvent
    case class TurnCompleteEvent extends ControllerEvent

    def buildInitialCell(x:Int, y:Int):Cell

    def bind(cont:ControllerType) = {
        cont.addHandler {
            case TurnCompleteEvent() => Some(CellUpdateEvent(applyLifeRule))
        }
    }

    protected def applyLifeRule : Set[Cell] = {
        val evaluated = cellsToEvaluate
        cellsToEvaluate.foreach(_.evaluate)
        cellsToEvaluate.foreach(_.evolve)
        cellsToEvaluate = Set.empty[Cell]
        evaluated
    }
}

trait GameEvent
trait ControllerEvent
