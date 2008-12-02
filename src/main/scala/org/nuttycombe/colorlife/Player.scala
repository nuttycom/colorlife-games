/*
 * Player.scala
 */

package org.nuttycombe.colorlife

import java.awt._

case class Player(name:String, color:Color) {
    var seeds : Int = 0
    var journeyRemaining : Int = 0
}
