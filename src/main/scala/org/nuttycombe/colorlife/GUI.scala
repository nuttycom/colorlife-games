/*
 * GUI.scala
 */

package org.nuttycombe.colorlife

import java.awt._
import javax.swing._
import java.awt.event._

trait GUI[T <: Game[T]] extends Controller[T] {
    type GameType = T
    type CellFaceType <: CellFace

    object frame extends JFrame("ColorLife Colonize Earth!")
    object board extends BoardComponent(game)

    frame.getContentPane.add(board)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.addWindowListener(new WindowAdapter() {
        override def windowOpened(e:WindowEvent) {
            frame.setExtendedState(Frame.MAXIMIZED_BOTH)
        }
    });

    //add handlers for key events
    board.addKeyListener(new KeyListener {
            override def keyPressed(ev:KeyEvent) {}
            override def keyReleased(ev:KeyEvent) {}

            override def keyTyped(ev:KeyEvent) {
                handleKeyTyped(ev)
            }
    });

    //Display the window.
    frame.pack();
    frame.setVisible(true);

    class BoardComponent(game: GameType) extends JComponent {
        override def paint(g:Graphics) {
            val xsize = (frame.getWidth - 20) / game.xsize
            val ysize = (frame.getHeight - 20) / game.ysize
            implicit def decorate(cell: game.Cell) = createCellFace(xsize, ysize, cell);

            game.cells.foreach(_.foreach(_.draw(g)))
        }
    }

    class CellFace(xsize: Int, ysize: Int, cell: GameType#Cell) {
        import cell._
        val xloc = x * xsize
        val yloc = y * ysize

        def draw(g:Graphics) {
            drawCell(g, frame.getBackground, cell.color)
        }

        def drawCell(g: Graphics, outline : Color, fill : Color) {
            g.setColor(outline)
            g.drawRoundRect(xloc, yloc, xsize - 1, ysize - 1, 3, 3)
            g.setColor(fill)
            g.fillRoundRect(xloc + 1, yloc + 1, xsize - 2, ysize - 2, 3, 3)
        }
    }

    def createCellFace(xsize: Int, ysize: Int, cell: GameType#Cell): CellFaceType
    def handleKeyTyped(ev:KeyEvent) {
        ev.getKeyChar match {
            case 'j' => changeActiveCell(moveLocDown _)
            case 'k' => changeActiveCell(moveLocUp _)
            case 'h' => changeActiveCell(moveLocLeft _)
            case 'l' => changeActiveCell(moveLocRight _)
            case '\r' => turnComplete
        }
    }

    private def changeActiveCell(f: () => Unit) {
        f()
        board.repaint()
    }
}
