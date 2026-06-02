plugins {
    java
}

dependencies {
    implementation(project(":model"))
    implementation(libs.jackson.annotations)
    implementation(libs.hibernate.validator)
}