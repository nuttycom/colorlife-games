/*
 * Color.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.nuttycombe.colorlife

object Colors {
    def blend(colors : Seq[java.awt.Color]) : java.awt.Color = {
        val components = colors.foldLeft(Array.make(3, 0.0f)) {(sum, c) =>
            sum(0) = sum(0) + c.getRed()
            sum(1) = sum(1) + c.getGreen()
            sum(2) = sum(2) + c.getBlue()
            sum
        }.map(_ / colors.size)

        new java.awt.Color(components(0), components(1), components(2));
    }
}
