import sbt._, Keys._
import com.typesafe.tools.mima.plugin.MimaPlugin
import com.typesafe.tools.mima.plugin.MimaKeys._

object MimaSettings {

  // TODO: Enable this after 3.5.0 release
  // val previousVersions = Set(0).map(patch => s"3.5.$patch")
  val previousVersions: Set[String] = Set.empty

  val mimaSettings = MimaPlugin.mimaDefaultSettings ++ Seq(
    previousArtifacts := {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, scalaMajor)) if scalaMajor <= 11 =>
          previousVersions.map { organization.value % s"${name.value}_${scalaBinaryVersion.value}" % _ }
        case _ => Set.empty
      }
    },
    test in Test := {
      reportBinaryIssues.value
      (test in Test).value
    }
  )

}
