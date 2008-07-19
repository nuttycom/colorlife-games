/*
 * Board.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.nuttycombe.colorlife

import java.awt._
import scala.collection.jcl._

abstract class Game(val x:Int, val y:Int) {
    val cells = new Array[Array[Cell]](x,y)
    var cellsToEvaluate = new HashSet[Cell]()

    for (i <- 0 until x) {
        for (j <- 0 until y) {
            cells(i)(j) = buildInitialCell(i,j)
        }
    }

    def buildInitialCell(i:Int, j:Int):Cell

    def applyLifeRule = {
        cellsToEvaluate.foreach {cell =>


        }
    }


}
