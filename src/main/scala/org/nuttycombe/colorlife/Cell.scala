/*
 * Cell.scala
 */

package org.nuttycombe.colorlife

import java.awt.Color

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
}