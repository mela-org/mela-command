repositories {
  maven("http://maven.sk89q.com/repo/")
  maven("https://dl.bintray.com/stunibert/maven")
  jcenter()
}

dependencies {
  compile(Deps.guice)
  compile(Deps.guava)
  compile(Deps.Test.JUnit.api)
}