/*
 * ColonizeEarthUI.scala
 */

package org.nuttycombe.colorlife.ce

import colorlife._
import java.awt._
import java.awt.event._

abstract class ColonizeEarthUI(game: ColonizeEarthGame) extends UI[ColonizeEarthGame](game) {
    def dropSpore = doEvent(DropSporeEvent(locx, locy))
    def journey(length:Int) = doEvent(JourneyEvent(length))
}
