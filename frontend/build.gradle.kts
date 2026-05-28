plugins {
    java
}

val springVersion = "6.2.9"

dependencies {
    implementation(project(":common"))
    implementation(project(":model"))
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-web:$springVersion")
    implementation("org.springframework:spring-webmvc:$springVersion")
    implementation("org.springframework.security:spring-security-web:6.3.1")
    implementation("org.springframework.security:spring-security-config:6.3.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:2.0.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.7")
    implementation("com.auth0:java-jwt:4.4.0")
}