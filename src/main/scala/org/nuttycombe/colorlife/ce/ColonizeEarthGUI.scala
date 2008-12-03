/*
 * ColonizeEarthGUI.scala
 */

package org.nuttycombe.colorlife.ce

import colorlife._
import java.awt.Graphics
import java.awt.Color
import java.awt.event._

trait ColonizeEarthGUI extends ColonizeEarthUI with GUI[ColonizeEarthGame] {
    class ColonizeEarthCellFace(size:Int, cell:ColonizeEarthGame#Cell) extends CellFace[ColonizeEarthGame](size, cell) {
        import cell._

        override def draw(g:Graphics) = {
            //handle special cell type stuff
            cell match {
                case _ : ColonizeEarthGame#EarthCell => {
                        g.setColor(Color.LIGHT_GRAY)
                        g.drawRect(x * size, y * size, size, size)
                    }
            }
        }
    }

    override def handleKeyTyped(ev:KeyEvent) = {
        super.handleKeyTyped(ev)
        ev.getKeyChar match {
            case ' ' => dropSpore
            case n if (1 until 8).contains(n.asDigit) => journey(n.asDigit)
        }
    }

    override def update(ev:UIEvent) {

    }
}
