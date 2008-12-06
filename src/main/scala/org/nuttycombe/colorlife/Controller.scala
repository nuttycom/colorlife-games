/*
 * UI.scala
 */

package org.nuttycombe.colorlife

abstract class Controller[T <: Game[T]](val game:T) {
    import game._

    protected var locx:Int = game.xsize / 2
    protected var locy:Int = game.ysize / 2
    private var eventHandlers : List[PartialFunction[ControllerEvent,Option[GameEvent]]] = Nil

    def moveLocDown = locx -= 1
    def moveLocUp = locx += 1
    def moveLocLeft = locy -= 1
    def moveLocRight = locy += 1
    def turnComplete = doEvent(TurnCompleteEvent())

    def addHandler(f:PartialFunction[ControllerEvent, Option[GameEvent]]) {
        eventHandlers = f :: eventHandlers
    }

    def doEvent(ev:ControllerEvent) {
        eventHandlers.foreach(f =>
            if (f.isDefinedAt(ev)) {
                f(ev).map(update(_))
            }
        )
    }

    def update(ev:GameEvent)

    def start
}
