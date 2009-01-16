/*
 * ColonizeEarth.scala
 */

package org.nuttycombe.colorlife.ce

object ColonizeEarth {
    def main(argv: Array[String]) {
        val game = new ColonizeEarthGame(33, 25, 3)
        val controller = new ColonizeEarthController(game) with ColonizeEarthGUI
        controller.start
    }
}

