import ScalafixBuild.autoImport.isScala2
import sbt.Keys.scalaVersion
import sbt._

import scala.util.Try

/* scalafmt: { maxColumn = 120 }*/

object Dependencies {
  val scala212 = sys.props.getOrElse("scala212.nightly", "2.12.18")
  val scala213 = sys.props.getOrElse("scala213.nightly", "2.13.12")
  val scala3 = sys.props.getOrElse("scala3.nightly", "3.3.1")

  val buildScalaVersions = Seq(scala212, scala213, scala3)
  val buildWithTargetVersions: Seq[(String, String)] = {
    val all = buildScalaVersions.map(sv => (sv, sv)) ++
      Seq(scala213, scala212).flatMap(sv => previousVersions(sv).map(prev => (sv, prev))) ++
      Seq(scala213, scala212).map(sv => (sv, scala3))

    all.filter {
      case (_, v) if System.getProperty("java.version") == "21" =>
        !Seq("2.12.16", "2.12.17", "2.13.10").contains(v)
      case _ =>
        true
    }
  }

  val bijectionCoreV = "0.9.7"
  val collectionCompatV = "2.11.0"
  val coursierV = "2.1.8"
  val coursierInterfaceV = "1.0.19"
  val commontTextV = "1.11.0"
  val googleDiffV = "1.3.0"
  val java8CompatV = "1.0.2"
  val jgitV = "5.13.3.202401111512-r"
  val metaconfigV = "0.12.0"
  val nailgunV = "0.9.1"
  val scalaXmlV = "2.2.0"
  val scalametaV = "4.8.15"
  val scalatestV = "3.2.17"
  val munitV = "0.7.29"

  // scala-steward:off

  // Now shaded in dependencies, kept here only for backward compatbility
  val pprintV = "0.6.6"
  val scalametaFastparseV = "2.3.1"
  val genyV = "0.6.5"

  // scala-steward:on

  val bijectionCore = "com.twitter" %% "bijection-core" % bijectionCoreV
  val collectionCompat = "org.scala-lang.modules" %% "scala-collection-compat" % collectionCompatV
  val commonText = "org.apache.commons" % "commons-text" % commontTextV
  val coursier = ("io.get-coursier" %% "coursier" % coursierV)
    .cross(CrossVersion.for3Use2_13)
  val coursierInterfaces = "io.get-coursier" % "interface" % coursierInterfaceV
  val googleDiff = "com.googlecode.java-diff-utils" % "diffutils" % googleDiffV
  val java8Compat = "org.scala-lang.modules" %% "scala-java8-compat" % java8CompatV
  val jgit = "org.eclipse.jgit" % "org.eclipse.jgit" % jgitV
  val metaconfig = "com.geirsson" %% "metaconfig-typesafe-config" % metaconfigV
  val pprint = "com.lihaoyi" %% "pprint" % pprintV
  val metaconfigDoc = "com.geirsson" %% "metaconfig-docs" % metaconfigV
  val metacp = "org.scalameta" %% "metacp" % scalametaV
  val nailgunServer = "com.martiansoftware" % "nailgun-server" % nailgunV
  val scalaXml = "org.scala-lang.modules" %% "scala-xml" % scalaXmlV
  // https://github.com/scalameta/scalameta/issues/2485
  val scalameta = ("org.scalameta" %% "scalameta" % scalametaV)
    .cross(CrossVersion.for3Use2_13)
  val scalametaTeskit = ("org.scalameta" %% "testkit" % scalametaV)
    .cross(CrossVersion.for3Use2_13)
  val scalametaFastparse = "org.scalameta" %% "fastparse-v2" % scalametaFastparseV
  val geny = "com.lihaoyi" %% "geny" % genyV
  val scalatest = "org.scalatest" %% "scalatest" % scalatestV
  val munit = "org.scalameta" %% "munit" % munitV
  val semanticdbScalacCore = "org.scalameta" % "semanticdb-scalac-core" % scalametaV cross CrossVersion.full

  private def previousVersions(scalaVersion: String): Seq[String] = {
    val split = scalaVersion.split('.')
    val binaryVersion = split.take(2).mkString(".")
    val compilerVersion = Try(split.last.toInt).toOption
    val previousPatchVersions =
      compilerVersion.map(version => List.range(version - 2, version).filter(_ >= 0)).getOrElse(Nil)
    previousPatchVersions.map(v => s"$binaryVersion.$v")
  }
}
