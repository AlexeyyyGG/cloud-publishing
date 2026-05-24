plugins {
    java
    war
}

val springVersion = "6.2.9"

dependencies {
    implementation(project(":common"))
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-web:$springVersion")
    implementation("org.springframework:spring-webmvc:$springVersion")
    implementation("org.springframework:spring-jdbc:$springVersion")
    implementation("org.springframework.security:spring-security-web:6.3.1")
    implementation("org.springframework.security:spring-security-config:6.3.1")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.zaxxer:HikariCP:7.0.2")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
}