import sbt._

class ColorLife(info: ProjectInfo) extends DefaultProject(info) with ProguardProject {
  override def mainClass = Some("org.nuttycombe.colorlife.ce.ColonizeEarth")
  override def proguardInJars = super.proguardInJars +++ scalaLibraryPath
  override def proguardOptions = List(
    proguardKeepMain("org.nuttycombe.colorlife.ce.ColonizeEarth")
  )
}


// vim: set ts=4 sw=4 et:
