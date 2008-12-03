/*
 * ColonizeEarthUI.scala
 */

package org.nuttycombe.colorlife.ce

import colorlife._
import java.awt._
import java.awt.event._

object ColonizeEarthUI extends Application {
    val game = new ColonizeEarthGame(33, 25, 3)
    val ui = new ColonizeEarthUI(game) with ColonizeEarthGUI
}

class ColonizeEarthUI(game: ColonizeEarthGame) extends UI[ColonizeEarthGame](game) {
    def dropSpore = doEvent(DropSporeEvent(locx, locy))
    def journey(length:Int) = doEvent(JourneyEvent(length))

    override def update(ev:UIEvent) = {

    }
}
