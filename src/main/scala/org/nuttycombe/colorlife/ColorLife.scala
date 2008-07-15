/*
 * ColorLife.scala
 */

package org.nuttycombe.colorlife

import javax.swing._

object ColorLife extends Application {
    //Create and set up the window.
    var frame:JFrame = new JFrame("HelloWorldSwing");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Add the ubiquitous "Hello World" label.
    var label:JLabel = new JLabel("Hello World");
    frame.getContentPane().add(label);
    
    //Display the window.
    frame.pack();
    frame.setVisible(true);
}
