/*
 * BoardComponent.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.nuttycombe.colorlife.ui

import java.awt._
import javax.swing._
import java.awt.event._

class BoardComponent(game:Game, var cellSize:Int) extends Component {
    implicit def wrapCell(cell:Cell) = new CellFace(cellSize, cell);

    override def paint(g:Graphics) {
        game.cells.foreach {cell =>
            cell.draw(g)
        }
    }
}
