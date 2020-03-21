plugins {
    `java-library`
    checkstyle
}

group = "io.github.mela"
version = "1.0.0"

object Deps {
    const val guice = "com.google.inject:guice:4.2.3"
    const val guava = "com.google.guava:guava:28.2-jre"
    const val junitApi = "org.junit.jupiter:junit-jupiter-api:5.6.0"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:5.6.0"
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    compileOnly(Deps.guice)
    api(Deps.guava)
    testImplementation(Deps.junitApi)
    testImplementation(Deps.junitEngine)
    testImplementation(Deps.guice)
}

tasks {
    val sourceSets: SourceSetContainer by project

    val sourcesJar by registering(Jar::class) {
        dependsOn(JavaPlugin.CLASSES_TASK_NAME)
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    val javadocJar by registering(Jar::class) {
        dependsOn(JavaPlugin.JAVADOC_TASK_NAME)
        archiveClassifier.set("javadoc")
        from(javadoc)
    }

    artifacts {
        add("archives", javadocJar)
        add("archives", sourcesJar)
    }

    build {
        dependsOn(jar)
        dependsOn(sourcesJar)
        dependsOn(javadocJar)

        jar.get().mustRunAfter(clean)
        sourcesJar.get().mustRunAfter(jar)
    }

    test {
        useJUnitPlatform()
    }

    checkstyle {
        checkstyleTest.get().enabled = false
        toolVersion = "8.19"
    }

    checkstyleMain {
        configFile = file("$rootDir/config/checkstyle/google_checks.xml")
        configProperties = mapOf("config_loc" to "${rootProject.projectDir}/config/checkstyle")
    }

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

