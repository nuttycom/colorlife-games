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
        g.drawRect(cell.x * size, cell.y * size, size, size)
    }

}
