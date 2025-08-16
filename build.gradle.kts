plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("io.freefair.lombok") version "8.14"
}

group = "pl.teksusik"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("Kick4j")
                description.set("Java library for interaction with Kick.com streaming platform API")
            }
        }
    }

    repositories {
        maven {
            name = "reposilite"
            url = uri(
                "https://repo.teksusik.pl/${
                    if (version.toString().endsWith("-SNAPSHOT")) "snapshots" else "releases"
                }"
            )
            credentials {
                username = findProperty("reposiliteUser") as String? ?: System.getenv("REPOSILITE_USER")
                password = findProperty("reposiliteToken") as String? ?: System.getenv("REPOSILITE_TOKEN")
            }
        }
    }
}