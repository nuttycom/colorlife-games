/*
 * Game.scala
 */

package org.nuttycombe.colorlife

import java.awt._
import scala.collection.mutable._

abstract class Game[T <: Game[T]](val xsize:Int, val ysize:Int) {
    self : T =>
    type GameType = T

    case class Cell(x:Int, y:Int) {
        lazy val neighbors : Seq[Cell] = for (i <- Math.max(0, x-1) until Math.min(x+1, xsize-1);
                                              j <- Math.max(0, y-1) until Math.min(y+1, ysize-1);
                                              if (!(i == x && j == y))) yield cells(i)(j)

        private var c : Color = Color.BLACK
        private var nextColor : Color = Color.BLACK

        def color = c
        def color_=(c:Color) = {
            setColor(c)
        }

        private def setColor(c:Color) = {
            if (c != this.c) {
                Game.this.cellsToEvaluate + this ++= this.neighbors
                this.c = c
            }
        }

        def evaluate = {
            val liveNeighbors = neighbors.filter(_.c != Color.BLACK)
            this.nextColor = if (liveNeighbors.size < 2 || liveNeighbors.size > 3) {
                Color.BLACK
            } else if (liveNeighbors.size == 3 && c == Color.BLACK) {
                val colorCounts = liveNeighbors.foldLeft(Map.empty[Color,Int]) {(counts, n) =>
                    counts.put(n.c, counts.getOrElseUpdate(n.c, 0) + 1)
                    counts
                }

                if (colorCounts.size < 3) {
                    colorCounts.foldLeft((Color.BLACK, 0)) {(max, entry) =>
                        if (entry._2 > max._2) entry;
                        else max;
                    }._1
                } else {
                    Colors.blend(liveNeighbors.map(_.c))
                }
            } else {
                c
            }
        }

        def evolve = {
            setColor(this.nextColor)
        }
    }

    val cells : Array[Array[Cell]] = Array.fromFunction(buildInitialCell(_,_))(xsize, ysize)
    private var cellsToEvaluate = new HashSet[Cell]()

    def buildInitialCell(x:Int, y:Int):Cell

    def initUI(ui:UI[GameType])

    protected def applyLifeRule = {
        cellsToEvaluate.foreach(_.evaluate)
        cellsToEvaluate = new HashSet[Cell]
        cells.foreach(_.foreach(_.evolve))
    }
}

trait GameEvent
case class TurnCompleteEvent() extends GameEvent