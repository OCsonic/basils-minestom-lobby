plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "com.basil.lobby"
version = "0.1.0a"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom-snapshots:0ca1dda2fe")
    implementation("com.google.code.gson:gson:+")
    implementation("com.google.guava:guava:+")
    implementation("org.reflections:reflections:+")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    shadowJar {
        manifest {
            attributes["Main-Class"] = "com.basil.lobby.Server"
        }
        mergeServiceFiles()
        archiveClassifier.set("")
        archiveVersion.set(project.version.toString())
    }

    test {
        useJUnitPlatform()
    }

    build {
        dependsOn(shadowJar)
    }
}
