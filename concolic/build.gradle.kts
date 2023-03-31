plugins {
    id("java")
}

//group = "org.apache.calcite"
//version = "1.32.0-SNAPSHOT"

//repositories {
//    mavenCentral()
//}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
