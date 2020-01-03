import org.gradle.gradlebuild.unittestandcompile.ModuleType

plugins {
    java
}

repositories {
    maven {
        url = project.uri(file("repo"))
    }
}

dependencies {
    runtimeOnly("org.gradle:dependencyProblem:6.2+")
}

gradlebuildJava {
    moduleType = ModuleType.INTERNAL
}
