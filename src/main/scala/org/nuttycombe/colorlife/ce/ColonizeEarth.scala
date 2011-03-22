/*
 * ColonizeEarth.scala
 */

package org.nuttycombe.colorlife
package ce

import java.lang.Math._
import java.awt.Color
import java.awt.event._
import javax.swing._
import javax.swing.JOptionPane
import scala.util.Random
import scala.annotation.tailrec

object ColonizeEarth extends Life2DToroidal with ColorLife with GUI {
  override val gameTitle = "ColorLife: Colonize Earth!"
  override val xsize = 41
  override val ysize = 35

  val earthSize = 3

  val xcenter = xsize/2.0f
  val ycenter = ysize/2.0f
  val earthHalfWidth = earthSize/2.0f

  def main(argv: Array[String]) {
    drawBoard
    play(getPlayers, Map())
  }

  def getPlayers : List[Player] = {
    val playerCount = JOptionPane.showInputDialog(board, "Choose a number of players").toInt
    val r = new Random

    def getPlayer(i: Int) = Player(
      JOptionPane.showInputDialog(board, "Player " + (i+1) + ":"),
      new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255))
    )

    (0 until playerCount).map(getPlayer).toList
  }

  @tailrec def play(players: List[Player], pop: Population): Population = {
    winner(players, pop) match {
      case Some(w) => pop
      case None => play(players, generation(cycle(players.map(v => (random, v)).sortBy(_._1).map(_._2), pop)))
    }
  }

  @tailrec def cycle(players: List[Player], pop: Population): Population = {
    players match {
      case p :: xs => cycle(xs, p.turn(pop))
      case Nil => pop
    }
  }

  val earthLocations: Set[Location] = {
    (
      for {
        x <- round(xcenter - earthHalfWidth) until round(xcenter + earthHalfWidth)
        y <- round(ycenter - earthHalfWidth) until round(ycenter + earthHalfWidth)
      } yield (x, y)
    ).toSet
  }

  def inExclusionZone(x: Int, y: Int) = abs(x - xcenter) < (earthHalfWidth + 1) && abs(y - ycenter) < (earthHalfWidth + 1)

  override def createCellFace(x: Int, y: Int) = if (earthLocations.contains((x, y))) new CellFace(x, y, Color.WHITE) else new CellFace(x, y)

  def winner(players: List[Player], pop: Population): Option[Player] = {
    val earthColors = pop.filterKeys(earthLocations).values.toSet
    if (earthColors.size == 1) players.find(_.color == earthColors.head) else None
  }

  case class Player(name: String, color: Color) {
    private var seeds : Int = 0
    private var journeyLength : Int = 0
    private var journeyRemaining : Int = 0

    def turn(pop: Population): Population = {
      if (journeyRemaining > 0) {
        journeyRemaining -= 1
        pop
      } else if (journeyLength > 0) {
        seeds += pow(2, journeyLength).toInt
        journeyLength = 0
        pop
      } else {
        var turnComplete = false
        var population = pop

        val keyListener = new KeyListener {
          def endTurn = {
            board.removeKeyListener(this)
            turnComplete = true
            Player.this.notifyAll
          }

          override def keyPressed(ev:KeyEvent)  = ()
          override def keyReleased(ev:KeyEvent) = ()
          override def keyTyped(ev:KeyEvent) = ev.getKeyChar match {
            case ' ' if seeds > 0 && !pop.contains((userx, usery)) && !inExclusionZone(userx, usery) =>
              seeds -= 1
              population = pop + ((userx, usery) -> color)

            case n if (1 until 8).contains(n.asDigit) => 
              journeyLength = n
              journeyRemaining = n
              endTurn

            case '\r' =>
              endTurn

            case _ => ()
          }
        }

        board.addKeyListener(keyListener)
        this.synchronized {
          while (!turnComplete) {
            this.wait
          }
        }

        population
      }
    }
  }
}
