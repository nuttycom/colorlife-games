/*
 * CellFace.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.nuttycombe.colorlife.ui

import java.awt._

class CellFace(size:Int, cell:Cell) {

    def draw(g:Graphics) = {
        g.setColor(Color.BLACK)
        if (cell.x % 10 == 0 && cell.y % 10 == 0) cell.neighbors.foreach {_.color = Color.RED}
        g.drawRect(cell.x * size, cell.y * size, size, size)
        g.setColor(cell.color)
        g.fillRect(cell.x * size + 1, cell.y * size + 1, size - 2, size - 2)
    }

}
