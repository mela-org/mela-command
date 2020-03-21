version = "1.0.0"

dependencies {
    api(Dependencies.guice)
    compileOnly(project(":bind"))
    compileOnly(project(":provided"))
}
