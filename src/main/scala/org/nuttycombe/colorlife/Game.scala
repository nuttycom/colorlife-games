/*
 * Board.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.nuttycombe.colorlife


class Game(x:Int, y:Int) {
  val cells = new Array[Cell](x * y)
  
  for (i <- 0 until x) {
      for (j <- 0 until y) {
          cells(i * x + j) = new Cell(x, y, null)
      }
  }


}
