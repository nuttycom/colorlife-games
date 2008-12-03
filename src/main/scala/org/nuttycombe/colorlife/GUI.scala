/*
 * GUI.scala
 */

package org.nuttycombe.colorlife

import java.awt._
import javax.swing._
import java.awt.event._

trait GUI[T <: Game[T]] extends UI[T] {
    type GameType = T
    type CellFaceType <: CellFace

    class CellFace(size:Int, cell:GameType#Cell) {
        import cell._

        def draw(g:Graphics) = {
            g.setColor(Color.BLACK)
            g.drawRect(x * size, y * size, size, size)
            g.setColor(color)
            g.fillRect(x * size + 1, y * size + 1, size - 2, size - 2)
        }
    }

    def createCellFace(size:Int, cell:GameType#Cell):CellFaceType

    class BoardComponent(game: GameType, cellSize: Int) extends Component {
        implicit def decorate(cell: game.Cell) = createCellFace(cellSize, cell);

        override def paint(g:Graphics) {
            game.cells.foreach(_.foreach(_.draw(g)))
        }
    }

    object frame extends JFrame("ColorLife Colonize Earth!")
    object board extends BoardComponent(game, 20)

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
                handleKeyTyped(ev)
            }
    });

    def handleKeyTyped(ev:KeyEvent) = {
        ev.getKeyChar match {
            case 'j' => moveLocDown
            case 'k' => moveLocUp
            case 'h' => moveLocLeft
            case 'l' => moveLocRight
            case '\r' => turnComplete
        }
    }
}
