import org.springframework.boot.gradle.tasks.run.BootRun
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.node-gradle.node") version "7.1.0"
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.spring") version "2.3.21"
    kotlin("kapt") version "2.3.21"
    id("com.diffplug.spotless") version "7.0.2"
}

spotless {
    kotlin {
        ktlint().editorConfigOverride(
            mapOf("ktlint_standard_package-name" to "disabled")
        )
    }
}

group = "dev.pollito"
version = "0.0.1-SNAPSHOT"

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(24))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    implementation("org.mapstruct:mapstruct:1.6.3")
    kapt("org.mapstruct:mapstruct-processor:1.6.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("com.h2database:h2")
}

kapt {
    includeCompileClasspath = false
}

node {
    download.set(true)
    version.set("24.16.0")
}

val npmRunBuild = tasks.register<com.github.gradle.node.npm.task.NpmTask>("npmRunBuild") {
    args.set(listOf("run", "build"))

    dependsOn(tasks.npmInstall)

    inputs.files(fileTree("node_modules"))
    inputs.files(fileTree("src/main/resources"))
    inputs.file("package.json")
    inputs.file("tsconfig.json")
    inputs.file("webpack.config.js")
    outputs.dir(layout.buildDirectory.dir("resources/main/static"))
}

tasks.processResources {
    dependsOn(npmRunBuild)
}

tasks.named("compileKotlin") {
    dependsOn("spotlessApply")
}

tasks.getByName<BootRun>("bootRun") {
    environment["SPRING_PROFILES_ACTIVE"] = environment["SPRING_PROFILES_ACTIVE"] ?: "local"
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjsr305=strict", "-Xannotation-default-target=param-property", "-jvm-default=no-compatibility"))
        jvmTarget.set(JvmTarget.JVM_24)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
