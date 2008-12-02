/*
 * CellFace.scala
 */

package org.nuttycombe.colorlife.ui

import java.awt.Graphics
import java.awt.Color

class CellFace(size: Int, cell: Game#Cell) {
    import cell._

    def draw(g:Graphics) = {
        g.setColor(Color.BLACK)
        g.drawRect(x * size, y * size, size, size)
        g.setColor(color)
        g.fillRect(x * size + 1, y * size + 1, size - 2, size - 2)

        //handle special cell type stuff
        cell match {
            case _ : ColonizeEarth#EarthCell => {
                    g.setColor(Color.LIGHT_GRAY)
                    g.drawRect(x * size, y * size, size, size)
            }
        }
    }
}
