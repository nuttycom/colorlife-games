/*
 * GUI.scala
 */

package org.nuttycombe.colorlife

import java.awt._
import java.awt.event._
import javax.swing._

trait GUI {
  val gameTitle: String
  val xsize: Int
  val ysize: Int

  lazy val cellFaces = (for (x <- 0 to xsize; y <- 0 to ysize) yield ((x, y), createCellFace(x, y))).toMap

  object frame extends JFrame(gameTitle)
  object board extends JComponent {
    override def paint(g:Graphics) = for (face <- cellFaces.values) {
      face.draw(g)
    }
  }

  def drawBoard = {
    frame.getContentPane.add(board)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.addWindowListener(
      new WindowAdapter() {
        override def windowOpened(e: WindowEvent) = frame.setExtendedState(Frame.MAXIMIZED_BOTH)
      }
    )

    //add handlers for key events
    frame.addKeyListener(navigationListener)

    //Display the window.
    frame.pack()
    frame.setVisible(true)
  }

  private var _userx: Int = xsize / 2
  private var _usery: Int = ysize / 2
  def userx = _userx
  def usery = _usery

  def cellWidth = (frame.getWidth - 20) / xsize
  def cellHeight = (frame.getHeight - 20) / ysize

  class CellFace(val x: Int, val y: Int, var color: Color = Color.BLACK) {
    val borderColor: Color = frame.getBackground

    def draw(g:Graphics) = drawCell(g, borderColor, color)

    final def drawCell(g: Graphics, outline : Color, fill : Color) {
      val xloc = x * cellWidth
      val yloc = y * cellHeight
      val highlighted = x == userx && y == usery

      g.setColor(if (highlighted) fill else outline)
      g.drawRoundRect(xloc, yloc, cellWidth - 1, cellHeight - 1, 3, 3)
      g.setColor(if (highlighted) outline else fill)
      g.fillRoundRect(xloc + 1, yloc + 1, cellWidth - 2, cellHeight - 2, 3, 3)
    }
  }

  def createCellFace(x: Int, y: Int): CellFace

  val navigationListener = new KeyListener {
    override def keyPressed(ev:KeyEvent)  = ()
    override def keyReleased(ev:KeyEvent) = ()
    override def keyTyped(ev:KeyEvent) = ev.getKeyChar match {
      case 'j'  => refresh(_usery = (usery + 1) % ysize)
      case 'k'  => refresh(_usery = (usery - 1) % ysize)
      case 'h'  => refresh(_userx = (userx - 1) % xsize)
      case 'l'  => refresh(_userx = (userx + 1) % xsize)
      case _ => ()
    }
  }

  def refresh[T](f: => T): T = {
    val result = f;
    board.repaint()
    result
  }
}
