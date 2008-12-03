/*
 * UI.scala
 */

package org.nuttycombe.colorlife

abstract class UI[T <: Game[T]](val game:T) {
    trait UIEvent

    protected var locx:Int = game.xsize / 2
    protected var locy:Int = game.ysize / 2
    private var eventHandlers : List[PartialFunction[GameEvent,UIEvent]] = Nil

    def moveLocDown = locx -= 1
    def moveLocUp = locx += 1
    def moveLocLeft = locy -= 1
    def moveLocRight = locy += 1
    def turnComplete = doEvent(TurnCompleteEvent())

    def addHandler(f:PartialFunction[GameEvent,UIEvent]) {
        eventHandlers = f :: eventHandlers
    }

    def doEvent(ev:GameEvent) {
        eventHandlers.map(_.andThen(update)(ev))
    }

    def update(ev:UIEvent)
}
