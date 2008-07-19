/*
 * Board.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.nuttycombe.colorlife

import java.awt._

class Game(x:Int, y:Int, earthSize:Int) {
    val cells = new Array[Array[Cell]](x,y)

    for (i <- 0 until x) {
        for (j <- 0 until y) {
            cells(i)(j) = new Cell(i, j, Color.BLACK)
        }
    }

    

}
