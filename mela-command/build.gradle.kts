repositories {
  maven("https://dl.bintray.com/stunibert/maven")
}

dependencies {
  compile(Deps.guice)
  compile(Deps.intake)
  compile(project(":mela-event"))
}