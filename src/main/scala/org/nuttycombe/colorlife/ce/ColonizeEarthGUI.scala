/*
 * ColonizeEarthGUI.scala
 */

package org.nuttycombe.colorlife.ce

import colorlife._
import java.awt.Graphics
import java.awt.Color
import java.awt.event._
import javax.swing.JOptionPane
//import scala.swing._
import scala.util.Random

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
            case _ =>
        }
    }

    override def update(ev:GameEvent) {

    }

    override def getPlayers : Seq[Player] = {
        val playerCount = JOptionPane.showInputDialog(board, "Choose a number of players").toInt
        val r = new Random

        def getPlayer(i:Int) : Player = {
            Player(JOptionPane.showInputDialog(board, "Player " + (i+1) + ":"),
                   new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)))
        }

        (0 until playerCount).map(getPlayer(_)).force //force evaluation
    }
}
