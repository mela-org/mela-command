plugins {
  `java-library`
  pmd
  checkstyle
  id("com.github.spotbugs") version "1.7.1"
}

group = "com.github.stupremee"
version = "1.0.0"

subprojects {
  apply(plugin = "java-library")
  apply(plugin = "pmd")
  apply(plugin = "checkstyle")
  apply(plugin = "com.github.spotbugs")

  group = "com.github.stupremee"
  version = "1.0.0"

  repositories {
    jcenter()
    mavenCentral()
  }

  dependencies {
    implementation("com.discord4j:discord4j-core:3.0.4")

    implementation("com.google.inject:guice:4.2.2")
    implementation("com.google.guava:guava:27.1-jre")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation("org.assertj:assertj-core:3.11.1")

    spotbugs("com.h3xstream.findsecbugs:findsecbugs-plugin:1.9.0")
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

    val fatJar by registering(Jar::class) {
      dependsOn(jar)
      archiveClassifier.set("shaded")
      duplicatesStrategy = DuplicatesStrategy.EXCLUDE
      from(configurations.runtimeClasspath.get()
          .onEach { println("Add from dependencies: ${it.name}") }
          .map { if (it.isDirectory) it else zipTree(it) })
      val sourcesMain = sourceSets.main.get()
      sourcesMain.allSource.forEach { println("Add from sources: ${it.name}") }
      from(sourcesMain.output)
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

    spotbugs {
      toolVersion = "4.0.0-beta1"
    }

    spotbugsMain {
      reports {
        html.isEnabled = true
        xml.isEnabled = false
      }
    }

    pmd {
      pmdTest.get().enabled = false
    }

    pmdMain {
      ignoreFailures = true
      ruleSetConfig = this@subprojects.resources.text.fromFile(file("$projectDir/config/pmd/ruleset.xml"))
    }
  }

  configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
  }
}
