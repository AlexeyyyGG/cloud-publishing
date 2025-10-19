val springVersion = "6.2.9"
plugins {
    id("java")
    id("application")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application{
    mainClass.set("Application")
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
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.7")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
}

tasks.test {
    useJUnitPlatform()
}