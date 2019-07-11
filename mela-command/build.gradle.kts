repositories {
  jcenter()
}

dependencies {
  api(Deps.guice)
  implementation(Deps.guava)
  testImplementation(Deps.Test.JUnit.api)
}