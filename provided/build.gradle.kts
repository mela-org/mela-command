version = "1.0.0"

dependencies {
    compileOnly(Dependencies.guice)
    compileOnly(Dependencies.slf4jApi)
    api(project(":bind"))
}