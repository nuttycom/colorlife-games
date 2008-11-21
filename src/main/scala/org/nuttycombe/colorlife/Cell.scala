/*
 * Cell.scala
 */

package org.nuttycombe.colorlife

import java.awt.Color
import scala.collection.mutable._

class Cell(game:Game, val x:Int, val y:Int, private var c:Color) {
    var neighborCount = 0
    
    def color = c
    def color_=(c:Color) = { 
        if (c != this.c) {
            game.cellsToEvaluate ++= this.neighbors.map { cell =>
                if (c == Color.BLACK) cell.neighborCount -= 1
                else cell.neighborCount += 1
                cell
            }
            
            this.c = c
        }
    }

    def neighbors:Seq.Projection[Cell] = {
        for {i <- Math.max(0, x-1) until Math.min(x+2, game.x)
             j <- Math.max(0, y-1) until Math.min(y+2, game.y)
             if (!(i == x && j == y))} yield game.cells(i)(j)                    
    }

    def evaluate = {
        val liveNeighbors = neighbors.filter(_.color != Color.BLACK)
        if (liveNeighbors.size < 2 || liveNeighbors.size > 3) {
            color = Color.BLACK
        } else if (liveNeighbors.size == 3 && color == Color.BLACK) {
            val colorCounts = liveNeighbors.foldLeft(Map.empty[Color,Int]) {(counts, n) =>
                counts.put(n.color, counts.getOrElseUpdate(n.color, 0) + 1)
                counts
            }

            if (colorCounts.size < 3) {
                color = colorCounts.foldLeft((Color.BLACK, 0)) {(max, entry) =>
                    if (entry._2 > max._2) entry;
                    else max;
                }._1
            } else {
                color = Colors.blend(liveNeighbors.map(_.color))
            }
        }
    }
}