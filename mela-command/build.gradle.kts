repositories {
  maven("http://maven.sk89q.com/repo/")
}

dependencies {
  compile(Deps.guice)
  compile(Deps.intake)
  compile(project(":mela-event"))
}