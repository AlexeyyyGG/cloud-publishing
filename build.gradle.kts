val springVersion = "6.2.9"
plugins {
    id("war")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-web:$springVersion")
    implementation("org.springframework:spring-webmvc:$springVersion")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    compileOnly("jakarta.servlet:jakarta.servlet-api:5.0.0")
}

tasks.test {
    useJUnitPlatform()
}