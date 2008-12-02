/*
 * ColonizeEarth.scala
 */

package org.nuttycombe.colorlife

import java.awt.Color
import Math._

class ColonizeEarth(xsize:Int, ysize:Int, earthSize:Int) extends Game(xsize, ysize) {
    var players : List[Player] = Nil

    class EarthCell(x:Int, y:Int) extends Cell(x,y) {
        override def color_=(c:Color) = {}
    }

    def buildInitialCell(x:Int, y:Int):Cell = {
        if (abs(x - xsize/2f) < earthSize/2f && abs(y - ysize/2f) < earthSize/2f) {
            new EarthCell(x, y)
        } else {
            new Cell(x, y)
        }
    }
}
