/*
 * ColonizeEarth.scala
 */

package org.nuttycombe.colorlife.ce

object ColonizeEarth extends Application {
    val game = new ColonizeEarthGame(33, 25, 3)
    val ui = new ColonizeEarthUI(game) with ColonizeEarthGUI
}

