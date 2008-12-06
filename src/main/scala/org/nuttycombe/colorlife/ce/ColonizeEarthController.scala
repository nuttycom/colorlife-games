/*
 * ColonizeEarthController.scala
 */

package org.nuttycombe.colorlife.ce

import colorlife._
import java.awt._
import java.awt.event._

abstract class ColonizeEarthController(game: ColonizeEarthGame) extends Controller[ColonizeEarthGame](game) {
    import game._

    def dropSpore = doEvent(DropSporeEvent(locx, locy))
    def journey(length:Int) = doEvent(JourneyEvent(length))

    def getPlayers : Seq[Player]

    override def start = game.bind(this)
}
