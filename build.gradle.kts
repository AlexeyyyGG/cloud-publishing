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
    implementation("org.springframework.security:spring-security-web:6.3.1")
    implementation("org.springframework.security:spring-security-config:6.3.1")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.7")
    implementation("com.zaxxer:HikariCP:7.0.2")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:2.0.0")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}