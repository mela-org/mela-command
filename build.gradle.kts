plugins {
    `java-library`
    checkstyle
    `maven-publish`
}

group = "io.github.mela"
version = "1.0.0"

subprojects {

    group = "io.github.mela"

    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
    apply(plugin = "checkstyle")

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        testImplementation(Dependencies.junitApi)
        testImplementation(Dependencies.junitEngine)
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

    publishing {
        publications {
            create<MavenPublication>(name) {
                groupId = "io.github.mela"
                artifactId = "mela-command-$name"
                version = version

                from(components["java"])
            }
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}



