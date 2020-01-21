import org.gradle.gradlebuild.unittestandcompile.ModuleType

plugins {
    java
}

repositories {
    maven {
        url = project.uri(file("repo"))
    }
}

// The repo folder contains a full entry for a 6.1 version and a damaged/incomplete entry for 6.2
val requestedVersion = if(project.hasProperty("fail")) {
    "6.2+"
} else {
    "6.2.1"
}

dependencies {
    runtimeOnly("org.gradle:example-dependency:$requestedVersion")
}

gradlebuildJava {
    moduleType = ModuleType.INTERNAL
}
