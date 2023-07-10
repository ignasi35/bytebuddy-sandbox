import Dependencies._

lazy val scala213 = "2.13.11"
lazy val scala212 = "2.12.18"
lazy val supportedScalaVersions = List(scala212, scala213)

ThisBuild / scalaVersion     := scala212
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val all = Project(
  id = "bb-experiments",
  base = file("."),
)
  .settings(
    // crossScalaVersions must be set to Nil on the aggregating project
    crossScalaVersions := Nil,
    publish / skip := true
  )
  .aggregate(
    `agent-api`,
    agent,
    `uber-agent`,
    sample
  )

lazy val `agent-api` =Project(
  id = "bb-agent-api-35",
  base = file("agent-api")
)
  .settings(
    // TODO: the agent should not be cross-built. It's pure Java
    crossScalaVersions := supportedScalaVersions,
    Compile/ packageDoc / publishArtifact := false
  )

lazy val agent =Project(
  id = "bb-agent-35",
  base = file("agent")
)
  .enablePlugins(AssemblyPlugin)
  .settings(
    // TODO: the agent should not be cross-built. It's pure Java
    crossScalaVersions := supportedScalaVersions,
  libraryDependencies += "net.bytebuddy" % "byte-buddy" % "1.14.5" ,
//  libraryDependencies += (ThisBuild / organization).value % (sample.id) % (ThisBuild / version).value ,
 Compile / packageBin / packageOptions += Package.ManifestAttributes("Premain-Class" -> "example.BBMain"),
 Compile / packageBin / packageOptions += Package.ManifestAttributes("Can-Redefine-Classes" -> "true"),
 Compile / packageBin / packageOptions += Package.ManifestAttributes("Can-Retransform-Classes" -> "true"),
    Compile/ packageDoc / publishArtifact := false
)
  .dependsOn(`agent-api`)
  .dependsOn(`sample` % "compile")

lazy val `uber-agent` = project
  .settings(
    name := "bb-uber-agent-35",
    // TODO: the agent should not be cross-built. It's pure Java
    crossScalaVersions := supportedScalaVersions,
    // I am sober. no dependencies.
    Compile / packageBin := (agent / assembly).value
  )

lazy val sample =Project(
  id = "bb-sample-35",
  base = file("sample")
)
  .settings(
    crossScalaVersions := supportedScalaVersions,
    mainClass := Some("example.Hello"),
  )
  .dependsOn(`agent-api`)
