/*
 * BoardComponent.scala
 */

package org.nuttycombe.colorlife.ui

import java.awt._
import javax.swing._
import java.awt.event._

class BoardComponent(val game:Game, val cellSize:Int) extends Component {
    implicit def decorate(cell: game.Cell):CellFace = new CellFace(cellSize, cell);

    override def paint(g:Graphics) {
        game.cells.foreach(_.foreach(_.draw(g)))
    }
}
