/*
 * Color.scala
 */

package org.nuttycombe.colorlife

object Colors {
    def blend(colors : Seq[java.awt.Color]) = {
        val rgb = colors.foldLeft((0.0f, 0.0f, 0.0f)) {(sum, c) =>
            (sum._1 + c.getRed(), sum._2 + c.getGreen(), sum._3 + c.getBlue())
        }

        new java.awt.Color(rgb._1 / colors.size, rgb._2 / colors.size, rgb._3 / colors.size)
    }
}
