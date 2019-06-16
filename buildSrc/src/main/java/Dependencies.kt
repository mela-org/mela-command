object Deps {
  const val discord4j = "com.discord4j:discord4j-core:${Versions.discord4j}"
  const val guice = "com.google.inject:guice:${Versions.guice}"
  const val guava = "com.google.guava:guava:${Versions.guava}"
  const val reactor_extra = "io.projectreactor.addons:reactor-extra"
  const val reactor_core = "io.projectreactor:reactor-core"
  const val classgraph = "io.github.classgraph:classgraph:${Versions.classgraph}"
  const val mbassador = "net.engio:mbassador:${Versions.mbassador}"
  const val intake = "com.github.stupremee:intake:${Versions.intake}"

  object Jackson {
    const val databind = "com.fasterxml.jackson.core:jackson-databind:${Versions.Jackson.databind}"

    object Dataformat {
      const val yaml = "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Versions.Jackson.Dataformat.yaml}"
    }
  }

  object Test {
    object JUnit {
      const val api = "org.junit.jupiter:junit-jupiter-api:${Versions.Test.JUnit.api}"
      const val engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.Test.JUnit.engine}"
    }

    const val assert4j = "org.assertj:assertj-core:${Versions.Test.assert4j}"
  }
}

object Versions {
  const val discord4j = "3.0.7"
  const val guice = "4.2.2"
  const val guava = "28.0-jre"
  const val reactor_bom = "Dysprosium-M2"
  const val classgraph = "4.8.41"
  const val mbassador = "1.3.2"
  const val intake = "4.2"

  object Jackson {
    const val databind = "2.9.9"

    object Dataformat {
      const val yaml = "2.9.9"
    }
  }

  object Test {
    object JUnit {
      const val api = "5.3.1"
      const val engine = "5.3.1"
    }

    const val assert4j = "3.11.1"
  }
}