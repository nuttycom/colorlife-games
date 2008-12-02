/*
 * UI.scala
 */

package org.nuttycombe.colorlife.ui
import org.nuttycombe.colorlife._

abstract class UI(val game:Game) {
    private var locx:Int = game.xsize / 2
    private var locy:Int = game.ysize / 2

    def moveLocDown = locx -= 1
    def moveLocUp = locx += 1
    def moveLocLeft = locy -= 1
    def moveLocRight = locy += 1
    def dropSpore = sendEvent(DropSporeEvent(locx, locy))
    def journey(length:Int) = sendEvent(JourneyEvent(length))
    def turnComplete = sendEvent(TurnCompleteEvent())

    def sendEvent(ev:GameEvent) = {

    }
}
