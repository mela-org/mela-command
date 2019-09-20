plugins {
  `java-library`
  pmd
  checkstyle
  id("com.github.spotbugs") version "1.7.1"
  id("io.spring.dependency-management") version "1.0.7.RELEASE"
}

group = "com.github.stupremee"
version = "1.0.0"

subprojects {
  apply(plugin = "java-library")
  apply(plugin = "pmd")
  apply(plugin = "checkstyle")
  apply(plugin = "com.github.spotbugs")
  apply(plugin = "io.spring.dependency-management")

  group = "com.github.stupremee"
  version = "1.0.0"

  repositories {
    maven("https://repo.spring.io/milestone")
    jcenter()
    mavenCentral()
  }

  dependencyManagement {
    imports {
      mavenBom("io.projectreactor:reactor-bom:${Versions.reactor_bom}")
    }
  }

  dependencies {
    spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.9.0")
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
      ruleSetConfig = this@subprojects.resources.text.fromFile(file("${rootProject.projectDir}/config/pmd/ruleset.xml"))
    }
  }

  configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}
