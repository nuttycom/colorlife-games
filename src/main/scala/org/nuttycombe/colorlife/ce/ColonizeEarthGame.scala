/*
 * ColonizeEarthGame.scala
 */

package org.nuttycombe.colorlife.ce

import java.awt.Color
import Math._

class ColonizeEarthGame(xsize:Int, ysize:Int, earthSize:Int) extends Game[ColonizeEarthGame](xsize, ysize) {
    case class Player(name:String, color:Color) {
        var seeds : Int = 0
        var journeyRemaining : Int = 0
    }

    class EarthCell(x:Int, y:Int) extends Cell(x,y) {
        //earth cells cannot have color set externally
        override def color_=(c:Color) = {}
    }

    var players : List[Player] = Nil
    var currentPlayer : Player = _

    def buildInitialCell(x:Int, y:Int):Cell = {
        if (abs(x - xsize/2f) < earthSize/2f && abs(y - ysize/2f) < earthSize/2f) {
            new EarthCell(x, y)
        } else {
            new Cell(x, y)
        }
    }

    case class DropSporeEvent(x:Int, y:Int) extends ControllerEvent
    case class JourneyEvent(turns:Int) extends ControllerEvent

    override def initController(cont:Controller[GameType]) = {
        super.initController(cont)
        cont.addHandler({
                case DropSporeEvent(x, y) => cells(x)(y).color = currentPlayer.color; Some(CellUpdateEvent(Set(cells(x)(y))))
                case JourneyEvent(turns) => None
            })
    }
}

