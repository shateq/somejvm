import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    java
    antlr
    idea
    application
}

group = "com.somejvm"
version = "1.0-SNAPSHOT"

val vAntlr = "4.11.1"
val vAsm = "9.4"

repositories {
    mavenCentral()
    mavenLocal()
    maven(url = "https://maven.hackery.site/")
}

dependencies {
    antlr("org.antlr:antlr4:$vAntlr")
    implementation("org.antlr:antlr4-runtime:$vAntlr")

    implementation("org.ow2.asm:asm:$vAsm")
    implementation("org.ow2.asm:asm-tree:$vAsm")
    implementation("org.ow2.asm:asm-commons:$vAsm")

    implementation("codes.som.anthony:koffee:8.0.2")
    implementation("codes.som.anthony:koffee-kotlin-reflect-sugar:1.0.0")

    testImplementation(kotlin("test"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    test {
        useJUnitPlatform()
    }

    generateGrammarSource {
        maxHeapSize = "64m"
        arguments = listOf("-visitor", "-package", "com.somejvm.generated")

        outputDirectory = File("src/main/java/com/somejvm/generated/")
    }

    compileJava {
        dependsOn(generateTestGrammarSource)
    }

    clean {
        delete("src/main/java/com/llamalad7/pseudo/generated/")
    }
}

application {
    mainClass.set("com.somejvm.compiler.Mainkt")
}
