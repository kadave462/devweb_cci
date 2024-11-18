
plugins {
    application
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.hibernate.orm:hibernate-community-dialects:6.3.1.Final")
    implementation("org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.4.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.h2database:h2:2.2.224")
    //implementation("org.springframework.boot:spring-boot-starter-security")
    //implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    //implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:3.+")
    testImplementation("org.hamcrest:hamcrest:2.2")
    //testImplementation("org.springframework.security:spring-security-test")
}

tasks.test {
    useJUnitPlatform()
}




