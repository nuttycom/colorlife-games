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

  @tailrec def play(players: List[Player], pop: Population): Unit = {
    winner(players, pop) match {
      case Some(w) =>
        if (JOptionPane.showConfirmDialog(board, "Player " + w.name + " has won! Play again?") == JOptionPane.OK_OPTION) {
          play(players, cycle(players.map(v => (random, v)).sortBy(_._1).map(_._2), updateCells(Map())))
        }

      case None    =>
        play(players, cycle(players.map(v => (random, v)).sortBy(_._1).map(_._2), pop))
    }
  }

  @tailrec def cycle(players: List[Player], pop: Population): Population = {
    players match {
      case p :: xs => cycle(xs, p.turn(pop))
      case Nil     => updateCells(generation(pop))
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

  def inExclusionZone(x: Int, y: Int) = {
    val exclusionHalfWidth = earthHalfWidth + 1
    (xcenter - exclusionHalfWidth <= x && xcenter + exclusionHalfWidth > x) &&
    (ycenter - exclusionHalfWidth <= y && ycenter + exclusionHalfWidth > y)
  }

  def defaultColor(x: Int, y: Int) = if (earthLocations.contains((x, y))) Color.WHITE else Color.BLACK

  override def createCellFace(x: Int, y: Int) = new CellFace(x, y, defaultColor(x, y))

  def updateCells(pop: Population): Population = refresh({
    for ((loc @ (x, y), face) <- cellFaces) face.color = pop.getOrElse(loc, defaultColor(x, y))
    pop
  })

  def winner(players: List[Player], pop: Population): Option[Player] = {
    val earthColors = pop.filterKeys(earthLocations).values.toSet
    if (earthColors.size == 1 && earthLocations.count(pop.contains(_)) > (earthSize * earthSize * 0.66)) players.find(_.color == earthColors.head) else None
  }

  case class Player(name: String, color: Color) {
    private var seeds : Int = 0
    private var journeyLength : Int = 0
    private var journeyRemaining : Int = 0

    def turn(pop: Population): Population = {
      if (journeyRemaining > 0) {
        JOptionPane.showMessageDialog(board, name + " is on a journey and has " + journeyRemaining + " turns left before it gets back.")
        journeyRemaining -= 1
        pop
      } else if (journeyLength > 0) {
        seeds += pow(2, journeyLength).toInt
        journeyLength = 0
        JOptionPane.showMessageDialog(board, name + " is back from its journey and has " + seeds + " spores to deploy!")
        interact(pop)
      } else {
        JOptionPane.showMessageDialog(board, "Starting turn for player " + name + " with " + seeds + " spores available.")
        interact(pop)
      }
    }

    def interact(pop: Population): Population = {
        var turnComplete = false
        var population = pop

        val keyListener = new KeyListener {
          def endTurn = {
            frame.removeKeyListener(this)
            Player.this.synchronized {
              turnComplete = true
              Player.this.notifyAll
            }
          }

          override def keyPressed(ev:KeyEvent)  = ()
          override def keyReleased(ev:KeyEvent) = ()
          override def keyTyped(ev:KeyEvent) = ev.getKeyChar match {
            case ' ' =>
              if (seeds > 0) {
                if (!population.contains((userx, usery)) && !inExclusionZone(userx, usery)) {
                  seeds -= 1
                  population = population + ((userx, usery) -> color)
                  refresh(cellFaces(userx, usery).color = color)
                }
              } else {
                JOptionPane.showMessageDialog(board, "You don't have any seeds, better go find some!")
              }

            case n if n.isDigit =>
              journeyLength = n.asDigit
              journeyRemaining = n.asDigit
              endTurn

            case '\n' | '\r' =>
              if (JOptionPane.showConfirmDialog(board, "Done with your turn?", "Turn Complete!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                endTurn
              }

            case x => ()
          }
        }

        frame.addKeyListener(keyListener)
        this.synchronized {
          while (!turnComplete) Player.this.wait;
        }

        population
    }
  }
}
