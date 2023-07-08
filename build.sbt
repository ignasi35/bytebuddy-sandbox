import Dependencies._

ThisBuild / scalaVersion     := "2.13.11"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val all = Project(
  id = "bb-experiments",
  base = file("."),
)
  .settings(
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
    Compile/ packageDoc / publishArtifact := false
  )

lazy val agent =Project(
  id = "bb-agent-35",
  base = file("agent")
)
  .enablePlugins(AssemblyPlugin)
  .settings(
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
    // I am sober. no dependencies.
    Compile / packageBin := (agent / assembly).value
  )

lazy val sample =Project(
  id = "bb-sample-35",
  base = file("sample")
)
  .settings(
    mainClass := Some("example.Hello"),
  )
  .dependsOn(`agent-api`)
