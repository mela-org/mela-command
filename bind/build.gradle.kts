version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(Dependencies.guice)
    api(project(":core"))
}