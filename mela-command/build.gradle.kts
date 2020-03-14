repositories {
  jcenter()
}

dependencies {
  api(Deps.guice)
  implementation(Deps.guava)
  testImplementation(Deps.Test.JUnit.api)
  testImplementation(Deps.Test.JUnit.engine)
}