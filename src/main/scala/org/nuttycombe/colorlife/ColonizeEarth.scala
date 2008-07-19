/*
 * ColonizeEarth.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.nuttycombe.colorlife

import java.awt._

class ColonizeEarth(x:Int, y:Int, earthSize:Int) extends Game(x,y) {
    def buildInitialCell(i:Int, j:Int):Cell = new Cell(this, i, j, Color.BLACK)
}
