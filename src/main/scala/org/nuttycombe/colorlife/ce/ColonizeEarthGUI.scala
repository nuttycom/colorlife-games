/*
 * ColonizeEarthGUI.scala
 */

package org.nuttycombe.colorlife.ce

import colorlife._
import java.awt.Graphics
import java.awt.Color
import java.awt.event._

trait ColonizeEarthGUI extends ColonizeEarthController with GUI[ColonizeEarthGame] {
    override type CellFaceType = ColonizeEarthCellFace

    class ColonizeEarthCellFace(size:Int, cell:ColonizeEarthGame#Cell) extends CellFace(size, cell) {
        import cell._

        override def draw(g:Graphics) = {
            super.draw(g)

            //handle special cell type stuff
            cell match {
                case ec : ColonizeEarthGame#EarthCell => {
                        g.setColor(Color.RED)
                        g.drawRect(x * size, y * size, size-1, size-1)
                    }
                case _ =>
            }
        }
    }

    override def createCellFace(size:Int, cell:GameType#Cell) = new ColonizeEarthCellFace(size, cell)

    override def handleKeyTyped(ev:KeyEvent) = {
        super.handleKeyTyped(ev)
        ev.getKeyChar match {
            case ' ' => dropSpore
            case n if (1 until 8).contains(n.asDigit) => journey(n.asDigit)
        }
    }

    override def update(ev:GameEvent) {

    }
}
