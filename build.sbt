name := "monocledemo"

scalaVersion := "2.11.8"

val monocleVersion = "1.4.0"

libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %%  "monocle-core"  % monocleVersion,
  "com.github.julien-truffaut" %%  "monocle-macro" % monocleVersion,
  "com.github.julien-truffaut" %%  "monocle-state" % monocleVersion,
  "com.github.julien-truffaut" %%  "monocle-law" % monocleVersion
)
