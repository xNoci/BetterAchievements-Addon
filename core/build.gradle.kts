import net.labymod.labygradle.common.extension.LabyModAnnotationProcessorExtension.ReferenceType

dependencies {
    labyProcessor()
    api(project(":api"))

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    // An example of how to add an external dependency that is used by the addon.
    // addonMavenDependency("org.jeasy:easy-random:5.0.0")
}

labyModAnnotationProcessor {
    referenceType = ReferenceType.DEFAULT
}