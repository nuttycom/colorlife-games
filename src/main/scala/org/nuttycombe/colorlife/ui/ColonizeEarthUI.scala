/*
 * ColonizeEarthUI.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.nuttycombe.colorlife.ui

import java.awt._
import java.awt.event._

object ColonizeEarthUI extends Application {
    val game = new ColonizeEarth(33, 25, 3)
    val ui = new ColonizeEarthUI(game) with GUI
}

class ColonizeEarthUI(game: ColonizeEarth) extends UI(game) {

}
