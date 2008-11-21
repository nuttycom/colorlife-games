/*
 * GUI.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.nuttycombe.colorlife.ui
import org.nuttycombe.colorlife._

import java.awt._
import javax.swing._
import java.awt.event._

class GUI(game:Game) extends UI(game) {
    val frame:JFrame = new JFrame("ColorLife Colonize Earth!")
    val board:BoardComponent = new BoardComponent(game, 20)

    frame.add(board)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.addWindowListener(new WindowAdapter() {
        override def windowOpened(e:WindowEvent) = frame.setExtendedState(Frame.MAXIMIZED_BOTH)
    });

    //Display the window.
    frame.pack();
    frame.setVisible(true);
}
