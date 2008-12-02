/*
 * GUI.scala
 */

package org.nuttycombe.colorlife.ui

import java.awt._
import javax.swing._
import java.awt.event._

trait GUI extends UI {
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

    board.addKeyListener(new KeyListener {
            override def keyPressed(ev:KeyEvent) = {}
            override def keyReleased(ev:KeyEvent) = {}

            override def keyTyped(ev:KeyEvent) = {
                ev.getKeyChar match {
                    case 'j' => moveLocDown
                    case 'k' => moveLocUp
                    case 'h' => moveLocLeft
                    case 'l' => moveLocRight
                    case ' ' => dropSpore
                    case n if (1 until 8).contains(n.asDigit) => journey(n.asDigit)
                    case '\r' => turnComplete
                }
            }
    });

}
